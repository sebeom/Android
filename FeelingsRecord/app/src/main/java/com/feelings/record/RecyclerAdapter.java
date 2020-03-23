package com.feelings.record;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context content;
    List<Data> dataArrayList;

    public RecyclerAdapter(Context context, List<Data> data) {
        this.content=context;
        this.dataArrayList=data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.data_cardview,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Data data=dataArrayList.get(position);
        final int cardId=data.getId();
        String img = data.getImageview(); //경로에있던 이미지를 불러온다.
        //holder.image.setImageResource(img);//비트맵(변수이름변경)
        // 기분에 받는값에 따라(switch)
        if(data.getImageview()!=null){
            Uri externalUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String[] projection = new String[]{
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DESCRIPTION,
                    MediaStore.Images.Media.DISPLAY_NAME
            };
            Cursor cursor = content.getContentResolver().query(externalUri,projection,
                    "_display_name='"+data.getImageview()+"' AND description='FLR'",null,null);
            if(cursor != null && cursor.moveToFirst()){
                    Drawable drawable = BitmapDrawable.createFromPath(cursor.getString(0));
                    holder.image.setImageDrawable(drawable);
            }
            ViewGroup.LayoutParams params = holder.image.getLayoutParams();
            params.height = 300;
        }
        else{
            ViewGroup.LayoutParams params = holder.image.getLayoutParams();
            params.height = 0;
            params.width = 0;
        }

        holder.title.setText(data.getContent());
        SimpleDateFormat format = new SimpleDateFormat("yyyy년MM월dd일");
        String[] saveDate = data.getDate().split(",");
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(saveDate[0]),Integer.parseInt(saveDate[1]),Integer.parseInt(saveDate[2]));
        holder.dateText.setText(format.format(c.getTime()));
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(content,FeelwriteActivity.class);
                intent.putExtra("DATA",data);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                content.startActivity(intent);

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
        TextView dateText;
        int cardId;


        public ViewHolder(View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.image);
            title=(TextView)itemView.findViewById(R.id.content);
            cardview=(CardView)itemView.findViewById(R.id.cardview);
            moodImage=itemView.findViewById(R.id.imageView2);
            dateText=itemView.findViewById(R.id.dateTextView);
        }
    }
    private Drawable drawableMoodImage(int type){
        int imageId=9999;
        switch (type){
            case Data.VERY_HAPPY:
                imageId = R.drawable.feel1;
                break;
            case Data.HAPPY:
                imageId = R.drawable.feel2;
                break;
            case Data.NORMAL:
                imageId = R.drawable.feel3;
                break;
            case Data.BAD:
                imageId = R.drawable.feel4;
                break;
            case Data.HORRIBLE:
                imageId = R.drawable.feel5;
                break;
        }
        if (imageId==9999)return null;
        else return content.getResources().getDrawable(imageId);
    }
}