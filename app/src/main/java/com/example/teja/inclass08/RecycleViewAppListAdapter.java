/*Assignment : Inclass08
Yash Ghia
Prabhakar Teja Seeda*/


package com.example.teja.inclass08;

/**
 * Created by teja on 10/30/17.
 */


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.security.PolicySpi;
import java.util.ArrayList;
import java.util.jar.Manifest;

public class RecycleViewAppListAdapter extends RecyclerView.Adapter<RecycleViewAppListAdapter.ViewHolder>  {
    private IAppListner mAppListner;
    private ArrayList<Integer> list;

    public RecycleViewAppListAdapter(IAppListner mAppListner,ArrayList<Integer> list ) {
        this.mAppListner = mAppListner;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView deleteImage ;
        public EditText ingredients;
        int data;
        public ViewHolder(View itemView) {
            super(itemView);
            ingredients = (EditText) itemView.findViewById(R.id.name);
            deleteImage = (ImageView) itemView.findViewById(R.id.removeImageView);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.ingredients,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        int data = list.get(position);
        holder.data=data;
        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //list.remove(position);
                MainActivity.trackResultsList.remove(position);
                SearchFragment.recycleAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    interface IAppListner
    {
        void addNewRow(ArrayList<Integer> trackResults) ;
    }
}
