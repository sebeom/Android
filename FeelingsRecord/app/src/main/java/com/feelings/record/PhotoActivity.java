package com.feelings.record;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PhotoActivity extends AppCompatActivity {
    GridView gridView;
    private Context context;
    private Uri imageUri;

    private static final String TAG = "PhotoActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feel_photo);
        context = this;

        gridView = findViewById(R.id.gridView);

        final ImageAdapter ia = new ImageAdapter(this);
        gridView.setAdapter(ia);
        gridView.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                ia.callImageViewer(position);

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
            context = c;
            thumbsDataList = new ArrayList<String>();
            thumbsIDList = new ArrayList<String>();
            getThumbInfo(thumbsIDList, thumbsDataList);
        }

        public final void callImageViewer(int selectedIndex){
            Intent intent = new Intent(context, photoAdapter.class);
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
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,220));
                imageView.setAdjustViewBounds(true);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setPadding(3, 3, 3, 3);

            }else{
                imageView = (ImageView) convertView;
            }

            /*앨범의 화질을 좋게하기위해 넣은코드 적용 안됨 손봐야함
             * 아니면 밑에있는 섬네일을 수정*/
            /*Display dp = ((WindowManager) context.getSystemService(
                    Context.WINDOW_SERVICE)).getDefaultDisplay();
            int dpWidth = dp.getWidth();
            int dpHeight = dp.getHeight();*/

            BitmapFactory.Options bo = new BitmapFactory.Options();
            bo.inPreferredConfig = Bitmap.Config.RGB_565;
            bo.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(), R.id.gridView, bo);

            bo.inSampleSize = 4;

            ContentResolver cr = getContentResolver();
            int id = Integer.parseInt(thumbsIDList.get(position));
            Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(cr, id,
                    MediaStore.Images.Thumbnails.MICRO_KIND, bo);
            //bitmap = getOrientationBitmap(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, bitmap);
            imageView.setImageBitmap(bitmap);

            return imageView;

        }

        public Bitmap getOrientationBitmap(Uri uri, Bitmap bm){
            try {
                ExifInterface exif = new ExifInterface(uri.getPath());
                int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                int exifDegree = exifOrientationToDegrees(exifOrientation);
                bm = rotate(bm, exifDegree);
                return bm;
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
            /*String images = Environment.getExternalStorageDirectory() + "cache";
            String imgPath = "/data/dara/com.feelings.record/cache";//or context.getCacheDir().getPath();
            context.getCacheDir().getPath(); 로 하니까
            /data/user/0/com.feelings.record/cache로 지정됨*/
            String imgPath = "data/user/0/com.feelings.record/cache";
            File file = new File(context.getCacheDir().getPath());

            String[] proj = {MediaStore.Images.Media.DATA};

            Cursor cs = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, null
                    , null
                    ,null);
            Log.d(TAG, "갤리러 보기 쿼리 : " + cs);


            if (cs.moveToFirst()){
                String ID;
                String ImageID;
                String Data;


                int IDCol = cs.getColumnIndex(MediaStore.Images.Media._ID);
                int DataCol = cs.getColumnIndex(MediaStore.Images.Media.DATA);
                int ImageIDCol = cs.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
                int num = 0;
                do {
                    ID = cs.getString(IDCol);
                    Data = cs.getString(DataCol);
                    ImageID = cs.getString(ImageIDCol);
                    num++;
                    if (ImageID != null){
                        IDs.add(ID);
                        Datas.add(Data);
                    }
                }while (cs.moveToNext());
            }
            Log.d(TAG, "INTERNAL PRIVATE CACHE DIR: "
                    + getCacheDir().getAbsolutePath());
            cs.close();
            return;
        }

        private String getImageInfo(String thumbID){
            String imageDataPath = null;
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cs = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    proj, "_ID='"+ thumbID +"'", null, null);

            Log.d(TAG, "상세보기 쿼리 : " + cs);
            if (cs.moveToFirst()){
                if (cs.getCount() > 0){
                    int imgData = cs.getColumnIndex(MediaStore.Images.Media.DATA);
                    imageDataPath = cs.getString(imgData);
                }
            }
            cs.close();
            return imageDataPath;
        }
    }
}