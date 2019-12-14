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

import com.example.sti_agent.Constant;
import com.example.sti_agent.Detail_activities.MyAllRiskDetail;
import com.example.sti_agent.Detail_activities.MyCustomerDetail;
import com.example.sti_agent.Model.CustomerModel.CustomerDetail;
import com.example.sti_agent.Model.TransactionHistroy.History;
import com.example.sti_agent.R;
import com.example.sti_agent.interfaces.ItemClickListener;
import com.example.sti_agent.operation_activity.PolicyPaymentActivity;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {

    private Context context;
    private List<CustomerDetail> mCustomerDetailList;


    public CustomerAdapter(Context context, List<CustomerDetail> mCustomerDetail) {
        this.context = context;
        this.mCustomerDetailList = mCustomerDetail;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list_item, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        CustomerDetail customerOption = mCustomerDetailList.get(i);

        if (customerOption.getCompanyName() != null&& !customerOption.getCompanyName().equals("null")) {

            holder.mCustomerName.setText(customerOption.getCompanyName());
            holder.mAddress.setText(customerOption.getEmployerAddress());
            holder.mEmail.setText(customerOption.getEmail());
            holder.mPhonenumber.setText(customerOption.getPhone());
            if(customerOption.getPicture()!=null)
            Picasso.with(context).load(customerOption.getPicture())
                    .centerCrop()
                    .resize(100, 100)
                    .placeholder(R.drawable.man)
                    .into(holder.mUserThumbnail);
            //holder.mUserThumbnail.setText(mCustomerDetailList.getEmail());


            holder.setItemClickListener(pos -> {

                nextActivity(mCustomerDetailList.get(pos).getTitle(), mCustomerDetailList.get(pos).getFirstName(), mCustomerDetailList.get(pos).getLastName(),
                        mCustomerDetailList.get(pos).getPhone(), mCustomerDetailList.get(pos).getEmail(),
                        mCustomerDetailList.get(pos).getAddress(),mCustomerDetailList.get(pos).getEmployerAddress(), mCustomerDetailList.get(pos).getGender(), mCustomerDetailList.get(pos).getMaritalStatus(), mCustomerDetailList.get(pos).getNextOfKin(),
                        mCustomerDetailList.get(pos).getNextOfKinPhone(), mCustomerDetailList.get(pos).getBusiness(), mCustomerDetailList.get(pos).getNationality(),
                        mCustomerDetailList.get(pos).getCompanyName(),mCustomerDetailList.get(pos).getPicture(),MyCustomerDetail.class);


            });

        } else {


        holder.mCustomerName.setText(customerOption.getFirstName() + " " + customerOption.getLastName());
        holder.mAddress.setText(customerOption.getAddress());
        holder.mEmail.setText(customerOption.getEmail());
        holder.mPhonenumber.setText(customerOption.getPhone());
        Picasso.with(context).load(customerOption.getPicture())
                .centerCrop()
                .resize(100, 100)
                .placeholder(R.drawable.man)
                .into(holder.mUserThumbnail);
        //holder.mUserThumbnail.setText(mCustomerDetailList.getEmail());


        holder.setItemClickListener(pos -> {

            nextActivity(mCustomerDetailList.get(pos).getTitle(), mCustomerDetailList.get(pos).getFirstName(), mCustomerDetailList.get(pos).getLastName(),
                    mCustomerDetailList.get(pos).getPhone(), mCustomerDetailList.get(pos).getEmail(),
                    mCustomerDetailList.get(pos).getAddress(),mCustomerDetailList.get(pos).getEmployerAddress(), mCustomerDetailList.get(pos).getGender(), mCustomerDetailList.get(pos).getMaritalStatus(), mCustomerDetailList.get(pos).getNextOfKin(),
                    mCustomerDetailList.get(pos).getNextOfKinPhone(), mCustomerDetailList.get(pos).getBusiness(), mCustomerDetailList.get(pos).getNationality(),
                    mCustomerDetailList.get(pos).getCompanyName(),mCustomerDetailList.get(pos).getPicture(), MyCustomerDetail.class);



        });

    }


    }

    private void nextActivity(String title, String firstname, String lastname, String phone_no,String email,
            String address,String employer_addr,String gender,String marital,String nextOfKin,String nextOfKinPhone,String business,String nationality,
                              String company_name,String picture,Class productActivityClass) {

        Intent i = new Intent(context, productActivityClass);
        i.putExtra(Constant.TITLE, title);
        i.putExtra(Constant.CUSTOMER_FIRSTNAME, firstname);
        i.putExtra(Constant.CUSTOMER_LASTNAME, lastname);
        i.putExtra(Constant.CUSTOMER_PHONE_NO, phone_no);
        i.putExtra(Constant.CUSTOMER_EMAIL, email);
        i.putExtra(Constant.ADDRESS, address);
        i.putExtra(Constant.EMPLOYER_ADDR, employer_addr);
        i.putExtra(Constant.GENDER, gender);
        i.putExtra(Constant.MARITAL_STATUS, marital);
        i.putExtra(Constant.NEXT_OF_KIN, nextOfKin);
        i.putExtra(Constant.NEXT_OF_KIN_PHONE, nextOfKinPhone);
        i.putExtra(Constant.BUSNIESS, business);
        i.putExtra(Constant.NATIONALITY, nationality);
        i.putExtra(Constant.COMPANY_NAME, company_name);
        i.putExtra(Constant.CUSTOMER_PICTURE, picture);
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return mCustomerDetailList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * ButterKnife Code
         **/
        @BindView(R.id.transaction_card)
        MaterialCardView mProductCard;
        @BindView(R.id.user_thumbnail)
        CircleImageView mUserThumbnail;
        @BindView(R.id.customerName)
        TextView mCustomerName;
        @BindView(R.id.phone)
        TextView mPhonenumber;
        @BindView(R.id.email)
        TextView mEmail;
        @BindView(R.id.address)
        TextView mAddress;

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

