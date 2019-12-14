package com.example.sti_agent.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sti_agent.Model.Claim.MyClaims.Claim;
import com.example.sti_agent.Model.Claim.MyClaims.ClaimHead;
import com.example.sti_agent.Model.Errors.APIError;
import com.example.sti_agent.Model.Errors.ErrorUtils;
import com.example.sti_agent.Model.ServiceGenerator;
import com.example.sti_agent.R;
import com.example.sti_agent.UserPreferences;
import com.example.sti_agent.adapter.ClaimsListAdapter;
import com.example.sti_agent.retrofit_interface.ApiInterface;
import com.google.android.material.snackbar.Snackbar;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyClaimFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /** ButterKnife Code **/
    @BindView(R.id.my_claim_layout)
    LinearLayout mMyClaimsLayout;
    @BindView(R.id.avi1)
    AVLoadingIndicatorView mAvi1;
    @BindView(R.id.search_not_found_layout)
    LinearLayout mSearchNotFoundLayout;
    @BindView(R.id.recycler_claims)
    RecyclerView mRecyclerClaims;
    /** ButterKnife Code **/

    //Adapters
    private ClaimsListAdapter ClaimsListAdapter;

    List<Claim> claimLst;


    LinearLayoutManager layoutManager;
    UserPreferences userPreferences;

    ApiInterface client= ServiceGenerator.createService(ApiInterface.class);



    public MyClaimFragment() {
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
    public static MyClaimFragment newInstance(String param1, String param2) {
        MyClaimFragment fragment = new MyClaimFragment();
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
        View view=inflater.inflate(R.layout.fragment_my_claim, container, false);
        ButterKnife.bind(this,view);
        userPreferences=new UserPreferences(getContext());

        getClaims();
        
        return  view;
    }



    private void getClaims(){

        mAvi1.setVisibility(View.VISIBLE);
        //get client and call object for request

        Call<ClaimHead> call=client.get_my_claims("Token "+userPreferences.getUserToken());
        call.enqueue(new Callback<ClaimHead>() {
            @Override
            public void onResponse(Call<ClaimHead> call, Response<ClaimHead> response) {

                if (response.code() == 400) {
                    showMessage("Check your internet connection");

                    mAvi1.setVisibility(View.GONE);
                    return;
                } else if (response.code() == 429) {
                    showMessage("Too many requests on database");

                    mAvi1.setVisibility(View.GONE);
                    return;
                } else if (response.code() == 500) {
                    showMessage("Server Error");

                    mAvi1.setVisibility(View.GONE);
                    return;
                } else if (response.code() == 401) {
                    showMessage("Unauthorized access, please try login again");

                    mAvi1.setVisibility(View.GONE);
                    return;
                }



                if(!response.isSuccessful()){
                    try {
                        APIError apiError = ErrorUtils.parseError(response);

                        showMessage("Fetch Failed: " + apiError.getErrors());
                        Log.i("Invalid Fetch", String.valueOf(apiError.getErrors()));
                        //Log.i("Invalid Entry", response.errorBody().toString());
                        mAvi1.setVisibility(View.GONE);

                    } catch (Exception e) {
                        Log.i("Fetch Failed", e.getMessage());
                        showMessage("Fetch Failed");
                        mAvi1.setVisibility(View.GONE);

                    }

                    mAvi1.setVisibility(View.GONE);

                    return;
                }

                claimLst=response.body().getData().getClaims();

                int count_claims=claimLst.size();
           

                mAvi1.setVisibility(View.GONE);

                if(count_claims==0){
                    //mMyClaimsLayout.setVisibility(View.GONE);
                    mSearchNotFoundLayout.setVisibility(View.VISIBLE);
                    showMessage("Empty Claim");

                } else {

                    //adapter for claims
                    layoutManager = new LinearLayoutManager(getContext());
                    mRecyclerClaims.setLayoutManager(layoutManager);
                    ClaimsListAdapter = new ClaimsListAdapter(getContext(), claimLst);
                    mRecyclerClaims.setAdapter(ClaimsListAdapter);
                    ClaimsListAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailure(Call<ClaimHead> call, Throwable t) {
                showMessage("Fetch failed, please try again "+t.getMessage());
                Log.i("GEtError",t.getMessage());
            }
        });


    }

    private void showMessage(String s) {
        Snackbar.make(mMyClaimsLayout, s, Snackbar.LENGTH_LONG).show();
    }


}
