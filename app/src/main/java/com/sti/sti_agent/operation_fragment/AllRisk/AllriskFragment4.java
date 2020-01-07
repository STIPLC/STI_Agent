package com.sti.sti_agent.operation_fragment.AllRisk;

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

import com.sti.sti_agent.Constant;
import com.sti.sti_agent.MainActivity;
import com.sti.sti_agent.Model.AllRisk.AllRiskPost.AllRiskPostHead;
import com.sti.sti_agent.Model.AllRisk.AllRiskPost.AllriskPersona;
import com.sti.sti_agent.Model.AllRisk.AllRiskPost.Item;
import com.sti.sti_agent.Model.AllRisk.AllriskPolicy;
import com.sti.sti_agent.Model.AllRisk.FormSuccessDetail.BuyQuoteFormGetHead_AllRisk;
import com.sti.sti_agent.Model.AllRisk.FormSuccessDetail.Policy;
import com.sti.sti_agent.Model.AllRisk.ItemDetail;
import com.sti.sti_agent.Model.AllRisk.Personal_Detail_allrisk;
import com.sti.sti_agent.Model.Errors.APIError;
import com.sti.sti_agent.Model.Errors.ErrorUtils;
import com.sti.sti_agent.Model.ServiceGenerator;
import com.sti.sti_agent.NetworkConnection;
import com.sti.sti_agent.R;
import com.sti.sti_agent.UserPreferences;
import com.sti.sti_agent.adapter.ItemListAdapter;
import com.sti.sti_agent.fragment.TransactionHistoryFragment;
import com.sti.sti_agent.operation_activity.PolicyPaymentActivity;
import com.sti.sti_agent.operation_activity.PolicyWalletPayActivity;
import com.sti.sti_agent.retrofit_interface.ApiInterface;
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

public class AllriskFragment4 extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match

    private static final String PRIMARY_KEY = "primaryKey";
    private static final String FOREIGN_KEY = "foreignKey";


    // TODO: Rename and change types of parameters
    private String primaryKey,foreignKey;


    
    @BindView(R.id.qb_form_layout4)
    FrameLayout mQbFormLayout4;
    @BindView(R.id.step_view)
    StepView mStepView;
    @BindView(R.id.personal_info_a4)
    TextView mPersonalInfoA4;
    @BindView(R.id.fabShowItemInfo_a4)
    FloatingActionButton mFabShowItemInfoA4;
     @BindView(R.id.inputLayoutPin_a4)
     TextInputLayout mInputLayoutPinA4;
     @BindView(R.id.pin_txt_a4)
     EditText mPinTxtA4;
    @BindView(R.id.modeOfPayment_spinner_a4)
    Spinner mModeOfPaymentSpinnerA4;
    @BindView(R.id.btn_layout4_a4)
    LinearLayout mBtnLayout4A4;
    @BindView(R.id.v_back_btn4_a4)
    Button mVBackBtn4A4;
    @BindView(R.id.v_next_btn4_a4)
    Button mVNextBtn4A4;
    @BindView(R.id.progressbar4_a4)
    AVLoadingIndicatorView mProgressbar4A4;
    


    private  int currentStep=3;
    Realm realm;
    ItemListAdapter itemListAdapter;
    UserPreferences userPreferences;
    
    String modeofPaymentString;
  

   
    AllriskPolicy allriskPolicy;
    
    RealmList<Personal_Detail_allrisk> personal_detail_allriskss;
    //All Risk return policy
    List<Policy> policy;

    String total_quoteprice;

    List<Item> itemList_post=new ArrayList<Item>();

    NetworkConnection networkConnection=new NetworkConnection();
    String policy_num="";
    String total_price="";




    public AllriskFragment4() {
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
    public static AllriskFragment4 newInstance(String param1) {
        AllriskFragment4 fragment = new AllriskFragment4();
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
        View view=inflater.inflate(R.layout.fragment_allrisk4, container, false);
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

                    asyncAllriskPolicy(primaryKey);
                    userPreferences.setTempAllRiskQuotePrice("0.0");
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
        mModeOfPaymentSpinnerA4.setAdapter(staticAdapter);

        mModeOfPaymentSpinnerA4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String modeofPaymentTypeString = (String) parent.getItemAtPosition(position);

                if(modeofPaymentTypeString.equals("PayStack")){
                    mInputLayoutPinA4.setClickable(false);
                    mInputLayoutPinA4.setVisibility(View.GONE);
                }else if(modeofPaymentTypeString.equals("Wallet")){
                    mInputLayoutPinA4.setClickable(true);
                    mInputLayoutPinA4.setVisibility(View.VISIBLE);
                }else{
                    mInputLayoutPinA4.setClickable(true);
                    mInputLayoutPinA4.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mModeOfPaymentSpinnerA4.getItemAtPosition(0);
                mInputLayoutPinA4.setClickable(true);
                mInputLayoutPinA4.setVisibility(View.VISIBLE);
            }
        });

    }
    private void init(){


        //retrieve data for personal detail first
        allriskPolicy=realm.where(AllriskPolicy.class).equalTo("id",primaryKey).findFirst();
        total_quoteprice=allriskPolicy.getQuote_price();
        personal_detail_allriskss=allriskPolicy.getPersonal_detail_allrisks();


        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
        nf.setMaximumFractionDigits(2);
        DecimalFormat df = (DecimalFormat) nf;
        String v_price = df.format(Double.valueOf(total_quoteprice));

        if (userPreferences.getAllRiskPtype().equals("Corporate")) {
            String corperate = "Company Name: " + personal_detail_allriskss.get(0).getCompany_name() + "\n" + "Phone Number: " + personal_detail_allriskss.get(0).getPhone() + "\n" +
                    "Office Address: "+personal_detail_allriskss.get(0).getOffice_address()+"\n"+"Contact Person: "+personal_detail_allriskss.get(0).getContact_person()+"\n"+
                    "Phone Number: "+personal_detail_allriskss.get(0).getPhone()+"\n"+"Email Address: "+personal_detail_allriskss.get(0).getEmail()+"\n"+"Mailing Address: "+personal_detail_allriskss.get(0).getMailing_addr()+"\n"+
                    "Total Premium: ₦" + v_price;
            mPersonalInfoA4.setText(corperate);

        } else if (userPreferences.getAllRiskPtype().equals("Individual")) {
            String individual="Prefix: "+personal_detail_allriskss.get(0).getPrefix()+"\n"+"First Name: "+personal_detail_allriskss.get(0).getFirst_name()+"\n"+
                    "Last Name: "+personal_detail_allriskss.get(0).getLast_name()+"\n"+"Phone Number: "+personal_detail_allriskss.get(0).getPhone()+"\n"+
                    "Gender: "+personal_detail_allriskss.get(0).getResident_address()+"\n"+"Mailing Address: "+personal_detail_allriskss.get(0).getMailing_addr()+"\n"+
                    "Total Premium: ₦" + v_price;

            mPersonalInfoA4.setText(individual);

        }




    }



    //seting onclicks listeners
    private void setViewActions() {

        mVNextBtn4A4.setOnClickListener(this);
        mVBackBtn4A4.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.v_next_btn4_a4:
//                send quote to client and sti
                ValidateForm();
                break;

            case R.id.v_back_btn4_a4:

                mStepView.go(1, true);
                userPreferences.setTempAllRiskQuotePrice("0.0");
                asyncAllriskPolicy(primaryKey);
                Fragment allriskFragment2 = new AllriskFragment2();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_allrisk_form_container, allriskFragment2);
                ft.commit();

                break;
        }
    }
    
    


    @OnClick(R.id.fabShowItemInfo_a4)
    public void showBottomVehicleList() {
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
        final RealmResults<ItemDetail> results;
        //String title;
        results=realm.where(ItemDetail.class).findAll();

        if(results==null){
            showMessage("Result is null");
        }

        //Bind
        itemListAdapter=new ItemListAdapter(results,getActivity().getBaseContext());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity().getBaseContext(),RecyclerView.VERTICAL,false);
        recycler_vehicles.setLayoutManager(linearLayoutManager);
        recycler_vehicles.setAdapter(itemListAdapter);

        dialog.setContentView(view);
        dialog.show();
    }

    private void ValidateForm() {

        if (networkConnection.isNetworkConnected(getContext())) {
            boolean isValid = true;

            if (mPinTxtA4.getText().toString().isEmpty()&&mInputLayoutPinA4.isClickable()) {
                mInputLayoutPinA4.setError("Pin is required!");
                isValid = false;
            }else if (mPinTxtA4.getText().toString().trim().length()!=4 && mInputLayoutPinA4.isClickable()) {
                mInputLayoutPinA4.setError("Invalid pin entered!");
                isValid = false;
            } else if (!mPinTxtA4.getText().toString().trim().equals(userPreferences.getAgentPin()) && mInputLayoutPinA4.isClickable()) {
                mInputLayoutPinA4.setError("Invalid pin entered!");
                isValid = false;
            } else {
                mInputLayoutPinA4.setErrorEnabled(false);
            }
            //Prefix Spinner
            modeofPaymentString = mModeOfPaymentSpinnerA4.getSelectedItem().toString();
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

        mBtnLayout4A4.setVisibility(View.GONE);
        mProgressbar4A4.setVisibility(View.VISIBLE);
        String pin=mPinTxtA4.getText().toString();

        //retrieve data
        final RealmResults<ItemDetail> results;

        //String title;
        results=realm.where(ItemDetail.class).findAll();


        if(userPreferences.getAllRiskPtype().equals("Corporate")){

            AllriskPersona allriskPersona =new AllriskPersona("2","null","null","null",personal_detail_allriskss.get(0).getCompany_name(),personal_detail_allriskss.get(0).getEmail(),
                    "null",personal_detail_allriskss.get(0).getPhone(),"null","null",personal_detail_allriskss.get(0).getOffice_address(),personal_detail_allriskss.get(0).getMailing_addr(),
                    "null",personal_detail_allriskss.get(0).getContact_person(),personal_detail_allriskss.get(0).getPicture());

            for(int i=0;i<results.size();i++) {
                ItemDetail itemsDetails=results.get(i);
                Item item=new Item(itemsDetails.getItem(),itemsDetails.getValue(),itemsDetails.getStartDate(),
                        itemsDetails.getReceipt(),itemsDetails.getSerial(),itemsDetails.getImei());
                itemList_post.add(item);

                Log.i("policyItemLoop",itemsDetails.getItem());

            }

            AllRiskPostHead allriskPostHead=new AllRiskPostHead(allriskPersona,modeofPaymentString,total_quoteprice,
                    pin, results.size(), itemList_post);


            sendPolicy(allriskPostHead);


        }else if (userPreferences.getAllRiskPtype().equals("Individual")){

            AllriskPersona allriskPersona =new AllriskPersona("1",personal_detail_allriskss.get(0).getPrefix(),personal_detail_allriskss.get(0).getFirst_name(),personal_detail_allriskss.get(0).getLast_name()
                    ,"null",personal_detail_allriskss.get(0).getEmail(),
                    "null",personal_detail_allriskss.get(0).getPhone(),personal_detail_allriskss.get(0).getGender(),personal_detail_allriskss.get(0).getResident_address(),"null",personal_detail_allriskss.get(0).getMailing_addr(),
                    personal_detail_allriskss.get(0).getNext_of_kin(),"null",personal_detail_allriskss.get(0).getPicture());

            for(int i=0;i<results.size();i++) {
                ItemDetail itemsDetails=results.get(i);
                Item item=new Item(itemsDetails.getItem(),itemsDetails.getValue(),itemsDetails.getStartDate(),
                        itemsDetails.getReceipt(),itemsDetails.getSerial(),itemsDetails.getImei());
                itemList_post.add(item);

                Log.i("policyItemLoop",itemsDetails.getItem());

            }

            AllRiskPostHead allriskPostHead=new AllRiskPostHead(allriskPersona,modeofPaymentString,total_quoteprice,
                    pin, results.size(), itemList_post);


            sendPolicy(allriskPostHead);


        }



        
    }


    private void sendPolicy(AllRiskPostHead allRiskPostHead){


        //get client and call object for request
        ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

        Call<BuyQuoteFormGetHead_AllRisk> call=client.allrisk_policy("Token "+userPreferences.getUserToken(),allRiskPostHead);

        call.enqueue(new Callback<BuyQuoteFormGetHead_AllRisk>() {
            @Override
            public void onResponse(Call<BuyQuoteFormGetHead_AllRisk> call, Response<BuyQuoteFormGetHead_AllRisk> response) {
                Log.i("ResponseCode", String.valueOf(response.code()));
                if(response.code()==400){
                    failed_alert("Check your internet connection");
                    mBtnLayout4A4.setVisibility(View.VISIBLE);
                    mProgressbar4A4.setVisibility(View.GONE);
                    return;
                }else if(response.code()==429){
                    failed_alert("Too many requests on database");
                    mBtnLayout4A4.setVisibility(View.VISIBLE);
                    mProgressbar4A4.setVisibility(View.GONE);
                    return;
                }else if(response.code()==500){
                    failed_alert("Server Error");
                    mBtnLayout4A4.setVisibility(View.VISIBLE);
                    mProgressbar4A4.setVisibility(View.GONE);
                    return;
                }else if(response.code()==401){
                    failed_alert("Unauthorized access, please try login again");
                    mBtnLayout4A4.setVisibility(View.VISIBLE);
                    mProgressbar4A4.setVisibility(View.GONE);
                    return;
                }

                try {
                    if (!response.isSuccessful()) {

                        try{
                            APIError apiError= ErrorUtils.parseError(response);

                            failed_alert("Invalid Entry \n" + apiError.getErrors());
                            Log.i("Invalid EntryK",apiError.getErrors().toString());
                            Log.i("Invalid Entry",response.errorBody().toString());

                        }catch (Exception e){
                            Log.i("InvalidEntry",e.getMessage());
                            Log.i("ResponseError",response.errorBody().string());
                            failed_alert("Failed to Submit, try again\n" + e.getMessage());
                            mBtnLayout4A4.setVisibility(View.VISIBLE);
                            mProgressbar4A4.setVisibility(View.GONE);

                        }
                        mBtnLayout4A4.setVisibility(View.VISIBLE);
                        mProgressbar4A4.setVisibility(View.GONE);
                        return;
                    }

                    policy=response.body().getData().getPolicy();
                    for(int i=0;i<policy.size();i++){
                        policy_num=policy_num.concat("\n"+policy.get(i).getPolicyNumber());
                    }

                    total_price= String.valueOf(response.body().getData().getUnitPrice());
                    String ref= response.body().getData().getTransactions().get(0).getReference();

                    Log.i("policyNum", policy_num);
                    Log.i("totalPrice", total_price);
                    Log.i("ref", ref);


                    if (total_price != null) {
                        mBtnLayout4A4.setVisibility(View.VISIBLE);
                        mProgressbar4A4.setVisibility(View.GONE);
                        userPreferences.setTempAllRiskQuotePrice("0.0");
                        asyncAllriskPolicy(primaryKey);
                        if(modeofPaymentString.equals("PayStack")) {
                            Intent intent = new Intent(getContext(), PolicyPaymentActivity.class);
                            intent.putExtra(Constant.TOTAL_PRICE, total_price);
                            intent.putExtra(Constant.POLICY_NUM, policy_num);
                            intent.putExtra(Constant.POLICY_TYPE, "all_risk");
                            intent.putExtra(Constant.REF, ref);
                            startActivity(intent);
                            getActivity().finish();

                        }else{
                            Intent intent = new Intent(getContext(), PolicyWalletPayActivity.class);
                            intent.putExtra(Constant.TOTAL_PRICE, total_price);
                            intent.putExtra(Constant.POLICY_NUM, policy_num);
                            intent.putExtra(Constant.POLICY_TYPE, "all_risk");
                            intent.putExtra(Constant.REF, ref);
                            startActivity(intent);
                            getActivity().finish();
                        }

                    } else {
                        incomplete_alert(String.valueOf(response.body()));
                        mBtnLayout4A4.setVisibility(View.VISIBLE);
                        mProgressbar4A4.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    incomplete_alert("Transaction not complete, check your internet and click continue\n" + e.getMessage());
                    Log.i("policyResponse", e.getMessage());

                    mBtnLayout4A4.setVisibility(View.VISIBLE);
                    mProgressbar4A4.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<BuyQuoteFormGetHead_AllRisk> call, Throwable t) {
                failed_alert("Submission Failed, TRY AGAIN \n" + t.getMessage());
                Log.i("GetError", t.getMessage());
              /*  asyncAllriskPolicy(primaryKey);
                userPreferences.setTempAllRiskQuotePrice("0.0");
                Fragment allriskFragment2 = new AllriskFragment2();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_allrisk_form_container, allriskFragment2);
                ft.commit();*/

                mBtnLayout4A4.setVisibility(View.VISIBLE);
                mProgressbar4A4.setVisibility(View.GONE);
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
                        asyncAllriskPolicy(primaryKey);
                        userPreferences.setTempAllRiskQuotePrice("0.0");
                        Fragment transactionHistoryFragment = new TransactionHistoryFragment();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_allrisk_form_container, transactionHistoryFragment);
                        ft.commit();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                        asyncAllriskPolicy(primaryKey);
                        userPreferences.setTempAllRiskQuotePrice("0.0");
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }
                })
                .show();

    }

    //To Delete vehicle
    private void asyncAllriskPolicy(final String id){
        AsyncTask<Void, Void, Void> remoteItem =new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                realm=Realm.getDefaultInstance();

                AllriskPolicy allriskPolicy=realm.where(AllriskPolicy.class).equalTo("id",id).findFirst();
                if(allriskPolicy !=null){
                    realm.beginTransaction();
                    allriskPolicy.deleteFromRealm();
                    realm.commitTransaction();
                }

                RealmResults<ItemDetail> vehicleDetails=realm.where(ItemDetail.class).findAll();
                if(vehicleDetails!=null){
                    realm.beginTransaction();
                    vehicleDetails.deleteAllFromRealm();
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
        Snackbar.make(mQbFormLayout4, s, Snackbar.LENGTH_LONG).show();
    }



}
