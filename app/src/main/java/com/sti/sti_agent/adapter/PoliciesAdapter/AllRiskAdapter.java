package com.sti.sti_agent.adapter.PoliciesAdapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sti.sti_agent.Detail_activities.MyAllRiskDetail;
import com.sti.sti_agent.Model.MyPolicies.AllRisk;
import com.sti.sti_agent.R;
import com.sti.sti_agent.retrofit_interface.ItemClickListener;
import com.google.android.material.card.MaterialCardView;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllRiskAdapter extends RecyclerView.Adapter<AllRiskAdapter.MyViewHolder> {

    private Context context;
    private List<AllRisk> allriskList;


    public AllRiskAdapter(Context context, List<AllRisk> allriskList) {
        this.context = context;
        this.allriskList = allriskList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allrisk_policies_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        AllRisk allriskOption = allriskList.get(i);
        String start_text = "Start Date: "+allriskOption.getCreatedAt();
        String end_text = "Expire Date: "+allriskOption.getEnd();

        Log.i("PriceAllRisk", allriskOption.getPrice());

        if (allriskOption.getPrice() != null && !allriskOption.getPrice().equals("") ) {

            holder.mPolicyNum.setText(allriskOption.getPolicyNumber());
            NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
            nf.setMaximumFractionDigits(2);
            DecimalFormat df = (DecimalFormat) nf;
            String v_price = "₦" + df.format(Double.valueOf(allriskOption.getPrice()));



            holder.mDateTime.setText(start_text);
            holder.mExpireTime.setText(end_text);

            holder.mPrice.setText(v_price);
            holder.mStatus.setText(allriskOption.getStatus());
            holder.mPaymentStatus.setText(allriskOption.getPaymentStatus());
            holder.mPolicyType.setText(allriskOption.getPolicyType());


            holder.setItemClickListener(pos -> {
                nextActivity(allriskList.get(pos).getItem(), allriskList.get(pos).getValue(), allriskList.get(pos).getPeriod(),
                        allriskList.get(pos).getPolicyNumber(), allriskList.get(pos).getPolicyType(),
                        allriskList.get(pos).getPrice(), allriskList.get(pos).getStart(), allriskList.get(pos).getEnd(), allriskList.get(pos).getReceipt(),
                        allriskList.get(pos).getPaymentStatus(), allriskList.get(pos).getStatus(), allriskList.get(pos).getPaymentReference(),
                        allriskList.get(pos).getImei(), allriskList.get(pos).getSerial(), MyAllRiskDetail.class);

            });

        } else {

            holder.mDateTime.setText(start_text);
            holder.mExpireTime.setText(end_text);
            holder.mPolicyNum.setText(allriskOption.getPolicyNumber());
            holder.mPrice.setText(allriskOption.getPrice());
            holder.mStatus.setText(allriskOption.getStatus());
            holder.mPaymentStatus.setText(allriskOption.getPaymentStatus());
            holder.mPolicyType.setText(allriskOption.getPolicyType());


          /*  holder.setItemClickListener(pos -> {
                nextActivity(allriskList.get(pos).getItem(), allriskList.get(pos).getValue(), allriskList.get(pos).getPeriod(),
                        allriskList.get(pos).getPolicyNumber(), allriskList.get(pos).getPolicyType(),
                        allriskList.get(pos).getPrice(), allriskList.get(pos).getStart(), allriskList.get(pos).getEnd(), allriskList.get(pos).getReceipt(),
                        allriskList.get(pos).getPaymentStatus(), allriskList.get(pos).getStatus(), allriskList.get(pos).getPaymentReference(),
                        allriskList.get(pos).getImei(), allriskList.get(pos).getSerial(), MyAllRiskDetail.class);

            });*/
        }
    }

    private void nextActivity(String Item, String value, String period,
                              String policynum, String policy_type,
                              String policy_price, String start_date, String end_date, String receipt_link,
                              String payment_status, String status, String payment_reference, String imei, String serial,
                              Class allriskActivityClass) {
        Intent i = new Intent(context, allriskActivityClass);
        i.putExtra("Item", Item);
        i.putExtra("value", value);
        i.putExtra("period", period);
        i.putExtra("policynum", policynum);
        i.putExtra("policy_type", policy_type);
        i.putExtra("start_date", start_date);
        i.putExtra("end_date", end_date);
        i.putExtra("receipt_link", receipt_link);
        i.putExtra("policy_price", policy_price);
        i.putExtra("payment_status", payment_status);
        i.putExtra("status", status);
        i.putExtra("payment_reference", payment_reference);
        i.putExtra("imei", imei);
        i.putExtra("serial", serial);

        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return allriskList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

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

