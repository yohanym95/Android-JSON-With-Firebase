package com.example.yohan.readhub1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ImageTypeViewHolder> {

    private ArrayList<Model> dataSet ;
    private Context context;
    public  onItemClicked mListner;

    public interface onItemClicked{
        void OnItemClick(int index);

    }

    public void SetOnItemClickListener(onItemClicked listner){
       mListner = listner;
    }

    public RecycleViewAdapter(ArrayList<Model> mlist, Context context){
        this.dataSet = mlist;
        this.context = context;
    }

    public  class ImageTypeViewHolder extends RecyclerView.ViewHolder{

        TextView title,subtitle;
        ImageView imageView;

        public ImageTypeViewHolder(@NonNull View itemView) {
            super(itemView);

            this.title = itemView.findViewById(R.id.tvtitle);
            this.subtitle = itemView.findViewById(R.id.tvsubtitle);
            this.imageView = itemView.findViewById(R.id.iconImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListner.OnItemClick(position);
                        }
                    }

                }
            });


        }
    }
    @NonNull
    @Override
    public ImageTypeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.postdeatils,viewGroup,false);

        return new ImageTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageTypeViewHolder viewHolder, int i) {

        final Model model = dataSet.get(i);
        viewHolder.title.setText(model.title);
        viewHolder.subtitle.setText(model.date);
        String imgarurl = model.image;


        Picasso.get().load(imgarurl).fit().centerInside().into(viewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
