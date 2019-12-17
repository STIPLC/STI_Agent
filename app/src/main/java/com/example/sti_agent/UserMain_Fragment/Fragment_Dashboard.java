package com.example.sti_agent.UserMain_Fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.sti_agent.Constant;
import com.example.sti_agent.GridSpacingItemDecoration;
import com.example.sti_agent.MainActivity;
import com.example.sti_agent.Model.Card;
import com.example.sti_agent.Model.Errors.APIError;
import com.example.sti_agent.Model.Errors.ErrorUtils;
import com.example.sti_agent.Model.ServiceGenerator;
import com.example.sti_agent.Model.TransactionHistroy.History;
import com.example.sti_agent.Model.TransactionHistroy.TransactionHead;
import com.example.sti_agent.Model.WalletModel.WalletObj;
import com.example.sti_agent.Model.WalletModel.Wallet_History;
import com.example.sti_agent.NetworkConnection;
import com.example.sti_agent.R;
import com.example.sti_agent.SignIn;
import com.example.sti_agent.UserPreferences;
import com.example.sti_agent.adapter.CardAdapter;
import com.example.sti_agent.adapter.WalletHistoryAdapter;
import com.example.sti_agent.fragment.TransactionHistoryFragment;
import com.example.sti_agent.operation_activity.QuoteBuyActivity;
import com.example.sti_agent.retrofit_interface.ApiInterface;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Dashboard extends Fragment implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.dash_layout)
    CoordinatorLayout dash_layout;
    @BindView(R.id.wallet_balance)
    TextView wallet_balance;
    @BindView(R.id.wallet_kobo)
    TextView wallet_kobo;
    @BindView(R.id.fund_wallet_txt)
    TextView fund_wallet_txt;
    @BindView(R.id.wallet_blance_card)
    MaterialCardView wallet_blance_card;
    @BindView(R.id.fund_wallet_card)
    MaterialCardView fund_wallet_card;
    @BindView(R.id.progressbar)
    AVLoadingIndicatorView progressbar;
    private CardAdapter cardAdapter;
    private List<Card> cardList;
    UserPreferences userPreferences;
    List<Wallet_History> wallet_histories;
    Fragment fragment;
    WalletHistoryAdapter walletHistoryAdapter;

    @BindView(R.id.slider)
    SliderLayout mSlider;
    @BindView(R.id.transactn_notify_btn)
    Button mTrasactnNotifyBtn;
    @BindView(R.id.transact_note)
    TextView mTrasactnNote;
    @BindView(R.id.transaction_notify)
    MaterialCardView mTransactnNotify;

    String policy_type;
    String policyNumString;
    String checkIncomplete;


    List<History> policy_item;

    public Fragment_Dashboard() {
        // Required empty public constructor
    }

    NetworkConnection networkConnection = new NetworkConnection();
    ApiInterface client = ServiceGenerator.createService(ApiInterface.class);

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Dashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Dashboard newInstance(String param1, String param2) {
        Fragment_Dashboard fragment = new Fragment_Dashboard();
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
        View view = inflater.inflate(R.layout.fragment_fragment__dashboard, container, false);
        ButterKnife.bind(this, view);
        userPreferences = new UserPreferences(getActivity());

        setWallet_balance();
        getWalletHistroy();
        cardList = new ArrayList<>();
        cardAdapter = new CardAdapter(getContext(), cardList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cardAdapter);

//        populating the card
        insertElement();
        setAction();
        getHistory();

        SLide();

        mTrasactnNotifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new TransactionHistoryFragment();
                showFragment(fragment);
            }
        });


        return view;
    }

    private void setAction() {
        // wallet_blance_card.setOnClickListener(this);
        fund_wallet_card.setOnClickListener(this);
    }

    private void setWallet_balance() {
        String balance = userPreferences.getWalletBalance();
        /*String[] parts = balance.split(Pattern.quote("."));
        String kobo = parts[1];
        String naira = parts[0];*/

        NumberFormat nf_sum = NumberFormat.getNumberInstance(new Locale("en", "US"));
        nf_sum.setMaximumFractionDigits(2);
        DecimalFormat df_sum = (DecimalFormat) nf_sum;
        String actual_balance = df_sum.format(Double.valueOf(balance));

        wallet_balance.setText(actual_balance);
        //wallet_kobo.setText(kobo);
    }

    private void insertElement() {
//        referencing drawable for the logo
        int[] icons = new int[]{
                R.drawable.ic_quote_buy_img,
                R.drawable.ic_make_claim,
                R.drawable.ic_recent_actors_black_24dp,
                R.drawable.ic_claim_medium,
                R.drawable.ic_pin
        };

        Card m = new Card("Quote and Buy Insurance Policy", icons[0]);
        cardList.add(m);

        m = new Card("Make a Claim", icons[1]);
        cardList.add(m);

        /*m = new Card("Customer Management", icons[2]);
        cardList.add(m);*/

        m = new Card("List of Claim", icons[3]);
        cardList.add(m);

        /*m = new Card("Register New Customer", icons[4]);
        cardList.add(m);*/

        m = new Card("Manage Policy", icons[2]);
        cardList.add(m);

        m = new Card("Change Wallet Pin", icons[4]);
        cardList.add(m);

    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    private void getHistory() {


        //get client and call object for request

        Call<TransactionHead> call = client.transaction_hist("Token " + userPreferences.getUserToken());
        call.enqueue(new Callback<TransactionHead>() {
            @Override
            public void onResponse(Call<TransactionHead> call, Response<TransactionHead> response) {

                if (!response.isSuccessful()) {
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

                policy_item = response.body().getHistory();


                int count = policy_item.size();

                Log.i("Re-SuccessSize", String.valueOf(policy_item.size()));

                if (count == 0) {
                    mTransactnNotify.setVisibility(View.GONE);

                } else {

                    for (int i = 0; i < count; i++) {
                        checkIncomplete = policy_item.get(i).getStatus();
                        if (checkIncomplete.equals("Pending") || checkIncomplete.equals("Initiated")) {
                            mTransactnNotify.setVisibility(View.VISIBLE);
                            String name = userPreferences.getAgentLastName();
                            String full_note = "Hi! " + name + ", you have an incomplete transaction";
                            mTrasactnNote.setText(full_note);
                            return;
                        }
                    }

                    Log.i("SuccessChecked", checkIncomplete);
                }

            }

            @Override
            public void onFailure(Call<TransactionHead> call, Throwable t) {
                showMessage("Fetch failed, check your internet " + t.getMessage());
                Log.i("GEtError", t.getMessage());
            }
        });


    }

    @SuppressLint("CheckResult")
    private void SLide(){

        ArrayList<Integer> listImage = new ArrayList<>();
        ArrayList<String> listName = new ArrayList<>();

        listImage.add(R.drawable.motor);
        listName.add("Motor Insurance");

        listImage.add(R.drawable.marine);
        listName.add("Marine Insurance");

        listImage.add(R.drawable.swiss);
        listName.add("Swiss-F for you Family");

        listImage.add(R.drawable.travel);
        listName.add("Travel Insurance");

        listImage.add(R.drawable.all_risk);
        listName.add("All Risk Insurance");

        listImage.add(R.drawable.other);
        listName.add("Other Insurance");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.img_empty);
        //.placeholder(R.drawable.placeholder)


        for (int i = 0; i < listImage.size(); i++) {
            TextSliderView sliderView = new TextSliderView(getContext());
            // initialize SliderLayout
            sliderView
                    .image(listImage.get(i))
                    .description(listName.get(i))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(this);

            //add your extra information
            sliderView.bundle(new Bundle());
            sliderView.getBundle().putString("extra", listName.get(i));
            mSlider.addSlider(sliderView);
        }

        // set Slider Transition Animation
        // mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mSlider.setPresetTransformer(SliderLayout.Transformer.FlipPage);

        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
        mSlider.addOnPageChangeListener(this);

    }


    private void getWalletHistroy() {
        if (networkConnection.isNetworkConnected(getContext())) {

            progressbar.setVisibility(View.VISIBLE);
            Call<WalletObj> call = client.wallet_history("Token " + userPreferences.getUserToken());

            call.enqueue(new Callback<WalletObj>() {
                @Override
                public void onResponse(Call<WalletObj> call, Response<WalletObj> response) {
                    if (!response.isSuccessful()) {

                        try {
                            APIError apiError = ErrorUtils.parseError(response);

                            showMessage("Invalid Fetch: " + apiError.getErrors());
                            Log.i("Invalid Fetch", apiError.getErrors().toString());
                            Log.i("Invalid FetchErrorBody", response.errorBody().toString());
                            timeout_alert();

                        } catch (Exception e) {
                            Log.i("InvalidEntry", e.getMessage());
                            showMessage("Failed to fetch wallet history");
                            timeout_alert();

                        }
                        progressbar.setVisibility(View.GONE);
                        showMessage("Failed to fetch wallet history");

                    }

                    wallet_histories = response.body().getWallet_History();
                    progressbar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<WalletObj> call, Throwable t) {
                    showMessage("Failed to fetch wallet history " + t.getMessage());
                    Log.i("GEtError", t.getMessage());

                    progressbar.setVisibility(View.GONE);
                }
            });
        } else {
            showMessage("No Internet Connection");
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fund_wallet_card:

                fragment = new Fragment_FundWallet();
                showFragment(fragment);

                break;
           /* case R.id.wallet_blance_card:
                break;*/
        }
    }


    private void showMessage(String s) {
        Snackbar.make(dash_layout, s, Snackbar.LENGTH_SHORT).show();
    }

    @OnClick(R.id.wallet_blance_card)
    void showWalletHistory() {
        try {
            View view = getLayoutInflater().inflate(R.layout.fragment_bottom_wallet, null);
            final TextView textView = (TextView) view.findViewById(R.id.detail);
            final RecyclerView recycler_wallet_history = (RecyclerView) view.findViewById(R.id.recycler_wallet_history);
            final ImageView close = (ImageView) view.findViewById(R.id.close);
            BottomSheetDialog dialog = new BottomSheetDialog(getContext());

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


            walletHistoryAdapter = new WalletHistoryAdapter(getContext(), wallet_histories);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(), RecyclerView.VERTICAL, false);
            recycler_wallet_history.setLayoutManager(linearLayoutManager);
            recycler_wallet_history.setItemAnimator(new DefaultItemAnimator());
            recycler_wallet_history.setAdapter(walletHistoryAdapter);

            dialog.setContentView(view);
            dialog.show();
        } catch (Exception e) {
            showMessage("Something went wrong");
        }
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    @Override
    public void onStop() {
        mSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        nextActivity("Quote and Buy", "Select insurance product", QuoteBuyActivity.class);


    }

    private void nextActivity(String title,String subTitle, Class cardActivityClass) {
        Intent i = new Intent(getContext(), cardActivityClass);
        i.putExtra(Constant.CARD_OPTION_TITLE, title);
        i.putExtra(Constant.CARD_OPTION_SUBTITLE, subTitle);
        getContext().startActivity(i);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    private void timeout_alert() {

        new AlertDialog.Builder(getContext())
                .setTitle("Error !")
                .setIcon(R.drawable.ic_error_outline_black_24dp)
                .setMessage("Session Time-Out, Click Okay to re-login")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                        startActivity(new Intent(getActivity(), SignIn.class));
                        getActivity().finish();
                    }
                })
                .show();

    }
}
