package com.example.sti_agent.adapter.PoliciesAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sti_agent.Detail_activities.MyEticDetail;
import com.example.sti_agent.Model.MyPolicies.Travel;
import com.example.sti_agent.R;
import com.example.sti_agent.retrofit_interface.ItemClickListener;
import com.google.android.material.card.MaterialCardView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EticAdapter extends RecyclerView.Adapter<EticAdapter.MyViewHolder> {

    private Context context;
    private List<Travel> travelList;


    public EticAdapter(Context context, List<Travel> travelList) {
        this.context = context;
        this.travelList = travelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.etic_policies_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Travel travelOption = travelList.get(i);

        String start_text = "Start Date: "+travelOption.getCreatedAt();
        String end_text = "Expire Date: "+travelOption.getEnd();

        if (travelOption.getPrice() != null&& !travelOption.getPrice().equals("")) {
            holder.mPolicyNum.setText(travelOption.getPolicyNumber());

            NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
            nf.setMaximumFractionDigits(2);
            DecimalFormat df = (DecimalFormat) nf;
            String v_price = "₦" + df.format(Double.valueOf(travelOption.getPrice()));



            holder.mDateTime.setText(start_text);
            holder.mExpireTime.setText(end_text);

            holder.mPrice.setText(v_price);
            holder.mStatus.setText(travelOption.getStatus());
            holder.mPaymentStatus.setText(travelOption.getPaymentStatus());
            holder.mPolicyType.setText(travelOption.getPolicyType());


            holder.setItemClickListener(pos -> {

                nextActivity(travelList.get(pos).getPolicyNumber(), travelList.get(pos).getTripDuration(), travelList.get(pos).getPlaceDeparture()
                        , travelList.get(pos).getPlaceArrival(), travelList.get(pos).getTravelMode(), travelList.get(pos).getPolicyType(),
                        travelList.get(pos).getPrice(), travelList.get(pos).getStart(), travelList.get(pos).getEnd(), travelList.get(pos).getPaymentReference()
                        , travelList.get(pos).getStatus(), travelList.get(pos).getPaymentStatus(), travelList.get(pos).getAddressCountryOfVisit()
                        , MyEticDetail.class);


            });
        } else {

            holder.mDateTime.setText(start_text);
            holder.mExpireTime.setText(end_text);
            holder.mPolicyNum.setText(travelOption.getPolicyNumber());
            holder.mPrice.setText("--");
            holder.mStatus.setText(travelOption.getStatus());
            holder.mPaymentStatus.setText(travelOption.getPaymentStatus());
            holder.mPolicyType.setText(travelOption.getPolicyType());

            holder.setItemClickListener(pos -> {

                /*nextActivity(travelList.get(pos).getPolicyNumber(), travelList.get(pos).getTripDuration(), travelList.get(pos).getPlaceDeparture()
                        , travelList.get(pos).getPlaceArrival(), travelList.get(pos).getTravelMode(), travelList.get(pos).getPolicyType(),
                        travelList.get(pos).getPrice(), travelList.get(pos).getStart(), travelList.get(pos).getEnd(), travelList.get(pos).getPaymentReference()
                        , travelList.get(pos).getStatus(), travelList.get(pos).getPaymentStatus(), travelList.get(pos).getAddressCountryOfVisit()
                        , MyEticDetail.class);*/


            });
        }
    }

    private void nextActivity(String policynum, String trip_duration, String departure_plc
                    , String arrive_place, String travel_mode, String policy_type,
                              String price, String startdate, String end_date, String payment_ref
                    , String status, String payment_status, String addr_country_visit,
                              Class eticActivityClass) {

        Intent i = new Intent(context, eticActivityClass);
        i.putExtra("policynum", policynum);
        i.putExtra("trip_duration", trip_duration);
        i.putExtra("departure_plc", departure_plc);
        i.putExtra("policy_type", policy_type);
        i.putExtra("start_date", startdate);
        i.putExtra("end_date", end_date);
        i.putExtra("arrive_place", arrive_place);
        i.putExtra("policy_price", price);
        i.putExtra("payment_status", payment_status);
        i.putExtra("status", status);
        i.putExtra("payment_reference", payment_ref);
        i.putExtra("travel_mode", travel_mode);
        i.putExtra("addr_country_visit", addr_country_visit);

        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return travelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /** ButterKnife Code **/
        @BindView(R.id.transaction_card)
        MaterialCardView mTransactionCard;
        @BindView(R.id.product_thumnail)
        ImageView mProductThumnail;
        @BindView(R.id.policy_num)
        TextView mPolicyNum;
        @BindView(R.id.price)
        TextView mPrice;
        @BindView(R.id.policy_type)
        TextView mPolicyType;
        @BindView(R.id.date_time)
        TextView mDateTime;
        @BindView(R.id.status)
        TextView mStatus;
        @BindView(R.id.payment_status)
        TextView mPaymentStatus;
        @BindView(R.id.expire_time)
        TextView mExpireTime;
        /** ButterKnife Code **/

        ItemClickListener itemClickListener;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }

    }
}

