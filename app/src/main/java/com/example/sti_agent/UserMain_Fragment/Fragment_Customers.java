package com.example.sti_agent.UserMain_Fragment;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sti_agent.MainActivity;
import com.example.sti_agent.Model.CustomerModel.CustomerDetail;
import com.example.sti_agent.Model.CustomerModel.CustomerModel;
import com.example.sti_agent.Model.Errors.APIError;
import com.example.sti_agent.Model.Errors.ErrorUtils;
import com.example.sti_agent.Model.ServiceGenerator;
import com.example.sti_agent.R;
import com.example.sti_agent.UserPreferences;
import com.example.sti_agent.adapter.CustomerAdapter;
import com.example.sti_agent.retrofit_interface.ApiInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class Fragment_Customers extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

  /*  @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private CardAdapter cardAdapter;
    private List<Card> cardList;*/

    /** ButterKnife Code **/
    @BindView(R.id.trasanction_history_layout)
    LinearLayout mPaymentHistoryLayout;
    @BindView(R.id.inputLayoutCustomerName)
    TextInputLayout mCustomerInput;
    @BindView(R.id.customer_name_editxt)
    EditText customer_name_editxt;
    @BindView(R.id.search)
    Button mSearch;

    @BindView(R.id.avi1)
    AVLoadingIndicatorView mAvi1;
    @BindView(R.id.search_not_found_layout)
    LinearLayout mSearchNotFoundLayout;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_customers)
    RecyclerView mRecyclerPaymentHistory;
    @BindView(R.id.progressbar)
    AVLoadingIndicatorView progressbar;

    /** ButterKnife Code **/

    private CustomerAdapter mCustomerAdapter;
    LinearLayoutManager layoutManager;
    UserPreferences userPreferences;
    List<CustomerDetail>  cardList;


    ApiInterface client= ServiceGenerator.createService(ApiInterface.class);




    public Fragment_Customers() {
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
    public static Fragment_Customers newInstance(String param1, String param2) {
        Fragment_Customers fragment = new Fragment_Customers();
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
        View view=inflater.inflate(R.layout.fragment_customers, container, false);
        ButterKnife.bind(this,view);
        init();

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search();
            }
        });

        cardList = new ArrayList<>();

        /*cardList = new ArrayList<>();
        cardAdapter = new CardAdapter(getContext(), cardList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cardAdapter);

//        populating the card

*/

        return  view;
    }
    private void init() {
        userPreferences = new UserPreferences(getContext());
        getCustomers();
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void getCustomers() {
        //get client and call object for request

        Call<CustomerModel> call=client.fetchCustomers("Token "+userPreferences.getUserToken());
        call.enqueue(new Callback<CustomerModel>() {
            @Override
            public void onResponse(Call<CustomerModel> call, Response<CustomerModel> response) {

                if(!response.isSuccessful()){
                    try {
                        APIError apiError = ErrorUtils.parseError(response);

                        showMessage("Fetch Failed: " + apiError.getErrors());
                        Log.i("Invalid Fetch", String.valueOf(apiError.getErrors()));
                        //Log.i("Invalid Entry", response.errorBody().toString());

                    } catch (Exception e) {
                        Log.i("Fetch Failed", e.getMessage());
                        showMessage("Fetch Failed");

                    }

                    return;
                }
                mSwipeRefreshLayout.setRefreshing(false);

                List<CustomerDetail> policy_item=response.body().getData();

                int count=policy_item.size();

                Log.i("Re-SuccessSize", String.valueOf(policy_item.size()));

                if(count==0){
                    mSearchNotFoundLayout.setVisibility(View.VISIBLE);
                    mSwipeRefreshLayout.setVisibility(View.GONE);

                }else {
                    mSearchNotFoundLayout.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setVisibility(View.VISIBLE);

                    layoutManager = new LinearLayoutManager(getContext());
                    mRecyclerPaymentHistory.setLayoutManager(layoutManager);
                    mCustomerAdapter = new CustomerAdapter(getContext(), policy_item);
                    mRecyclerPaymentHistory.setAdapter(mCustomerAdapter);
                    mCustomerAdapter.notifyDataSetChanged();

                    Log.i("Success", response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<CustomerModel> call, Throwable t) {
                showMessage("Fetch failed, please try again "+t.getMessage());
                Log.i("GEtError",t.getMessage());
            }
        });
    }

    private void showMessage(String s) {
        Snackbar.make(mPaymentHistoryLayout, s, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onRefresh() {
        getCustomers();
    }


    private void Search(){

            cardList.clear();
            mSearch.setVisibility(View.GONE);
            progressbar.setVisibility(View.VISIBLE);
            Call<CustomerModel> call=client.fetchCustomers("Token "+userPreferences.getUserToken());
            call.enqueue(new Callback<CustomerModel>() {
                @Override
                public void onResponse(Call<CustomerModel> call, Response<CustomerModel> response) {

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


                    List<CustomerDetail> search_item=response.body().getData();

                    int count=search_item.size();

                    Log.i("Re-SuccessSize2", String.valueOf(search_item.size()));

                    if(count==0){
                        mSearchNotFoundLayout.setVisibility(View.VISIBLE);
                        mSwipeRefreshLayout.setVisibility(View.GONE);

                    }else {
                        mSearchNotFoundLayout.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setVisibility(View.VISIBLE);


                        for(int i=0;i<search_item.size();i++) {
                            String firstname = response.body().getData().get(i).getFirstName().toLowerCase();
//                            String lastname = response.body().getData().get(i).getLastName();
Log.i("firstname",firstname);


                            if(firstname.equals(customer_name_editxt.getText().toString().toLowerCase())) {

                                cardList.add(search_item.get(i));


                            }

                        }
                        if(cardList.size()!=0) {
                            layoutManager = new LinearLayoutManager(getContext());
                            mRecyclerPaymentHistory.setLayoutManager(layoutManager);
                            mCustomerAdapter = new CustomerAdapter(getContext(), cardList);
                            mRecyclerPaymentHistory.setAdapter(mCustomerAdapter);
                            mCustomerAdapter.notifyDataSetChanged();



                            Log.i("Success2", response.body().toString());
                        }else{
                                mSearchNotFoundLayout.setVisibility(View.VISIBLE);
                                mSwipeRefreshLayout.setVisibility(View.GONE);
                            }

                        mSearch.setVisibility(View.VISIBLE);
                        progressbar.setVisibility(View.GONE);
                        customer_name_editxt.setText("");
                    }

                }

                @Override
                public void onFailure(Call<CustomerModel> call, Throwable t) {
                    showMessage("Fetch failed, please try again "+t.getMessage());
                    Log.i("GEtError",t.getMessage());
                    mSearch.setVisibility(View.VISIBLE);
                    progressbar.setVisibility(View.GONE);
                }
            });



        }




}
