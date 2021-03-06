package com.example.registrocovid;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView mnombre, mbarrio, mresultadotv,matencion,mfecha,mid,mdireccion;
    View mView;

    public ViewHolder(@NonNull View itemView)  {
        super(itemView);

        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }

        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v,getAdapterPosition());
                return true;
            }
        });

        mnombre=itemView.findViewById(R.id.rnombre);
        mbarrio=itemView.findViewById(R.id.rbarrio);
        mresultadotv=itemView.findViewById(R.id.rresultadotv);
        mid=itemView.findViewById(R.id.rid);
        mdireccion = itemView.findViewById(R.id.rdireccion);
        mfecha=itemView.findViewById(R.id.rdireccion);
        matencion = itemView.findViewById(R.id.ratencion);

    }

    private ViewHolder.ClickListener mClickListener;

    public  interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
