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
import com.sti.sti_agent.Model.Swiss.QouteHeadSwiss;
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

public class MySwissDetail extends AppCompatActivity {

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
    @BindView(R.id.firstname)
    TextView mFirstname;
    @BindView(R.id.last_name)
    TextView mLastName;
    @BindView(R.id.email)
    TextView mEmail;
    @BindView(R.id.phone_no)
    TextView mPhoneNo;
    @BindView(R.id.addr)
    TextView mAddr;
    @BindView(R.id.start_date)
    TextView mStartDate;
    @BindView(R.id.end_date)
    TextView mEndDate;
    @BindView(R.id.price)
    TextView mPrice;
    @BindView(R.id.dob)
    TextView mDob;
    @BindView(R.id.category)
    TextView mCategory;
    @BindView(R.id.gender)
    TextView mGender;
    @BindView(R.id.marital_status)
    TextView mMaritalStatus;
    @BindView(R.id.next_of_kin)
    TextView mNextOfKin;
    @BindView(R.id.next_of_kin_phone_no)
    TextView mNextOfKinPhoneNo;
    @BindView(R.id.renew_btn)
    TextView mRenew;
    @BindView(R.id.progress)
    AVLoadingIndicatorView mProgress;
    @BindView(R.id.user_policy_btn)
    MaterialCardView user_policy_btn;
    /** ButterKnife Code **/

    Animation slide_front_left, blink, slide_front_right;
    String policynum, dob, quote_price, category;
    ;
    double roundOff;

    private UserPreferences userPreferences;
    NetworkConnection networkConnection=new NetworkConnection();
    //get client and call object for request
    ApiInterface client = ServiceGenerator.createService(ApiInterface.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swiss_detail);
        ButterKnife.bind(this);
        userPreferences=new UserPreferences(this);
        applyToolbarChildren("Policy Detail");


        Intent intent=getIntent();
        String firstname=intent.getStringExtra("firstname");
        String lastname=intent.getStringExtra("lastname");
        String phone_num=intent.getStringExtra("phone_num");
        String getAddr=intent.getStringExtra("getAddr");
        String start_date=intent.getStringExtra("start_date");
        String end_date=intent.getStringExtra("end_date");
        String price=intent.getStringExtra("price");
        dob = intent.getStringExtra("dob");
        String payment_status=intent.getStringExtra("payment_status");
        String status=intent.getStringExtra("status");
        String category=intent.getStringExtra("category");
        String email=intent.getStringExtra("email");
        String next_of_kin=intent.getStringExtra("next_of_kin");
        String next_of_phonenum=intent.getStringExtra("next_of_phonenum");
        String gender=intent.getStringExtra("gender");
        String marital_status=intent.getStringExtra("marital_status");
        policynum = intent.getStringExtra("policynum");


        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
        nf.setMaximumFractionDigits(2);
        DecimalFormat df = (DecimalFormat) nf;
        String v_price = "₦" + df.format(Double.valueOf(price));

        mFirstname.setText(firstname);
        mStatus.setText(status);
        mLastName.setText(lastname);
        mPolicyNum.setText(policynum);
        mPhoneNo.setText(phone_num);
        mAddr.setText(getAddr);
        mStartDate.setText(start_date);
        mEndDate.setText(end_date);
        mPrice.setText(v_price);
        mDob.setText(dob);
        mPaymentStatus.setText(payment_status);
        mCategory.setText(category);
        mEmail.setText(email);
        mNextOfKin.setText(next_of_kin);
        mNextOfKinPhoneNo.setText(next_of_phonenum);
        mGender.setText(gender);
        mMaritalStatus.setText(marital_status);

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


                            sendSwissData();


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

    private void sendSwissData() {

        //get client and call object for request
        ApiInterface client = ServiceGenerator.createService(ApiInterface.class);


        Call<QouteHeadSwiss> call = client.swiss_quote("Token " + userPreferences.getUserToken(), dob);

        call.enqueue(new Callback<QouteHeadSwiss>() {
            @Override
            public void onResponse(Call<QouteHeadSwiss> call, Response<QouteHeadSwiss> response) {
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
                    category = response.body().getData().getCategory();
                    switch (category) {
                        case "Adult":
                            quote_price = "1500";
                            break;
                        case "Child":
                            quote_price = "250";
                            break;

                        default:
                            quote_price = "0.0";
                            break;

                    }

                    roundOff = Math.round(Double.valueOf(quote_price) * 100) / 100.00;

                    Log.i("quote_price", quote_price);
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


                                Intent i = new Intent(MySwissDetail.this, PolicyPaymentActivity.class);
                                i.putExtra(Constant.POLICY_NUM, policyNumber);
                                i.putExtra(Constant.TOTAL_PRICE, amount);
                                i.putExtra(Constant.POLICY_TYPE, "swiss");
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
            public void onFailure(Call<QouteHeadSwiss> call, Throwable t) {
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
