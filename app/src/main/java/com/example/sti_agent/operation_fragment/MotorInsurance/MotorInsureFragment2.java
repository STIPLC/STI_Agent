package com.example.sti_agent.operation_fragment.MotorInsurance;

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
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sti_agent.Model.Errors.APIError;
import com.example.sti_agent.Model.Errors.ErrorUtils;
import com.example.sti_agent.Model.ServiceGenerator;
import com.example.sti_agent.Model.Vehicle.BrandType.VehicleBrandType;
import com.example.sti_agent.Model.Vehicle.BrandType.VehicleTypeData;
import com.example.sti_agent.Model.Vehicle.Quote.PostVehicleData;
import com.example.sti_agent.Model.Vehicle.Quote.QouteHead;
import com.example.sti_agent.Model.Vehicle.VehicleBrand.VehicleData;
import com.example.sti_agent.Model.Vehicle.VehicleBrand.Vehicles_Brand;
import com.example.sti_agent.R;
import com.example.sti_agent.UserPreferences;
import com.example.sti_agent.retrofit_interface.ApiInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import com.shuhart.stepview.StepView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MotorInsureFragment2 extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.step_view)
    StepView stepView;

    //EditText
    @BindView(R.id.start_date)
    EditText start_date;
    @BindView(R.id.vehicle_year)
    EditText vehicle_year;
    @BindView(R.id.vehicle_reg_num)
    EditText vehicle_reg_num;
    @BindView(R.id.vehicle_chasis_num)
    EditText vehicle_chasis_num;
    @BindView(R.id.vehicle_engine_num)
    EditText vehicle_engine_num;
    @BindView(R.id.motor_cycle_value)
    EditText motor_cycle_value;
    @BindView(R.id.vehicle_value)
    EditText vehicle_value;
    @BindView(R.id.vehicletype_tag)
    TextView vehicletype_tag;

    @BindView(R.id.qb_form_layout2)
    FrameLayout qb_form_layout2;

    //TextInput

    @BindView(R.id.inputLayoutYear)
    TextInputLayout inputLayoutYear;
    @BindView(R.id.inputLayoutRegNum)
    TextInputLayout inputLayoutRegNum;
    @BindView(R.id.inputLayoutChasisNum)
    TextInputLayout inputLayoutChasisNum;
    @BindView(R.id.inputLayoutEngNum)
    TextInputLayout inputLayoutEngNum;
    @BindView(R.id.inputLayoutMotorCyValue)
    TextInputLayout inputLayoutMotorCyValue;
    @BindView(R.id.inputLayoutVehicleValue)
    TextInputLayout inputLayoutVehicleValue;

    //Spinners
    @BindView(R.id.private_comm_spinner)
    Spinner private_comm_spinner;
    @BindView(R.id.poly_select_type_spinner)
    Spinner poly_select_type_spinner;
    @BindView(R.id.vehicle_make_spinner)
    Spinner vehicle_make_spinner;
    @BindView(R.id.vehicle_type_spinner)
    Spinner vehicle_type_spinner;
    @BindView(R.id.vehicle_body_type_spinner)
    Spinner vehicle_body_type_spinner;
    @BindView(R.id.prEnhance_type_spinner)
    Spinner prEnhance_type_spinner;

    private int currentStep = 1;

    //The variables for VehincleData
    List<VehicleData> vehicleDataList;
    ArrayList<String> vehiclesMakerSpinnerList=new ArrayList<>();
    //The Variables for VehicleBrandType
    List<VehicleTypeData> vehicleBrandTypeList;
    ArrayList<String> vehiclesBrandSpinnerList=new ArrayList<>();
    String value="";

    int vehicleId;
    UserPreferences userPreferences;

  //Button
    @BindView(R.id.v_next_btn1)
  Button v_next_btn;

    //Button

    @BindView(R.id.v_back_btn1)
    Button v_back_btn;

    @BindView(R.id.btn_layout2)
    LinearLayout btn_layout2;

    @BindView(R.id.progressbar)
    AVLoadingIndicatorView progressbar;


    String private_commString,polySelectTypeString,prEnhanceString;
    String motorCycleTypeString,vehicleMakeString,vehicleTypeString,vehincleBodyString,startDateStrg;
    ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

    DatePickerDialog datePickerDialog1;
    String quote_price;
    ArrayList<String> covers=new ArrayList<>();

    public MotorInsureFragment2() {
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
    public static MotorInsureFragment2 newInstance(String param1, String param2) {
        MotorInsureFragment2 fragment = new MotorInsureFragment2();
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
        View view=inflater.inflate(R.layout.fragment_motor_insured2, container, false);
        ButterKnife.bind(this,view);
        //        stepView next registration step

        inputLayoutMotorCyValue.setClickable(false);
        userPreferences = new UserPreferences(getContext());

        stepView.go(currentStep, true);


        init();

        private_CommSpinner();

        polySelectSpinner();
       // privateTypeString = private_type_spinner.getSelectedItem().toString();
        pEnhancetypeSpinner();

        setViewActions();

        vehicleBodySpinner();
        showDatePicker();


        return  view;
    }


    private  void init(){
        UserPreferences userPreferences = new UserPreferences(getContext());

        //Temporal save and go to next Operation


        //start_date.setText(userPreferences.getMotorStartDate());

        vehicle_year.setText(userPreferences.getMotorVehicleYear());

        vehicle_reg_num.setText(userPreferences.getMotorVehicleRegNum());

        vehicle_chasis_num.setText(userPreferences.getMotorVehicleChasisNum());

        vehicle_engine_num.setText(userPreferences.getMotorVehicleEngNum());

        motor_cycle_value.setText(userPreferences.getMotorCycleValue());

        vehicle_value.setText(userPreferences.getMotorVehicleValue());

        fetchVehicleMaker();


    }




    private void private_CommSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.policy_type_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        private_comm_spinner.setAdapter(staticAdapter);

        private_comm_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String stringText = (String) parent.getItemAtPosition(position);
                if(stringText.equals("Private")){

                    private_comm_spinner.setVisibility(View.VISIBLE);
                    private_comm_spinner.setClickable(true);
                    //private_commString="private";


                    //De-Visualizing the corporate form

                    inputLayoutMotorCyValue.setVisibility(View.GONE);
                    inputLayoutMotorCyValue.setClickable(false);


                    //Visualizing the individual form
                    start_date.setVisibility(View.VISIBLE);
                    start_date.setClickable(true);
                    poly_select_type_spinner.setVisibility(View.VISIBLE);
                    poly_select_type_spinner.setClickable(true);
                    vehicletype_tag.setVisibility(View.VISIBLE);

                    covers.clear();
                    covers.add("Select Covers");
                    covers.add("3rd Party Only");
                    covers.add("3rd Party Fire & Theft");
                    covers.add("Enhanced 3rd Party");
                    covers.add("Comprehensive");

                    polySelectSpinner();

                    vehicle_make_spinner.setVisibility(View.VISIBLE);
                    vehicle_make_spinner.setClickable(true);
                    vehicle_type_spinner.setVisibility(View.VISIBLE);
                    vehicle_type_spinner.setClickable(true);
                    vehicle_body_type_spinner.setVisibility(View.VISIBLE);
                    vehicle_body_type_spinner.setClickable(true);
                    inputLayoutYear.setVisibility(View.VISIBLE);
                    inputLayoutYear.setClickable(true);
                    inputLayoutRegNum.setVisibility(View.VISIBLE);
                    inputLayoutRegNum.setClickable(true);
                    inputLayoutChasisNum.setVisibility(View.VISIBLE);
                    inputLayoutChasisNum.setClickable(true);
                    inputLayoutEngNum.setVisibility(View.VISIBLE);
                    inputLayoutEngNum.setClickable(true);
                    inputLayoutVehicleValue.setVisibility(View.VISIBLE);
                    inputLayoutVehicleValue.setClickable(true);


                }else if(stringText.equals("Commercial")){

                    private_comm_spinner.setVisibility(View.VISIBLE);
                    private_comm_spinner.setClickable(true);
                    // private_commString="commercial";

                    poly_select_type_spinner.setVisibility(View.VISIBLE);
                    poly_select_type_spinner.setClickable(true);
                    vehicletype_tag.setVisibility(View.VISIBLE);


                    covers.clear();
                    covers.add("Select Covers");
                    covers.add("3rd Party Only");
                    covers.add("Comprehensive");

                    polySelectSpinner();

                    //De-Visualizing the corporate form
                    prEnhance_type_spinner.setVisibility(View.GONE);
                    prEnhance_type_spinner.setClickable(false);
                    inputLayoutMotorCyValue.setVisibility(View.GONE);
                    inputLayoutMotorCyValue.setClickable(false);


                    //Visualizing the individual form
                    start_date.setVisibility(View.VISIBLE);
                    start_date.setClickable(true);
                    vehicle_make_spinner.setVisibility(View.VISIBLE);
                    vehicle_make_spinner.setClickable(true);
                    vehicle_type_spinner.setVisibility(View.VISIBLE);
                    vehicle_type_spinner.setClickable(true);
                    vehicle_body_type_spinner.setVisibility(View.VISIBLE);
                    vehicle_body_type_spinner.setClickable(true);
                    inputLayoutYear.setVisibility(View.VISIBLE);
                    inputLayoutYear.setClickable(true);
                    inputLayoutRegNum.setVisibility(View.VISIBLE);
                    inputLayoutRegNum.setClickable(true);
                    inputLayoutChasisNum.setVisibility(View.VISIBLE);
                    inputLayoutChasisNum.setClickable(true);
                    inputLayoutEngNum.setVisibility(View.VISIBLE);
                    inputLayoutEngNum.setClickable(true);
                    inputLayoutVehicleValue.setVisibility(View.VISIBLE);
                    inputLayoutVehicleValue.setClickable(true);

                }else if(stringText.equals("Motor Cycle")){
                    private_comm_spinner.setVisibility(View.VISIBLE);
                    private_comm_spinner.setClickable(true);
                    //private_commString="motor_cycle";
                    vehicleTypeString="motor_cycle";

                    poly_select_type_spinner.setVisibility(View.VISIBLE);
                    poly_select_type_spinner.setClickable(true);

                    vehicletype_tag.setVisibility(View.GONE);

                    covers.clear();
                    covers.add("Select Covers");
                    covers.add("3rd Party Only");
                    covers.add("Comprehensive");

                    polySelectSpinner();

                    //De-Visualizing the corporate form
                    prEnhance_type_spinner.setVisibility(View.GONE);
                    prEnhance_type_spinner.setClickable(false);
                    vehicle_make_spinner.setVisibility(View.GONE);
                    vehicle_make_spinner.setClickable(false);
                    vehicle_type_spinner.setVisibility(View.GONE);
                    vehicle_type_spinner.setClickable(false);
                    vehicle_body_type_spinner.setVisibility(View.GONE);
                    vehicle_body_type_spinner.setClickable(false);

                    inputLayoutVehicleValue.setVisibility(View.GONE);
                    inputLayoutVehicleValue.setClickable(false);



                    //Visualizing the individual form
                    start_date.setVisibility(View.VISIBLE);
                    start_date.setClickable(true);
                    inputLayoutYear.setVisibility(View.VISIBLE);
                    inputLayoutYear.setClickable(true);
                    inputLayoutRegNum.setVisibility(View.VISIBLE);
                    inputLayoutRegNum.setClickable(true);
                    inputLayoutChasisNum.setVisibility(View.VISIBLE);
                    inputLayoutChasisNum.setClickable(true);
                    inputLayoutEngNum.setVisibility(View.VISIBLE);
                    inputLayoutEngNum.setClickable(true);
                    inputLayoutMotorCyValue.setVisibility(View.VISIBLE);
                    inputLayoutMotorCyValue.setClickable(true);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                private_comm_spinner.getItemAtPosition(0);
                //De-Visualizing the individual form
                vehicletype_tag.setVisibility(View.GONE);
                poly_select_type_spinner.setVisibility(View.GONE);
                poly_select_type_spinner.setClickable(false);
                vehicle_body_type_spinner.setVisibility(View.GONE);
                vehicle_body_type_spinner.setClickable(false);
                vehicle_make_spinner.setVisibility(View.GONE);
                vehicle_make_spinner.setClickable(false);
                vehicle_type_spinner.setVisibility(View.GONE);
                vehicle_type_spinner.setClickable(false);

                inputLayoutMotorCyValue.setVisibility(View.GONE);
                inputLayoutMotorCyValue.setClickable(false);

                //Visualizing the individual form
                start_date.setVisibility(View.VISIBLE);
                start_date.setClickable(true);
                private_comm_spinner.setVisibility(View.VISIBLE);
                private_comm_spinner.setClickable(true);
                inputLayoutYear.setVisibility(View.VISIBLE);
                inputLayoutYear.setClickable(true);
                inputLayoutRegNum.setVisibility(View.VISIBLE);
                inputLayoutRegNum.setClickable(true);
                inputLayoutChasisNum.setVisibility(View.VISIBLE);
                inputLayoutChasisNum.setClickable(true);
                inputLayoutEngNum.setVisibility(View.VISIBLE);
                inputLayoutEngNum.setClickable(true);
                inputLayoutVehicleValue.setVisibility(View.VISIBLE);
                inputLayoutVehicleValue.setClickable(true);


            }
        });

    }

    private void polySelectSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner
        poly_select_type_spinner
                .setAdapter(new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        covers));


        poly_select_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String privateTypeString = (String) parent.getItemAtPosition(position);
                if(privateTypeString.equals("Enhanced 3rd Party")){
                    prEnhance_type_spinner.setVisibility(View.VISIBLE);
                    prEnhance_type_spinner.setClickable(true);
                }else{
                    prEnhance_type_spinner.setVisibility(View.GONE);
                    prEnhance_type_spinner.setClickable(false);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                poly_select_type_spinner.getItemAtPosition(0);
            }
        });

    }



    //Sub Spinners




    private void pEnhancetypeSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.pEnhance_type_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        prEnhance_type_spinner.setAdapter(staticAdapter);

        prEnhance_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String pEnhanceTypeString = (String) parent.getItemAtPosition(position);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                prEnhance_type_spinner.getItemAtPosition(0);
            }
        });

    }




    private void vehicleBodySpinner() {
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.vehicleBody_type_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        vehicle_body_type_spinner.setAdapter(staticAdapter);

        vehicle_body_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String vehicleBodyString = (String) parent.getItemAtPosition(position);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                vehicle_body_type_spinner.getItemAtPosition(0);
            }
        });

    }







    //seting onclicks listeners
    private void setViewActions() {

        v_next_btn.setOnClickListener(this);
        v_back_btn.setOnClickListener(this);
        start_date.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.v_next_btn1:
//                validate user input
                validateUserInputs();
                break;

            case R.id.start_date:
//                show date picker
                datePickerDialog1.show();

                break;

            case R.id.v_back_btn1:
                //do to previous fragment
                if (currentStep > 0) {
                    currentStep--;
                }
                stepView.done(false);
                stepView.go(currentStep, true);

                Fragment quoteBuyFragment1 = new MotorInsureFragment1();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_motor_form_container, quoteBuyFragment1);
                ft.commit();

                break;
        }
    }

    private void validateUserInputs() {

        boolean isValid = true;

        if (start_date.getText().toString().isEmpty()&&start_date.isClickable()) {
            showMessage("Your Start Date is required!");

            isValid = false;
        } else if (vehicle_year.getText().toString().isEmpty()&&inputLayoutYear.isClickable()) {
            inputLayoutYear.setError("Vehicle Year is required!");

            isValid = false;
        } else if (vehicle_reg_num.getText().toString().isEmpty()&&inputLayoutRegNum.isClickable()) {
            inputLayoutRegNum.setError("Vehicle Registration Number is required!");

            isValid = false;
        } else if (vehicle_chasis_num.getText().toString().isEmpty()&&inputLayoutChasisNum.isClickable()) {
            inputLayoutChasisNum.setError("Chasis Number is required!");

            isValid = false;
        } else if (vehicle_engine_num.getText().toString().isEmpty()&&inputLayoutEngNum.isClickable()) {
            inputLayoutEngNum.setError("Your Engine Number is required!");

            isValid = false;
        }else if (vehicle_value.getText().toString().isEmpty()&&inputLayoutVehicleValue.isClickable()) {
            inputLayoutVehicleValue.setError("Vehicle value Number is required!");

            isValid = false;
        }else {

            inputLayoutYear.setErrorEnabled(false);
            inputLayoutRegNum.setErrorEnabled(false);
            inputLayoutChasisNum.setErrorEnabled(false);
            inputLayoutEngNum.setErrorEnabled(false);
            inputLayoutVehicleValue.setErrorEnabled(false);
        }

        if (motor_cycle_value.getText().toString().isEmpty()&&inputLayoutMotorCyValue.isClickable()) {
            inputLayoutMotorCyValue.setError("Motor Cycle Value Number is required!");
            String b= String.valueOf(motor_cycle_value.isClickable());

            isValid = false;
        }else {
            inputLayoutMotorCyValue.setErrorEnabled(false);
        }


        // Spinner Validations
        //policyType validation
        private_commString = private_comm_spinner.getSelectedItem().toString();
        if (private_commString.equals("Select Policy Type")&&private_comm_spinner.isClickable()) {
            showMessage("Select Policy Type*");
            isValid = false;
        }
        //Private Spinner
        prEnhanceString = prEnhance_type_spinner.getSelectedItem().toString();
        if (prEnhanceString.equals("Select Enhanced 3rd Party*") && prEnhance_type_spinner.isClickable()) {
            showMessage("Select your Enhance Party Category");
            isValid = false;
        }

        //Private Spinner
        polySelectTypeString = poly_select_type_spinner.getSelectedItem().toString();

        if (polySelectTypeString.equals("Select Cover*") && poly_select_type_spinner.isClickable()) {
            showMessage("Select your Covers");
            isValid = false;
        }


        private_commString = private_comm_spinner.getSelectedItem().toString();
        if (!private_commString.equals("Motor Cycle")) {
            //VehincleMaker Spinner
            vehicleMakeString = vehicle_make_spinner.getSelectedItem().toString();
            if (vehicleMakeString.equals("Select Vehicle Maker*") && vehicle_make_spinner.isClickable()) {
                showMessage("Select your Motor Vehicle Maker");
                isValid = false;
            }

            //VehicleType Spinner

            vehicleTypeString = vehicle_type_spinner.getSelectedItem().toString();
            if (vehicleTypeString.equals("Select Brand*") && vehicle_type_spinner.isClickable()) {
                showMessage("Select your Motor Brand");
                isValid = false;
            }


            //VehicleBody Spinner
            vehincleBodyString = vehicle_body_type_spinner.getSelectedItem().toString();
            if (vehincleBodyString.equals("Select Body Type*") && vehicle_body_type_spinner.isClickable()) {
                showMessage("Select your Vehicle Body Type");
                isValid = false;
            }
        }


        if (isValid) {
//            send inputs to next next page
//            Goto to the next Registration step
            initFragment();
        }

    }

    private void sendVehicleData(PostVehicleData postVehicleData){


        //get client and call object for request
        ApiInterface client = ServiceGenerator.createService(ApiInterface.class);


        Call<QouteHead> call=client.getVehicleQuote("Token "+userPreferences.getUserToken(),postVehicleData);

        call.enqueue(new Callback<QouteHead>() {
            @Override
            public void onResponse(Call<QouteHead> call, Response<QouteHead> response) {
                Log.i("ResponseCode", String.valueOf(response.code()));

                if(response.code()==400){
                    showMessage("Check your internet connection");
                    btn_layout2.setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.GONE);
                    return;
                }else if(response.code()==429){
                    showMessage("Too many requests on database");
                    btn_layout2.setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.GONE);
                    return;
                }else if(response.code()==500){
                    showMessage("Server Error");
                    btn_layout2.setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.GONE);
                    return;
                }else if(response.code()==401){
                    showMessage("Unauthorized access, please try login again");
                    btn_layout2.setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.GONE);
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
                            btn_layout2.setVisibility(View.VISIBLE);
                            progressbar.setVisibility(View.GONE);

                        }
                        btn_layout2.setVisibility(View.VISIBLE);
                        progressbar.setVisibility(View.GONE);
                        return;
                    }

                    quote_price=response.body().getData().getPrice();

                    double roundOff = Math.round(Double.valueOf(quote_price)*100)/100.00;


                    Log.i("quote_price",quote_price);
                    showMessage("Successfully Fetched Quote");
                    btn_layout2.setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.GONE);

                    userPreferences.setInitQuotePrice(String.valueOf(roundOff));
                    if (!private_commString.equals("motor_cycle")) {
                        // Fragment quoteBuyFragment3 = new MotorInsureFragment3();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_motor_form_container, MotorInsureFragment3.newInstance(userPreferences.getMotorVehicleMake(), String.valueOf(roundOff)), MotorInsureFragment3.class.getSimpleName());
                        ft.commit();
                    } else {
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_motor_form_container, MotorInsureFragment3.newInstance("Motor Cycle ", String.valueOf(roundOff)), MotorInsureFragment3.class.getSimpleName());
                        ft.commit();
                    }
                }catch (Exception e){
                    Log.i("policyResponse", e.getMessage());
                    btn_layout2.setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.GONE);
                }

            }
            @Override
            public void onFailure(Call<QouteHead> call, Throwable t) {
                showMessage("Submission Failed "+t.getMessage());
                Log.i("GEtError",t.getMessage());
                btn_layout2.setVisibility(View.VISIBLE);
                progressbar.setVisibility(View.GONE);
            }
        });

    }


    private void initFragment() {
        btn_layout2.setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);

        try {
            switch (private_commString) {
                case "Private":
                    private_commString = "private";
                    value=vehicle_value.getText().toString();
                    break;
                case "Commercial":
                    private_commString = "commercial";
                    value=vehicle_value.getText().toString();
                    break;
                case "Motor Cycle":
                    private_commString = "motor_cycle";
                    vehicleTypeString="motor_cycle";
                    userPreferences.setMotorVehicleMake("Motor Cycle");
                    userPreferences.setMotorVehicleBody(" ");
                    value=motor_cycle_value.getText().toString();

                    break;

            }



            if(poly_select_type_spinner.isClickable()) {
                switch (polySelectTypeString) {
                    case "3rd Party Only":
                        polySelectTypeString = "third_party_only";
                        prEnhanceString = "third_party_only";
                        break;
                    case "3rd Party Fire & Theft":
                        polySelectTypeString = "third_party_fire_theft";
                        prEnhanceString = "third_party_fire_theft";
                        break;
                    case "Comprehensive":
                        polySelectTypeString = "comprehensive";
                        prEnhanceString = "comprehensive";
                        break;

                    case "Enhanced 3rd Party":
                        polySelectTypeString = "enhanced_third_party";
                        break;
                }

                if(prEnhance_type_spinner.isClickable()){
                    switch (prEnhanceString) {
                        case "Unique":
                            polySelectTypeString = "third_party_unique";
                            prEnhanceString="Third_Party";
                            break;
                        case "Luxury":
                            polySelectTypeString = "third_party_luxury";
                            prEnhanceString="Third_Party";
                            break;
                        case "Prestige":
                            polySelectTypeString = "third_party_prestige";
                            prEnhanceString="Third_Party";
                            break;
                    }
                }

                Log.i("Testing",private_commString+":"+polySelectTypeString+":"+prEnhanceString+":"+vehicleTypeString);

                PostVehicleData postVehicleData = new PostVehicleData(value, private_commString,
                        polySelectTypeString, prEnhanceString, vehicleTypeString);

                sendVehicleData(postVehicleData);

            }

            //Temporal save and go to next Operation

            userPreferences.setMotorStartDate(start_date.getText().toString());
            userPreferences.setMotorPolicyType(private_commString);
            userPreferences.setMotorPolySelectType(polySelectTypeString);
            userPreferences.setMotorPEnhanceType(prEnhanceString);
            userPreferences.setMotorCycleType(motorCycleTypeString);
            userPreferences.setMotorVehicleMake(vehicleMakeString);
            userPreferences.setMotorVehicleType(vehicleTypeString);
            userPreferences.setMotorVehicleBody(vehincleBodyString);
            userPreferences.setMotorVehicleYear(vehicle_year.getText().toString());
            userPreferences.setMotorVehicleRegNum(vehicle_reg_num.getText().toString());
            userPreferences.setMotorVehicleChasisNum(vehicle_chasis_num.getText().toString());
            userPreferences.setMotorVehicleEngNum(vehicle_engine_num.getText().toString());
            userPreferences.setMotorVehicleValue(vehicle_value.getText().toString());
            userPreferences.setMotorCycleValue(motor_cycle_value.getText().toString());





        }catch (Exception e){
            Log.i("Form Error",e.getMessage());
            progressbar.setVisibility(View.GONE);
            v_next_btn.setVisibility(View.VISIBLE);
            showMessage("Error: " + e.getMessage());
        }
    }


    private void showMessage(String s) {
        Snackbar.make(qb_form_layout2, s, Snackbar.LENGTH_LONG).show();
    }

    private void showDatePicker() {
        //Get current date
        Calendar calendar = Calendar.getInstance();

        //Create datePickerDialog with initial date which is current and decide what happens when a date is selected.
        datePickerDialog1 = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //When a date is selected, it comes here.
                //Change birthdayEdittext's text and dismiss dialog.
                if(year<calendar.get(Calendar.YEAR) ){
                    showMessage("Invalid Start Date");
                    datePickerDialog1.dismiss();
                    start_date.setText("");
                    Log.i("Calendar", year + " " + calendar.get(Calendar.YEAR));

                }else if( monthOfYear+1<calendar.get(Calendar.MONTH) ) {
                    showMessage("Invalid Start Date");
                    datePickerDialog1.dismiss();
                    start_date.setText("");
                    Log.i("Calendar", monthOfYear+1 + " " + calendar.get(Calendar.MONTH));

                }else if(dayOfMonth<calendar.get(Calendar.DAY_OF_MONTH)) {
                    showMessage("Invalid Start Date");
                    datePickerDialog1.dismiss();
                    start_date.setText("");
                    Log.i("Calendar", dayOfMonth + " " + calendar.get(Calendar.DAY_OF_MONTH));

                }else {
                    int monthofYear = monthOfYear + 1;
                    startDateStrg = year + "-" + monthofYear + "-" + dayOfMonth;
                    start_date.setText(startDateStrg);
                    datePickerDialog1.dismiss();
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void fetchVehicleMaker(){
        //get client and call object for request

        showMessage("Initializing page, please wait...");
        btn_layout2.setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);
        Call<Vehicles_Brand> call=client.vehicle_brand();
        call.enqueue(new Callback<Vehicles_Brand>() {
            @Override
            public void onResponse(Call<Vehicles_Brand> call, Response<Vehicles_Brand> response) {

                if(response.code()==400){
                    showMessage("Check your internet connection");
                    btn_layout2.setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.GONE);
                    return;
                }else if(response.code()==429){
                    showMessage("Too many requests on database");
                    btn_layout2.setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.GONE);
                    return;
                }else if(response.code()==500){
                    showMessage("Server Error");
                    btn_layout2.setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.GONE);
                    return;
                }else if(response.code()==401){
                    showMessage("Unauthorized access, please try login again");
                    btn_layout2.setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.GONE);
                    return;
                }
                try {
                    if (!response.isSuccessful()) {

                        try{
                            APIError apiError= ErrorUtils.parseError(response);

                            showMessage("Fetch Failed: "+apiError.getErrors());
                            Log.i("Fetch Failed",apiError.getErrors().toString());
                            Log.i("Fetch Failed",response.errorBody().toString());

                        }catch (Exception e){
                            Log.i("Fetch Failed",e.getMessage());
                            showMessage("Fetch Failed");

                        }
                        btn_layout2.setVisibility(View.VISIBLE);
                        progressbar.setVisibility(View.GONE);
                        return;
                    }

                    vehicleDataList = response.body().getVehicleData();

                    vehiclesMakerSpinnerList.add("Select Vehicle Maker");
                    for(int i=0; i<vehicleDataList.size();i++){
                        vehiclesMakerSpinnerList.add(vehicleDataList.get(i).getName());
                    }
                    vehicleMakerSpinner();

                    btn_layout2.setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.GONE);
                    showMessage("You can now select you policy..");

                }catch (Exception e){
                    showMessage("Fetch Error: " + e.getMessage());
                    btn_layout2.setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.GONE);
                }



            }

            @Override
            public void onFailure(Call<Vehicles_Brand> call, Throwable t) {
                showMessage("Fetch Failed "+t.getMessage());
                Log.i("GEtError",t.getMessage());
                btn_layout2.setVisibility(View.VISIBLE);
                progressbar.setVisibility(View.GONE);
            }
        });


    }
    private void vehicleMakerSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner
        vehicle_make_spinner
                .setAdapter(new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        vehiclesMakerSpinnerList));

        vehicle_make_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String VehicleMakerString = (String) parent.getItemAtPosition(position);
                if (position != 0) {
                    vehicleId = position;
                //get client and call object for request

                showMessage("Please wait...");
                btn_layout2.setVisibility(View.GONE);
                progressbar.setVisibility(View.VISIBLE);
                Call<VehicleBrandType> call=client.brand_type(vehicleId);

                call.enqueue(new Callback<VehicleBrandType>() {
                    @Override
                    public void onResponse(Call<VehicleBrandType> call, Response<VehicleBrandType> response) {

                        if(response.code()==400){
                            showMessage("Check your internet connection");
                            btn_layout2.setVisibility(View.VISIBLE);
                            progressbar.setVisibility(View.GONE);
                            return;
                        }else if(response.code()==429){
                            showMessage("Too many requests on database");
                            btn_layout2.setVisibility(View.VISIBLE);
                            progressbar.setVisibility(View.GONE);
                            return;
                        }else if(response.code()==500){
                            showMessage("Server Error");
                            btn_layout2.setVisibility(View.VISIBLE);
                            progressbar.setVisibility(View.GONE);
                            return;
                        }else if(response.code()==401){
                            showMessage("Unauthorized access, please try login again");
                            btn_layout2.setVisibility(View.VISIBLE);
                            progressbar.setVisibility(View.GONE);
                            return;
                        }

                        try {
                            if (!response.isSuccessful()) {

                                try{
                                    APIError apiError= ErrorUtils.parseError(response);

                                    showMessage("Fetch Failed: "+apiError.getErrors());
                                    Log.i("Fetch Failed",apiError.getErrors().toString());
                                    Log.i("Fetch Failed",response.errorBody().toString());

                                }catch (Exception e){
                                    Log.i("Fetch Failed",e.getMessage());
                                    showMessage("Fetch Failed");

                                }
                                btn_layout2.setVisibility(View.VISIBLE);
                                progressbar.setVisibility(View.GONE);
                                return;
                            }

                            vehicleBrandTypeList = response.body().getData();
                            vehiclesBrandSpinnerList.clear();
                            vehiclesBrandSpinnerList.add("Select Brand");
                            for(int i=0; i<vehicleBrandTypeList.size();i++){


                                vehiclesBrandSpinnerList.add(vehicleBrandTypeList.get(i).getName());
                            }

                            vehicleTypeSpinner();

                            btn_layout2.setVisibility(View.VISIBLE);
                            progressbar.setVisibility(View.GONE);
                            showMessage("Pick your Brand Type...");

                        }catch (Exception e){
                            showMessage("Fetch Error: " + e.getMessage());
                            btn_layout2.setVisibility(View.VISIBLE);
                            progressbar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<VehicleBrandType> call, Throwable t) {
                        showMessage("Fetch Failed "+t.getMessage());
                        Log.i("GEtError",t.getMessage());
                        btn_layout2.setVisibility(View.VISIBLE);
                        progressbar.setVisibility(View.GONE);
                    }
                });

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                vehicle_make_spinner.getItemAtPosition(0);
            }
        });

    }

    private void vehicleTypeSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner
        vehicle_type_spinner
                .setAdapter(new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        vehiclesBrandSpinnerList));

        vehicle_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String VehicleBraandTypeString = (String) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                vehicle_type_spinner.getItemAtPosition(0);
            }
        });

    }

}
