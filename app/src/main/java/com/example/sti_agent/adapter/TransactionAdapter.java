package com.example.sti_agent.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sti_agent.Constant;
import com.example.sti_agent.Forms.MotorInsuredForm;
import com.example.sti_agent.Model.TransactionHistroy.History;
import com.example.sti_agent.R;
import com.example.sti_agent.interfaces.ItemClickListener;
import com.example.sti_agent.operation_activity.PolicyPaymentActivity;
import com.example.sti_agent.operation_activity.PolicyWalletPayActivity;
import com.google.android.material.card.MaterialCardView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {

    private Context context;
    private List<History> transactionList;
    String poliy_type;


    public TransactionAdapter(Context context, List<History> transactionList) {
        this.context = context;
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trans_history_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        History transactionOption = transactionList.get(i);

        String p_tag = transactionOption.getPolicyNumber();
        holder.mPolicyNum.setText(transactionOption.getPolicyNumber());

        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
        nf.setMaximumFractionDigits(2);
        DecimalFormat df = (DecimalFormat) nf;
        String v_amount = "₦" + df.format(Double.valueOf(transactionOption.getAmount()));

        holder.mAmount.setText(v_amount);
        holder.mDateTime.setText(transactionOption.getCreatedAt());
        holder.mDecription.setText(transactionOption.getDescription());

        if ("Pending".equals(transactionOption.getStatus())) {
            holder.mStatus.setText("Incomplete");
        } else {
            holder.mStatus.setText(transactionOption.getStatus());
        }


        holder.setItemClickListener(pos -> {
            if (transactionOption.getStatus().equals("Pending")) {


                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Choose Mode of Payment");
// add a list
                String[] entry = {"Paystack", "Wallet"};
                builder.setItems(entry, (dialog, option) -> {
                    switch (option) {
                        case 0:
                            // direct entry





                            if (p_tag.contains("MOT")) {
                                poliy_type = "vehicle";
                            } else if (p_tag.contains("SWISS")) {
                                poliy_type = "swiss";
                            } else if (p_tag.contains("ET")) {
                                poliy_type = "travel";
                            } else if (p_tag.contains("ACC/AR")) {
                                poliy_type = "all_risk";
                            } else if (p_tag.contains("MAR")) {
                                poliy_type = "marine";
                            } else {
                                poliy_type = "vehicle";
                            }


                            nextActivity(transactionList.get(pos).getReference(), transactionList.get(pos).getPolicyNumber(),
                                    transactionList.get(pos).getAmount(), poliy_type, PolicyPaymentActivity.class);

                            break;

                        case 1: // export





                            if (p_tag.contains("MOT")) {
                                poliy_type = "vehicle";
                            } else if (p_tag.contains("SWISS")) {
                                poliy_type = "swiss";
                            } else if (p_tag.contains("ET")) {
                                poliy_type = "travel";
                            } else if (p_tag.contains("ACC/AR")) {
                                poliy_type = "all_risk";
                            } else if (p_tag.contains("MAR")) {
                                poliy_type = "marine";
                            } else {
                                poliy_type = "vehicle";
                            }


                            nextActivity(transactionList.get(pos).getReference(), transactionList.get(pos).getPolicyNumber(),
                                    transactionList.get(pos).getAmount(), poliy_type, PolicyWalletPayActivity.class);


                            break;

                    }

                });
// create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }







                });


    }

    private void nextActivity(String ref, String policy_num, String amount, String poliy_type, Class productActivityClass) {
        Intent i = new Intent(context, productActivityClass);
        i.putExtra(Constant.REF, ref);
        i.putExtra(Constant.POLICY_NUM, policy_num);
        i.putExtra(Constant.TOTAL_PRICE, amount);
        i.putExtra(Constant.POLICY_TYPE, poliy_type);
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * ButterKnife Code
         **/
        @BindView(R.id.transaction_card)
        MaterialCardView mProductCard;
        @BindView(R.id.product_thumnail)
        ImageView mProductThumnail;
        @BindView(R.id.policy_num)
        TextView mPolicyNum;
        @BindView(R.id.amount)
        TextView mAmount;
        @BindView(R.id.decription)
        TextView mDecription;
        @BindView(R.id.date_time)
        TextView mDateTime;
        @BindView(R.id.status)
        TextView mStatus;
        /**
         * ButterKnife Code
         **/

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

