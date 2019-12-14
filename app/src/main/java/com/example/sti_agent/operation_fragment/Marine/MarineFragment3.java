package com.example.sti_agent.operation_fragment.Marine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sti_agent.MainActivity;
import com.example.sti_agent.Model.Marine.CargoDetail;
import com.example.sti_agent.Model.Marine.MarinePolicy;
import com.example.sti_agent.Model.Marine.Personal_Detail_marine;
import com.example.sti_agent.R;
import com.example.sti_agent.UserPreferences;
import com.google.android.material.snackbar.Snackbar;
import com.shuhart.stepview.StepView;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;

public class MarineFragment3 extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SUM_INSURED = "sum_insured";
    private static final String PREMIUM_AMOUNT = "amount_marine";
    //private static final String SUM_INSURED = "sum_insured";

    // TODO: Rename and change types of parameters
    public String sum_insured;
    public String p_amount;


    // public String sum_insured;


    @BindView(R.id.qb_form_layout3)
    FrameLayout mQbFormLayout3;
    @BindView(R.id.step_view)
    StepView mStepView;
    @BindView(R.id.premium_txt)
    TextView mPremiumTxt;
    @BindView(R.id.amount_m3)
    TextView mAmountM3;

    @BindView(R.id.sum_insured_amount_m3)
    TextView mSumInsuredAmount;


    @BindView(R.id.btn_layout3_m3)
    LinearLayout mBtnLayout3M3;
    @BindView(R.id.v_back_btn3_m3)
    Button mVBackBtn3M3;
    @BindView(R.id.v_next_btn3_m3)
    Button mVNextBtn3M3;
    @BindView(R.id.progressbar3_m3)
    AVLoadingIndicatorView mProgressbar3M3;
  /*  @BindView(R.id.fabAddCargo_m3)
    FloatingActionButton mFabAddCargoM3;*/

    private  int currentStep=2;

    Realm realm;


    MarinePolicy id=new MarinePolicy();
    String primaryKey=id.getId();
    UserPreferences userPreferences;

    public MarineFragment3() {
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
    public static MarineFragment3 newInstance(String param1, String param2) {
        MarineFragment3 fragment = new MarineFragment3();
        Bundle args = new Bundle();
        args.putString(SUM_INSURED, param1);
        args.putString(PREMIUM_AMOUNT, param2);
        //args.putString(SUM_INSURED, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserPreferences userPreferences=new UserPreferences(getContext());

        if (getArguments() != null) {
            sum_insured = getArguments().getString(SUM_INSURED);

            double oldquote= Double.valueOf(userPreferences.getTempMarineQuotePrice());
            p_amount=getArguments().getString(PREMIUM_AMOUNT);

            double newquote= Double.valueOf(getArguments().getString(PREMIUM_AMOUNT));
            double total_quote=oldquote+newquote;

            userPreferences.setTempMarineQuotePrice(String.valueOf(total_quote));


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_marine3, container, false);
        ButterKnife.bind(this,view);
        //        mStepView next registration step
        mStepView.go(currentStep, true);
        userPreferences=new UserPreferences(getContext());


        //instancial Realm db
        realm=Realm.getDefaultInstance();


        if(p_amount==null){
            p_amount="000";
            String format = p_amount + ".00";
            mAmountM3.setText(format);

            sum_insured = "000";
            String format_sum = sum_insured + ".00";
            mSumInsuredAmount.setText(format_sum);

        }else {
            NumberFormat nf_sum = NumberFormat.getNumberInstance(new Locale("en", "US"));
            nf_sum.setMaximumFractionDigits(2);
            DecimalFormat df_sum = (DecimalFormat) nf_sum;
            String v_sum = "₦" + df_sum.format(Double.valueOf(sum_insured));

            mSumInsuredAmount.setText(v_sum);

            //String format = p_amount ;
            NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
            nf.setMaximumFractionDigits(2);
            DecimalFormat df = (DecimalFormat) nf;
            String v_price = "₦" + df.format(Double.valueOf(p_amount));
            mAmountM3.setText(v_price);
        }

        setViewActions();

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("backPress_KeyCode", "keyCode: " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Log.i("backPress", "onKey Back listener is working!!!");
                    userPreferences.setTempMarineQuotePrice("0.0");
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    return true;
                }
                return false;
            }
        });


        return  view;
    }



    //seting onclicks listeners
    private void setViewActions() {

        mVNextBtn3M3.setOnClickListener(this);
        mVBackBtn3M3.setOnClickListener(this);
       // mFabAddCargoM3.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {



            case R.id.v_next_btn3_m3:
//                send quote to client and sti
                mailClientAndSt();
                break;

           /* case R.id.fabAddCargo_m3:



                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        Personal_Detail_marine personal_detail_marine=new Personal_Detail_marine();

                        if(realm.isEmpty()){


                            personal_detail_marine.setPrefix(userPreferences.getMarineIPrefix());
                            personal_detail_marine.setFirst_name(userPreferences.getMarineIFirstName());
                            personal_detail_marine.setLast_name(userPreferences.getMarineILastName());
                            personal_detail_marine.setEmail(userPreferences.getMarineIEmail());
                            personal_detail_marine.setGender(userPreferences.getMarineIGender());
                            personal_detail_marine.setMarital_status(userPreferences.getMarineIGender());
                            personal_detail_marine.setPhone(userPreferences.getMarineIPhoneNum());
                            personal_detail_marine.setResident_address(userPreferences.getMarineIResAdrr());
                            personal_detail_marine.setCustomer_type(userPreferences.getMarinePtype());
                            personal_detail_marine.setCompany_name(userPreferences.getMarineICompanyName());
                            personal_detail_marine.setMailing_address(userPreferences.getMarineIMailingAddr());
                            personal_detail_marine.setTin_number(userPreferences.getMarineITinNumber());
                            personal_detail_marine.setTrade(userPreferences.getMarineITinNumber());
                            personal_detail_marine.setState(userPreferences.getMarineIState());
                            personal_detail_marine.setOffice_address(userPreferences.getMarineIOff_addr());
                            personal_detail_marine.setContact_person(userPreferences.getMarineIContPerson());
                            //Cargo List
                            CargoDetail cargoDetail=new CargoDetail();

                            cargoDetail.setPfi_number(userPreferences.getMarineIProfInvNO());
                            cargoDetail.setPfi_date(userPreferences.getMarineIDateProfInv());
                            cargoDetail.setDesc_goods(userPreferences.getMarineIDescOfGoods());
                            cargoDetail.setStart_date_cover(userPreferences.getMarineIStartDateCover());
                            cargoDetail.setQuantity(userPreferences.getMarineIQuantity());
                            cargoDetail.setCurrency(userPreferences.getMarineICurrency());
                            cargoDetail.setCover(userPreferences.getMarineICover());
                            cargoDetail.setValue(userPreferences.getMarineITotalAmount());
                            cargoDetail.setConversion_rate(userPreferences.getMarineINairaConvert());
                            cargoDetail.setLoading_port(userPreferences.getMarineIPortOfLoading());
                            cargoDetail.setDischarge_port(userPreferences.getMarineIPortOfDischarge());
                            cargoDetail.setConveyance_mode(userPreferences.getMarineIModeOfConvey());
                            cargoDetail.setPicture(userPreferences.getMarineIProfImage());
                            

                            RealmList<CargoDetail>cargoDetailList=new RealmList<>();
                            cargoDetailList.add(cargoDetail);

                            personal_detail_marine.setCargoDetails(cargoDetailList);




                            final Personal_Detail_marine personal_detail_marine1=realm.copyToRealm(personal_detail_marine);

                            MarinePolicy marinePolicy=realm.createObject(MarinePolicy.class,primaryKey);
                            marinePolicy.setAgent_id(userPreferences.getUserId());
                            marinePolicy.setQuote_price(String.valueOf(userPreferences.getTempMarineQuotePrice()));
                            marinePolicy.setPayment_source("paystack");
                            marinePolicy.setPin("0000");

                            Log.i("Primary1",primaryKey);

                            marinePolicy.getPersonal_detail_marines().add(personal_detail_marine1);



                        }else if(!realm.isEmpty()){



                            //Cargo List
                            CargoDetail cargoDetail=new CargoDetail();
                            cargoDetail.setPfi_number(userPreferences.getMarineIProfInvNO());
                            cargoDetail.setPfi_date(userPreferences.getMarineIDateProfInv());
                            cargoDetail.setDesc_goods(userPreferences.getMarineIDescOfGoods());
                            cargoDetail.setStart_date_cover(userPreferences.getMarineIStartDateCover());
                            cargoDetail.setQuantity(userPreferences.getMarineIQuantity());
                            cargoDetail.setCurrency(userPreferences.getMarineICurrency());
                            cargoDetail.setCover(userPreferences.getMarineICover());
                            cargoDetail.setValue(userPreferences.getMarineITotalAmount());
                            cargoDetail.setConversion_rate(userPreferences.getMarineINairaConvert());
                            cargoDetail.setLoading_port(userPreferences.getMarineIPortOfLoading());
                            cargoDetail.setDischarge_port(userPreferences.getMarineIPortOfDischarge());
                            cargoDetail.setConveyance_mode(userPreferences.getMarineIModeOfConvey());
                            cargoDetail.setPicture(userPreferences.getMarineIProfImage());


                            RealmList<CargoDetail>cargoDetailList=new RealmList<>();
                            cargoDetailList.add(cargoDetail);
                            personal_detail_marine.setCargoDetails(cargoDetailList);

                            final Personal_Detail_marine personal_detail_marine2=realm.copyToRealm(personal_detail_marine);
                            MarinePolicy marinePolicy=realm.createObject(MarinePolicy.class,primaryKey);
                            marinePolicy.setQuote_price(String.valueOf(userPreferences.getTempMarineQuotePrice()));
                            marinePolicy.getPersonal_detail_marines().add(personal_detail_marine2);

                            showMessage(primaryKey);



                        }else {
                            showMessage("Invalid transaction");
                        }



                    }
                });



                mStepView.done(false);
                mStepView.go(1, true);

                Fragment marineFragment2 = new MarineFragment2();
                FragmentTransaction ftrans = getFragmentManager().beginTransaction();
                ftrans.replace(R.id.fragment_marine_form_container, marineFragment2);
                ftrans.commit();
                break;
*/



            case R.id.v_back_btn3_m3:
                if (currentStep > 0) {
                    currentStep--;
                }
                mStepView.done(false);
                mStepView.go(currentStep, true);
                userPreferences.setTempMarineQuotePrice("0.0");
                Fragment marineFragment21 = new MarineFragment2();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_marine_form_container, marineFragment21);
                ft.commit();

                break;
        }
    }

    private void mailClientAndSt() {
        UserPreferences userPreferences = new UserPreferences(getContext());

        mBtnLayout3M3.setVisibility(View.GONE);
        mProgressbar3M3.setVisibility(View.VISIBLE);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Personal_Detail_marine personal_detail_marine=new Personal_Detail_marine();



                    personal_detail_marine.setPrefix(userPreferences.getMarineIPrefix());
                    personal_detail_marine.setFirst_name(userPreferences.getMarineIFirstName());
                    personal_detail_marine.setLast_name(userPreferences.getMarineILastName());
                    personal_detail_marine.setEmail(userPreferences.getMarineIEmail());
                    personal_detail_marine.setGender(userPreferences.getMarineIGender());
                    personal_detail_marine.setMarital_status(userPreferences.getMarineIGender());
                    personal_detail_marine.setPhone(userPreferences.getMarineIPhoneNum());
                    personal_detail_marine.setState(userPreferences.getMarineIState());
                    personal_detail_marine.setResident_address(userPreferences.getMarineIResAdrr());
                    personal_detail_marine.setCustomer_type(userPreferences.getMarinePtype());
                    personal_detail_marine.setCompany_name(userPreferences.getMarineICompanyName());
                    personal_detail_marine.setMailing_address(userPreferences.getMarineIMailingAddr());
                    personal_detail_marine.setTin_number(userPreferences.getMarineITinNumber());
                    personal_detail_marine.setTrade(userPreferences.getMarineITinNumber());
                    personal_detail_marine.setOffice_address(userPreferences.getMarineIOff_addr());
                    personal_detail_marine.setContact_person(userPreferences.getMarineIContPerson());
                    //Cargo List
                    CargoDetail cargoDetail=new CargoDetail();

                    cargoDetail.setPfi_number(userPreferences.getMarineIProfInvNO());
                    cargoDetail.setPfi_date(userPreferences.getMarineIDateProfInv());
                    cargoDetail.setDesc_goods(userPreferences.getMarineIDescOfGoods());
                    cargoDetail.setStart_date_cover(userPreferences.getMarineIStartDateCover());
                    cargoDetail.setQuantity(userPreferences.getMarineIQuantity());
                    cargoDetail.setCurrency(userPreferences.getMarineICurrency());
                    cargoDetail.setCover(userPreferences.getMarineICover());
                    cargoDetail.setValue(userPreferences.getMarineITotalAmount());
                    cargoDetail.setConversion_rate(userPreferences.getMarineINairaConvert());
                    cargoDetail.setLoading_port(userPreferences.getMarineIPortOfLoading());
                    cargoDetail.setDischarge_port(userPreferences.getMarineIPortOfDischarge());
                    cargoDetail.setConveyance_mode(userPreferences.getMarineIModeOfConvey());
                    cargoDetail.setPicture(userPreferences.getMarineIProfImage());


                    RealmList<CargoDetail>cargoDetailList=new RealmList<>();
                    cargoDetailList.add(cargoDetail);

                    personal_detail_marine.setCargoDetails(cargoDetailList);




                    final Personal_Detail_marine personal_detail_marine1=realm.copyToRealm(personal_detail_marine);

                    MarinePolicy marinePolicy=realm.createObject(MarinePolicy.class,primaryKey);
                    marinePolicy.setAgent_id("1");
                    marinePolicy.setQuote_price(String.valueOf(userPreferences.getTempMarineQuotePrice()));
                    marinePolicy.setPayment_source("paystack");
                    marinePolicy.setPin("11234");

                    Log.i("Primary1",primaryKey);

                    marinePolicy.getPersonal_detail_marines().add(personal_detail_marine1);



            }
        });

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_marine_form_container, MarineFragment4.newInstance(primaryKey));
        ft.commit();

    }
    private void showMessage(String s) {
        Snackbar.make(mQbFormLayout3, s, Snackbar.LENGTH_SHORT).show();
    }


}