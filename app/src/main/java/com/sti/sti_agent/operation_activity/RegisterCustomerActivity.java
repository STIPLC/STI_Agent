package com.sti.sti_agent.operation_activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.sti.sti_agent.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterCustomerActivity extends AppCompatActivity {

    @BindView(R.id.img_logo)
    ImageView imgLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_customer);
        ButterKnife.bind(this);


    }

}
