package com.example.sti_agent.operation_fragment.Claim;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.sti_agent.Model.Claim.TrackClaim.Claim;
import com.example.sti_agent.Model.Claim.TrackClaim.GetClaimStatus;
import com.example.sti_agent.Model.Errors.APIError;
import com.example.sti_agent.Model.Errors.ErrorUtils;
import com.example.sti_agent.Model.ServiceGenerator;
import com.example.sti_agent.NetworkConnection;
import com.example.sti_agent.R;
import com.example.sti_agent.UserPreferences;
import com.example.sti_agent.retrofit_interface.ApiInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Track_Claim extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /** ButterKnife Code **/
    @BindView(R.id.track_claim_layout1)
    FrameLayout mTrackClaimLayout1;

    @BindView(R.id.inputLayoutClaimNum)
    TextInputLayout inputLayoutClaimNum;
    @BindView(R.id.claim_num_editxt)
    EditText mClaimNumEditxt;
    @BindView(R.id.search)
    Button mSearch;
    @BindView(R.id.progressbar)
    AVLoadingIndicatorView mProgressbar;
    @BindView(R.id.status)
    TextView mStatus;
    @BindView(R.id.type)
    TextView mType;
    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.cost_estimate)
    TextView mCostEstimate;
    @BindView(R.id.loss_estimate)
    TextView mLossEstimate;
    @BindView(R.id.ref_code)
    TextView mRefCode;
    @BindView(R.id.scroll_parent)
    ScrollView scroll_parent;
    /** ButterKnife Code **/





    String policyTypeString,type,cost_estimate,loss_estimate,status,reference_code,description;

    NetworkConnection networkConnection=new NetworkConnection();

    UserPreferences userPreferences;


    public Track_Claim() {
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
    public static Track_Claim newInstance(String param1, String param2) {
        Track_Claim fragment = new Track_Claim();
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
        View view=inflater.inflate(R.layout.fragment_track_claim, container, false);
        ButterKnife.bind(this,view);
        userPreferences=new UserPreferences(getContext());


        setViewActions();

        return  view;
    }





    //seting onclicks listeners
    private void setViewActions() {

        mSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.search:
//                validate user input

                validateUserInputs();
                break;
        }
    }



    private void validateUserInputs() {

        boolean isValid = true;

        if (mClaimNumEditxt.getText().toString().isEmpty()) {
            inputLayoutClaimNum.setError("Claim Number is Required!");
            isValid = false;
        } else {
            inputLayoutClaimNum.setErrorEnabled(false);

        }
            if (isValid) {
                if(networkConnection.isNetworkConnected(getContext())) {
                    initFragment();
                }else {
                    showMessage("No Internet Connection");
                }
            }





    }



    private void initFragment() {
        mSearch.setVisibility(View.GONE);
        mProgressbar.setVisibility(View.VISIBLE);


        sendClaimData();




    }

    private void sendClaimData(){


        //get client and call object for request
        ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

        Call<GetClaimStatus> call=client.track_claim("Token "+userPreferences.getUserToken(),mClaimNumEditxt.getText().toString());

        call.enqueue(new Callback<GetClaimStatus>() {
            @Override
            public void onResponse(Call<GetClaimStatus> call, Response<GetClaimStatus> response) {
                Log.i("ResponseCode", String.valueOf(response.code()));

                if(response.code()==406){
                    showMessage("Error! Wrong Claim Number provided!");
                    mSearch.setVisibility(View.VISIBLE);
                    mProgressbar.setVisibility(View.GONE);
                    return;
                }

                if(response.code()==400){
                    showMessage("Check your internet connection");
                    mSearch.setVisibility(View.VISIBLE);
                    mProgressbar.setVisibility(View.GONE);
                    return;
                }else if(response.code()==429){
                    showMessage("Too many requests on database");
                    mSearch.setVisibility(View.VISIBLE);
                    mProgressbar.setVisibility(View.GONE);
                    return;
                }else if(response.code()==500){
                    showMessage("Server Error");
                    mSearch.setVisibility(View.VISIBLE);
                    mProgressbar.setVisibility(View.GONE);
                    return;
                }else if(response.code()==401){
                    showMessage("Unauthorized access, please try login again");
                    mSearch.setVisibility(View.VISIBLE);
                    mProgressbar.setVisibility(View.GONE);
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
                            showMessage("Failed to Register"+e.getMessage());
                            mSearch.setVisibility(View.VISIBLE);
                            mProgressbar.setVisibility(View.GONE);

                        }
                        mSearch.setVisibility(View.VISIBLE);
                        mProgressbar.setVisibility(View.GONE);
                        return;
                    }

                    status=response.body().getData().getClaim().getStatus();
                    Claim flag=response.body().getData().getClaim();

                    if(status!=null||flag!=null) {
                        scroll_parent.setVisibility(View.VISIBLE);
                        status = response.body().getData().getClaim().getStatus();
                        type = response.body().getData().getClaim().getType();
                        cost_estimate = response.body().getData().getClaim().getCostEstimate();
                        loss_estimate = response.body().getData().getClaim().getLossEstimate();
                        reference_code = response.body().getData().getClaim().getReference();
                        description = response.body().getData().getClaim().getDescription();

                        mStatus.setText(status);
                        mType.setText(type);
                        mCostEstimate.setText(cost_estimate);
                        mLossEstimate.setText(loss_estimate);
                        mRefCode.setText(reference_code);
                        mDescription.setText(description);

                        Log.i("claim_response", status);

                    }else{
                        scroll_parent.setVisibility(View.GONE);
                        showMessage("Error in Claim Number");
                    }

                    mSearch.setVisibility(View.VISIBLE);
                    mProgressbar.setVisibility(View.GONE);


                }catch (Exception e){
                    showMessage("Submission Error: " + e.getMessage());
                    Log.i("policyResponse", e.getMessage());
                    mSearch.setVisibility(View.VISIBLE);
                    mProgressbar.setVisibility(View.GONE);
                }

            }
            @Override
            public void onFailure(Call<GetClaimStatus> call, Throwable t) {
                showMessage("Submission Failed "+t.getMessage());
                mSearch.setVisibility(View.VISIBLE);
                mProgressbar.setVisibility(View.GONE);
                Log.i("GEtError",t.getMessage());
            }
        });




    }


    private void showMessage(String s) {
        Snackbar.make(mTrackClaimLayout1, s, Snackbar.LENGTH_LONG).show();
    }


}
