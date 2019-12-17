package com.example.sti_agent.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sti_agent.Model.CustomerModel.CustomerDetail;
import com.example.sti_agent.Model.CustomerModel.CustomerModel;
import com.example.sti_agent.Model.Errors.APIError;
import com.example.sti_agent.Model.Errors.ErrorUtils;
import com.example.sti_agent.Model.MyPolicies.AllRisk;
import com.example.sti_agent.Model.MyPolicies.Marine;
import com.example.sti_agent.Model.MyPolicies.PolicyHead;
import com.example.sti_agent.Model.MyPolicies.Swis;
import com.example.sti_agent.Model.MyPolicies.Travel;
import com.example.sti_agent.Model.MyPolicies.Vehicle;
import com.example.sti_agent.Model.ServiceGenerator;
import com.example.sti_agent.R;
import com.example.sti_agent.UserPreferences;
import com.example.sti_agent.adapter.CustomerAdapter;
import com.example.sti_agent.adapter.PoliciesAdapter.AllRiskAdapter;
import com.example.sti_agent.adapter.PoliciesAdapter.EticAdapter;
import com.example.sti_agent.adapter.PoliciesAdapter.MarineAdapter;
import com.example.sti_agent.adapter.PoliciesAdapter.SwissAdapter;
import com.example.sti_agent.adapter.PoliciesAdapter.VehicleAdapter;
import com.example.sti_agent.retrofit_interface.ApiInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyPoliciesFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /** ButterKnife Code **/
    @BindView(R.id.my_policies_layout)
    LinearLayout mMyPoliciesLayout;
    @BindView(R.id.avi1)
    com.wang.avi.AVLoadingIndicatorView mAvi1;
    @BindView(R.id.search_not_found_layout)
    LinearLayout mSearchNotFoundLayout;
    @BindView(R.id.vehicle_policy_layout)
    LinearLayout mVehiclePolicyLayout;
    @BindView(R.id.recycler_vehicle)
    RecyclerView mRecyclerVehicle;
    @BindView(R.id.swiss_policy_layout)
    LinearLayout mSwissPolicyLayout;
    @BindView(R.id.recycler_swiss)
    RecyclerView mRecyclerSwiss;
    @BindView(R.id.marine_policy_layout)
    LinearLayout mMarinePolicyLayout;
    @BindView(R.id.recycler_marine)
    RecyclerView mRecyclerMarine;
    @BindView(R.id.etic_policy_layout)
    LinearLayout mEticPolicyLayout;
    @BindView(R.id.recycler_etic)
    RecyclerView mRecyclerEtic;
    @BindView(R.id.allrisk_policy_layout)
    LinearLayout mAllriskPolicyLayout;
    @BindView(R.id.recycler_allrisk)
    RecyclerView mRecyclerAllrisk;
    @BindView(R.id.allPolicyLayout)
    ScrollView mAllPolicyLayout;

    @BindView(R.id.vehicle_count)
    TextView vehicle_count;
    @BindView(R.id.swiss_count)
    TextView swiss_count;
    @BindView(R.id.marine_count)
    TextView marine_count;
    @BindView(R.id.allrisk_count)
    TextView allrisk_count;
    @BindView(R.id.travel_count)
    TextView travel_count;


    @BindView(R.id.inputLayoutPolcyNum)
    TextInputLayout mPolicyNum;
    @BindView(R.id.policy_num_editxt)
    EditText policy_num_editxt;
    @BindView(R.id.search)
    Button mSearch;
    @BindView(R.id.progressbar)
    AVLoadingIndicatorView progressbar;




    /** ButterKnife Code **/

    //Adapters
    private VehicleAdapter vehicleAdapter;
    private AllRiskAdapter allRiskAdapter;
    private EticAdapter eticAdapter;
    private MarineAdapter marineAdapter;
    private SwissAdapter swissAdapter;

    //list
    List<Vehicle> vehicleList;
    List<Swis> swisList;
    List<Marine> marineList;
    List<Travel> travelList;
    List<AllRisk> allRiskList;

    List<Vehicle>  vList;
    List<Swis> sList;
    List<Marine> mList;
    List<Travel> tList;
    List<AllRisk> aList;

    
    LinearLayoutManager layoutManager;
    UserPreferences userPreferences;

    ApiInterface client= ServiceGenerator.createService(ApiInterface.class);



    public MyPoliciesFragment() {
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
    public static MyPoliciesFragment newInstance(String param1, String param2) {
        MyPoliciesFragment fragment = new MyPoliciesFragment();
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
        View view=inflater.inflate(R.layout.fragment_my_policies, container, false);
        ButterKnife.bind(this,view);
        userPreferences=new UserPreferences(getContext());

        getPolicies();
        vList = new ArrayList<>();
        sList = new ArrayList<>();
        mList = new ArrayList<>();
        aList = new ArrayList<>();
        tList = new ArrayList<>();

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search();
            }
        });


        
        return  view;
    }



    private void getPolicies(){

        mAvi1.setVisibility(View.VISIBLE);
        //get client and call object for request

        Call<PolicyHead> call=client.get_paid_policies("Token "+userPreferences.getUserToken());
        call.enqueue(new Callback<PolicyHead>() {
            @Override
            public void onResponse(Call<PolicyHead> call, Response<PolicyHead> response) {

                if(!response.isSuccessful()){
                    try {
                        APIError apiError = ErrorUtils.parseError(response);

                        showMessage("Fetch Failed: " + apiError.getErrors());
                        Log.i("Invalid Fetch", String.valueOf(apiError.getErrors()));
                        //Log.i("Invalid Entry", response.errorBody().toString());

                    } catch (Exception e) {
                        Log.i("Fetch Failed", e.getMessage());
                        showMessage("Fetch Failed");
                        mAvi1.setVisibility(View.GONE);

                    }

                    mAvi1.setVisibility(View.GONE);

                    return;
                }

                vehicleList=response.body().getData().getPolicies().getVehicle();
                swisList=response.body().getData().getPolicies().getSwiss();
                travelList=response.body().getData().getPolicies().getTravel();
                allRiskList=response.body().getData().getPolicies().getAllRisk();
                marineList=response.body().getData().getPolicies().getMarine();

                int count_vehicle=vehicleList.size();
                int count_swis=swisList.size();
                int count_travel=travelList.size();
                int count_allrisk=allRiskList.size();
                int count_marine=marineList.size();

                mAvi1.setVisibility(View.GONE);

                if(count_vehicle==0){
                    //mVehiclePolicyLayout.setVisibility(View.GONE);
                    vehicle_count.setText("Vehicle Insurance(0)");
                }
                if (count_swis == 0) {
                    // mSwissPolicyLayout.setVisibility(View.GONE);
                    swiss_count.setText("SWIS-F Insurance(0)");
                }
                if (count_travel == 0) {
                    // mEticPolicyLayout.setVisibility(View.GONE);
                    travel_count.setText("ETIC(Travel) Insurance(0)");
                }
                if (count_allrisk == 0) {
                    //mAllriskPolicyLayout.setVisibility(View.GONE);
                    allrisk_count.setText("All-Risk Insurance(0)");
                }
                if (count_marine == 0) {
                    //mMarinePolicyLayout.setVisibility(View.GONE);
                    marine_count.setText("Marine Insurance(0)");
                }

                if(count_allrisk==0&&count_marine==0&&count_swis==0&&count_travel==0&&count_vehicle==0){
                    mSearchNotFoundLayout.setVisibility(View.VISIBLE);
                    //mAllPolicyLayout.setVisibility(View.GONE);
                    return;
                }

                //vehicle
                layoutManager = new LinearLayoutManager(getContext());
                mRecyclerVehicle.setLayoutManager(layoutManager);
                vehicleAdapter = new VehicleAdapter(getContext(), vehicleList);
                mRecyclerVehicle.setAdapter(vehicleAdapter);
                vehicleAdapter.notifyDataSetChanged();

                //swiss
                layoutManager = new LinearLayoutManager(getContext());
                mRecyclerSwiss.setLayoutManager(layoutManager);
                swissAdapter = new SwissAdapter(getContext(), swisList);
                mRecyclerSwiss.setAdapter(swissAdapter);
                swissAdapter.notifyDataSetChanged();

                //marine
                layoutManager = new LinearLayoutManager(getContext());
                mRecyclerMarine.setLayoutManager(layoutManager);
                marineAdapter = new MarineAdapter(getContext(), marineList);
                mRecyclerMarine.setAdapter(marineAdapter);
                marineAdapter.notifyDataSetChanged();

                //etic
                layoutManager = new LinearLayoutManager(getContext());
                mRecyclerEtic.setLayoutManager(layoutManager);
                eticAdapter = new EticAdapter(getContext(), travelList);
                mRecyclerEtic.setAdapter(eticAdapter);
                eticAdapter.notifyDataSetChanged();

                //all risk
                layoutManager = new LinearLayoutManager(getContext());
                mRecyclerAllrisk.setLayoutManager(layoutManager);
                allRiskAdapter = new AllRiskAdapter(getContext(), allRiskList);
                mRecyclerAllrisk.setAdapter(allRiskAdapter);
                allRiskAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<PolicyHead> call, Throwable t) {
                showMessage("Fetch failed, please try again "+t.getMessage());
                Log.i("GEtError",t.getMessage());
            }
        });


    }

    private void showMessage(String s) {
        Snackbar.make(mMyPoliciesLayout, s, Snackbar.LENGTH_SHORT).show();
    }

    private void Search(){

        vList.clear();
        sList.clear();
        mList.clear();
        aList.clear();
        tList.clear();
        mSearch.setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);
        Call<PolicyHead> call=client.get_paid_policies("Token "+userPreferences.getUserToken());
        call.enqueue(new Callback<PolicyHead>() {
            @Override
            public void onResponse(Call<PolicyHead> call, Response<PolicyHead> response) {

                if(!response.isSuccessful()){
                    try {
                        APIError apiError = ErrorUtils.parseError(response);

                        showMessage("Fetch Failed: " + apiError.getErrors());
                        Log.i("Invalid Fetch", String.valueOf(apiError.getErrors()));
                        //Log.i("Invalid Entry", response.errorBody().toString());
                        mSearch.setVisibility(View.VISIBLE);
                        progressbar.setVisibility(View.GONE);

                    } catch (Exception e) {
                        Log.i("Fetch Failed", e.getMessage());
                        showMessage("Fetch Failed");
                        mSearch.setVisibility(View.VISIBLE);
                        progressbar.setVisibility(View.GONE);

                    }

                    return;
                }
                //mSwipeRefreshLayout.setRefreshing(false);
                vehicleList=response.body().getData().getPolicies().getVehicle();
                swisList=response.body().getData().getPolicies().getSwiss();
                travelList=response.body().getData().getPolicies().getTravel();
                allRiskList=response.body().getData().getPolicies().getAllRisk();
                marineList=response.body().getData().getPolicies().getMarine();

                int count_vehicle=vehicleList.size();
                int count_swis=swisList.size();
                int count_travel=travelList.size();
                int count_allrisk=allRiskList.size();
                int count_marine=marineList.size();

                Log.i("Re-SuccessSize2", String.valueOf(vehicleList.size()));
                if(count_allrisk==0&&count_marine==0&&count_swis==0&&count_travel==0&&count_vehicle==0){

                    mSearchNotFoundLayout.setVisibility(View.VISIBLE);
                   // mSwipeRefreshLayout.setVisibility(View.GONE);

                }else {
                    mSearchNotFoundLayout.setVisibility(View.GONE);
                   // mSwipeRefreshLayout.setVisibility(View.VISIBLE);


                    for(int i=0;i<vehicleList.size();i++) {

                        String v_item = response.body().getData().getPolicies().getVehicle().get(i).getPolicyNumber();


                        if(v_item.toLowerCase().equals(policy_num_editxt.getText().toString()) ||
                                v_item.equals(policy_num_editxt.getText().toString())) {

                            vList.add(vehicleList.get(i));

                        }


                    }

                    Log.i("vList",String.valueOf(vList.size()));
                    if(vList.size()!=0) {

                        mRecyclerSwiss.setVisibility(View.GONE);
                        mRecyclerMarine.setVisibility(View.GONE);
                        mRecyclerAllrisk.setVisibility(View.GONE);
                        mRecyclerEtic.setVisibility(View.GONE);

                        //vehicle
                        layoutManager = new LinearLayoutManager(getContext());
                        mRecyclerVehicle.setLayoutManager(layoutManager);
                        vehicleAdapter = new VehicleAdapter(getContext(), vList);
                        mRecyclerVehicle.setAdapter(vehicleAdapter);
                        vehicleAdapter.notifyDataSetChanged();
                    }
                    for(int i=0;i<swisList.size();i++) {

                        String s_item = response.body().getData().getPolicies().getSwiss().get(i).getPolicyNumber();

                        if(s_item.toLowerCase().equals(policy_num_editxt.getText().toString())
                                || s_item.equals(policy_num_editxt.getText().toString())) {
                            sList.add(swisList.get(i));

                        }

                    }
                    Log.i("sList",String.valueOf(sList.size()));
                    if(sList.size()!=0) {
                        mRecyclerVehicle.setVisibility(View.GONE);
                        mRecyclerMarine.setVisibility(View.GONE);
                        mRecyclerAllrisk.setVisibility(View.GONE);
                        mRecyclerEtic.setVisibility(View.GONE);
                        //swiss
                        layoutManager = new LinearLayoutManager(getContext());
                        mRecyclerSwiss.setLayoutManager(layoutManager);
                        swissAdapter = new SwissAdapter(getContext(), sList);
                        mRecyclerSwiss.setAdapter(swissAdapter);
                        swissAdapter.notifyDataSetChanged();
                    }
                    for(int i=0;i<marineList.size();i++) {

                        String m_item = response.body().getData().getPolicies().getMarine().get(i).getPolicyNumber();
                        // String lastname = response.body().getData().get(i).getLastName();

                       if(m_item.toLowerCase().equals(policy_num_editxt.getText().toString())
                               || m_item.equals(policy_num_editxt.getText().toString())) {

                            mList.add(marineList.get(i));


                        }

                    }
                    if(mList.size()!=0) {
                        mRecyclerSwiss.setVisibility(View.GONE);
                        mRecyclerVehicle.setVisibility(View.GONE);
                        mRecyclerAllrisk.setVisibility(View.GONE);
                        mRecyclerEtic.setVisibility(View.GONE);
                        //marine
                        layoutManager = new LinearLayoutManager(getContext());
                        mRecyclerMarine.setLayoutManager(layoutManager);
                        marineAdapter = new MarineAdapter(getContext(), mList);
                        mRecyclerMarine.setAdapter(marineAdapter);
                        marineAdapter.notifyDataSetChanged();
                    }

                    for(int i=0;i<allRiskList.size();i++) {

                        String a_item = response.body().getData().getPolicies().getAllRisk().get(i).getPolicyNumber();

                       if(a_item.toLowerCase().equals(policy_num_editxt.getText().toString())
                               || a_item.equals(policy_num_editxt.getText().toString())) {

                            aList.add(allRiskList.get(i));


                        }
                    }

                    if(aList.size()!=0) {
                        mRecyclerSwiss.setVisibility(View.GONE);
                        mRecyclerMarine.setVisibility(View.GONE);
                        mRecyclerVehicle.setVisibility(View.GONE);
                        mRecyclerEtic.setVisibility(View.GONE);
                        //all risk
                        layoutManager = new LinearLayoutManager(getContext());
                        mRecyclerAllrisk.setLayoutManager(layoutManager);
                        allRiskAdapter = new AllRiskAdapter(getContext(), aList);
                        mRecyclerAllrisk.setAdapter(allRiskAdapter);
                        allRiskAdapter.notifyDataSetChanged();
                    }
                    for(int i=0;i<travelList.size();i++) {

                        String t_item = response.body().getData().getPolicies().getTravel().get(i).getPolicyNumber();


                      if(t_item.toLowerCase().equals(policy_num_editxt.getText().toString()) ||
                              t_item.equals(policy_num_editxt.getText().toString())) {

                            tList.add(travelList.get(i));


                        }
                    }
                    if(tList.size()!=0) {
                        mRecyclerSwiss.setVisibility(View.GONE);
                        mRecyclerMarine.setVisibility(View.GONE);
                        mRecyclerAllrisk.setVisibility(View.GONE);
                        mRecyclerVehicle.setVisibility(View.GONE);
                        //etic
                        layoutManager = new LinearLayoutManager(getContext());
                        mRecyclerEtic.setLayoutManager(layoutManager);
                        eticAdapter = new EticAdapter(getContext(), tList);
                        mRecyclerEtic.setAdapter(eticAdapter);
                        eticAdapter.notifyDataSetChanged();
                    }
                    if(vList.size()==0&&sList.size()==0&&mList.size()==0&&tList.size()==0&&aList.size()==0){

                        mSearchNotFoundLayout.setVisibility(View.VISIBLE);
                         mAllPolicyLayout.setVisibility(View.GONE);

                    }

                    mSearch.setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.GONE);
                    policy_num_editxt.setText("");

                    Log.i("Success2", response.body().toString());


                }

            }

            @Override
            public void onFailure(Call<PolicyHead> call, Throwable t) {
                showMessage("Fetch failed, please try again "+t.getMessage());
                Log.i("GEtError",t.getMessage());
                mSearch.setVisibility(View.VISIBLE);
                progressbar.setVisibility(View.GONE);
            }
        });



    }


}
