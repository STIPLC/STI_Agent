package com.sti.sti_agent.operation_fragment.Pin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sti.sti_agent.MainActivity;
import com.sti.sti_agent.Model.Errors.APIError;
import com.sti.sti_agent.Model.Errors.ErrorUtils;
import com.sti.sti_agent.Model.Pin.UserChangePin;
import com.sti.sti_agent.Model.Pin.changePin;
import com.sti.sti_agent.Model.ServiceGenerator;
import com.sti.sti_agent.NetworkConnection;
import com.sti.sti_agent.R;
import com.sti.sti_agent.UserPreferences;
import com.sti.sti_agent.retrofit_interface.ApiInterface;
import com.google.android.material.textfield.TextInputLayout;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /** ButterKnife Code **/
    @BindView(R.id.pin_layout)
    CoordinatorLayout mPINLayout;
   /* @BindView(R.id.set_pin_layout)
    LinearLayout mSetPinLayout;*/
 /*   @BindView(R.id.inputLayoutPin)
    TextInputLayout mInputLayoutPin;*/
   /* @BindView(R.id.pin_editxt)
    EditText mPinEditxt;*/
    /*@BindView(R.id.set_pin_btn)
    Button mSetPinBtn;*/
    /*@BindView(R.id.set_change_pin_txt)
    TextView set_change_pin_txt;*/
   /* @BindView(R.id.set_pin_progressbar)
    AVLoadingIndicatorView mSetPinProgressbar;*/
    @BindView(R.id.change_pin_layout)
    LinearLayout mChangePinLayout;
    @BindView(R.id.inputLayoutOldPin)
    TextInputLayout mInputLayoutOldPin;
    @BindView(R.id.old_pin_editxt)
    EditText mOldPinEditxt;

    @BindView(R.id.inputLayoutNewPin)
    TextInputLayout mInputLayoutNewPin;
    @BindView(R.id.new_pin_editxt)
    EditText mNewPinEditxt;
    @BindView(R.id.change_pin_btn)
    Button mChangePinBtn;
    @BindView(R.id.change_pin_progressbar)
    AVLoadingIndicatorView mChangePinProgressbar;
   /* @BindView(R.id.change_pin_txt)
    TextView mChangePinTxt;*/
    /** ButterKnife Code **/

    Fragment fragment;

    NetworkConnection networkConnection=new NetworkConnection();

    UserPreferences userPreferences;


    public PinFragment() {
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
    public static PinFragment newInstance(String param1, String param2) {
        PinFragment fragment = new PinFragment();
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
        View view=inflater.inflate(R.layout.fragment_pin, container, false);
        ButterKnife.bind(this,view);
       /* mChangePinTxt.setPaintFlags(mChangePinTxt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mChangePinTxt.setText("Click to Change Your Pin");*/
        userPreferences=new UserPreferences(getContext());

        setViewActions();

        return  view;
    }



    //seting onclicks listeners
    private void setViewActions() {

        mChangePinBtn.setOnClickListener(this);
        //mChangePinTxt.setOnClickListener(this);
        //mSetPinBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

           /* case R.id.set_pin_btn:
//                validate user input
                set_change_pin_txt.setText("Set your Pin");
                validateUserInputs();
                break;*/

            case R.id.change_pin_btn:
//                validate user input
                //set_change_pin_txt.setText("Change your Pin");
                validateUserInputs_change_pin();
                break;

           /* case R.id.change_pin_txt:
//                validate user input
                mChangePinLayout.setVisibility(View.VISIBLE);
                mSetPinLayout.setVisibility(View.GONE);
                mChangePinTxt.setVisibility(View.GONE);
                break;*/
        }
    }


/*    private void validateUserInputs() {

        *//*mSetPinLayout.setVisibility(View.VISIBLE);
        mChangePinLayout.setVisibility(View.GONE);*//*

        boolean isValid = true;

        if (mPinEditxt.getText().toString().trim().length() >4 ||mPinEditxt.getText().toString().trim().length() <4|| mPinEditxt.getText().toString().isEmpty()) {
            mInputLayoutPin.setError("Invalid Entry !");
            isValid = false;
        }else {
            mInputLayoutPin.setErrorEnabled(false);
        }

        if (isValid) {
            if(networkConnection.isNetworkConnected(getContext())) {
                initFragmentSetPin();
            }else {
                showMessage("No Internet Connection");
            }
        }

    }*/

    private void validateUserInputs_change_pin() {

        mChangePinBtn.setVisibility(View.VISIBLE);
       // mSetPinLayout.setVisibility(View.GONE);

        boolean isValid = true;

        if (mOldPinEditxt.getText().toString().trim().length()!=4) {
            mInputLayoutOldPin.setError("Invalid Pin Entered !");
            isValid = false;
        }else if(!userPreferences.getAgentPin().equals(mOldPinEditxt.getText().toString().trim())){
            mInputLayoutOldPin.setError("Incorrect old PIN!");
            isValid = false;
        }else if (mNewPinEditxt.getText().toString().trim().length() !=4) {
            mInputLayoutNewPin.setError("Invalid Pin Entered !");
            isValid = false;
        }else {
            mInputLayoutNewPin.setErrorEnabled(false);
        }

        if (isValid) {
            if(networkConnection.isNetworkConnected(getContext())) {
                initFragmentChangePin();
            }else {
                showMessage("No Internet Connection");
            }
        }

    }

   /* private void initFragmentSetPin() {
       *//* mSetPinBtn.setVisibility(View.GONE);
        mSetPinProgressbar.setVisibility(View.VISIBLE);*//*

        UserPin userPin=new UserPin(mPinEditxt.getText().toString());
        setPin setPin=new setPin(userPin);
        sendPinData(setPin);

    }*/

   /* private void sendPinData(setPin setPin){


        //get client and call object for request
        ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

        Call<ResponseBody> call=client.set_pin("Token "+userPreferences.getUserToken(),setPin);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("ResponseCode", String.valueOf(response.code()));
                if(response.code()==406){
                    showMessage("Error! Wrong pin type provided!");
                    mSetPinBtn.setVisibility(View.VISIBLE);
                    mSetPinProgressbar.setVisibility(View.GONE);
                    return;
                }

                if(response.code()==400){
                    showMessage("Check your internet connection");
                    mSetPinBtn.setVisibility(View.VISIBLE);
                    mSetPinProgressbar.setVisibility(View.GONE);
                    return;

                }else if(response.code()==429){
                    showMessage("Too many requests on database");
                    mSetPinBtn.setVisibility(View.VISIBLE);
                    mSetPinProgressbar.setVisibility(View.GONE);
                    return;
                }else if(response.code()==500){
                    showMessage("Server Error");
                    mSetPinBtn.setVisibility(View.VISIBLE);
                    mSetPinProgressbar.setVisibility(View.GONE);
                    return;
                }else if(response.code()==401){
                    showMessage("Unauthorized access, please try login again");
                    mSetPinBtn.setVisibility(View.VISIBLE);
                    mSetPinProgressbar.setVisibility(View.GONE);
                    return;
                }

                try {
                    if (!response.isSuccessful()) {

                        try{
                            APIError apiError= ErrorUtils.parseError(response);

                            showMessage("Invalid Entry: "+apiError.getErrors());
                            Log.i("Invalid EntryK",apiError.getErrors().toString());
                            Log.i("Invalid Entry",response.errorBody().toString());

                            mSetPinBtn.setVisibility(View.VISIBLE);
                            mSetPinProgressbar.setVisibility(View.GONE);

                        }catch (Exception e){
                            Log.i("InvalidEntry",e.getMessage());
                            Log.i("ResponseError",response.errorBody().string());
                            showMessage("Failed to Register"+e.getMessage());
                            mSetPinBtn.setVisibility(View.VISIBLE);
                            mSetPinProgressbar.setVisibility(View.GONE);

                        }
                        mSetPinBtn.setVisibility(View.VISIBLE);
                        mSetPinProgressbar.setVisibility(View.GONE);
                        return;
                    }

                    showMessage("Pin Successfully set");

                    userPreferences.setAgentPin(mPinEditxt.getText().toString().trim());
                    mSetPinBtn.setVisibility(View.VISIBLE);
                    mSetPinProgressbar.setVisibility(View.GONE);


                }catch (Exception e){
                    showMessage("Submission Error: " + e.getMessage());
                    Log.i("Submission", e.getMessage());
                    mSetPinBtn.setVisibility(View.VISIBLE);
                    mSetPinProgressbar.setVisibility(View.GONE);
                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showMessage("Submission Failed "+t.getMessage());
                mSetPinBtn.setVisibility(View.VISIBLE);
                mSetPinProgressbar.setVisibility(View.GONE);
                Log.i("GEtError",t.getMessage());
            }
        });




    }*/



    private void initFragmentChangePin() {
        mChangePinBtn.setVisibility(View.GONE);
        mChangePinProgressbar.setVisibility(View.VISIBLE);

        UserChangePin userChangePin=new UserChangePin(mOldPinEditxt.getText().toString(),mNewPinEditxt.getText().toString());
        changePin changePin=new changePin(userChangePin);
        sendPinDataChangePin(changePin);

    }

    private void sendPinDataChangePin(changePin changePin){


        //get client and call object for request
        ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

        Call<ResponseBody> call=client.change_pin("Token "+userPreferences.getUserToken(),changePin);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("ResponseCode", String.valueOf(response.code()));
                if(response.code()==406){
                    mChangePinBtn.setVisibility(View.VISIBLE);
                    mChangePinProgressbar.setVisibility(View.GONE);
                    return;
                }

                if(response.code()==400){
                    showMessage("Check your internet connection");
                    mChangePinBtn.setVisibility(View.VISIBLE);
                    mChangePinProgressbar.setVisibility(View.GONE);
                    return;

                }else if(response.code()==429){
                    showMessage("Too many requests on database");
                    mChangePinBtn.setVisibility(View.VISIBLE);
                    mChangePinProgressbar.setVisibility(View.GONE);
                    return;
                }else if(response.code()==500){
                    showMessage("Server Error");
                    mChangePinBtn.setVisibility(View.VISIBLE);
                    mChangePinProgressbar.setVisibility(View.GONE);
                    return;
                }else if(response.code()==401){
                    showMessage("Unauthorized access, please try login again");
                    mChangePinBtn.setVisibility(View.VISIBLE);
                    mChangePinProgressbar.setVisibility(View.GONE);
                    return;
                }
                try {
                    if (!response.isSuccessful()) {

                        try{
                            APIError apiError= ErrorUtils.parseError(response);

                            showMessage("Invalid Entry: "+apiError.getErrors());
                            Log.i("Invalid EntryK",apiError.getErrors().toString());
                            Log.i("Invalid Entry",response.errorBody().toString());
                            mChangePinBtn.setVisibility(View.VISIBLE);
                            mChangePinProgressbar.setVisibility(View.GONE);

                        }catch (Exception e){
                            Log.i("InvalidEntry",e.getMessage());
                            Log.i("ResponseError",response.errorBody().string());
                            showMessage("Failed to Register"+e.getMessage());
                            mChangePinBtn.setVisibility(View.VISIBLE);
                            mChangePinProgressbar.setVisibility(View.GONE);

                        }
                        mChangePinBtn.setVisibility(View.VISIBLE);
                        mChangePinProgressbar.setVisibility(View.GONE);
                        return;
                    }


                    userPreferences.setAgentPin(mNewPinEditxt.getText().toString().trim());
                    mChangePinBtn.setVisibility(View.VISIBLE);
                    mChangePinProgressbar.setVisibility(View.GONE);
                    showMessage("Pin Successfully changed");
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();



                }catch (Exception e){
                    showMessage("Submission Error: " + e.getMessage());
                    Log.i("Submission", e.getMessage());
                    mChangePinBtn.setVisibility(View.VISIBLE);
                    mChangePinProgressbar.setVisibility(View.GONE);
                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showMessage("Submission Failed "+t.getMessage());
                mChangePinBtn.setVisibility(View.VISIBLE);
                mChangePinProgressbar.setVisibility(View.GONE);
                Log.i("GEtError",t.getMessage());
            }
        });




    }


    private void showMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }


}
