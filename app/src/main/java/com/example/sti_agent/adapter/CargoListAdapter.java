package com.example.sti_agent.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sti_agent.Model.Marine.CargoDetail;
import com.example.sti_agent.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class CargoListAdapter extends RealmRecyclerViewAdapter<CargoDetail, CargoListAdapter.MyViewHolder> {

private Context context;

    Realm realm;






    public CargoListAdapter(RealmResults<CargoDetail> list , Context context) {
        super(list,true,true);
        this.context=context;
        realm=Realm.getDefaultInstance();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.cargo_list,parent,false);
        ButterKnife.bind(this, view);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final CargoDetail temp=getItem(position);



      if(temp==null){
          Log.i("Database ","NUll");
      }

        assert temp != null;

        holder.pfi_noList_txt.setText(temp.getPfi_number());
        holder.pfi_dateList_txt.setText(temp.getPfi_date());
        holder.descripList_txt.setText(temp.getDesc_goods());
        holder.quantityList_txt.setText(temp.getQuantity());
        holder.toalAmountList_txt.setText(temp.getValue());
        holder.portLoadingList_txt.setText(temp.getLoading_port());
        holder.portDischargeList_txt.setText(temp.getDischarge_port());


    }


    public class MyViewHolder extends
            RecyclerView.ViewHolder {



          @BindView(R.id.pfi_noList_txt)
          TextView pfi_noList_txt;
          @BindView(R.id.pfi_dateList_txt)
          TextView pfi_dateList_txt;
          @BindView(R.id.descripList_txt)
          TextView descripList_txt;
          @BindView(R.id.quantityList_txt)
          TextView quantityList_txt;
          @BindView(R.id.toalAmountList_txt)
          TextView toalAmountList_txt;

          @BindView(R.id.portLoadingList_txt)
          TextView portLoadingList_txt;
          @BindView(R.id.portDischargeList_txt)
          TextView portDischargeList_txt;



        //private Context context;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}
