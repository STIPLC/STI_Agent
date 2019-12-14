package com.example.sti_agent.adapter.PoliciesAdapter;

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

import com.example.sti_agent.Detail_activities.MyVehicleDetail;
import com.example.sti_agent.Model.MyPolicies.Vehicle;
import com.example.sti_agent.R;
import com.example.sti_agent.retrofit_interface.ItemClickListener;
import com.google.android.material.card.MaterialCardView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.MyViewHolder> {

    private Context context;
    private List<Vehicle> vehicleList;


    public VehicleAdapter(Context context, List<Vehicle> vehicleList) {
        this.context = context;
        this.vehicleList = vehicleList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_policies_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Vehicle vehicleOption = vehicleList.get(i);

        String start_text = "Start Date: "+vehicleOption.getCreatedAt();
        String end_text = "Expire Date: "+vehicleOption.getEnd();

        if (vehicleOption.getPrice() != null && !vehicleOption.getPrice().equals("")) {

            holder.mPolicyNum.setText(vehicleOption.getPolicyNumber());
            NumberFormat nf = NumberFormat.getNumberInstance(new Locale("en", "US"));
            nf.setMaximumFractionDigits(2);
            DecimalFormat df = (DecimalFormat) nf;
            String v_price = "₦" + df.format(Double.valueOf(vehicleOption.getPrice()));

            holder.mDateTime.setText(start_text);
            holder.mExpireTime.setText(end_text);

            holder.mPrice.setText(v_price);
            holder.mStatus.setText(vehicleOption.getStatus());
            holder.mPaymentStatus.setText(vehicleOption.getPaymentStatus());
            holder.mPolicyType.setText(vehicleOption.getPolicyType());


            holder.setItemClickListener(pos -> {

                nextActivity(vehicleList.get(pos).getBodyType(), vehicleList.get(pos).getChasisNumber(), vehicleList.get(pos).getEnd(), vehicleList.get(pos).getStart(),
                        vehicleList.get(pos).getEngineNumber(), vehicleList.get(pos).getMake(), vehicleList.get(pos).getPaymentReference(),
                        vehicleList.get(pos).getPeriod(), vehicleList.get(pos).getPolicyType(), vehicleList.get(pos).getEnhancedThirdParty(), vehicleList.get(pos).getPrice(), vehicleList.get(pos).getPrivateCommercial(),
                        vehicleList.get(pos).getStatus(), vehicleList.get(pos).getPaymentStatus(), vehicleList.get(pos).getRegistrationNumber(), vehicleList.get(pos).getVehicleValue(),
                        vehicleList.get(pos).getYear(), vehicleList.get(pos).getPolicyNumber(), MyVehicleDetail.class);

            });


        } else {


            holder.mDateTime.setText(start_text);
            holder.mExpireTime.setText(end_text);

            holder.mPolicyNum.setText(vehicleOption.getPolicyNumber());
            holder.mPrice.setText("--");
            holder.mStatus.setText(vehicleOption.getStatus());
            holder.mPaymentStatus.setText(vehicleOption.getPaymentStatus());
            holder.mPolicyType.setText(vehicleOption.getPolicyType());

            holder.setItemClickListener(pos -> {

                /*nextActivity(vehicleList.get(pos).getBodyType(), vehicleList.get(pos).getChasisNumber(), vehicleList.get(pos).getEnd(), vehicleList.get(pos).getStart(),
                        vehicleList.get(pos).getEngineNumber(), vehicleList.get(pos).getMake(), vehicleList.get(pos).getPaymentReference(),
                        vehicleList.get(pos).getPeriod(), vehicleList.get(pos).getPolicyType(), vehicleList.get(pos).getEnhancedThirdParty(), vehicleList.get(pos).getPrice(), vehicleList.get(pos).getPrivateCommercial(),
                        vehicleList.get(pos).getStatus(), vehicleList.get(pos).getPaymentStatus(), vehicleList.get(pos).getRegistrationNumber(), vehicleList.get(pos).getVehicleValue(),
                        vehicleList.get(pos).getYear(), vehicleList.get(pos).getPolicyNumber(), MyVehicleDetail.class);
*/
            });

        }
    }

    private void nextActivity(String body_type, String chasis_num, String end_date, String start_date,
                              String engine_num, String make, String payment_ref,
                              String period, String policy_type, String enhancePolicetype, String price, String cover_type,
                              String status, String payment_status, String reg_num, String value,
                              String year, String policy_num, Class vehicleActivityClass) {

        Intent i = new Intent(context, vehicleActivityClass);
        i.putExtra("body_type", body_type);
        i.putExtra("chasis_num", chasis_num);
        i.putExtra("engine_num", engine_num);
        i.putExtra("policy_type", policy_type);
        i.putExtra("eh_policy_type", enhancePolicetype);
        i.putExtra("make", make);
        i.putExtra("start_date", start_date);
        i.putExtra("end_date", end_date);
        i.putExtra("price", price);
        i.putExtra("payment_ref", payment_ref);
        i.putExtra("payment_status", payment_status);
        i.putExtra("status", status);
        i.putExtra("cover_type", cover_type);
        i.putExtra("period", period);
        i.putExtra("reg_num", reg_num);
        i.putExtra("value", value);
        i.putExtra("policy_num", policy_num);
        i.putExtra("year", year);
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * ButterKnife Code
         **/
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

