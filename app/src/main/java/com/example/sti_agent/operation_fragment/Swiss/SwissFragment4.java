package com.example.sti_agent.operation_fragment.Swiss;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sti_agent.Constant;
import com.example.sti_agent.MainActivity;
import com.example.sti_agent.Model.Errors.APIError;
import com.example.sti_agent.Model.Errors.ErrorUtils;
import com.example.sti_agent.Model.ServiceGenerator;
import com.example.sti_agent.Model.Swiss.AdditionInsured;
import com.example.sti_agent.Model.Swiss.FormSuccessDetail.BuyQuoteFormGetHead_Swiss;
import com.example.sti_agent.Model.Swiss.FormSuccessDetail.Policy;
import com.example.sti_agent.Model.Swiss.Personal_Detail_swiss;
import com.example.sti_agent.Model.Swiss.SwissInsured;
import com.example.sti_agent.Model.Swiss.SwissPost.AdditionalInsuredPost;
import com.example.sti_agent.Model.Swiss.SwissPost.Swiss;
import com.example.sti_agent.Model.Swiss.SwissPost.SwissPersona;
import com.example.sti_agent.Model.Swiss.SwissPost.SwissPostHead;
import com.example.sti_agent.NetworkConnection;
import com.example.sti_agent.R;
import com.example.sti_agent.UserPreferences;
import com.example.sti_agent.adapter.AddInsuredListAdapter;
import com.example.sti_agent.fragment.TransactionHistoryFragment;
import com.example.sti_agent.operation_activity.PolicyPaymentActivity;
import com.example.sti_agent.operation_activity.PolicyWalletPayActivity;
import com.example.sti_agent.retrofit_interface.ApiInterface;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.shuhart.stepview.StepView;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwissFragment4 extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match

    private static final String PRIMARY_KEY = "primaryKey";
    private static final String FOREIGN_KEY = "foreignKey";


    // TODO: Rename and change types of parameters
    private String primaryKey,foreignKey;

    @BindView(R.id.qb_form_layout4)
    FrameLayout mQbFormLayout4;
    @BindView(R.id.step_view)
    StepView mStepView;
    @BindView(R.id.personal_info_s4)
    TextView mPersonalInfoS3;
    @BindView(R.id.fabShowAddInsure_s4)
    FloatingActionButton mFabShowAddInsureS3;
    @BindView(R.id.btn_layout4_s4)
    LinearLayout mBtnLayout4S4;
    @BindView(R.id.v_back_btn4_s4)
    Button mVBackBtn4S4;
    @BindView(R.id.v_next_btn4_s4)
    Button mVNextBtn4S4;
    @BindView(R.id.progressbar4_s4)
    AVLoadingIndicatorView mProgressbar4S4;
    @BindView(R.id.inputLayoutPin_s4)
    TextInputLayout inputLayoutPin_s4;

    @BindView(R.id.pin_txt_s4)
    EditText pin_txt_s4;

    @BindView(R.id.modeOfPayment_spinner_s4)
    Spinner modeOfPayment_spinner_s4;



    private  int currentStep=3;
    Realm realm;
    AddInsuredListAdapter addInsureListAdapter;

    String modeofPaymentString;
    UserPreferences userPreferences;
    SwissInsured swissInsured;
    RealmList<Personal_Detail_swiss> personal_detail_swisses;
    //Swiss return policy
    List<Policy> policy;

    String total_quoteprice;

    List<AdditionalInsuredPost> additionaList_post=new ArrayList<AdditionalInsuredPost>();

    NetworkConnection networkConnection=new NetworkConnection();
    Swiss swiss;
    String policy_num="";
    String total_price="";
    String ref="";




    public SwissFragment4() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     *
     * @return A new instance of fragment Fragment_Dashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static SwissFragment4 newInstance(String param1) {
        SwissFragment4 fragment = new SwissFragment4();
        Bundle args = new Bundle();
        args.putString(PRIMARY_KEY,param1);
        //args.putString(FOREIGN_KEY,param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            primaryKey = getArguments().getString(PRIMARY_KEY);
            //foreignKey = getArguments().getString(FOREIGN_KEY);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_swiss4, container, false);
        ButterKnife.bind(this,view);
        //  mStepView next registration step
        mStepView.go(currentStep, true);
        userPreferences=new UserPreferences(getContext());
        realm= Realm.getDefaultInstance();


        init();
        modeofPaymentSpinner();
        setViewActions();

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("backPress_KeyCode", "keyCode: " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Log.i("backPress", "onKey Back listener is working!!!");
                    asyncSwissInsured(primaryKey);
                    userPreferences.setTempSwissQuotePrice("0.0");
                    userPreferences.setSwissIPersonal_QuotePrice(0);
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    return true;
                }
                return false;
            }
        });



        return  view;
    }

    private void modeofPaymentSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.mode_of_payment,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        modeOfPayment_spinner_s4.setAdapter(staticAdapter);

        modeOfPayment_spinner_s4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String modeofPaymentTypeString = (String) parent.getItemAtPosition(position);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                modeOfPayment_spinner_s4.getItemAtPosition(0);
            }
        });

    }
    private void init(){

        UserPreferences userPreferences=new UserPreferences(getContext());

        //retrieve data for personal detail first


        swissInsured=realm.where(SwissInsured.class).equalTo("id",primaryKey).findFirst();
        total_quoteprice=swissInsured.getQuote_price();
        personal_detail_swisses=swissInsured.getPersonal_detail_swisses();

        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
        nf.setMaximumFractionDigits(2);
        DecimalFormat df = (DecimalFormat) nf;
        String v_price = df.format(Double.valueOf(total_quoteprice));

        String insurer = "First Person Insured " + "\n" + "Prefix: " + personal_detail_swisses.get(0).getPrefix() + "\n" + "First Name: " + personal_detail_swisses.get(0).getFirst_name() + "\n" +
                "Last Name: " + personal_detail_swisses.get(0).getLast_name() + "\n" + "Date of Birth: " + personal_detail_swisses.get(0).getDate_of_birth() + "\n" + "Phone Number: " + personal_detail_swisses.get(0).getPhone() + "\n" +
                "Gender: " + personal_detail_swisses.get(0).getResident_address() + "\n" + "Overall Total Premium: ₦" + v_price;
            mPersonalInfoS3.setText(insurer);


    }



    //seting onclicks listeners
    private void setViewActions() {

        mVNextBtn4S4.setOnClickListener(this);
        mVBackBtn4S4.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.v_next_btn4_s4:
//                send quote to client and sti
                ValidateForm();
                break;

            case R.id.v_back_btn4_s4:

                mStepView.go(1, true);
                asyncSwissInsured(primaryKey);
                userPreferences.setTempSwissQuotePrice("0.0");
                userPreferences.setSwissIPersonal_QuotePrice(0);
                Fragment swissFragment2 = new SwissFragment2();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_swiss_form_container, swissFragment2);
                ft.commit();

                break;
        }
    }




    @OnClick(R.id.fabShowAddInsure_s4)
    public void showBottomAddInsureList() {
        View view = getLayoutInflater().inflate(R.layout.fragment_bottom_sheet_vehicles, null);
        final TextView textView = (TextView) view.findViewById(R.id.detail);
        final RecyclerView recycler_vehicles = (RecyclerView) view.findViewById(R.id.recycler_detail);
        final ImageView close = (ImageView) view.findViewById(R.id.close);
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mStepView.go(5, true);
                dialog.dismiss();
            }
        });

        //retrieve data
        final RealmResults<AdditionInsured> results;
        //String title;
        results=realm.where(AdditionInsured.class).findAll();

        if(results==null){
            showMessage("No Additional Insured");
            return;
        }

        //Bind
        addInsureListAdapter=new AddInsuredListAdapter(results,getActivity().getBaseContext());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity().getBaseContext(),RecyclerView.VERTICAL,false);
        recycler_vehicles.setLayoutManager(linearLayoutManager);
        recycler_vehicles.setAdapter(addInsureListAdapter);

        dialog.setContentView(view);
        dialog.show();
    }

    private void ValidateForm() {

        if (networkConnection.isNetworkConnected(getContext())) {
            boolean isValid = true;

            if (pin_txt_s4.getText().toString().isEmpty()) {
                inputLayoutPin_s4.setError("Pin is required!");
                isValid = false;
            } else if (pin_txt_s4.getText().toString().trim().length()!=4) {
                inputLayoutPin_s4.setError("Invalid pin entered!");
                isValid = false;
            } else if (!pin_txt_s4.getText().toString().trim().equals(userPreferences.getAgentPin())) {
                inputLayoutPin_s4.setError("Invalid pin entered!");
                isValid = false;
            }else {
                inputLayoutPin_s4.setErrorEnabled(false);
            }
            //mode of payment
            modeofPaymentString = modeOfPayment_spinner_s4.getSelectedItem().toString();
            if (modeofPaymentString.equals("Mode of Payment")) {
                showMessage("Select your mode of payment");
                isValid = false;
            }

            if (isValid) {

                //Post Request to Api

                mSubmit();
            }

            return;
        }
        showMessage("No Internet connection discovered!");
    }

    private void mSubmit() {

        mBtnLayout4S4.setVisibility(View.GONE);
        mProgressbar4S4.setVisibility(View.VISIBLE);
        String pin=pin_txt_s4.getText().toString();

//retrieve data
        final RealmResults<AdditionInsured> results;

        //String title;
        results=realm.where(AdditionInsured.class).findAll();
        if (results == null) {


            SwissPersona swissPersona = new SwissPersona(personal_detail_swisses.get(0).getFirst_name(), personal_detail_swisses.get(0).getLast_name(), personal_detail_swisses.get(0).getEmail(), personal_detail_swisses.get(0).getGender(), personal_detail_swisses.get(0).getDate_of_birth()
                    , personal_detail_swisses.get(0).getPhone(), personal_detail_swisses.get(0).getResident_address(), "null",
                    personal_detail_swisses.get(0).getMarital_status(), personal_detail_swisses.get(0).getPicture(), personal_detail_swisses.get(0).getNext_of_kin(), personal_detail_swisses.get(0).getNext_of_kin_phone(), personal_detail_swisses.get(0).getPrice(), personal_detail_swisses.get(0).getNext_of_kin_address()
                    , personal_detail_swisses.get(0).getDisability());

            swiss = new Swiss("12 Months");

            SwissPostHead swissPostHead = new SwissPostHead(swissPersona, swiss, total_quoteprice, pin
                    , modeofPaymentString, userPreferences.getUserId());

            sendPolicy(swissPostHead);

        } else {
            for (int i = 0; i < results.size(); i++) {
                AdditionInsured additionInsured = results.get(i);
                AdditionalInsuredPost additionalInsuredPost = new AdditionalInsuredPost(additionInsured.getFirst_name(), additionInsured.getLast_name(),
                        additionInsured.getEmail(), additionInsured.getGender(), additionInsured.getPhone(), additionInsured.getDate_of_birth(),
                        additionInsured.getMarital_status(), additionInsured.getPicture(), additionInsured.getDisability(), additionInsured.getPrice());
                additionaList_post.add(additionalInsuredPost);

                Log.i("policyItemLoop", additionInsured.getMarital_status());

            }

            SwissPersona swissPersona =new SwissPersona(personal_detail_swisses.get(0).getFirst_name(),personal_detail_swisses.get(0).getLast_name(),personal_detail_swisses.get(0).getEmail(),personal_detail_swisses.get(0).getGender(),personal_detail_swisses.get(0).getDate_of_birth()
                    ,personal_detail_swisses.get(0).getPhone(),personal_detail_swisses.get(0).getResident_address(),"null",
                    personal_detail_swisses.get(0).getMarital_status(), personal_detail_swisses.get(0).getPicture(), personal_detail_swisses.get(0).getNext_of_kin(), personal_detail_swisses.get(0).getNext_of_kin_phone(), personal_detail_swisses.get(0).getPrice(), personal_detail_swisses.get(0).getNext_of_kin_address()
                    ,personal_detail_swisses.get(0).getDisability(),additionaList_post);

            swiss=new Swiss("12 Months");

            SwissPostHead swissPostHead = new SwissPostHead(swissPersona, swiss, total_quoteprice, pin
                    , modeofPaymentString, userPreferences.getUserId());


            sendPolicy(swissPostHead);
        }






    }

    private void sendPolicy(SwissPostHead swissPostHead){


        //get client and call object for request
        ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

        Call<BuyQuoteFormGetHead_Swiss> call=client.swiss_policy("Token "+userPreferences.getUserToken(),swissPostHead);

        call.enqueue(new Callback<BuyQuoteFormGetHead_Swiss>() {
            @Override
            public void onResponse(Call<BuyQuoteFormGetHead_Swiss> call, Response<BuyQuoteFormGetHead_Swiss> response) {
                Log.i("ResponseCode", String.valueOf(response.code()));
                if(response.code()==400){
                    failed_alert("Check your internet connection");
                    mBtnLayout4S4.setVisibility(View.VISIBLE);
                    mProgressbar4S4.setVisibility(View.GONE);
                    return;
                }else if(response.code()==429){
                    failed_alert("Too many requests on database");
                    mBtnLayout4S4.setVisibility(View.VISIBLE);
                    mProgressbar4S4.setVisibility(View.GONE);
                    return;
                }else if(response.code()==500){
                    failed_alert("Server Error");
                    mBtnLayout4S4.setVisibility(View.VISIBLE);
                    mProgressbar4S4.setVisibility(View.GONE);
                    return;
                }else if(response.code()==401){
                    failed_alert("Unauthorized access, please try login again");
                    mBtnLayout4S4.setVisibility(View.VISIBLE);
                    mProgressbar4S4.setVisibility(View.GONE);
                    return;
                }

                try {
                    if (!response.isSuccessful()) {

                        try{
                            APIError apiError= ErrorUtils.parseError(response);

                            failed_alert("Invalid Entry \n" + apiError.getErrors());
                            Log.i("Invalid EntryK", apiError.getErrors().toString());
                            Log.i("Invalid Entry",response.errorBody().toString());

                        }catch (Exception e){
                            Log.i("InvalidEntry",e.getMessage());
                            Log.i("ResponseError",response.errorBody().string());
                            failed_alert("Failed to Submit, try again\n" + e.getMessage());
                            mBtnLayout4S4.setVisibility(View.VISIBLE);
                            mProgressbar4S4.setVisibility(View.GONE);

                        }
                        mBtnLayout4S4.setVisibility(View.VISIBLE);
                        mProgressbar4S4.setVisibility(View.GONE);
                        return;
                    }

                    policy=response.body().getData().getPolicy();
                    for(int i=0;i<policy.size();i++){
                        policy_num=policy_num.concat("\n"+policy.get(i).getPolicyNumber());
                    }

                    total_price= String.valueOf(response.body().getData().getTransactions().getAmount());
                    ref= response.body().getData().getTransactions().getReference();

                    Log.i("policyNum", policy_num);
                    Log.i("totalPrice", total_price);


                    if (total_price != null) {
                        mBtnLayout4S4.setVisibility(View.VISIBLE);
                        mProgressbar4S4.setVisibility(View.GONE);
                        userPreferences.setTempSwissQuotePrice("0.0");
                        userPreferences.setSwissIPersonal_QuotePrice(0);
                        asyncSwissInsured(primaryKey);
                        if(modeofPaymentString.equals("PayStack")) {
                            Intent intent = new Intent(getContext(), PolicyPaymentActivity.class);
                            intent.putExtra(Constant.TOTAL_PRICE, total_price);
                            intent.putExtra(Constant.POLICY_NUM, policy_num);
                            intent.putExtra(Constant.POLICY_TYPE, "swiss");
                            intent.putExtra(Constant.REF, ref);
                            startActivity(intent);
                            getActivity().finish();

                        }else{
                            Intent intent = new Intent(getContext(), PolicyWalletPayActivity.class);
                            intent.putExtra(Constant.TOTAL_PRICE, total_price);
                            intent.putExtra(Constant.POLICY_NUM, policy_num);
                            intent.putExtra(Constant.POLICY_TYPE, "swiss");
                            intent.putExtra(Constant.REF, ref);
                            startActivity(intent);
                            getActivity().finish();
                        }

                    } else {
                        incomplete_alert(String.valueOf(response.body()));
                        mBtnLayout4S4.setVisibility(View.VISIBLE);
                        mProgressbar4S4.setVisibility(View.GONE);

                    }
                }catch (Exception e){
                    incomplete_alert("Transaction not complete, check your internet and click continue\n" + e.getMessage());
                    Log.i("policyResponse", e.getMessage());
                    mBtnLayout4S4.setVisibility(View.VISIBLE);
                    mProgressbar4S4.setVisibility(View.GONE);

                }

            }
            @Override
            public void onFailure(Call<BuyQuoteFormGetHead_Swiss> call, Throwable t) {
                failed_alert("Submission Failed, TRY AGAIN \n" + t.getMessage());
                Log.i("GEtError",t.getMessage());
                mBtnLayout4S4.setVisibility(View.VISIBLE);
                mProgressbar4S4.setVisibility(View.GONE);

            }
        });

    }

    private void failed_alert(String msg) {

        new AlertDialog.Builder(getContext())
                .setIcon(R.drawable.ic_error_outline_black_24dp)
                .setTitle("Error !")
                .setMessage(msg)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();

                    }
                })
                .show();

    }

    private void incomplete_alert(String msg) {

        new AlertDialog.Builder(getContext())
                .setTitle("Error !")
                .setIcon(R.drawable.ic_error_outline_black_24dp)
                .setMessage(msg)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                        asyncSwissInsured(primaryKey);
                        userPreferences.setTempSwissQuotePrice("0.0");
                        userPreferences.setSwissIPersonal_QuotePrice(0);
                        Fragment transactionHistoryFragment = new TransactionHistoryFragment();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_motor_form_container, transactionHistoryFragment);
                        ft.commit();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                        asyncSwissInsured(primaryKey);
                        userPreferences.setTempSwissQuotePrice("0.0");
                        userPreferences.setSwissIPersonal_QuotePrice(0);
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }
                })
                .show();

    }


    //To Delete vehicle
    private void asyncSwissInsured(final String id){
        AsyncTask<Void, Void, Void> remoteItem =new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                realm=Realm.getDefaultInstance();

                SwissInsured swissPolicy=realm.where(SwissInsured.class).equalTo("id",id).findFirst();
                if(swissPolicy !=null){
                    realm.beginTransaction();
                    swissPolicy.deleteFromRealm();
                    realm.commitTransaction();
                }

                RealmResults<AdditionInsured> addInsureDetails=realm.where(AdditionInsured.class).findAll();
                if(addInsureDetails!=null){
                    realm.beginTransaction();
                    addInsureDetails.deleteAllFromRealm();
                    realm.commitTransaction();
                }else {
                    showMessage("Error in deletion");
                }
                realm.close();
                return null;
            }
        };
        remoteItem.execute();
    }




    private void showMessage(String s) {
        Snackbar.make(mQbFormLayout4, s, Snackbar.LENGTH_SHORT).show();
    }

}
