package com.example.sti_agent.Detail_activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sti_agent.Constant;
import com.example.sti_agent.Model.AllRisk.QouteHeadAllrisk;
import com.example.sti_agent.Model.Errors.APIError;
import com.example.sti_agent.Model.Errors.ErrorUtils;
import com.example.sti_agent.Model.RenewPolicyGet;
import com.example.sti_agent.Model.ServiceGenerator;
import com.example.sti_agent.NetworkConnection;
import com.example.sti_agent.R;
import com.example.sti_agent.UserPreferences;
import com.example.sti_agent.operation_activity.PolicyPaymentActivity;
import com.example.sti_agent.retrofit_interface.ApiInterface;
import com.google.android.material.card.MaterialCardView;

import com.wang.avi.AVLoadingIndicatorView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAllRiskDetail extends AppCompatActivity {

    /** ButterKnife Code **/
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.status_bg)
    ImageView mStatusBg;

    @BindView(R.id.user_policy_btn)
    MaterialCardView user_policy_btn;

    @BindView(R.id.status)
    TextView mStatus;
    @BindView(R.id.payment_status)
    TextView mPaymentStatus;
    @BindView(R.id.policy_num)
    TextView mPolicyNum;
    @BindView(R.id.policy_type)
    TextView mPolicyType;
    @BindView(R.id.item)
    TextView mItem;
    @BindView(R.id.value)
    TextView mValue;
    @BindView(R.id.period)
    TextView mPeriod;
    @BindView(R.id.start_date)
    TextView mStartDate;
    @BindView(R.id.end_date)
    TextView mEndDate;
    @BindView(R.id.price)
    TextView mPrice;
    @BindView(R.id.imei_num)
    TextView mImeiNum;
    @BindView(R.id.serial_num)
    TextView mSerialNum;
    /* @BindView(R.id.payment_ref)
     TextView mPaymentRef;*/
    @BindView(R.id.renew_btn)
    TextView mRenew;
    @BindView(R.id.progress)
    AVLoadingIndicatorView mProgress;

    /** ButterKnife Code **/

    Animation slide_front_left, blink, slide_front_right;

    private UserPreferences userPreferences;
    NetworkConnection networkConnection=new NetworkConnection();
    String value, policynum;
    double roundOff;

    ApiInterface client = ServiceGenerator.createService(ApiInterface.class);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allrisk_detail);
        ButterKnife.bind(this);
        userPreferences=new UserPreferences(this);
        applyToolbarChildren("Policy Detail");


        Intent intent=getIntent();

        String Item=intent.getStringExtra("Item");
        value = intent.getStringExtra("value");
        String period=intent.getStringExtra("period");
        policynum = intent.getStringExtra("policynum");
        String policy_type=intent.getStringExtra("policy_type");
        String start_date=intent.getStringExtra("start_date");
        String end_date=intent.getStringExtra("end_date");
        String policy_price=intent.getStringExtra("policy_price");
        String payment_status=intent.getStringExtra("payment_status");
        String status=intent.getStringExtra("status");
        String payment_reference=intent.getStringExtra("payment_reference");
        String imei=intent.getStringExtra("imei");
        String serial = intent.getStringExtra("serial");

            NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
            nf.setMaximumFractionDigits(2);
            DecimalFormat df = (DecimalFormat) nf;
            String v_price = "₦" + df.format(Double.valueOf(policy_price));


        mItem.setText(Item);
        mStatus.setText(status);
        mValue.setText(value);
        mPolicyNum.setText(policynum);
        String period_txt = period + "Days";
        mPeriod.setText(period_txt);
        mPolicyType.setText(policy_type);
        mStartDate.setText(start_date);
        mEndDate.setText(end_date);
        mPrice.setText(v_price);
        mPaymentStatus.setText(payment_status);
        //mPaymentRef.setText(payment_reference);
        mImeiNum.setText(imei);
        mSerialNum.setText(serial);

        slide_front_left = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_from_left);


        blink = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink);

        //start animation
        mStatusBg.startAnimation(slide_front_left);

        mStatus.startAnimation(blink);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date strDate = null;
        try {
            strDate = sdf.parse(end_date);
            //if current date is greater then make renew visible

            if (System.currentTimeMillis() > strDate.getTime()) {
                mRenew.setVisibility(View.VISIBLE);

                mRenew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (networkConnection.isNetworkConnected(getBaseContext())) {

                            mRenew.setVisibility(View.GONE);
                            mProgress.setVisibility(View.VISIBLE);
                            sendAllRiskData();

                        } else {
                            showShortMsg("No Internet Connection");
                        }


                    }

                });


            }


        } catch (ParseException e) {
            e.printStackTrace();
            showShortMsg("Error: " + e.getMessage());
        }


    }

    public void sendAllRiskData() {


        //get client and call object for request


        Call<QouteHeadAllrisk> call = client.allrisk_quote("Token " + userPreferences.getUserToken(), value);

        call.enqueue(new Callback<QouteHeadAllrisk>() {
            @Override
            public void onResponse(Call<QouteHeadAllrisk> call, Response<QouteHeadAllrisk> response) {
                Log.i("ResponseCode", String.valueOf(response.code()));
                if (response.code() == 400) {
                    showShortMsg("Check your internet connection");
                    return;
                } else if (response.code() == 429) {
                    showShortMsg("Too many requests on database");
                    return;
                } else if (response.code() == 500) {
                    showShortMsg("Server Error");
                    return;
                } else if (response.code() == 401) {
                    showShortMsg("Unauthorized access, please try login again");
                    return;
                }

                try {
                    if (!response.isSuccessful()) {

                        try {
                            APIError apiError = ErrorUtils.parseError(response);

                            showShortMsg("Invalid Entry: " + apiError.getErrors());
                            Log.i("Invalid EntryK", apiError.getErrors().toString());
                            Log.i("Invalid Entry", response.errorBody().toString());

                        } catch (Exception e) {
                            Log.i("InvalidEntry", e.getMessage());
                            Log.i("ResponseError", response.errorBody().string());
                            showShortMsg("Failed to Fetch Quote" + e.getMessage());
                            mRenew.setVisibility(View.VISIBLE);
                            mProgress.setVisibility(View.GONE);

                        }
                        mRenew.setVisibility(View.VISIBLE);
                        mProgress.setVisibility(View.GONE);
                        return;
                    }

                    long quote_price = response.body().getData().getPrice();

                    roundOff = Math.round(quote_price * 100) / 100.00;

                    Log.i("quote_price", String.valueOf(quote_price));
                    //get client and call object for request
                    //ApiInterface client = ServiceGenerator.createService(ApiInterface.class);


                    Call<RenewPolicyGet> call2 = client.renew_policy("Token " + userPreferences.getUserToken(), policynum, String.valueOf(roundOff), "paystack");

                    call2.enqueue(new Callback<RenewPolicyGet>() {
                        @Override
                        public void onResponse(Call<RenewPolicyGet> call, Response<RenewPolicyGet> response) {
                            Log.i("ResponseCode", String.valueOf(response.code()));

                            if (response.code() == 400) {
                                showShortMsg("Check your internet connection");
                                mRenew.setVisibility(View.VISIBLE);
                                mProgress.setVisibility(View.GONE);
                                return;
                            } else if (response.code() == 429) {
                                showShortMsg("Too many requests on database");
                                mRenew.setVisibility(View.VISIBLE);
                                mProgress.setVisibility(View.GONE);
                                return;
                            } else if (response.code() == 500) {
                                showShortMsg("Server Error");
                                mRenew.setVisibility(View.VISIBLE);
                                mProgress.setVisibility(View.GONE);
                                return;
                            } else if (response.code() == 401) {
                                showShortMsg("Unauthorized access, please try login again");
                                mRenew.setVisibility(View.VISIBLE);
                                mProgress.setVisibility(View.GONE);
                                return;
                            }
                            try {
                                if (!response.isSuccessful()) {

                                    try {
                                        APIError apiError = ErrorUtils.parseError(response);

                                        showShortMsg("Invalid Entry: " + apiError.getErrors());
                                        Log.i("Invalid EntryK", apiError.getErrors().toString());
                                        Log.i("Invalid Entry", response.errorBody().toString());

                                    } catch (Exception e) {
                                        Log.i("InvalidEntry", e.getMessage());
                                        Log.i("ResponseError", response.errorBody().string());
                                        showShortMsg("Failed to Renew" + e.getMessage());
                                        mRenew.setVisibility(View.VISIBLE);
                                        mProgress.setVisibility(View.GONE);

                                    }
                                    mRenew.setVisibility(View.VISIBLE);
                                    mProgress.setVisibility(View.GONE);
                                    return;
                                }


                                String amount = response.body().getAmount();
                                String policyNumber = response.body().getPolicyNumber();
                                String reference = response.body().getReference();


                                Intent i = new Intent(MyAllRiskDetail.this, PolicyPaymentActivity.class);
                                i.putExtra(Constant.POLICY_NUM, policyNumber);
                                i.putExtra(Constant.TOTAL_PRICE, amount);
                                i.putExtra(Constant.POLICY_TYPE, "all_risk");
                                i.putExtra(Constant.REF, reference);
                                startActivity(i);

                                mRenew.setVisibility(View.VISIBLE);
                                mProgress.setVisibility(View.GONE);

                            } catch (Exception e) {
                                Log.i("PolicyRenewError", e.getMessage());
                                mRenew.setVisibility(View.VISIBLE);
                                mProgress.setVisibility(View.GONE);
                            }

                        }

                        @Override
                        public void onFailure(Call<RenewPolicyGet> call, Throwable t) {
                            showShortMsg("Renewed Failed " + t.getMessage());
                            Log.i("GetError", t.getMessage());
                            mRenew.setVisibility(View.VISIBLE);
                            mProgress.setVisibility(View.GONE);
                        }


                    });


                } catch (Exception e) {
                    Log.i("policyResponse", e.getMessage());
                    mRenew.setVisibility(View.VISIBLE);
                    mProgress.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<QouteHeadAllrisk> call, Throwable t) {
                showShortMsg("Submission Failed " + t.getMessage());
                Log.i("GEtError", t.getMessage());
                mRenew.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.GONE);
            }
        });

    }


    private void applyToolbarChildren(String title) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);
        //setting Elevation for > API 21
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolbar.setElevation(10f);
        }

    }




    private void showShortMsg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    private void renewPolicy(){


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

}
