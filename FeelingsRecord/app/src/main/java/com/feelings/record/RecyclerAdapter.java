package com.feelings.record;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context content;
    List<Data> dataArrayList;
    int img;
    public RecyclerAdapter(Context context, List<Data> data, int img) {
        this.content=context;
        this.dataArrayList=data;
        this.img=img;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.data_cardview,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Data data=dataArrayList.get(position);
        String img = data.getImageview(); //경로에있던 이미지를 불러온다.
      //  holder.image.setImageResource(img);//비트맵(변수이름변경)
         // 기분에 받는값에 따라(switch)
        if (data.getImageview()==null)holder.image.setVisibility(View.INVISIBLE);
        else holder.image.setImageURI(Uri.parse(data.getImageview()));


        holder.title.setText(data.getContent());
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(content,data.getContent(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.moodImage.setImageDrawable(drawableMoodImage(data.getMood()));
    }

    @Override
    public int getItemCount() {
        return this.dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        CardView cardview;
        ImageView moodImage;


        public ViewHolder(View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.image);
            title=(TextView)itemView.findViewById(R.id.content);
            cardview=(CardView)itemView.findViewById(R.id.cardview);
            moodImage=itemView.findViewById(R.id.imageView2);
        }
    }
    private Drawable drawableMoodImage(int type){
        int imageId=9999;
        switch (type){
            case FeelwriteActivity.VERY_HAPPY:
                imageId = R.drawable.feel1;
                break;
            case FeelwriteActivity.HAPPY:
                imageId = R.drawable.feel2;
                break;
            case FeelwriteActivity.NORMAL:
                imageId = R.drawable.feel3;
                break;
            case FeelwriteActivity.BAD:
                imageId = R.drawable.feel4;
                break;
            case FeelwriteActivity.HORRIBLE:
                imageId = R.drawable.feel5;
                break;
        }
        if (imageId==9999)return null;
        else return content.getResources().getDrawable(imageId);
    }
}