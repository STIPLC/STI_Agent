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

import com.sti.sti_agent.Detail_activities.MySwissDetail;
import com.sti.sti_agent.Model.MyPolicies.Swis;
import com.sti.sti_agent.R;
import com.sti.sti_agent.retrofit_interface.ItemClickListener;
import com.google.android.material.card.MaterialCardView;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SwissAdapter extends RecyclerView.Adapter<SwissAdapter.MyViewHolder> {

    private Context context;
    private List<Swis> swisList;


    public SwissAdapter(Context context, List<Swis> swisList) {
        this.context = context;
        this.swisList = swisList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swiss_policies_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Swis swisOption = swisList.get(i);

        String start_text = "Start Date: "+swisOption.getCreatedAt();
        String end_text = "Expire Date: "+swisOption.getEnd();

        if (swisOption.getPrice() != null&& !swisOption.getPrice().equals("")) {
            holder.mPolicyNum.setText(swisOption.getPolicyNumber());
            NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
            nf.setMaximumFractionDigits(2);
            DecimalFormat df = (DecimalFormat) nf;
            String v_price = "₦" + df.format(Double.valueOf(swisOption.getPrice()));



            holder.mDateTime.setText(start_text);
            holder.mExpireTime.setText(end_text);
            holder.mPrice.setText(v_price);
            holder.mStatus.setText(swisOption.getStatus());
            holder.mPaymentStatus.setText(swisOption.getPaymentStatus());
            holder.mPolicyType.setText("Swiss-F Insurance");


            holder.setItemClickListener(pos -> {

                nextActivity(swisList.get(pos).getFirstName(), swisList.get(pos).getLastName(),
                        swisList.get(pos).getPhone(), swisList.get(pos).getPicture(), swisList.get(pos).getAddress(), swisList.get(pos).getDateOfBirth(),
                        swisList.get(pos).getCategory(), swisList.get(pos).getEmail(), swisList.get(pos).getStart(), swisList.get(pos).getEnd(),
                        swisList.get(pos).getPrice(), swisList.get(pos).getStatus(), swisList.get(pos).getPaymentStatus(),
                        swisList.get(pos).getNextOfKin(), swisList.get(pos).getNextOfKinPhone(), swisList.get(pos).getGender(), swisList.get(pos).getMaritalStatus(), swisList.get(pos).getPolicyNumber(), MySwissDetail.class);

            });

        } else {

            holder.mDateTime.setText(start_text);
            holder.mExpireTime.setText(end_text);

            holder.mPolicyNum.setText(swisOption.getPolicyNumber());
            holder.mPrice.setText("--");
            holder.mStatus.setText(swisOption.getStatus());
            holder.mPaymentStatus.setText(swisOption.getPaymentStatus());
            holder.mPolicyType.setText("Swiss-F Insurance");


            holder.setItemClickListener(pos -> {

                /*nextActivity(swisList.get(pos).getFirstName(), swisList.get(pos).getLastName(),
                        swisList.get(pos).getPhone(), swisList.get(pos).getPicture(), swisList.get(pos).getAddress(), swisList.get(pos).getDateOfBirth(),
                        swisList.get(pos).getCategory(), swisList.get(pos).getEmail(), swisList.get(pos).getStart(), swisList.get(pos).getEnd(),
                        swisList.get(pos).getPrice(), swisList.get(pos).getStatus(), swisList.get(pos).getPaymentStatus(),
                        swisList.get(pos).getNextOfKin(), swisList.get(pos).getNextOfKinPhone(), swisList.get(pos).getGender(), swisList.get(pos).getMaritalStatus(), swisList.get(pos).getPolicyNumber(), MySwissDetail.class);
*/
            });
        }
    }

    private void nextActivity(String firstname, String lastname,
                              String phone_num, String picture, String getAddr, String dob,
                              String category, String email, String start_date, String end_date,
                              String price, String status, String payment_status,
                              String next_of_kin, String next_of_phonenum, String gender, String marital_status,
                              String policynum, Class swissActivityClass) {
        Intent i = new Intent(context, swissActivityClass);

        i.putExtra("firstname", firstname);
        i.putExtra("lastname", lastname);
        i.putExtra("phone_num", phone_num);
        i.putExtra("picture", picture);
        i.putExtra("getAddr", getAddr);
        i.putExtra("start_date", start_date);
        i.putExtra("end_date", end_date);
        i.putExtra("price", price);
        i.putExtra("dob", dob);
        i.putExtra("payment_status", payment_status);
        i.putExtra("status", status);
        i.putExtra("category", category);
        i.putExtra("email", email);
        i.putExtra("next_of_kin", next_of_kin);
        i.putExtra("next_of_phonenum", next_of_phonenum);
        i.putExtra("gender", gender);
        i.putExtra("marital_status", marital_status);
        i.putExtra("policynum", policynum);

        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return swisList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


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

