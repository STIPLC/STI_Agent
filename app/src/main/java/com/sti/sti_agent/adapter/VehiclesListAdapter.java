package com.sti.sti_agent.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sti.sti_agent.Model.Vehicle.VehicleDetails;
import com.sti.sti_agent.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class VehiclesListAdapter extends RealmRecyclerViewAdapter<VehicleDetails, VehiclesListAdapter.MyViewHolder> {

private Context context;

    Realm realm;
   // int count=0;


    public VehiclesListAdapter(RealmResults<VehicleDetails> list , Context context) {
        super(list,true,true);
        this.context=context;
        realm=Realm.getDefaultInstance();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.vehicles_list,parent,false);
        ButterKnife.bind(this, view);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final VehicleDetails temp=getItem(position);



      if(temp==null){
          Log.i("Database ","NUll");
      }

        assert temp != null;

      if(temp.getPrivate_com_type().equals("Motor Cycle")){
          holder.policytype_txt.setText(temp.getPrivate_com_type());
          holder.vehicle_value_title.setText("Motor Cycle\nValue");
          holder.vehicle_value.setText(temp.getMotorcylce_value());
          holder.reg_num_txt.setText(temp.getRegistration_number());
          holder.reg_code.setText(temp.getRegistration_number());
          holder.vehicle_make_txt.setText("---");
          holder.vehicle_type_txt.setText("---");

      }else {

          holder.policytype_txt.setText(temp.getPrivate_com_type());
          holder.vehicle_make_txt.setText(temp.getVehicle_make());
          holder.vehicle_type_txt.setText(temp.getVehicle_type());
          holder.reg_num_txt.setText(temp.getRegistration_number());
          holder.vehicle_value.setText(temp.getVehicle_value());
          holder.reg_code.setText(temp.getRegistration_number());
      }


    }


    public class MyViewHolder extends
            RecyclerView.ViewHolder {


          /*@BindView(R.id.numbering)
          TextView numbering;*/
          @BindView(R.id.reg_code)
          TextView reg_code;
          @BindView(R.id.policytype_txt)
          TextView policytype_txt;
          @BindView(R.id.vehicle_make_txt)
          TextView vehicle_make_txt;
          @BindView(R.id.vehicle_type_txt)
          TextView vehicle_type_txt;
          @BindView(R.id.reg_num_txt)
          TextView reg_num_txt;
          @BindView(R.id.vehicle_value_txt)
          TextView vehicle_value;
        @BindView(R.id.vehicle_value_title)
        TextView vehicle_value_title;



        //private Context context;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}
