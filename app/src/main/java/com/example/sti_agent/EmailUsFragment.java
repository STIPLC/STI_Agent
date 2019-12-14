package com.example.sti_agent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.example.sti_agent.Model.ServiceGenerator;
import com.example.sti_agent.operation_activity.QuoteBuyActivity;
import com.example.sti_agent.retrofit_interface.ApiInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EmailUsFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /** ButterKnife Code **/
    @BindView(R.id.email_us_layout)
    CoordinatorLayout mEmailUsLayout;
    @BindView(R.id.inputLayoutFullName)
    TextInputLayout mInputLayoutFullName;
    @BindView(R.id.full_name)
    EditText mFullName;
    @BindView(R.id.inputLayoutEmail)
    TextInputLayout mInputLayoutEmail;
    @BindView(R.id.email)
    EditText mEmail;
    @BindView(R.id.inputLayoutPhoneNum)
    TextInputLayout mInputLayoutPhoneNum;
    @BindView(R.id.phone_num)
    EditText mPhoneNum;
    @BindView(R.id.inputLayoutMessage)
    TextInputLayout mInputLayoutMessage;
    @BindView(R.id.message)
    EditText mMessage;
    @BindView(R.id.send_mail)
    Button mSendMail;
    @BindView(R.id.progressbar)
    AVLoadingIndicatorView mProgressbar;
    /** ButterKnife Code **/


    UserPreferences userPreferences;

    ApiInterface client= ServiceGenerator.createService(ApiInterface.class);



    public EmailUsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Dashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static EmailUsFragment newInstance(String param1, String param2) {
        EmailUsFragment fragment = new EmailUsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_email_us, container, false);
        ButterKnife.bind(this,view);
        userPreferences=new UserPreferences(getContext());

        mSendMail.setOnClickListener(this);

        
        return  view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_mail:
//                send quote to client and sti
                ValidateForm();
                break;

        }
    }

    private void ValidateForm() {


        boolean isValid = true;

        if (mFullName.getText().toString().isEmpty()) {
            mInputLayoutFullName.setError("Full name is required!");
            isValid = false;
        }else if (mMessage.getText().toString().isEmpty()) {
            mInputLayoutMessage.setError("Message is required!");
            isValid = false;
        } else {
            mInputLayoutFullName.setErrorEnabled(false);
            mInputLayoutFullName.setErrorEnabled(false);
        }
        if (mEmail.getText().toString().isEmpty()) {
            mInputLayoutEmail.setError("Email is required!");
            isValid = false;
        } else if (!isValidEmailAddress(mEmail.getText().toString()) && mInputLayoutEmail.isClickable()) {
            mInputLayoutEmail.setError("Valid Email is required!");
            isValid = false;
        } else {
            mInputLayoutEmail.setErrorEnabled(false);
        }

        if (mPhoneNum.getText().toString().isEmpty()) {
            mInputLayoutPhoneNum.setError("Phone number is required");
            isValid = false;
        } else if (mPhoneNum.getText().toString().trim().length() < 11&&mInputLayoutPhoneNum.isClickable()) {
            mInputLayoutPhoneNum.setError("Your Phone number must be 11 in length");
            isValid = false;
        } else {
            mInputLayoutPhoneNum.setErrorEnabled(false);
        }


        if (isValid) {
//            send inputs to next next page
//            Goto to the next Registration step
            initFragment();
        }
    }


    private void initFragment(){
        mProgressbar.setVisibility(View.VISIBLE);
        mSendMail.setVisibility(View.GONE);
        try {

            String to = "customerservice@stiplc.com";
            String subject = mFullName.getText().toString();
            String phone_num = mPhoneNum.getText().toString();
            String sender_email = mEmail.getText().toString();

            String message = mMessage.getText().toString() + "\n\n" + phone_num + "\n\n" + sender_email;

            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
            email.putExtra(Intent.EXTRA_SUBJECT, subject);
            email.putExtra(Intent.EXTRA_TEXT, message);

            //need this to prompts email client only
            email.setType("message/rfc822");

            startActivity(Intent.createChooser(email, "Choose an Email client :"));

            mFullName.setText("");
            mPhoneNum.setText("");
            mEmail.setText("");
            mMessage.setText("");
            mProgressbar.setVisibility(View.GONE);
            mSendMail.setVisibility(View.VISIBLE);

        }catch (Exception e){

            Log.i("Mailing Error",e.getMessage());
            mProgressbar.setVisibility(View.GONE);
            mSendMail.setVisibility(View.VISIBLE);
            showMessage("Mailing Error");
        }


    }

        private static boolean isValidEmailAddress(String email) {
            boolean result = true;
            if (null != email) {
                String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(email);
                if (!matcher.matches()) {
                    result = false;
                }
            }

            return result;
        }

    private void showMessage(String s) {
        Snackbar.make(mEmailUsLayout, s, Snackbar.LENGTH_SHORT).show();
    }


}
