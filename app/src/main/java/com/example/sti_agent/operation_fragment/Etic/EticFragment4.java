package com.example.sti_agent.operation_fragment.Etic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sti_agent.Constant;
import com.example.sti_agent.MainActivity;
import com.example.sti_agent.Model.Errors.APIError;
import com.example.sti_agent.Model.Errors.ErrorUtils;
import com.example.sti_agent.Model.Etic.EticPolicy;
import com.example.sti_agent.Model.Etic.EticPost.EticPersona;
import com.example.sti_agent.Model.Etic.EticPost.EticPostHead;
import com.example.sti_agent.Model.Etic.EticPost.Trip;
import com.example.sti_agent.Model.Etic.FormSuccessDetail.BuyQuoteFormGetHead_Etic;
import com.example.sti_agent.Model.Etic.FormSuccessDetail.Policy;
import com.example.sti_agent.Model.Etic.Personal_Detail_etic;
import com.example.sti_agent.Model.Etic.Travel_Info;
import com.example.sti_agent.Model.ServiceGenerator;
import com.example.sti_agent.NetworkConnection;
import com.example.sti_agent.R;
import com.example.sti_agent.UserPreferences;
import com.example.sti_agent.adapter.travel_infoListAdapter;
import com.example.sti_agent.fragment.TransactionHistoryFragment;
import com.example.sti_agent.operation_activity.PolicyPaymentActivity;
import com.example.sti_agent.operation_activity.PolicyWalletPayActivity;
import com.example.sti_agent.retrofit_interface.ApiInterface;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import com.google.android.material.textfield.TextInputLayout;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EticFragment4 extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match

    private static final String PRIMARY_KEY = "primaryKey";
    private static final String FOREIGN_KEY = "foreignKey";


    // TODO: Rename and change types of parameters
    private String primaryKey,foreignKey;



    @BindView(R.id.qb_form_layout3)
    FrameLayout mQbFormLayout3;
    @BindView(R.id.step_view)
    com.shuhart.stepview.StepView mStepView;
    @BindView(R.id.personal_info_txt_e4)
    TextView mPersonalInfoTxtE4;
    @BindView(R.id.fabShowTranspDetail_e4)
    FloatingActionButton mFabShowTranspDetailE4;
      @BindView(R.id.inputLayoutPin_e4)
      TextInputLayout mInputLayoutPinE4;
      @BindView(R.id.pin_txt_e4)
      EditText mPinTxtE4;
    @BindView(R.id.modeOfPayment_spinner_e4)
    Spinner mModeOfPaymentSpinnerE4;
    @BindView(R.id.btn_layout4_e4)
    LinearLayout mBtnLayout4E4;
    @BindView(R.id.v_back_btn4_e4)
    Button mVBackBtn4E4;
    @BindView(R.id.v_next_btn4_e4)
    Button mVNextBtn4E4;
    @BindView(R.id.progressbar4_m4)
    AVLoadingIndicatorView mProgressbar4M4;



    private  int currentStep=3;
    Realm realm;
    travel_infoListAdapter travelListAdapter;

    String modeofPaymentString;
    RealmList<Personal_Detail_etic> personal_detail_etics;
    Trip trip;
    String total_quotepric ;
    String policy_num="";
    String total_price="";
    //All Risk return policy
    List<Policy> policy;


    NetworkConnection networkConnection=new NetworkConnection();
    UserPreferences userPreferences;


    public EticFragment4() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     *
     * @return A new instance of fragment Fragment_Dashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static EticFragment4 newInstance(String param1) {
        EticFragment4 fragment = new EticFragment4();
        Bundle args = new Bundle();
        args.putString(PRIMARY_KEY,param1);
        //args.putString(FOREIGN_KEY,param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            primaryKey = getArguments().getString(PRIMARY_KEY);
            //foreignKey = getArguments().getString(FOREIGN_KEY);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_etic4, container, false);
        ButterKnife.bind(this,view);
        //  mStepView next registration step
        mStepView.go(currentStep, true);
        userPreferences=new UserPreferences(getContext());
        
        realm= Realm.getDefaultInstance();

        init();
        modeofPaymentSpinner();
        setViewActions();

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("backPress_KeyCode", "keyCode: " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Log.i("backPress", "onKey Back listener is working!!!");

                    asyncEticPolicy(primaryKey);
                    userPreferences.setTempEticQuotePrice("0.0");
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    return true;
                }
                return false;
            }
        });

        return  view;
    }

    private void modeofPaymentSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.mode_of_payment,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        mModeOfPaymentSpinnerE4.setAdapter(staticAdapter);

        mModeOfPaymentSpinnerE4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String modeofPaymentTypeString = (String) parent.getItemAtPosition(position);

                if(modeofPaymentTypeString.equals("PayStack")){
                    mInputLayoutPinE4.setClickable(false);
                    mInputLayoutPinE4.setVisibility(View.GONE);
                }else if(modeofPaymentTypeString.equals("Wallet")){
                    mInputLayoutPinE4.setClickable(true);
                    mInputLayoutPinE4.setVisibility(View.VISIBLE);
                }else{
                    mInputLayoutPinE4.setClickable(true);
                    mInputLayoutPinE4.setVisibility(View.VISIBLE);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mModeOfPaymentSpinnerE4.getItemAtPosition(0);
                mInputLayoutPinE4.setClickable(true);
                mInputLayoutPinE4.setVisibility(View.VISIBLE);
            }
        });

    }
    private void init(){

        //retrieve data for personal detail first
        EticPolicy eticPolicy;

        eticPolicy=realm.where(EticPolicy.class).equalTo("id",primaryKey).findFirst();
        total_quotepric=eticPolicy.getQuote_price();
        personal_detail_etics=eticPolicy.getPersonal_detail_etic();

        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
        nf.setMaximumFractionDigits(2);
        DecimalFormat df = (DecimalFormat) nf;
        String v_price = df.format(Double.valueOf(total_quotepric));
        
            String individual="Prefix: "+personal_detail_etics.get(0).getPrefix()+"\n"+"First Name: "+personal_detail_etics.get(0).getFirst_name()+"\n"+
                    "Last Name: "+personal_detail_etics.get(0).getLast_name()+"\n"+"Phone Number: "+personal_detail_etics.get(0).getPhone()+"\n"+
                    "Gender: "+personal_detail_etics.get(0).getResident_address()+"\n"+"Mailing Address: "+personal_detail_etics.get(0).getMailing_addr()+"\n"+
                    "Premium: ₦" + v_price;
            mPersonalInfoTxtE4.setText(individual);
            

    }



    //seting onclicks listeners
    private void setViewActions() {

        mVNextBtn4E4.setOnClickListener(this);
        mVBackBtn4E4.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.v_next_btn4_e4:
//                send quote to client and sti
                ValidateForm();
                break;

            case R.id.v_back_btn4_e4:

                mStepView.go(1, true);
                asyncEticPolicy(primaryKey);
                userPreferences.setTempEticQuotePrice("0.0");
                Fragment eticFragment2 = new EticFragment2();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_etic_form_container, eticFragment2);
                ft.commit();

                break;
        }
    }

    private void ValidateForm() {

        if (networkConnection.isNetworkConnected(getContext())) {
            boolean isValid = true;

            if (mPinTxtE4.getText().toString().isEmpty()&&mInputLayoutPinE4.isClickable()) {
                mInputLayoutPinE4.setError("Pin is required!");
                isValid = false;
            }else if (mPinTxtE4.getText().toString().trim().length()!=4 && mInputLayoutPinE4.isClickable()) {
                mInputLayoutPinE4.setError("Invalid pin entered!");
                isValid = false;
            } else if (!mPinTxtE4.getText().toString().trim().equals(userPreferences.getAgentPin()) && mInputLayoutPinE4.isClickable()) {
                mInputLayoutPinE4.setError("Invalid pin entered!");
                isValid = false;
            } else {
                mInputLayoutPinE4.setErrorEnabled(false);
            }
            //Prefix Spinner
            modeofPaymentString = mModeOfPaymentSpinnerE4.getSelectedItem().toString();
            if (modeofPaymentString.equals("Mode of Payment")) {
                showMessage("Select your mode of payment");
                isValid = false;
            }

            if (isValid) {

                //Post Request to Api

                mSubmit();
            }

            return;
        }
        showMessage("No Internet connection discovered!");
    }



    @OnClick(R.id.fabShowTranspDetail_e4)
    public void showBottomVehicleList() {
        View view = getLayoutInflater().inflate(R.layout.fragment_bottom_sheet_vehicles, null);
        final TextView textView = (TextView) view.findViewById(R.id.detail);
        final RecyclerView recycler_vehicles = (RecyclerView) view.findViewById(R.id.recycler_detail);
        final ImageView close = (ImageView) view.findViewById(R.id.close);
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //retrieve data
        final RealmResults<Travel_Info> results;
        //String title;
        results=realm.where(Travel_Info.class).findAll();

        if(results==null){
            showMessage("Result is null");
        }

        //Bind
        travelListAdapter=new travel_infoListAdapter(results,getActivity().getBaseContext());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity().getBaseContext(),RecyclerView.VERTICAL,false);
        recycler_vehicles.setLayoutManager(linearLayoutManager);
        recycler_vehicles.setAdapter(travelListAdapter);
        


        dialog.setContentView(view);
        dialog.show();
    }

    private void mSubmit() {

        mBtnLayout4E4.setVisibility(View.GONE);
        mProgressbar4M4.setVisibility(View.VISIBLE);
        String pin=mPinTxtE4.getText().toString();


        //retrieve data
        final RealmResults<Travel_Info> results;

        //String title;
        results=realm.where(Travel_Info.class).findAll();


        EticPersona eticPersona =new EticPersona(personal_detail_etics.get(0).getPrefix(),personal_detail_etics.get(0).getFirst_name(),personal_detail_etics.get(0).getLast_name(),personal_detail_etics.get(0).getEmail(),
                "null",personal_detail_etics.get(0).getPhone(),personal_detail_etics.get(0).getGender(),personal_detail_etics.get(0).getResident_address(),
                personal_detail_etics.get(0).getEmployer_name(), personal_detail_etics.get(0).getEmployer_addr(), "null", "null", personal_detail_etics.get(0).getIntnded_start_dateCover(), personal_detail_etics.get(0).getPicture(),
                "null", "null", "null", personal_detail_etics.get(0).getPicture());

        for(int i=0;i<results.size();i++) {
            Travel_Info travel_info=results.get(i);
            trip=new Trip(travel_info.getTrip_duration(),travel_info.getTravel_mode(),travel_info.getDisability(),travel_info.getDisability_details(),
                    travel_info.getPlace_departure(),travel_info.getPlace_arrival(),travel_info.getAddress_country_of_visit());

            Log.i("policyItemLoop",travel_info.getPlace_departure());

        }

        EticPostHead eticPostHead = new EticPostHead(eticPersona, modeofPaymentString, total_quotepric, pin, trip);


        sendPolicy(eticPostHead);

    }

    private void sendPolicy(EticPostHead eticPostHead){


        //get client and call object for request
        ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

        Call<BuyQuoteFormGetHead_Etic> call=client.etic_policy("Token "+userPreferences.getUserToken(),eticPostHead);

        call.enqueue(new Callback<BuyQuoteFormGetHead_Etic>() {
            @Override
            public void onResponse(Call<BuyQuoteFormGetHead_Etic> call, Response<BuyQuoteFormGetHead_Etic> response) {
                Log.i("ResponseCode", String.valueOf(response.code()));

                if(response.code()==400){
                    failed_alert("Check your internet connection");
                    mBtnLayout4E4.setVisibility(View.VISIBLE);
                    mProgressbar4M4.setVisibility(View.GONE);
                    return;
                }else if(response.code()==429){
                    failed_alert("Too many requests on database");
                    mBtnLayout4E4.setVisibility(View.VISIBLE);
                    mProgressbar4M4.setVisibility(View.GONE);
                    return;
                }else if(response.code()==500){
                    failed_alert("Server Error");
                    mBtnLayout4E4.setVisibility(View.VISIBLE);
                    mProgressbar4M4.setVisibility(View.GONE);
                    return;
                }else if(response.code()==401){
                    failed_alert("Unauthorized access, please try login again");
                    mBtnLayout4E4.setVisibility(View.VISIBLE);
                    mProgressbar4M4.setVisibility(View.GONE);
                    return;
                }

                try {
                    if (!response.isSuccessful()) {

                        try{
                            APIError apiError= ErrorUtils.parseError(response);

                            failed_alert("Invalid Entry \n" + apiError.getErrors());
                            Log.i("Invalid EntryK",apiError.getErrors().toString());
                            Log.i("Invalid Entry",response.errorBody().toString());

                        }catch (Exception e){
                            Log.i("InvalidEntry",e.getMessage());
                            Log.i("ResponseError",response.errorBody().string());
                            failed_alert("Failed to Submit, try again\n" + e.getMessage());
                            mBtnLayout4E4.setVisibility(View.VISIBLE);
                            mProgressbar4M4.setVisibility(View.GONE);

                        }
                        mBtnLayout4E4.setVisibility(View.VISIBLE);
                        mProgressbar4M4.setVisibility(View.GONE);
                        return;
                    }

                    policy=response.body().getData().getPolicy();
                    for(int i=0;i<policy.size();i++){
                        policy_num=policy_num.concat("\n"+policy.get(i).getPolicyNumber());
                    }

                    total_price= String.valueOf(response.body().getData().getUnitPrice());
                    String ref= response.body().getData().getTransactions().get(0).getReference();

                    Log.i("policyNum", policy_num);
                    Log.i("totalPrice", total_price);

                    if (total_price != null) {
                        userPreferences.setTempEticQuotePrice("0.0");

                        mBtnLayout4E4.setVisibility(View.VISIBLE);
                        mProgressbar4M4.setVisibility(View.GONE);

                        asyncEticPolicy(primaryKey);
                        if(modeofPaymentString.equals("PayStack")) {
                            Intent intent = new Intent(getContext(), PolicyPaymentActivity.class);
                            intent.putExtra(Constant.TOTAL_PRICE, total_price);
                            intent.putExtra(Constant.POLICY_NUM, policy_num);
                            intent.putExtra(Constant.POLICY_TYPE, "travel");
                            intent.putExtra(Constant.REF, ref);
                            startActivity(intent);
                            getActivity().finish();

                        }else{
                            Intent intent = new Intent(getContext(), PolicyWalletPayActivity.class);
                            intent.putExtra(Constant.TOTAL_PRICE, total_price);
                            intent.putExtra(Constant.POLICY_NUM, policy_num);
                            intent.putExtra(Constant.POLICY_TYPE, "travel");
                            intent.putExtra(Constant.REF, ref);
                            startActivity(intent);
                            getActivity().finish();
                        }

                    } else {
                        incomplete_alert(String.valueOf(response.body()));
                        mBtnLayout4E4.setVisibility(View.VISIBLE);
                        mProgressbar4M4.setVisibility(View.GONE);


                    }
                }catch (Exception e){
                    incomplete_alert("Transaction not complete, check your internet and click continue\n" + e.getMessage());
                    Log.i("policyResponse", e.getMessage());


                }

            }

            @Override
            public void onFailure(Call<BuyQuoteFormGetHead_Etic> call, Throwable t) {
                failed_alert("Submission Failed, TRY AGAIN \n" + t.getMessage());
                Log.i("GEtError", t.getMessage());

                mBtnLayout4E4.setVisibility(View.VISIBLE);
                mProgressbar4M4.setVisibility(View.GONE);

            }
        });


    }


    private void failed_alert(String msg) {

        new AlertDialog.Builder(getContext())
                .setIcon(R.drawable.ic_error_outline_black_24dp)
                .setTitle("Error !")
                .setMessage(msg)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();

                    }
                })
                .show();

    }

    private void incomplete_alert(String msg) {

        new AlertDialog.Builder(getContext())
                .setTitle("Error !")
                .setIcon(R.drawable.ic_error_outline_black_24dp)
                .setMessage(msg)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                        asyncEticPolicy(primaryKey);
                        userPreferences.setTempEticQuotePrice("0.0");
                        Fragment transactionHistoryFragment = new TransactionHistoryFragment();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_etic_form_container, transactionHistoryFragment);
                        ft.commit();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                        asyncEticPolicy(primaryKey);
                        userPreferences.setTempEticQuotePrice("0.0");
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }
                })
                .show();

    }


    //To Delete vehicle
    private void asyncEticPolicy(final String id){
        AsyncTask<Void, Void, Void> remoteItem =new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                realm=Realm.getDefaultInstance();

                EticPolicy eticPolicy=realm.where(EticPolicy.class).equalTo("id",id).findFirst();
                if(eticPolicy !=null){
                    realm.beginTransaction();
                    eticPolicy.deleteFromRealm();
                    realm.commitTransaction();
                }

                RealmResults<Travel_Info> travel_infos=realm.where(Travel_Info.class).findAll();
                if(travel_infos!=null){
                    realm.beginTransaction();
                    travel_infos.deleteAllFromRealm();
                    realm.commitTransaction();
                }else {
                    showMessage("Error in deletion");
                }
                realm.close();
                return null;
            }
        };
        remoteItem.execute();
    }



    private void showMessage(String s) {
        Snackbar.make(mQbFormLayout3, s, Snackbar.LENGTH_SHORT).show();
    }



    public  boolean isNetworkConnected() {
        Context context = getContext();
        final ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            if (Build.VERSION.SDK_INT < 23) {
                final NetworkInfo ni = cm.getActiveNetworkInfo();

                if (ni != null) {
                    return (ni.isConnected() && (ni.getType() == ConnectivityManager.TYPE_WIFI || ni.getType() == ConnectivityManager.TYPE_MOBILE));
                }
            } else {
                final Network n = cm.getActiveNetwork();

                if (n != null) {
                    final NetworkCapabilities nc = cm.getNetworkCapabilities(n);

                    return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
                }
            }
        }

        return false;
    }


}
