package com.sti.sti_agent.Detail_activities;

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

import com.sti.sti_agent.Constant;
import com.sti.sti_agent.Model.Errors.APIError;
import com.sti.sti_agent.Model.Errors.ErrorUtils;
import com.sti.sti_agent.Model.RenewPolicyGet;
import com.sti.sti_agent.Model.ServiceGenerator;
import com.sti.sti_agent.Model.Vehicle.Quote.PostVehicleData;
import com.sti.sti_agent.Model.Vehicle.Quote.QouteHead;
import com.sti.sti_agent.NetworkConnection;
import com.sti.sti_agent.R;
import com.sti.sti_agent.UserPreferences;
import com.sti.sti_agent.operation_activity.PolicyPaymentActivity;
import com.sti.sti_agent.retrofit_interface.ApiInterface;
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

public class MyVehicleDetail extends AppCompatActivity {

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
    @BindView(R.id.cover_type)
    TextView mCoverType;
    @BindView(R.id.price)
    TextView mPrice;
    @BindView(R.id.start_date)
    TextView mStartDate;
    @BindView(R.id.end_date)
    TextView mEndDate;
    @BindView(R.id.vehicle_make)
    TextView mVehicleMake;
    @BindView(R.id.body_type)
    TextView mBodyType;
    @BindView(R.id.reg_num)
    TextView mRegNum;
    @BindView(R.id.period)
    TextView mPeriod;
    @BindView(R.id.chasis_num)
    TextView mChasisNum;
    @BindView(R.id.eng_num)
    TextView mEngNum;
    /* @BindView(R.id.payment_ref)
     TextView mPaymentRef;*/
    @BindView(R.id.vehicle_value)
    TextView mVehicleValue;
    @BindView(R.id.vehicle_year)
    TextView mVehicleYear;
    @BindView(R.id.renew_btn)
    TextView mRenew;
    @BindView(R.id.progress)
    AVLoadingIndicatorView mProgress;
    @BindView(R.id.user_policy_btn)
    MaterialCardView user_policy_btn;
    /** ButterKnife Code **/

    Animation slide_front_left, blink, slide_front_right;
    String policy_num, value, quote_price, policy_type, cover_type, make, eh_policy_type;
    double roundOff;

    private UserPreferences userPreferences;
    NetworkConnection networkConnection=new NetworkConnection();
    ApiInterface client = ServiceGenerator.createService(ApiInterface.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_detail);
        ButterKnife.bind(this);
        userPreferences=new UserPreferences(this);
        applyToolbarChildren("Policy Detail");


        Intent intent=getIntent();
        String body_type=intent.getStringExtra("body_type");
        String chasis_num=intent.getStringExtra("chasis_num");
        String engine_num=intent.getStringExtra("engine_num");
        policy_type = intent.getStringExtra("policy_type");
        eh_policy_type = intent.getStringExtra("eh_policy_type");
        make = intent.getStringExtra("make");
        String start_date=intent.getStringExtra("start_date");
        String end_date=intent.getStringExtra("end_date");
        String price=intent.getStringExtra("price");
        String payment_ref=intent.getStringExtra("payment_ref");
        String payment_status=intent.getStringExtra("payment_status");
        String status=intent.getStringExtra("status");
        cover_type = intent.getStringExtra("cover_type");

        String reg_num=intent.getStringExtra("reg_num");
        value = intent.getStringExtra("value");
        policy_num = intent.getStringExtra("policy_num");
        String year=intent.getStringExtra("year");
        String period = intent.getStringExtra("period");


        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
        nf.setMaximumFractionDigits(2);
        DecimalFormat df = (DecimalFormat) nf;
        String v_price = "₦" + df.format(Double.valueOf(price));


        mBodyType.setText(body_type);
        mStatus.setText(status);
        mChasisNum.setText(chasis_num);
        mPolicyNum.setText(policy_num);
        mEngNum.setText(engine_num);
        mVehicleMake.setText(make);
        mPolicyType.setText(policy_type);
        mStartDate.setText(start_date);
        mEndDate.setText(end_date);
        mPrice.setText(v_price);
        //mPaymentRef.setText(payment_ref);
        mPaymentStatus.setText(payment_status);
        mCoverType.setText(cover_type);
        mRegNum.setText(reg_num);
        mVehicleValue.setText(value);
        mVehicleYear.setText(year);
        String period_txt = period + "Days";
        mPeriod.setText(period_txt);

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


                        if(networkConnection.isNetworkConnected(getBaseContext())){
                            mRenew.setVisibility(View.GONE);
                            mProgress.setVisibility(View.VISIBLE);

                            PostVehicleData postVehicleData = new PostVehicleData(value, cover_type,
                                    policy_type, eh_policy_type, make);

                            sendVehicleData(postVehicleData);
                            //get client and call object for request


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


    private void sendVehicleData(PostVehicleData postVehicleData) {


        //get client and call object for request
        ApiInterface client = ServiceGenerator.createService(ApiInterface.class);


        Call<QouteHead> call = client.getVehicleQuote("Token " + userPreferences.getUserToken(), postVehicleData);

        call.enqueue(new Callback<QouteHead>() {
            @Override
            public void onResponse(Call<QouteHead> call, Response<QouteHead> response) {
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
                            showShortMsg("Failed to Fetch Quote" + e.getMessage());
                            mRenew.setVisibility(View.VISIBLE);
                            mProgress.setVisibility(View.GONE);

                        }
                        mRenew.setVisibility(View.VISIBLE);
                        mProgress.setVisibility(View.GONE);
                        return;
                    }

                    quote_price = response.body().getData().getPrice();

                    roundOff = Math.round(Double.valueOf(quote_price) * 100) / 100.00;

                    Log.i("quote_price", quote_price);

                    Call<RenewPolicyGet> call2 = client.renew_policy("Token " + userPreferences.getUserToken(), policy_num, String.valueOf(roundOff), "paystack");

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


                                Intent i = new Intent(MyVehicleDetail.this, PolicyPaymentActivity.class);
                                i.putExtra(Constant.POLICY_NUM, policyNumber);
                                i.putExtra(Constant.TOTAL_PRICE, amount);
                                i.putExtra(Constant.POLICY_TYPE, "vehicle");
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
            public void onFailure(Call<QouteHead> call, Throwable t) {
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



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

}
