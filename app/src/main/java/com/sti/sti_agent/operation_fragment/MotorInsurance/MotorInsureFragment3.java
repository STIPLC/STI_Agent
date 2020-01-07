package com.sti.sti_agent.operation_fragment.MotorInsurance;

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

import com.sti.sti_agent.MainActivity;
import com.sti.sti_agent.R;
import com.sti.sti_agent.UserPreferences;
import com.google.android.material.snackbar.Snackbar;

import com.shuhart.stepview.StepView;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MotorInsureFragment3 extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String VEHICLE_MAKER = "vehicle_maker";
    private static final String PREMIUM_AMOUNT = "amount";

    // TODO: Rename and change types of parameters
    private String vehicleMaker;
    private String p_amount;

    @BindView(R.id.step_view)
    StepView stepView;

    @BindView(R.id.v_next_btn2)
    Button v_next_btn;

    @BindView(R.id.v_back_btn2)
    Button v_back_btn;

    //TextView
    @BindView(R.id.vehicleMake_txt)
    TextView vehicleMake_txt;

    @BindView(R.id.amount)
    TextView amount;


    @BindView(R.id.qb_form_layout3)
    FrameLayout qb_form_layout3;

    @BindView(R.id.btn_layout3)
    LinearLayout btn_layout3;

    @BindView(R.id.progressbar)
    AVLoadingIndicatorView progressbar;
    UserPreferences userPreferences;


    private  int currentStep=2;
    public MotorInsureFragment3() {
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
    public static MotorInsureFragment3 newInstance(String param1, String param2) {
        MotorInsureFragment3 fragment = new MotorInsureFragment3();
        Bundle args = new Bundle();
        args.putString(VEHICLE_MAKER, param1);
        args.putString(PREMIUM_AMOUNT, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vehicleMaker = getArguments().getString(VEHICLE_MAKER);
            p_amount = getArguments().getString(PREMIUM_AMOUNT);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_motor_insured3, container, false);
        ButterKnife.bind(this,view);
        //        stepView next registration step
        stepView.go(currentStep, true);
        userPreferences=new UserPreferences(getContext());


        vehicleMake_txt.setText(vehicleMaker);
        if(p_amount==null){
            p_amount="000";
            String format = p_amount + ".00";
            amount.setText(format);
        }else {
            // String format = p_amount ;
            NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
            nf.setMaximumFractionDigits(2);
            DecimalFormat df = (DecimalFormat) nf;
            String v_price = "₦" + df.format(Double.valueOf(p_amount));
            amount.setText(v_price);
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
                    userPreferences.setTempQuotePrice("0.0");
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

        v_next_btn.setOnClickListener(this);
        v_back_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.v_next_btn2:
//                send quote to client and sti
                mailClientAndSti();
                break;

            case R.id.v_back_btn2:
                if (currentStep > 0) {
                    currentStep--;
                }
                stepView.done(false);
                stepView.go(currentStep, true);
                userPreferences.setTempQuotePrice("0.0");
                Fragment motorInsureFragment2 = new MotorInsureFragment2();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_motor_form_container, motorInsureFragment2);
                ft.commit();

                break;
        }
    }

    private void mailClientAndSti() {
        UserPreferences userPreferences = new UserPreferences(getContext());

        btn_layout3.setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_motor_form_container, MotorInsureFragment4.newInstance(userPreferences.getMotorVehicleMake(),p_amount), MotorInsureFragment4.class.getSimpleName());
        ft.commit();

    }




    private void showMessage(String s) {
        Snackbar.make(qb_form_layout3, s, Snackbar.LENGTH_SHORT).show();
    }

}
