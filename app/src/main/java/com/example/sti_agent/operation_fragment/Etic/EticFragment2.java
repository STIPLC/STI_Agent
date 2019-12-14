package com.example.sti_agent.operation_fragment.Etic;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sti_agent.Model.Errors.APIError;
import com.example.sti_agent.Model.Errors.ErrorUtils;
import com.example.sti_agent.Model.Etic.QouteHeadEtic;
import com.example.sti_agent.Model.ServiceGenerator;
import com.example.sti_agent.R;
import com.example.sti_agent.UserPreferences;
import com.example.sti_agent.retrofit_interface.ApiInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.shuhart.stepview.StepView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EticFragment2 extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAE2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.qb_form_layout2)
    FrameLayout mQbFormLayout2;
    @BindView(R.id.step_view)
    StepView mStepView;
    @BindView(R.id.inputLayoutTripDuratn_e2)
    TextInputLayout mInputLayoutTripDuratnE2;
    @BindView(R.id.trip_duration_e2)
    EditText mTripDurationE2;
    @BindView(R.id.start_date_e2)
    EditText mStartDateE2;
    @BindView(R.id.travel_mode_spinner_e2)
    Spinner travel_mode_spinner_e2;
    @BindView(R.id.disability_spinner_e2)
    Spinner mDisabilitySpinnerE2;
    @BindView(R.id.inputLayoutDisabilityDetail_e2)
    TextInputLayout mInputLayoutDisabilityDetailE2;
    @BindView(R.id.disable_detail_e2)
    EditText mDisableDetailE2;
    @BindView(R.id.inputLayoutDeptPlace_e2)
    TextInputLayout mInputLayoutDeptPlaceE2;
    @BindView(R.id.dept_place_txt_e2)
    EditText mDeptPlaceTxtE2;
    @BindView(R.id.inputLayoutArivalPlace_e2)
    TextInputLayout mInputLayoutArivalPlaceE2;
    @BindView(R.id.arrival_place_txt_e2)
    EditText mArrivalPlaceTxtE2;

    @BindView(R.id.inputLayoutCountryVisitAddr_e2)
    TextInputLayout mInputLayoutCountryVisitAddrE2;
    @BindView(R.id.countryVisit_addr_txt_e2)
    EditText mCountryVisitAddrE2;
    
    
    @BindView(R.id.btn_layout2_e2)
    LinearLayout mBtnLayout2E2;
    @BindView(R.id.v_back_btn2_e2)
    Button mVBackBtn2E2;
    @BindView(R.id.v_next_btn2_e2)
    Button mVNextBtn2E2;
    @BindView(R.id.progressbar2_e2)
    AVLoadingIndicatorView mProgressbar2E2;
    

    private int currentStep = 1;

    String disabilityString, startDateStrg, disable_DetailString, travelModeString;
    DatePickerDialog datePickerDialog1;
    UserPreferences userPreferences;



    

    public EticFragment2() {
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
    public static EticFragment2 newInstance(String param1, String param2) {
        EticFragment2 fragment = new EticFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAE2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAE2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_etic2, container, false);
        ButterKnife.bind(this,view);
        userPreferences = new UserPreferences(getContext());
        //        mStepView next registration step

        mStepView.go(currentStep, true);

        init();

        disabilitytypeSpinner();
        travelModetypeSpinner();
        setViewActions();
        showDatePicker();

        return  view;
    }


    private  void init(){
        UserPreferences userPreferences = new UserPreferences(getContext());

        //Temporal save and go to next Operation


        mTripDurationE2.setText(userPreferences.getEticITripDuration());

        //mStartDateE2.setText(userPreferences.getEticStartDate());


        mDisableDetailE2.setText(userPreferences.getEticIDisabilityDetail());

        mDeptPlaceTxtE2.setText(userPreferences.getEticIDeparturePlc());

        mArrivalPlaceTxtE2.setText(userPreferences.getEticIArrivalPlc());

        mCountryVisitAddrE2.setText(userPreferences.getEticICountryOfVisit());
        
    }
    
    //seting onclicks listeners
    private void setViewActions() {

        mVNextBtn2E2.setOnClickListener(this);
        mVBackBtn2E2.setOnClickListener(this);
        mStartDateE2.setOnClickListener(this);

    }


    private void disabilitytypeSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.disable_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        mDisabilitySpinnerE2.setAdapter(staticAdapter);

        mDisabilitySpinnerE2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String stringText = (String) parent.getItemAtPosition(position);
                boolean valid_disable = true;
                if(stringText.equals("Yes")){
                    mDisableDetailE2.setVisibility(View.VISIBLE);
                    mDisableDetailE2.setClickable(true);


                }else if(stringText.equals("No")){
                    mDisableDetailE2.setVisibility(View.GONE);
                    mDisableDetailE2.setClickable(false);
                    disable_DetailString = "null";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //De-Visualizing the individual form
                mDisabilitySpinnerE2.getItemAtPosition(0);
                mDisableDetailE2.setVisibility(View.GONE);
                mDisableDetailE2.setClickable(false);

            }
        });

    }


    private void travelModetypeSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.travel_mode_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        travel_mode_spinner_e2.setAdapter(staticAdapter);

        travel_mode_spinner_e2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String travelModeString = (String) parent.getItemAtPosition(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //De-Visualizing the individual form
                travel_mode_spinner_e2.getItemAtPosition(0);


            }
        });

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.v_next_btn2_e2:
//                validate user input
                validateUserInputs();
                break;

            case R.id.start_date_e2:
//                validate user input
                datePickerDialog1.show();
                break;

            case R.id.v_back_btn2_e2:
                if (currentStep > 0) {
                    currentStep--;
                }
                mStepView.done(false);
                mStepView.go(currentStep, true);

                Fragment eticFragment1 = new EticFragment1();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_etic_form_container, eticFragment1);
                ft.commit();

                break;
        }
    }

    private void validateUserInputs() {


        boolean isValid = true;

        if (mTripDurationE2.getText().toString().isEmpty()) {
            mInputLayoutTripDuratnE2.setError("Trip Duration is required!");
            isValid = false;
        } else if (mStartDateE2.getText().toString().isEmpty()) {
            showMessage("Start Date is required!");
            isValid = false;
        } else if (mDeptPlaceTxtE2.getText().toString().isEmpty()) {
            mInputLayoutDeptPlaceE2.setError("Departure Place is required!");

            isValid = false;
        }else if (mCountryVisitAddrE2.getText().toString().isEmpty()) {
            mInputLayoutCountryVisitAddrE2.setError("Country of Visit is required!");
            isValid = false;
        }else {
            mInputLayoutCountryVisitAddrE2.setErrorEnabled(false);
            mInputLayoutDeptPlaceE2.setErrorEnabled(false);
            mInputLayoutTripDuratnE2.setErrorEnabled(false);
        }

        if (mArrivalPlaceTxtE2.getText().toString().isEmpty()) {
            mInputLayoutArivalPlaceE2.setError("Arrival Place is required!");

            isValid = false;
        }else {
            mInputLayoutArivalPlaceE2.setErrorEnabled(false);
        }
                // Spinner Validations

        disabilityString = mDisabilitySpinnerE2.getSelectedItem().toString();
        if (disabilityString.equals("Yes")) {
            if (mDisableDetailE2.getText().toString().isEmpty() && mDisableDetailE2.isClickable()) {
                mInputLayoutDisabilityDetailE2.setError("Disability detail is required!");
                isValid = false;
            } else {
                mInputLayoutDisabilityDetailE2.setErrorEnabled(false);
            }
        }

        if (disabilityString.equals("Select Disability*")) {
            showMessage("Select Yes or No for disability");
            isValid = false;
        }

        //mode of travel validation
        travelModeString = travel_mode_spinner_e2.getSelectedItem().toString();
        if (travelModeString.equals("Select Mode of Travel*")) {
            showMessage("Select mode of travel");
            isValid = false;
        }




        if (isValid) {
//            send inputs to next next page
//            Goto to the next Registration step
            initFragment();
        }




    }

    private void initFragment() {
        mBtnLayout2E2.setVisibility(View.GONE);
        mProgressbar2E2.setVisibility(View.VISIBLE);

        try {


            //Temporal save and go to next Operation

            userPreferences.setEticITripDuration(mTripDurationE2.getText().toString());
            userPreferences.setEticIDisability(disabilityString);
            userPreferences.setEticStartDate(mStartDateE2.getText().toString());
            userPreferences.setEticITravelMode(travelModeString);
            userPreferences.setEticIDeparturePlc(mDeptPlaceTxtE2.getText().toString());
            if (disabilityString.equals("Yes")) {
                userPreferences.setEticIDisabilityDetail(mDisableDetailE2.getText().toString());

            } else {
                userPreferences.setEticIDisabilityDetail("null");
            }
            userPreferences.setEticIArrivalPlc(mArrivalPlaceTxtE2.getText().toString());
            userPreferences.setEticICountryOfVisit(mCountryVisitAddrE2.getText().toString());

            sendEticData();

        }catch (Exception e){
            Log.i("Form Error",e.getMessage());
            mProgressbar2E2.setVisibility(View.GONE);
            mVNextBtn2E2.setVisibility(View.VISIBLE);
            showMessage("Error: " + e.getMessage());
        }
    }

    private void sendEticData(){


        //get client and call object for request
        ApiInterface client = ServiceGenerator.createService(ApiInterface.class);
        Call<QouteHeadEtic> call=client.etic_quote("Token "+userPreferences.getUserToken(), 2000);

        call.enqueue(new Callback<QouteHeadEtic>() {
            @Override
            public void onResponse(Call<QouteHeadEtic> call, Response<QouteHeadEtic> response) {
                Log.i("ResponseCode", String.valueOf(response.code()));

                if(response.code()==406){
                    showMessage("Error! Wrong Value provided!");
                    mBtnLayout2E2.setVisibility(View.VISIBLE);
                    mProgressbar2E2.setVisibility(View.GONE);
                    return;
                }

                if(response.code()==400){
                    showMessage("Check your internet connection");
                    mBtnLayout2E2.setVisibility(View.VISIBLE);
                    mProgressbar2E2.setVisibility(View.GONE);
                    return;
                }else if(response.code()==429){
                    showMessage("Too many requests on database");
                    mBtnLayout2E2.setVisibility(View.VISIBLE);
                    mProgressbar2E2.setVisibility(View.GONE);
                    return;
                }else if(response.code()==500){
                    showMessage("Server Error");
                    mBtnLayout2E2.setVisibility(View.VISIBLE);
                    mProgressbar2E2.setVisibility(View.GONE);
                    return;
                }else if(response.code()==401){
                    showMessage("Unauthorized access, please try login again");
                    mBtnLayout2E2.setVisibility(View.VISIBLE);
                    mProgressbar2E2.setVisibility(View.GONE);
                    return;
                }

                try {
                    if (!response.isSuccessful()) {

                        try{
                            APIError apiError= ErrorUtils.parseError(response);

                            showMessage("Invalid Entry: "+apiError.getErrors());
                            Log.i("Invalid EntryK",apiError.getErrors().toString());
                            Log.i("Invalid Entry",response.errorBody().toString());

                        }catch (Exception e){
                            Log.i("InvalidEntry",e.getMessage());
                            Log.i("ResponseError",response.errorBody().string());
                            showMessage("Failed to Fetch Quote"+e.getMessage());
                            mBtnLayout2E2.setVisibility(View.VISIBLE);
                            mProgressbar2E2.setVisibility(View.GONE);

                        }
                        mBtnLayout2E2.setVisibility(View.VISIBLE);
                        mProgressbar2E2.setVisibility(View.GONE);
                        return;
                    }

                    String quote_price=response.body().getData().getPrice();

                    double roundOff = Math.round(Double.valueOf(quote_price)*100)/100.00;

                    Log.i("quote_price", String.valueOf(roundOff));
                    showMessage("Successfully Fetched Quote");
                    mBtnLayout2E2.setVisibility(View.VISIBLE);
                    mProgressbar2E2.setVisibility(View.GONE);


                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_etic_form_container, EticFragment3.newInstance(travelModeString, String.valueOf(roundOff)), EticFragment3.class.getSimpleName());
                    ft.commit();
                }catch (Exception e){
                    Log.i("policyResponse", e.getMessage());
                    mBtnLayout2E2.setVisibility(View.VISIBLE);
                    mProgressbar2E2.setVisibility(View.GONE);
                }

            }
            @Override
            public void onFailure(Call<QouteHeadEtic> call, Throwable t) {
                showMessage("Submission Failed "+t.getMessage());
                Log.i("GEtError",t.getMessage());
                mBtnLayout2E2.setVisibility(View.VISIBLE);
                mProgressbar2E2.setVisibility(View.GONE);
            }
        });

    }




    private void showMessage(String s) {
        Snackbar.make(mQbFormLayout2, s, Snackbar.LENGTH_SHORT).show();
    }



    private void showDatePicker() {
        //Get current date
        Calendar calendar = Calendar.getInstance();

        //Create datePickerDialog with initial date which is current and decide what happens when a date is selected.
        datePickerDialog1 = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //When a date is selected, it comes here.
                if(year<calendar.get(Calendar.YEAR) ){
                    showMessage("Invalid Start Date");
                    datePickerDialog1.dismiss();
                    mStartDateE2.setText("");
                    Log.i("Calendar", year + " " + calendar.get(Calendar.YEAR));

                }else if( monthOfYear+1<calendar.get(Calendar.MONTH) ) {
                    showMessage("Invalid Start Date");
                    datePickerDialog1.dismiss();
                    mStartDateE2.setText("");
                    Log.i("Calendar", monthOfYear+1 + " " + calendar.get(Calendar.MONTH));

                }else if(dayOfMonth<calendar.get(Calendar.DAY_OF_MONTH)) {
                    showMessage("Invalid Start Date");
                    datePickerDialog1.dismiss();
                    mStartDateE2.setText("");
                    Log.i("Calendar", dayOfMonth + " " + calendar.get(Calendar.DAY_OF_MONTH));

                }else {
                    int monthofYear = monthOfYear + 1;
                    startDateStrg = year + "-" + monthofYear + "-" + dayOfMonth;
                    mStartDateE2.setText(startDateStrg);
                    datePickerDialog1.dismiss();
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }


}
