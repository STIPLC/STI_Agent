package com.sti.sti_agent.Detail_activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.sti.sti_agent.Constant;
import com.sti.sti_agent.NetworkConnection;
import com.sti.sti_agent.R;
import com.sti.sti_agent.UserPreferences;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyCustomerDetail extends AppCompatActivity {

    /** ButterKnife Code **/
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.profile_photo)
    CircleImageView mProfilePhoto;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.firstname)
    TextView mFirstname;
    @BindView(R.id.lastname)
    TextView mLastname;
    @BindView(R.id.phone_no)
    TextView mPhoneNo;
    @BindView(R.id.email)
    TextView mEmail;
    @BindView(R.id.address)
    TextView mAddress;
    @BindView(R.id.gender_layout)
    LinearLayout mGenderLayout;
    @BindView(R.id.gender)
    TextView mGender;
    @BindView(R.id.marital_layout)
    LinearLayout mMaritalLayout;
    @BindView(R.id.marital_status)
    TextView mMaritalStatus;
    @BindView(R.id.next_kin_layout)
    LinearLayout mNextKinLayout;
    @BindView(R.id.next_of_kin)
    TextView mNextOfKin;
    @BindView(R.id.next_kin_phone_layout)
    LinearLayout mNextKinPhoneLayout;
    @BindView(R.id.next_of_kin_phone_no)
    TextView mNextOfKinPhoneNo;
    @BindView(R.id.business_layout)
    LinearLayout mBusinessLayout;
    @BindView(R.id.bussiness)
    TextView mBussiness;
    @BindView(R.id.nationality_layout)
    LinearLayout mNationalityLayout;
    @BindView(R.id.nationality)
    TextView mNationality;
    /** ButterKnife Code **/

    Animation slide_front_left, blink;

    private UserPreferences userPreferences;
    NetworkConnection networkConnection=new NetworkConnection();

    String title="",firstname="",lastname="",phone_no="",email="",addr="",gender="",marital_status="";
    String next_of_kin="",next_of_kin_phone="",business="",nationality="",company_name="",employer="",picture="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        ButterKnife.bind(this);
        userPreferences=new UserPreferences(this);
        applyToolbarChildren("Customer Detail");


        Intent intent=getIntent();
        title=intent.getStringExtra(Constant.TITLE);
        firstname=intent.getStringExtra(Constant.CUSTOMER_FIRSTNAME);
        lastname=intent.getStringExtra(Constant.CUSTOMER_LASTNAME);
        phone_no=intent.getStringExtra(Constant.CUSTOMER_PHONE_NO);
        email=intent.getStringExtra(Constant.CUSTOMER_EMAIL);
        addr=intent.getStringExtra(Constant.ADDRESS);
        employer=intent.getStringExtra(Constant.EMPLOYER_ADDR);
        gender=intent.getStringExtra(Constant.GENDER);
        marital_status=intent.getStringExtra(Constant.MARITAL_STATUS);
        next_of_kin=intent.getStringExtra(Constant.NEXT_OF_KIN);
        next_of_kin_phone=intent.getStringExtra(Constant.NEXT_OF_KIN_PHONE);
        business=intent.getStringExtra(Constant.BUSNIESS);
        nationality=intent.getStringExtra(Constant.NATIONALITY);
        company_name=intent.getStringExtra(Constant.COMPANY_NAME);
        picture=intent.getStringExtra(Constant.CUSTOMER_PICTURE);


        if(company_name==null || company_name.equals("null")) {

            mTitle.setVisibility(View.GONE);
            mFirstname.setText(firstname);
            mLastname.setText(lastname);
            mPhoneNo.setText(phone_no);
            mEmail.setText(email);
            mAddress.setText(addr);
            mMaritalLayout.setVisibility(View.GONE);
            mGender.setText(gender);
            mNextOfKin.setText(next_of_kin);
            mNextKinPhoneLayout.setVisibility(View.GONE);
            mBussiness.setText(business);
            mNationalityLayout.setVisibility(View.GONE);

            Picasso.with(this).load(picture)
                    .centerCrop()
                    .resize(150, 150)
                    .placeholder(R.drawable.man)
                    .into(mProfilePhoto);


        }else{
            mTitle.setVisibility(View.GONE);
            mFirstname.setText(company_name);
            mLastname.setVisibility(View.GONE);
            mPhoneNo.setText(phone_no);
            mEmail.setText(email);
            mAddress.setText(employer);
            mMaritalLayout.setVisibility(View.GONE);
            mGenderLayout.setVisibility(View.GONE);
            mNextKinLayout.setVisibility(View.GONE);
            mNextKinPhoneLayout.setVisibility(View.GONE);
            mBussiness.setText(business);
            mNationalityLayout.setVisibility(View.GONE);

            Picasso.with(this).load(picture)
                    .centerCrop()
                    .resize(150, 150)
                    .placeholder(R.drawable.man)
                    .into(mProfilePhoto);

        }


        slide_front_left = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_from_left);

        blink = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink);

        //start animation
        mProfilePhoto.startAnimation(slide_front_left);

        mProfilePhoto.startAnimation(blink);



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
