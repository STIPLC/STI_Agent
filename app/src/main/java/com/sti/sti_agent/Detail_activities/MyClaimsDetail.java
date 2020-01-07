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

import com.sti.sti_agent.NetworkConnection;
import com.sti.sti_agent.R;
import com.sti.sti_agent.UserPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyClaimsDetail extends AppCompatActivity {

    /** ButterKnife Code **/
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.status)
    TextView mStatus;
    @BindView(R.id.status_bg)
    ImageView mStatusBg;
    @BindView(R.id.claim_num)
    TextView mClaimNum;
    @BindView(R.id.cost)
    TextView mCost;
    @BindView(R.id.policy_num)
    TextView mPolicyNum;
    @BindView(R.id.type)
    TextView mType;
    @BindView(R.id.decription)
    TextView mDecription;
    /* @BindView(R.id.date_loss)
     TextView mDateLoss;*/
    @BindView(R.id.date_time)
    TextView mDateTime;
    /** ButterKnife Code **/

    Animation slide_front_left, blink;

    private UserPreferences userPreferences;
    NetworkConnection networkConnection=new NetworkConnection();

    String cost="",policynum="",claim_num="",desc="",policy_type="",status="",claim_date="",date_loss="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_detail);
        ButterKnife.bind(this);
        userPreferences=new UserPreferences(this);
        applyToolbarChildren("Claim Detail");


        Intent intent=getIntent();
        claim_num=intent.getStringExtra("claim_num");
        status=intent.getStringExtra("status");
        cost=intent.getStringExtra("cost");
        policynum=intent.getStringExtra("policynum");
        policy_type=intent.getStringExtra("policy_type");
        desc=intent.getStringExtra("desc");
        date_loss=intent.getStringExtra("date_loss");
        claim_date=intent.getStringExtra("claim_date");


        mClaimNum.setText(claim_num);
        mStatus.setText(status);
        mCost.setText(cost);
        mPolicyNum.setText(policynum);
        mType.setText(policy_type);
        mDecription.setText(desc);
        // mDateLoss.setText(date_loss);
        mDateTime.setText(claim_date);


        slide_front_left = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_from_left);

        blink = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink);

        //start animation
        mStatusBg.startAnimation(slide_front_left);

        mStatus.startAnimation(blink);



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
