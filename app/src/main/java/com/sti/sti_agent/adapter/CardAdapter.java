package com.sti.sti_agent.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sti.sti_agent.Constant;
import com.sti.sti_agent.Forms.ClaimList;
import com.sti.sti_agent.Forms.ManagePolicy;
import com.sti.sti_agent.Model.Card;
import com.sti.sti_agent.R;
import com.sti.sti_agent.interfaces.ItemClickListener;
import com.sti.sti_agent.operation_activity.MakeClaimActivity;
import com.sti.sti_agent.operation_activity.PinActivity;
import com.sti.sti_agent.operation_activity.QuoteBuyActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

private Context context;
private List<Card> cardList;

public CardAdapter(Context context, List<Card> cardList) {
        this.context = context;
        this.cardList = cardList;
        }

@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dash_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Card cardOption = cardList.get(i);

//        bind data to view
        holder.optionTitle.setText(cardOption.getTitle());
        holder.cardLogo.setImageResource(cardOption.getThumbnail());


        holder.setItemClickListener(pos -> {
                switch (cardList.get(pos).getTitle()) {
                case "Quote and Buy Insurance Policy":
                        nextActivity("Quote and Buy", "Select insurance product", QuoteBuyActivity.class);
                        break;
                case "Make a Claim":
                    nextActivity("Claim", "Tender your claim to us", MakeClaimActivity.class);
                        break;
                case "Manage Policy":
                        nextActivity("Manage Policy", "Buy more quote today", ManagePolicy.class);
                        break;
                case "List of Claim":
                    nextActivity("List of Claim", "check your claim status", ClaimList.class);
                        break;
                case "Change Wallet Pin":
                     nextActivity("Wallet Pin", "change your wallet pin", PinActivity.class);
                     break;

                }

                }

                );
        }

private void nextActivity(String title,String subTitle, Class cardActivityClass) {
        Intent i = new Intent(context, cardActivityClass);
        i.putExtra(Constant.CARD_OPTION_TITLE, title);
        i.putExtra(Constant.CARD_OPTION_SUBTITLE, subTitle);
        context.startActivity(i);

        }

@Override
public int getItemCount() {
        return cardList.size();
        }

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.option_title_tv)
    TextView optionTitle;
    @BindView(R.id.option_logo)
    ImageView cardLogo;
    

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
