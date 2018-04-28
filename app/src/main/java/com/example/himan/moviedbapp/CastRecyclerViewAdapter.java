package com.example.himan.moviedbapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by himan on 07-Apr-18.
 */

public class CastRecyclerViewAdapter extends RecyclerView.Adapter<CastRecyclerViewAdapter.ViewHolder> {
    interface OnItemClickListner{
        void onItemClick(int position);
    }
    OnItemClickListner mlistner;
    Context context;
    ArrayList<CastElement>castList;

    public CastRecyclerViewAdapter(OnItemClickListner mlistner, Context context, ArrayList<CastElement> castList) {
        this.mlistner = mlistner;
        this.context = context;
        this.castList = castList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=inflater.inflate(R.layout.movie_cast_element,parent,false);
        ViewHolder holder=new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CastElement castElement=castList.get(position);
        holder.name.setText(castElement.getName());
        if(castElement.getCharacter()!=null){
            holder.secondary.setText("as "+castElement.getCharacter());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistner.onItemClick(holder.getAdapterPosition());
            }
        });
        Picasso.get().load("http://image.tmdb.org/t/p/w342/"+castElement.getProfile_path()).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return castList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView secondary;
        ImageView image;
        View itemView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            name=itemView.findViewById(R.id.cast_name);
            secondary=itemView.findViewById(R.id.cast_secondary);
            image=itemView.findViewById(R.id.cast_image);
        }
    }
}
