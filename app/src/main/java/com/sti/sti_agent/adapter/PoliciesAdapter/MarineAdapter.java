package com.sti.sti_agent.adapter.PoliciesAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sti.sti_agent.Detail_activities.MyMarineDetail;
import com.sti.sti_agent.Model.MyPolicies.Marine;
import com.sti.sti_agent.R;
import com.sti.sti_agent.retrofit_interface.ItemClickListener;
import com.google.android.material.card.MaterialCardView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarineAdapter extends RecyclerView.Adapter<MarineAdapter.MyViewHolder> {

    private Context context;
    private List<Marine> marineList;


    public MarineAdapter(Context context, List<Marine> marineList) {
        this.context = context;
        this.marineList = marineList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marine_policies_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Marine marineOption = marineList.get(i);
        String start_text = "Start Date: "+marineOption.getCreatedAt();
        String end_text = "Expire Date: "+marineOption.getEnd();

        if (marineOption.getPrice() != null) {
            holder.mPolicyNum.setText(marineOption.getPolicyNumber());

            NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
            nf.setMaximumFractionDigits(2);
            DecimalFormat df = (DecimalFormat) nf;
            String v_price = "₦" + df.format(Double.valueOf(marineOption.getPrice()));



            holder.mDateTime.setText(start_text);
            holder.mExpireTime.setText(end_text);

            holder.mPrice.setText(v_price);
            holder.mStatus.setText(marineOption.getStatus());
            holder.mPaymentStatus.setText(marineOption.getPaymentStatus());
            holder.mPolicyType.setText(marineOption.getPolicyType());


            holder.setItemClickListener(pos -> {

                nextActivity(marineList.get(pos).getPolicyNumber(), marineList.get(pos).getDescription(), marineList.get(pos).getValue()
                        , marineList.get(pos).getLoadingPort(), marineList.get(pos).getDischargePort(), marineList.get(pos).getPolicyType(),
                        marineList.get(pos).getPrice(), marineList.get(pos).getStart(), marineList.get(pos).getEnd(), marineList.get(pos).getPaymentReference()
                        , marineList.get(pos).getStatus(), marineList.get(pos).getPfiDate(), marineList.get(pos).getPfiNumber(), marineList.get(pos).getConveyanceMode(), marineList.get(pos).getPaymentStatus(), marineList.get(pos).getConversionRate()
                        , MyMarineDetail.class);


            });

        } else {
            holder.mPolicyNum.setText(marineOption.getPolicyNumber());


            holder.mDateTime.setText(start_text);
            holder.mExpireTime.setText(end_text);
            holder.mPrice.setText("--");
            holder.mStatus.setText(marineOption.getStatus());
            holder.mPaymentStatus.setText(marineOption.getPaymentStatus());
            holder.mPolicyType.setText(marineOption.getPolicyType());


            holder.setItemClickListener(pos -> {

                /*nextActivity(marineList.get(pos).getPolicyNumber(), marineList.get(pos).getDescription(), marineList.get(pos).getValue()
                        , marineList.get(pos).getLoadingPort(), marineList.get(pos).getDischargePort(), marineList.get(pos).getPolicyType(),
                        marineList.get(pos).getPrice(), marineList.get(pos).getStart(), marineList.get(pos).getEnd(), marineList.get(pos).getPaymentReference()
                        , marineList.get(pos).getStatus(), marineList.get(pos).getPfiDate(), marineList.get(pos).getPfiNumber(), marineList.get(pos).getConveyanceMode(), marineList.get(pos).getPaymentStatus(), marineList.get(pos).getConversionRate()
                        , MyMarineDetail.class);

*/
            });
        }
    }

    private void nextActivity(String policy_num, String desc, String value
                    , String loadingPort, String dischargeport, String policy_type,
                              String price, String start_date, String end_date, String payment_ref
                    , String status, String Pfi_date, String pfi_num, String covey_mode, String payment_status, String coversion_rate
                    , Class marineActivityClass) {
        Intent i = new Intent(context, marineActivityClass);

        i.putExtra("policy_num", policy_num);
        i.putExtra("desc", desc);
        i.putExtra("loadingPort", loadingPort);
        i.putExtra("dischargeport", dischargeport);
        i.putExtra("policy_type", policy_type);
        i.putExtra("start_date", start_date);
        i.putExtra("end_date", end_date);
        i.putExtra("price", price);
        i.putExtra("payment_ref", payment_ref);
        i.putExtra("payment_status", payment_status);
        i.putExtra("status", status);
        i.putExtra("Pfi_date", Pfi_date);
        i.putExtra("pfi_num", pfi_num);
        i.putExtra("value", value);
        i.putExtra("covey_mode", covey_mode);
        i.putExtra("coversion_rate", coversion_rate);

        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return marineList.size();
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

