package com.sti.sti_agent.Detail_activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.sti.sti_agent.Model.ServiceGenerator;
import com.sti.sti_agent.NetworkConnection;
import com.sti.sti_agent.R;
import com.sti.sti_agent.UserPreferences;
import com.sti.sti_agent.retrofit_interface.ApiInterface;
import com.google.android.material.card.MaterialCardView;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyEticDetail extends AppCompatActivity {

    /** ButterKnife Code **/
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.status_bg)
    ImageView mStatusBg;
    @BindView(R.id.status)
    TextView mStatus;
    @BindView(R.id.payment_status)
    TextView mPaymentStatus;
    @BindView(R.id.policy_num)
    TextView mPolicyNum;
    @BindView(R.id.policy_type)
    TextView mPolicyType;
    @BindView(R.id.trip_duratn)
    TextView mTripDuratn;
    @BindView(R.id.travel_mode)
    TextView mTravelMode;
    @BindView(R.id.departure_plc)
    TextView mDeparturePlc;
    @BindView(R.id.arrival_plc)
    TextView mArrivalPlc;
    @BindView(R.id.addr_country_visit)
    TextView mAddrCountryVisit;
    @BindView(R.id.price)
    TextView mPrice;
    @BindView(R.id.start_date)
    TextView mStartDate;
    @BindView(R.id.end_date)
    TextView mEndDate;
    /*@BindView(R.id.payment_ref)
    TextView mPaymentRef;*/
    /*@BindView(R.id.renew_btn)
    TextView mRenew;*/
    @BindView(R.id.progress)
    AVLoadingIndicatorView mProgress;
    @BindView(R.id.user_policy_btn)
    MaterialCardView user_policy_btn;
    /** ButterKnife Code **/

    Animation slide_front_left, blink, slide_front_right;
    double roundOff;
    String policynum;

    private UserPreferences userPreferences;
    NetworkConnection networkConnection=new NetworkConnection();
    ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etic_detail);
        ButterKnife.bind(this);
        userPreferences=new UserPreferences(this);
        applyToolbarChildren("Policy Detail");

        Intent intent=getIntent();
        policynum = intent.getStringExtra("policynum");
        String trip_duration=intent.getStringExtra("trip_duration");
        String departure_plc=intent.getStringExtra("departure_plc");
        String policy_type=intent.getStringExtra("policy_type");
        String start_date=intent.getStringExtra("start_date");
        String end_date=intent.getStringExtra("end_date");
        String arrive_place=intent.getStringExtra("arrive_place");
        String policy_price=intent.getStringExtra("policy_price");
        String payment_status=intent.getStringExtra("payment_status");
        String status=intent.getStringExtra("status");
        String payment_reference=intent.getStringExtra("payment_reference");
        String travel_mode=intent.getStringExtra("travel_mode");
        String addr_country_visit=intent.getStringExtra("addr_country_visit");


        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
        nf.setMaximumFractionDigits(2);
        DecimalFormat df = (DecimalFormat) nf;
        String v_price = "₦" + df.format(Double.valueOf(policy_price));


        mTripDuratn.setText(trip_duration);
        mStatus.setText(status);
        mPaymentStatus.setText(payment_status);
        mPolicyNum.setText(policynum);
        mDeparturePlc.setText(departure_plc);
        mPolicyType.setText(policy_type);
        mStartDate.setText(start_date);
        mEndDate.setText(end_date);
        mArrivalPlc.setText(arrive_place);
        mPrice.setText(v_price);
        mPaymentStatus.setText(payment_status);
        mStatus.setText(status);
        //mPaymentRef.setText(payment_reference);
        mTravelMode.setText(travel_mode);
        mAddrCountryVisit.setText(addr_country_visit);


        slide_front_right = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_to_right);
        user_policy_btn.startAnimation(slide_front_right);


        slide_front_left = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_from_left);

        blink = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink);

        //start animation
        mStatusBg.startAnimation(slide_front_left);

        mStatus.startAnimation(blink);


        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date strDate = null;
        try {
            strDate = sdf.parse(end_date);
            //if current date is greater then make renew visible

            if (System.currentTimeMillis() > strDate.getTime()) {
                mRenew.setVisibility(View.VISIBLE);

                mRenew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if(networkConnection.isNetworkConnected(getBaseContext())){
                        mRenew.setVisibility(View.GONE);
                        mProgress.setVisibility(View.VISIBLE);
                        sendEticData();
                        //get client and call object for request




                        }else{
                            showShortMsg("No Internet Connection");
                        }



                    }
                });



            }


        } catch (ParseException e) {
            e.printStackTrace();
            showShortMsg("Error: "+e.getMessage());
        }*/


    }


    /*private void sendEticData(){

        Call<QouteHeadEtic> call=client.etic_quote("Token "+userPreferences.getUserToken(), 2000);

        call.enqueue(new Callback<QouteHeadEtic>() {
            @Override
            public void onResponse(Call<QouteHeadEtic> call, Response<QouteHeadEtic> response) {
                Log.i("ResponseCode", String.valueOf(response.code()));

                if(response.code()==406){
                    showShortMsg("Error! Wrong Value provided!");
                    mRenew.setVisibility(View.VISIBLE);
                    mProgress.setVisibility(View.GONE);
                    return;
                }

                if(response.code()==400){
                    showShortMsg("Check your internet connection");
                    mRenew.setVisibility(View.VISIBLE);
                    mProgress.setVisibility(View.GONE);
                    return;
                }else if(response.code()==429){
                    showShortMsg("Too many requests on database");
                    mRenew.setVisibility(View.VISIBLE);
                    mProgress.setVisibility(View.GONE);
                    return;
                }else if(response.code()==500){
                    showShortMsg("Server Error");
                    mRenew.setVisibility(View.VISIBLE);
                    mProgress.setVisibility(View.GONE);
                    return;
                }else if(response.code()==401){
                    showShortMsg("Unauthorized access, please try login again");
                    mRenew.setVisibility(View.VISIBLE);
                    mProgress.setVisibility(View.GONE);
                    return;
                }

                try {
                    if (!response.isSuccessful()) {

                        try{
                            APIError apiError= ErrorUtils.parseError(response);

                            showShortMsg("Invalid Entry: "+apiError.getErrors());
                            Log.i("Invalid EntryK",apiError.getErrors().toString());
                            Log.i("Invalid Entry",response.errorBody().toString());

                        }catch (Exception e){
                            Log.i("InvalidEntry",e.getMessage());
                            Log.i("ResponseError",response.errorBody().string());
                            showShortMsg("Failed to Fetch Quote"+e.getMessage());
                            mRenew.setVisibility(View.VISIBLE);
                            mProgress.setVisibility(View.GONE);

                        }
                        mRenew.setVisibility(View.VISIBLE);
                        mProgress.setVisibility(View.GONE);
                        return;
                    }

                    String quote_price=response.body().getData().getPrice();

                    roundOff = Math.round(Double.valueOf(quote_price)*100)/100.00;

                    Log.i("quote_price", String.valueOf(roundOff));
                    Call<RenewPolicyGet> call2=client.renew_policy("Token "+userPreferences.getUserToken(),policynum, String.valueOf(roundOff),"paystack");

                    call2.enqueue(new Callback<RenewPolicyGet>() {
                        @Override
                        public void onResponse(Call<RenewPolicyGet> call, Response<RenewPolicyGet> response) {
                            Log.i("ResponseCode", String.valueOf(response.code()));

                            if(response.code()==400){
                                showShortMsg("Check your internet connection");
                                mRenew.setVisibility(View.VISIBLE);
                                mProgress.setVisibility(View.GONE);
                                return;
                            }else if(response.code()==429){
                                showShortMsg("Too many requests on database");
                                mRenew.setVisibility(View.VISIBLE);
                                mProgress.setVisibility(View.GONE);
                                return;
                            }else if(response.code()==500){
                                showShortMsg("Server Error");
                                mRenew.setVisibility(View.VISIBLE);
                                mProgress.setVisibility(View.GONE);
                                return;
                            }else if(response.code()==401){
                                showShortMsg("Unauthorized access, please try login again");
                                mRenew.setVisibility(View.VISIBLE);
                                mProgress.setVisibility(View.GONE);
                                return;
                            }
                            try {
                                if (!response.isSuccessful()) {

                                    try{
                                        APIError apiError= ErrorUtils.parseError(response);

                                        showShortMsg("Invalid Entry: "+apiError.getErrors());
                                        Log.i("Invalid EntryK",apiError.getErrors().toString());
                                        Log.i("Invalid Entry",response.errorBody().toString());

                                    }catch (Exception e){
                                        Log.i("InvalidEntry",e.getMessage());
                                        Log.i("ResponseError",response.errorBody().string());
                                        showShortMsg("Failed to Renew"+e.getMessage());
                                        mRenew.setVisibility(View.VISIBLE);
                                        mProgress.setVisibility(View.GONE);

                                    }
                                    mRenew.setVisibility(View.VISIBLE);
                                    mProgress.setVisibility(View.GONE);
                                    return;
                                }


                                String amount=response.body().getAmount();
                                String policyNumber=response.body().getPolicyNumber();
                                String reference=response.body().getReference();


                                Intent i = new Intent(MyEticDetail.this, PolicyPaymentActivity.class);
                                i.putExtra(Constant.POLICY_NUM, policyNumber);
                                i.putExtra(Constant.TOTAL_PRICE, amount);
                                i.putExtra(Constant.POLICY_TYPE, "travel");
                                i.putExtra(Constant.REF, reference);
                                startActivity(i);

                                mRenew.setVisibility(View.VISIBLE);
                                mProgress.setVisibility(View.GONE);

                            }catch (Exception e){
                                Log.i("PolicyRenewError", e.getMessage());
                                mRenew.setVisibility(View.VISIBLE);
                                mProgress.setVisibility(View.GONE);
                            }

                        }
                        @Override
                        public void onFailure(Call<RenewPolicyGet> call, Throwable t) {
                            showShortMsg("Renewed Failed "+t.getMessage());
                            Log.i("GetError",t.getMessage());
                            mRenew.setVisibility(View.VISIBLE);
                            mProgress.setVisibility(View.GONE);
                        }
                    });


                }catch (Exception e){
                    Log.i("policyResponse", e.getMessage());
                    mRenew.setVisibility(View.VISIBLE);
                    mProgress.setVisibility(View.GONE);
                }

            }
            @Override
            public void onFailure(Call<QouteHeadEtic> call, Throwable t) {
                showShortMsg("Submission Failed "+t.getMessage());
                Log.i("GEtError",t.getMessage());
                mRenew.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.GONE);
            }
        });

    }*/
    
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



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

}
