package com.feelings.record;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import android.widget.AdapterView.OnItemClickListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PhotoActivity extends AppCompatActivity {
    GridView gridView;
    private Context mContext;
    private Uri uri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feel_photo);
        mContext = this;

        gridView = findViewById(R.id.gridView);

        final ImageAdapter view = new ImageAdapter(this);
        gridView.setAdapter(view);
        gridView.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                view.callImageViewer(position);

            }
        });

    }

    /**==========================================
     * 		        Adapter class
     * ==========================================*/
    public class ImageAdapter extends BaseAdapter {
        private ArrayList<String> thumbsDataList;
        private ArrayList<String> thumbsIDList;

        ImageAdapter(Context c){
            mContext = c;
            thumbsDataList = new ArrayList<String>();
            thumbsIDList = new ArrayList<String>();
            getThumbInfo(thumbsIDList, thumbsDataList);
        }

        public final void callImageViewer(int selectedIndex){
            Intent intent = new Intent(mContext, photoAdapter.class);
            String imgPath = getImageInfo(thumbsIDList.get(selectedIndex));
            intent.putExtra("filename", imgPath);
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 101);
        }

        public int getCount() {
            return thumbsIDList.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;

            if (convertView == null){
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,220));
                imageView.setAdjustViewBounds(true);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setPadding(3, 3, 3, 3);

            }else{
                imageView = (ImageView) convertView;
            }

            /*?⑤쾾???붿쭏??醫뗪쾶?섍린?꾪빐 ?ｌ?肄붾뱶 ?곸슜 ?덈맖 ?먮킄?쇳븿
             * ?꾨땲硫?諛묒뿉?덈뒗 ?щ꽕?쇱쓣 ?섏젙*/
            /*Display dp = ((WindowManager) mContext.getSystemService(
                    Context.WINDOW_SERVICE)).getDefaultDisplay();
            int dpWidth = dp.getWidth();
            int dpHeight = dp.getHeight();*/

            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inPreferredConfig = Bitmap.Config.RGB_565;
            option.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(), R.id.gridView, option);

            option.inSampleSize = 4;

            ContentResolver resolver = getContentResolver();
            int id = Integer.parseInt(thumbsIDList.get(position));
            Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(resolver, id,
                    MediaStore.Images.Thumbnails.MICRO_KIND, option);
            // bitmap = getOrientationBitmap(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, bitmap);
            imageView.setImageBitmap(bitmap);

            return imageView;

        }

        // ?대?吏 媛곷룄 議곗젅 ?곸슜?덈맖
        public Bitmap getOrientationBitmap(Uri uri, Bitmap bitmap){
            try {
                ExifInterface exif = new ExifInterface(uri.getPath());
                int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                int exifDegree = exifOrientationToDegrees(exifOrientation);
                bitmap = rotate(bitmap, exifDegree);
                return bitmap;
            }
            catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        private int exifOrientationToDegrees(int exifOrientation) {
            if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
                return 90;
            } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
                return 180;
            } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
                return 270;
            }
            return 0;
        }

        public Bitmap rotate(Bitmap src, float degree) {
            Matrix matrix = new Matrix();
            matrix.postRotate(degree);
            return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(),
                    matrix, true);
        }

        private void getThumbInfo(ArrayList<String> IDs, ArrayList<String> Datas){

            String[] proj = {MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DISPLAY_NAME
            };
            File file = new File(""+getCacheDir().getPath());
            Uri uri = Uri.parse("content://"+getCacheDir().getPath());
            Cursor cursor = getContentResolver().query( MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                    null, null, null, null);

            String result;
            for(File s : file.listFiles()){
                Log.d("CursorColumns",s.getPath());

            }
            if (cursor != null && cursor.moveToFirst()){
                int col_id = cursor.getColumnIndex("_id");
                int col_data = cursor.getColumnIndex("_data");
                int col_display_name = cursor.getColumnIndex("_display_name");

                do {
                    Log.d("CursorColumns",cursor.getString(col_data));
                }while (cursor.moveToNext());
            }
            cursor.close();
            return;
        }

        private String getImageInfo(String thumbID){
            String imageDataPath = null;
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    proj, "_ID='"+ thumbID +"'", null, null);

            if (cursor.moveToFirst()){
                if (cursor.getCount() > 0){
                    int imgData = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    imageDataPath = cursor.getString(imgData);
                }
            }
            cursor.close();
            return imageDataPath;
        }
    }
}