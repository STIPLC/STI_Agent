package com.example.sti_agent.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sti_agent.Detail_activities.MyClaimsDetail;
import com.example.sti_agent.Model.Claim.MyClaims.Claim;
import com.example.sti_agent.R;
import com.example.sti_agent.retrofit_interface.ItemClickListener;
import com.google.android.material.card.MaterialCardView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClaimsListAdapter extends RecyclerView.Adapter<ClaimsListAdapter.MyViewHolder> {

    private Context context;
    private List<Claim> claimList;


    public ClaimsListAdapter(Context context, List<Claim> claimList) {
        this.context = context;
        this.claimList = claimList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_claim_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Claim claimOption = claimList.get(i);
        String claim_num="Claim Num:";
        String policy_type="Policy Type:";
        holder.mClaimNum.setText(String.format("%s%s", claim_num, claimOption.getReference()));

        if (claimOption.getCostEstimate() != null) {
            NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
            nf.setMaximumFractionDigits(2);
            DecimalFormat df = (DecimalFormat) nf;
            String v_cost = "₦" + df.format(Double.valueOf(claimOption.getCostEstimate()));

            holder.mCost.setText(v_cost);
        } else {

            holder.mCost.setText("--");
        }

        holder.mType.setText(String.format("%s%s", policy_type, claimOption.getType()));
        holder.mDateTime.setText(claimOption.getCreatedAt());
        holder.mStatus.setText(claimOption.getStatus());
        holder.mDecription.setText(claimOption.getDescription());
        holder.setItemClickListener(pos -> {

            nextActivity(claimList.get(pos).getReference(),claimList.get(pos).getStatus(),claimList.get(pos).getCostEstimate(),
                    claimList.get(pos).getPolicyId(),claimList.get(pos).getType(),claimList.get(pos).getDescription(),
                    claimList.get(pos).getDateLoss(),claimList.get(pos).getCreatedAt(), MyClaimsDetail.class);



            });
    }

    private void nextActivity(String claim_num, String status, String cost, String policynum, String policy_type,
                              String desc, String date_loss, String claim_date, Class claimActivityClass) {
        Intent i = new Intent(context, claimActivityClass);

        i.putExtra("claim_num", claim_num);
        i.putExtra("status", status);
        i.putExtra("cost", cost);
        i.putExtra("policy_type", policy_type);
        i.putExtra("policynum", policynum);
        i.putExtra("desc", desc);
        i.putExtra("date_loss", date_loss);
        i.putExtra("claim_date", claim_date);
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return claimList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /** ButterKnife Code **/
        @BindView(R.id.transaction_card)
        MaterialCardView mTransactionCard;
        @BindView(R.id.product_thumnail)
        ImageView mProductThumnail;
        @BindView(R.id.claim_num)
        TextView mClaimNum;
        @BindView(R.id.cost)
        TextView mCost;
        @BindView(R.id.type)
        TextView mType;
        @BindView(R.id.decription)
        TextView mDecription;
        @BindView(R.id.date_time)
        TextView mDateTime;
        @BindView(R.id.status)
        TextView mStatus;
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

