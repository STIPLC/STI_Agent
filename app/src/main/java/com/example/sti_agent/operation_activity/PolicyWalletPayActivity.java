package com.example.sti_agent.operation_activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sti_agent.Constant;
import com.example.sti_agent.MainActivity;
import com.example.sti_agent.Model.Errors.APIError;
import com.example.sti_agent.Model.Errors.ErrorUtils;
import com.example.sti_agent.Model.ServiceGenerator;
import com.example.sti_agent.Model.WalletModel.WalletDebit.WalletGetHead;
import com.example.sti_agent.NetworkConnection;
import com.example.sti_agent.R;
import com.example.sti_agent.SignIn;
import com.example.sti_agent.UserPreferences;
import com.example.sti_agent.retrofit_interface.ApiInterface;
import com.google.android.material.textfield.TextInputLayout;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PolicyWalletPayActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolBar;
    


    @BindView(R.id.pay_button)
    Button payButton;
    @BindView(R.id.progress)
    AVLoadingIndicatorView mProgress;



    ProgressDialog dialog;
    int balance;

    private UserPreferences userPreferences;
    NetworkConnection networkConnection=new NetworkConnection();
    //get client and call object for request
    ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

    @BindView(R.id.amount)
    TextView mAmount;
    @BindView(R.id.policy_num)
    TextView mPolicyNum;

    String amount="",poly_num="",ref="";
    String provider_ref="",poliy_type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_walletpay);
        ButterKnife.bind(this);
        userPreferences=new UserPreferences(this);
        applyToolbarChildren("Payment");

        

        Intent intent=getIntent();
        amount=intent.getStringExtra(Constant.TOTAL_PRICE);
        poly_num=intent.getStringExtra(Constant.POLICY_NUM);
        ref=intent.getStringExtra(Constant.REF);
        poliy_type=intent.getStringExtra(Constant.POLICY_TYPE);

        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
        nf.setMaximumFractionDigits(2);
        DecimalFormat df = (DecimalFormat) nf;
        String v_price = df.format(Double.valueOf(amount));


        String amt = getString(R.string.naira_currency) + " " + v_price;
        poly_num=getString(R.string.policy_no)+": "+poly_num;
        mAmount.setText(amt);
        mPolicyNum.setText(poly_num);

        if(ref!=null || !ref.equals("")) {
            payButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (networkConnection.isNetworkConnected(getBaseContext())) {

                        String descr = "Withdrawal";
                        String transaction_type = "DEBIT";
                        String wallet_uid=ref;

                        long wallet_balance=(long) Math.round(Double.parseDouble(userPreferences.getWalletBalance()));
                        long total_policy_amt=(long) Math.round(Double.parseDouble(amount));

                        if (wallet_balance>=total_policy_amt) {
                            payButton.setEnabled(false);
                            //long charge=wallet_balance-total_policy_amt;
                            chargeWallet(total_policy_amt);

                        } else {
                            charge_error();
                        }

                    } else {
                        showShortMsg("Kindly, connect to the internet!");
                    }


                }
            });
        }else{
            showShortMsg("Payment Reference is Null");
        }

    }

    private void applyToolbarChildren(String title) {
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle(title);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);
        //setting Elevation for > API 21
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolBar.setElevation(10f);
        }

    }




    private void showShortMsg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    private void chargeWallet(long charge) {
        dialog = new ProgressDialog(PolicyWalletPayActivity.this);
        dialog.setMessage("Performing transaction... please wait");
        dialog.setCancelable(false);
        dialog.show();


        Call<WalletGetHead> call=client.debit_wallet("Token "+userPreferences.getUserToken(),charge,"Withdrawal","DEBIT",ref);

        call.enqueue(new Callback<WalletGetHead>() {
            @Override
            public void onResponse(Call<WalletGetHead> call, Response<WalletGetHead> response) {
                Log.i("ResponseCode", String.valueOf(response.code()));

                if(response.code()==400){
                    showShortMsg("Check your internet connection");
                    payButton.setVisibility(View.VISIBLE);
                    mProgress.setVisibility(View.GONE);
                    dialog.dismiss();
                    return;
                }else if(response.code()==429){
                    showShortMsg("Too many requests on database");
                    payButton.setVisibility(View.VISIBLE);
                    mProgress.setVisibility(View.GONE);
                    dialog.dismiss();
                    return;
                }else if(response.code()==500){
                    showShortMsg("Server Error");
                    payButton.setVisibility(View.VISIBLE);
                    mProgress.setVisibility(View.GONE);
                    dialog.dismiss();
                    return;
                }else if(response.code()==401){
                    showShortMsg("Unauthorized access, please try login again");
                    payButton.setVisibility(View.VISIBLE);
                    mProgress.setVisibility(View.GONE);
                    dialog.dismiss();
                    return;
                }
                try {
                    if (!response.isSuccessful()) {

                        try{
                            APIError apiError= ErrorUtils.parseError(response);

                            showShortMsg("Invalid Entry: "+apiError.getErrors());
                            Log.i("Invalid EntryK",apiError.getErrors().toString());
                            Log.i("Invalid Entry",response.errorBody().toString());
                            payButton.setVisibility(View.VISIBLE);
                            mProgress.setVisibility(View.GONE);
                            dialog.dismiss();

                        }catch (Exception e){
                            Log.i("InvalidEntry",e.getMessage());
                            Log.i("ResponseError",response.errorBody().string());
                            showShortMsg("Failed to Update"+e.getMessage());
                            payButton.setVisibility(View.VISIBLE);
                            mProgress.setVisibility(View.GONE);
                            dialog.dismiss();

                        }
                        payButton.setVisibility(View.VISIBLE);
                        mProgress.setVisibility(View.GONE);
                        dialog.dismiss();
                        return;
                    }

                     balance=response.body().getData().getBalance();

                    updatePayment();


                }catch (Exception e){
                    Log.i("PaymentUpdateError", e.getMessage());
                    payButton.setVisibility(View.VISIBLE);
                    mProgress.setVisibility(View.GONE);
                    dialog.dismiss();
                }

            }
            @Override
            public void onFailure(Call<WalletGetHead> call, Throwable t) {
                showShortMsg("Update Failed "+t.getMessage());
                Log.i("GEtError",t.getMessage());
                payButton.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });





    }


    private void gotoDashboard() {
        startActivity(new Intent(PolicyWalletPayActivity.this, MainActivity.class));
        this.finish();
        Toast.makeText(getApplicationContext(), "Transaction Successful, Check Your Policy", Toast.LENGTH_LONG).show();


    }


    private void updatePayment(){
        Call<ResponseBody> call=client.update_payment("Token "+userPreferences.getUserToken(),ref,ref,"Success",poliy_type);


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("ResponseCode", String.valueOf(response.code()));

                if(response.code()==400){
                    showShortMsg("Check your internet connection");
                    payButton.setVisibility(View.VISIBLE);
                    mProgress.setVisibility(View.GONE);
                    dialog.dismiss();
                    return;
                }else if(response.code()==429){
                    showShortMsg("Too many requests on database");
                    payButton.setVisibility(View.VISIBLE);
                    mProgress.setVisibility(View.GONE);
                    dialog.dismiss();
                    return;
                }else if(response.code()==500){
                    showShortMsg("Server Error");
                    payButton.setVisibility(View.VISIBLE);
                    mProgress.setVisibility(View.GONE);
                    dialog.dismiss();
                    return;
                }else if(response.code()==401){
                    showShortMsg("Unauthorized access, please try login again");
                    payButton.setVisibility(View.VISIBLE);
                    mProgress.setVisibility(View.GONE);
                    dialog.dismiss();
                    return;
                }
                try {
                    if (!response.isSuccessful()) {

                        try{
                            APIError apiError= ErrorUtils.parseError(response);

                            showShortMsg("Invalid Entry: "+apiError.getErrors());
                            Log.i("Invalid EntryK",apiError.getErrors().toString());
                            Log.i("Invalid Entry",response.errorBody().toString());
                            payButton.setVisibility(View.VISIBLE);
                            mProgress.setVisibility(View.GONE);
                            dialog.dismiss();

                        }catch (Exception e){
                            Log.i("InvalidEntry",e.getMessage());
                            Log.i("ResponseError",response.errorBody().string());
                            showShortMsg("Failed to Update"+e.getMessage());
                            payButton.setVisibility(View.VISIBLE);
                            mProgress.setVisibility(View.GONE);
                            dialog.dismiss();

                        }
                        payButton.setVisibility(View.VISIBLE);
                        mProgress.setVisibility(View.GONE);
                        dialog.dismiss();
                        return;
                    }

                    userPreferences.setWalletBalance(String.valueOf(balance));

                    payButton.setVisibility(View.VISIBLE);
                    mProgress.setVisibility(View.GONE);
                    dialog.dismiss();

                    gotoDashboard();

                }catch (Exception e){
                    Log.i("PaymentUpdateError", e.getMessage());
                    payButton.setVisibility(View.VISIBLE);
                    mProgress.setVisibility(View.GONE);
                    dialog.dismiss();
                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showShortMsg("Update Failed "+t.getMessage());
                Log.i("GEtError",t.getMessage());
                payButton.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });


    }


    private void charge_error() {

        new AlertDialog.Builder(this)
                .setTitle("Transaction Error !")
                .setIcon(R.drawable.ic_error_outline_black_24dp)
                .setMessage("Insufficient fund in wallet, go to transaction history to complete your transaction")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                        startActivity(new Intent(PolicyWalletPayActivity.this, MainActivity.class));
                        PolicyWalletPayActivity.this.finish();
                    }
                })
                .show();

    }
  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(PolicyWalletPayActivity.this, MainActivity.class));
        this.finish();
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PolicyWalletPayActivity.this, MainActivity.class));
        this.finish();
        super.onBackPressed();
    }
}
