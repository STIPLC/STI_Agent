package com.example.sti_agent.operation_fragment.Marine;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.sti_agent.BuildConfig;
import com.example.sti_agent.Model.Errors.APIError;
import com.example.sti_agent.Model.Errors.ErrorUtils;
import com.example.sti_agent.Model.Marine.QouteHeadMarine;
import com.example.sti_agent.Model.ServiceGenerator;
import com.example.sti_agent.NetworkConnection;
import com.example.sti_agent.R;
import com.example.sti_agent.UserPreferences;
import com.example.sti_agent.retrofit_interface.ApiInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.shuhart.stepview.StepView;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MarineFragment2 extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    @BindView(R.id.qb_form_layout2)
    FrameLayout mQbFormLayout2;
    @BindView(R.id.step_view)
    StepView mStepView;
    @BindView(R.id.inputLayoutProfInvoice_m2)
    TextInputLayout mInputLayoutProfInvoiceM2;
    @BindView(R.id.prof_invoice_txt_m2)
    EditText mProfInvoiceTxtM2;
    @BindView(R.id.prof_invoice_date_txt_m2)
    EditText mProfInvoiceDateTxtM2;
    @BindView(R.id.inputLayoutDescGoods_m2)
    TextInputLayout mInputLayoutDescGoodsM2;
    @BindView(R.id.desc_goods_m2)
    EditText mDescGoodsM2;
    @BindView(R.id.cover_m2)
    Spinner cover_spinner;

    @BindView(R.id.start_date_cover_m2)
    EditText start_date_cover_m2;
    @BindView(R.id.inputLayoutQuantity_m2)
    TextInputLayout mInputLayoutQuantityM2;
    @BindView(R.id.quantity_txt_m2)
    EditText mQuantityTxtM2;
    @BindView(R.id.currency_spinner_m2)
    Spinner mCurrencySpinnerM2;
    @BindView(R.id.inputLayoutTotalAmt)
    TextInputLayout mInputLayoutTotalAmt;
    @BindView(R.id.total_amt_txt_m2)
    EditText mTotalAmtTxtM2;
    @BindView(R.id.inputLayoutConversionRate)
    TextInputLayout mInputLayoutConversionRate;
    @BindView(R.id.coversion_rate_txt_m2)
    EditText mCoversionRateTxtM2;
    @BindView(R.id.inputLayoutPortOfLoad)
    TextInputLayout mInputLayoutPortOfLoad;
    @BindView(R.id.portofload_txt_m2)
    EditText mPortofloadTxtM2;
    @BindView(R.id.inputLayoutPortDischarge_m2)
    TextInputLayout mInputLayoutPortDischargeM2;
    @BindView(R.id.portdischarge_txt_m2)
    EditText mPortdischargeTxtM2;
    @BindView(R.id.modeOfConvey_spinner_m2)
    Spinner mModeOfConveySpinnerM2;
    @BindView(R.id.upload_prof_doc_btn_m2)
    Button mUploadProfDocBtnM2;
    @BindView(R.id.btn_layout2_m2)
    LinearLayout mBtnLayout2M2;
    @BindView(R.id.v_back_btn2_m2)
    Button mVBackBtn2M2;
    @BindView(R.id.v_next_btn2_m2)
    Button mVNextBtn2M2;
    @BindView(R.id.progressbar2_m2)
    AVLoadingIndicatorView mProgressbar2M2;
    
   

    private int currentStep = 1;
    String currencyString,conveyString,profInvDateStrg,startCoverDateStrg,cameraFilePath,coverString;
    int PICK_IMAGE_DOC = 1;
    int CAM_IMAGE_PASSPORT = 2;
    
    NetworkConnection networkConnection=new NetworkConnection();
    DatePickerDialog datePickerDialog1,datePickerDialog2;

    Uri doc_img_uri;
    String doc_img_url;
    UserPreferences userPreferences;




    public MarineFragment2() {
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
    public static MarineFragment2 newInstance(String param1, String param2) {
        MarineFragment2 fragment = new MarineFragment2();
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
        View view=inflater.inflate(R.layout.fragment_marine2, container, false);
        ButterKnife.bind(this,view);
        //        mStepView next registration step
        userPreferences = new UserPreferences(getContext());

        mStepView.go(currentStep, true);

        init();


        currencySpinner();
        conveySpinner();
        converSpinner();
        setViewActions();
        showDatePicker1();
        showDatePicker2();

        return  view;
    }


    private  void init(){
        UserPreferences userPreferences = new UserPreferences(getContext());

        //Temporal save and go to next Operation


        mProfInvoiceTxtM2.setText(userPreferences.getMarineIProfInvNO());

        //mProfInvoiceDateTxtM2.setText(userPreferences.getMarineIDateProfInv());

        mDescGoodsM2.setText(userPreferences.getMarineIDescOfGoods());

        //start_date_cover_m2.setText(userPreferences.getMarineIStartDateCover());

        mQuantityTxtM2.setText(userPreferences.getMarineIQuantity());

        mTotalAmtTxtM2.setText(userPreferences.getMarineITotalAmount());

        mCoversionRateTxtM2.setText(userPreferences.getMarineINairaConvert());
        mPortofloadTxtM2.setText(userPreferences.getMarineIPortOfLoading());
        mPortdischargeTxtM2.setText(userPreferences.getMarineIPortOfDischarge());



    }
    
    



    private void currencySpinner() {
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.currency_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        mCurrencySpinnerM2.setAdapter(staticAdapter);

        mCurrencySpinnerM2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String currencyString = (String) parent.getItemAtPosition(position);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCurrencySpinnerM2.getItemAtPosition(0);
            }
        });

    }

    private void converSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.cover_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        cover_spinner.setAdapter(staticAdapter);

        cover_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String coverText = (String) parent.getItemAtPosition(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cover_spinner.getItemAtPosition(0);


            }
        });

    }



    private void conveySpinner() {
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.convey_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        mModeOfConveySpinnerM2.setAdapter(staticAdapter);

        mModeOfConveySpinnerM2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String conveyString = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mModeOfConveySpinnerM2.getItemAtPosition(0);
            }
        });

    }

    //seting onclicks listeners
    private void setViewActions() {

        mVNextBtn2M2.setOnClickListener(this);
        mVBackBtn2M2.setOnClickListener(this);
        mProfInvoiceDateTxtM2.setOnClickListener(this);
        mUploadProfDocBtnM2.setOnClickListener(this);
        start_date_cover_m2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.v_next_btn2_m2:
//                validate user input
                validateUserInputs();
                break;

            case R.id.prof_invoice_date_txt_m2:
                datePickerDialog1.show();
                break;

            case R.id.start_date_cover_m2:
                datePickerDialog2.show();
                break;

            case R.id.v_back_btn2_m2:
                if (currentStep > 0) {
                    currentStep--;
                }
                mStepView.done(false);
                mStepView.go(currentStep, true);

                Fragment marineFragment1 = new MarineFragment1();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_marine_form_container, marineFragment1);
                ft.commit();

                break;
            case R.id.upload_prof_doc_btn_m2:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Choose Mode of Entry");
// add a list
                String[] entry = {"Camera", "Gallery"};
                builder.setItems(entry, (dialog, option) -> {
                    switch (option) {
                        case 0:
                            // direct entry
                            chooseIdImage_camera();
                            dialog.dismiss();
                            break;

                        case 1: // export

                            chooseImageFile();
                            dialog.dismiss();
                            break;

                    }
                });
// create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
                mUploadProfDocBtnM2.setBackgroundColor(getResources().getColor(R.color.colorAccentEnds));

                break;
                
        }
    }


    private void chooseImageFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_DOC);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //This is the directory in which the file will be created. This is the default location of Camera photos
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for using again
        cameraFilePath = "file://" + image.getAbsolutePath();
        return image;
    }



    private void chooseIdImage_camera() {

        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".fileprovider", createImageFile()));
            startActivityForResult(intent, CAM_IMAGE_PASSPORT);
        } catch (IOException ex) {
            ex.printStackTrace();
            showMessage("Invalid Entry");
            Log.i("Invalid_Cam_Entry",ex.getMessage());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0 ) {
            showMessage("No image is selected, try again");
            return;
        }


        showMessage("Uploading...");
        if (networkConnection.isNetworkConnected(getContext())) {
            Random random=new Random();
            String rand= String.valueOf(random.nextInt());
            if (requestCode == 1) {
                doc_img_uri = data.getData();

                try {
                    if (doc_img_uri != null) {
                        String name = mProfInvoiceTxtM2.getText().toString()+rand;
                        if (name.equals("")) {
                            showMessage("Enter your Proforma Invoice Number first");

                        } else {

                            String imageId = MediaManager.get().upload(Uri.parse(doc_img_uri.toString()))
                                    .option("public_id", "user_registration/profile_photos/user_passport" + name)
                                    .unsigned("xbiscrhh").callback(new UploadCallback() {
                                        @Override
                                        public void onStart(String requestId) {
                                            // your code here
                                            mBtnLayout2M2.setVisibility(View.GONE);
                                            mProgressbar2M2.setVisibility(View.VISIBLE);

                                        }

                                        @Override
                                        public void onProgress(String requestId, long bytes, long totalBytes) {
                                            // example code starts here
                                            Double progress = (double) bytes / totalBytes;
                                            // post progress to app UI (e.g. progress bar, notification)
                                            // example code ends here
                                            mProgressbar2M2.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onSuccess(String requestId, Map resultData) {
                                            // your code here

                                            showMessage("Image Uploaded Successfully");
                                            Log.i("ImageRequestId ", requestId);
                                            Log.i("ImageUrl ", String.valueOf(resultData.get("url")));
                                            mProgressbar2M2.setVisibility(View.GONE);
                                            mBtnLayout2M2.setVisibility(View.VISIBLE);
                                            doc_img_url = String.valueOf(resultData.get("url"));


                                        }

                                        @Override
                                        public void onError(String requestId, ErrorInfo error) {
                                            // your code here
                                            showMessage("Error: " + error.toString());
                                            Log.i("Error: ", error.toString());

                                            mBtnLayout2M2.setVisibility(View.VISIBLE);
                                            mProgressbar2M2.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onReschedule(String requestId, ErrorInfo error) {
                                            // your code here
                                        }
                                    })
                                    .dispatch();

                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    showMessage("Please Check your Image");

                }

            }else if(requestCode==2){
                doc_img_uri = Uri.parse(cameraFilePath);

                try {
                    if (doc_img_uri != null) {
                        String name = mProfInvoiceTxtM2.getText().toString()+rand;
                        if (name.equals("")) {
                            showMessage("Enter your Proforma Invoice Number first");

                        } else {

                            String imageId = MediaManager.get().upload(doc_img_uri)
                                    .option("public_id", "user_registration/profile_photos/user_passport" + name)
                                    .unsigned("xbiscrhh").callback(new UploadCallback() {
                                        @Override
                                        public void onStart(String requestId) {
                                            // your code here
                                            mBtnLayout2M2.setVisibility(View.GONE);
                                            mProgressbar2M2.setVisibility(View.VISIBLE);

                                        }

                                        @Override
                                        public void onProgress(String requestId, long bytes, long totalBytes) {
                                            // example code starts here
                                            Double progress = (double) bytes / totalBytes;
                                            // post progress to app UI (e.g. progress bar, notification)
                                            // example code ends here
                                            mProgressbar2M2.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onSuccess(String requestId, Map resultData) {
                                            // your code here

                                            showMessage("Image Uploaded Successfully");
                                            Log.i("ImageRequestId ", requestId);
                                            Log.i("ImageUrl ", String.valueOf(resultData.get("url")));
                                            mProgressbar2M2.setVisibility(View.GONE);
                                            mBtnLayout2M2.setVisibility(View.VISIBLE);
                                            doc_img_url = String.valueOf(resultData.get("url"));


                                        }

                                        @Override
                                        public void onError(String requestId, ErrorInfo error) {
                                            // your code here
                                            showMessage("Error: " + error.toString());
                                            Log.i("Error: ", error.toString());

                                            mBtnLayout2M2.setVisibility(View.VISIBLE);
                                            mProgressbar2M2.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onReschedule(String requestId, ErrorInfo error) {
                                            // your code here
                                        }
                                    })
                                    .dispatch();

                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    showMessage("Please Check your Image");

                }

            }
            return;
        }
        showMessage("No Internet connection discovered!");
    }

    private void validateUserInputs() {


        boolean isValid = true;

        if (mProfInvoiceTxtM2.getText().toString().isEmpty()) {
            mInputLayoutProfInvoiceM2.setError("Your Proforma Invoice Number is required!");
            isValid = false;
        } else if (mProfInvoiceDateTxtM2.getText().toString().isEmpty()) {
           showMessage("Your Proforma Invoice date is required!");
            isValid = false;
        } else if (mDescGoodsM2.getText().toString().isEmpty()) {
            mInputLayoutDescGoodsM2.setError("Your Goods description is required!");

            isValid = false;
        }else if (start_date_cover_m2.getText().toString().isEmpty()) {
            showMessage("Start Date Cover is required!");

            isValid = false;
        }  else if (mQuantityTxtM2.getText().toString().isEmpty()) {
            mInputLayoutQuantityM2.setError("Quantity is required!");

            isValid = false;
        }else if (mTotalAmtTxtM2.getText().toString().isEmpty()) {
            mInputLayoutTotalAmt.setError("Total Amount or Value on Proforma invoice is required!");
            isValid = false;
        }else {
            mInputLayoutProfInvoiceM2.setErrorEnabled(false);
            mInputLayoutTotalAmt.setErrorEnabled(false);
            mInputLayoutDescGoodsM2.setErrorEnabled(false);
            mInputLayoutQuantityM2.setErrorEnabled(false);

        }

        if (mCoversionRateTxtM2.getText().toString().isEmpty()) {
            mInputLayoutConversionRate.setError("Conversion rate is required!");

            isValid = false;
        }else {
            mInputLayoutConversionRate.setErrorEnabled(false);
        }

        if (mPortofloadTxtM2.getText().toString().isEmpty()) {
            mInputLayoutProfInvoiceM2.setError("Port of Loading is required!");

            isValid = false;
        }else {
            mInputLayoutProfInvoiceM2.setErrorEnabled(false);
        }

        if (mPortdischargeTxtM2.getText().toString().isEmpty()) {
            mInputLayoutPortDischargeM2.setError("Port of Discharge is required!");

            isValid = false;
        }else {
            mInputLayoutPortDischargeM2.setErrorEnabled(false);
        }


        // Spinner Validations
        //currency spinner
        currencyString = mCurrencySpinnerM2.getSelectedItem().toString();
        if (currencyString.equals("Select Currency")) {
            currencyString = "--";
            //  isValid = false;
        }
        //mode of convey Spinner
        conveyString = mModeOfConveySpinnerM2.getSelectedItem().toString();
        if (conveyString.equals("Mode of Conveyance")) {
            conveyString = "--";
            //isValid = false;
        }

        coverString = cover_spinner.getSelectedItem().toString();
        if (coverString.equals("Select Cover")) {
            coverString = "--";
            //isValid = false;
        }

        if (doc_img_url == null) {
            showMessage("Please upload the proforma invoice image");
            isValid = false;
        }



        if (isValid) {
//            send inputs to next next page
//            Goto to the next Registration step
            initFragment();
        }




    }

    private void initFragment() {
        mBtnLayout2M2.setVisibility(View.GONE);
        mProgressbar2M2.setVisibility(View.VISIBLE);

        try {


            //Temporal save and go to next Operation

            userPreferences.setMarineIProfInvNO(mProfInvoiceTxtM2.getText().toString());
            userPreferences.setMarineICurrency(currencyString);
            userPreferences.setMarineIModeOfConvey(conveyString);
            userPreferences.setMarineIDateProfInv(mProfInvoiceDateTxtM2.getText().toString());
            userPreferences.setMarineIDescOfGoods(mDescGoodsM2.getText().toString());
            userPreferences.setMarineIStartDateCover(start_date_cover_m2.getText().toString());
            userPreferences.setMarineICover(coverString);
            userPreferences.setMarineIQuantity(mQuantityTxtM2.getText().toString());
            userPreferences.setMarineITotalAmount(mTotalAmtTxtM2.getText().toString());
            userPreferences.setMarineINairaConvert(mCoversionRateTxtM2.getText().toString());
            userPreferences.setMarineIPortOfLoading(mPortofloadTxtM2.getText().toString());
            userPreferences.setMarineIPortOfDischarge(mPortdischargeTxtM2.getText().toString());
            userPreferences.setMarineIProfImage(doc_img_url);
            sendMarineData();

        }catch (Exception e){
            Log.i("Form Error",e.getMessage());
            mProgressbar2M2.setVisibility(View.GONE);
            mBtnLayout2M2.setVisibility(View.VISIBLE);
            showMessage("Error: " + e.getMessage());
        }
    }

    private void sendMarineData(){

        //get client and call object for request
        ApiInterface client = ServiceGenerator.createService(ApiInterface.class);
        Call<QouteHeadMarine> call=client.marine_quote("Token "+userPreferences.getUserToken(),mTotalAmtTxtM2.getText().toString(),mCoversionRateTxtM2.getText().toString());

        call.enqueue(new Callback<QouteHeadMarine>() {
            @Override
            public void onResponse(Call<QouteHeadMarine> call, Response<QouteHeadMarine> response) {
                Log.i("ResponseCode", String.valueOf(response.code()));


                if(response.code()==400){
                    showMessage("Check your internet connection");
                    mBtnLayout2M2.setVisibility(View.VISIBLE);
                    mProgressbar2M2.setVisibility(View.GONE);
                    return;
                }else if(response.code()==429){
                    showMessage("Too many requests on database");
                    mBtnLayout2M2.setVisibility(View.VISIBLE);
                    mProgressbar2M2.setVisibility(View.GONE);
                    return;
                }else if(response.code()==500){
                    showMessage("Server Error");
                    mBtnLayout2M2.setVisibility(View.VISIBLE);
                    mProgressbar2M2.setVisibility(View.GONE);
                    return;
                }else if(response.code()==401){
                    showMessage("Unauthorized access, please try login again");
                    mBtnLayout2M2.setVisibility(View.VISIBLE);
                    mProgressbar2M2.setVisibility(View.GONE);
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
                            mBtnLayout2M2.setVisibility(View.VISIBLE);
                            mProgressbar2M2.setVisibility(View.GONE);

                        }
                        mBtnLayout2M2.setVisibility(View.VISIBLE);
                        mProgressbar2M2.setVisibility(View.GONE);
                        return;
                    }

                    double quote_price=response.body().getData().getPrice();
                    double sum_insured=response.body().getData().getSum_insured();
                    Log.i("insured_price", String.valueOf(sum_insured));

                    double roundOff = Math.round(quote_price*100)/100.00;
                    double roundOffInsured = Math.round(sum_insured * 100) / 100.00;




                    mBtnLayout2M2.setVisibility(View.VISIBLE);
                    mProgressbar2M2.setVisibility(View.GONE);

                    String price_coverted = String.format("%.2f", roundOff);
                    String sum_covert = String.format("%.2f", roundOffInsured);

                    Log.i("quote_price", price_coverted);
                    Log.i("insured_amt", sum_covert);

                    // Fragment quoteBuyFragment3 = new MarineInsureFragment3();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_marine_form_container, MarineFragment3.newInstance(sum_covert, price_coverted), MarineFragment3.class.getSimpleName());
                    ft.commit();
                    showMessage("Successfully Fetched Quote");

                }catch (Exception e){
                    Log.i("policyResponse", e.getMessage());
                    mBtnLayout2M2.setVisibility(View.VISIBLE);
                    mProgressbar2M2.setVisibility(View.GONE);
                }

            }
            @Override
            public void onFailure(Call<QouteHeadMarine> call, Throwable t) {
                showMessage("Submission Failed "+t.getMessage());
                Log.i("GEtError",t.getMessage());
                mBtnLayout2M2.setVisibility(View.VISIBLE);
                mProgressbar2M2.setVisibility(View.GONE);
            }
        });

    }



    private void showMessage(String s) {
        Snackbar.make(mQbFormLayout2, s, Snackbar.LENGTH_LONG).show();
    }



    private void showDatePicker1() {
        //Get current date
        Calendar calendar = Calendar.getInstance();

        //Create datePickerDialog with initial date which is current and decide what happens when a date is selected.
        datePickerDialog1 = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int monthofYear=monthOfYear+1;
                profInvDateStrg = year + "-" + monthofYear + "-" +dayOfMonth ;
                mProfInvoiceDateTxtM2.setText(profInvDateStrg);
                datePickerDialog1.dismiss();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void showDatePicker2() {
        //Get current date
        Calendar calendar = Calendar.getInstance();

        //Create datePickerDialog with initial date which is current and decide what happens when a date is selected.
        datePickerDialog2 = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if(year<calendar.get(Calendar.YEAR) ){
                    showMessage("Invalid Start Date");
                    datePickerDialog1.dismiss();
                    start_date_cover_m2.setText("");
                    Log.i("Calendar", year + " " + calendar.get(Calendar.YEAR));

                }else if( monthOfYear+1<calendar.get(Calendar.MONTH) ) {
                    showMessage("Invalid Start Date");
                    datePickerDialog1.dismiss();
                    start_date_cover_m2.setText("");
                    Log.i("Calendar", monthOfYear+1 + " " + calendar.get(Calendar.MONTH));

                }else if(dayOfMonth<calendar.get(Calendar.DAY_OF_MONTH)) {
                    showMessage("Invalid Start Date");
                    datePickerDialog1.dismiss();
                    start_date_cover_m2.setText("");
                    Log.i("Calendar", dayOfMonth + " " + calendar.get(Calendar.DAY_OF_MONTH));

                }else {


                    int monthofYear = monthOfYear + 1;
                    startCoverDateStrg = year + "-" + monthofYear + "-" + dayOfMonth;
                    start_date_cover_m2.setText(startCoverDateStrg);
                    datePickerDialog2.dismiss();

                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }


}
