
package com.example.macbookpro.hcic;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class draw extends MainActivity{
    private static int RESULT_LOAD_IMAGE = 1;
    private Paint paint;
    private ImageView iv_canvas;
    private Button btn_resume;
    private Bitmap baseBitmap;
    private Canvas canvas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);



        Button buttonLoadImage = (Button) findViewById(R.id.uploadimage);
        buttonLoadImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // open photo gallery
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //set up the result and return
                startActivityForResult(i,RESULT_LOAD_IMAGE);

            }
        });

        //initalize a paint and the width of paint is 5, red color
        paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.RED);

        iv_canvas =  findViewById(R.id.imageView);
        btn_resume =  findViewById(R.id.resume);
        btn_resume.setOnClickListener(click);
        iv_canvas.setOnTouchListener(touch);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null !=data ){
            Uri selectedImage =data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor =getContentResolver().query(selectedImage,filePathColumn,null,null,null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath =cursor.getString(columnIndex);
            cursor.close();

            ImageView iv = findViewById(R.id.imageView);
            iv.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
    private View.OnTouchListener touch = new View.OnTouchListener() {
        float startX;
        float startY;
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if (baseBitmap == null){
                        baseBitmap = Bitmap.createBitmap(iv_canvas.getWidth(), iv_canvas.getHeight(), Bitmap.Config.ARGB_8888);
                        canvas = new Canvas(baseBitmap);
                        canvas.drawColor(Color.WHITE);
                    }
                    startX = event.getX();
                    startY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float stopX =event.getX();
                    float stopY = event.getY();
                    //draw a line between two points
                    canvas.drawLine(startX, startY, stopX, stopY, paint);
                    startX = event.getX();
                    startY = event.getY();
                    //display the image into imageview
                    iv_canvas.setImageBitmap(baseBitmap);
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                default:
                    break;
            }
            return true;
        }
    };
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.resume:
                    resumeCanvas();
                    break;
                default:
                    break;
            }
        }
    };
    //resume
    protected void resumeCanvas(){
        if(baseBitmap != null){
            baseBitmap = Bitmap.createBitmap(iv_canvas.getWidth(), iv_canvas.getHeight(), Bitmap.Config.ARGB_8888);
            canvas = new Canvas(baseBitmap);
            canvas.drawColor(Color.WHITE);
            iv_canvas.setImageBitmap(baseBitmap);

        }
    }






}





//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Matrix;
//import android.graphics.Paint;
//import android.graphics.PixelFormat;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.os.Environment;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnTouchListener;
//import android.widget.Button;
//import android.widget.ImageView;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//
//public class draw extends MainActivity {
//
//    private ImageView img, imageTwo;
//    private Bitmap mBitmap;
//    private Canvas canvas;
//    private Paint paint;
//    // 重置按钮
//    private Button reset_btn;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_draw);
//
//        img = (ImageView) findViewById(R.id.imageView);
//        imageTwo = (ImageView) findViewById(R.id.imageView);
//
//
//        reset_btn = (Button) findViewById(R.id.resume);
//
//
//        reset_btn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                img.setImageBitmap(null);
//                showImage();
//
//            }
//        });
//
//        // 绘图
//        showImage();
//
//    }
//
//    private void showImage() {
//        int height = getWindow().getWindowManager().getDefaultDisplay().getHeight();
//        int wehit = getWindow().getWindowManager().getDefaultDisplay().getWidth();
//        // 创建一张图片
//        mBitmap = drawableToBitamp(getResources().getDrawable(R.drawable.smile));   //drawable中的图片
//        // 创建一张画布
//        canvas = new Canvas(mBitmap);
//        // 创建画笔
//        paint = new Paint();
//        // 画笔颜色为蓝色
//        paint.setColor(Color.BLUE);
//        // 宽度5个像素
//        paint.setStrokeWidth(5);
//        // 先将背景画上
//        canvas.drawBitmap(mBitmap, new Matrix(), paint);
//        img.setImageBitmap(mBitmap);
//
//        img.setOnTouchListener(new OnTouchListener() {
//            int startX;
//            int startY;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        // 获取手按下时的坐标
//                        startX = (int) event.getX();
//                        startY = (int) event.getY();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        // 获取手移动后的坐标
//                        int endX = (int) event.getX();
//                        int endY = (int) event.getY();
//                        // 在开始和结束坐标间画一条线
//                        canvas.drawLine(startX, startY, endX, endY, paint);
//                        // 刷新开始坐标
//                        startX = (int) event.getX();
//                        startY = (int) event.getY();
//                        img.setImageBitmap(mBitmap);
//                        break;
//                }
//                return true;
//            }
//        });
//
//    }
////保存到内存卡中
//
//    public void setmBitmap(){
//        File file = new File(Environment.getExternalStorageDirectory(),
//                "/test" + ".png");
//        if(!file.exists()){
//            OutputStream stream;
//            try {
//                file.createNewFile() ;
//                stream = new FileOutputStream(file);
//                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                stream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }else {
//            OutputStream stream;
//            try {
//                stream = new FileOutputStream(file);
//                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                stream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        readSdPic();
//    }
//读取出图片并显示
//    public void readSdPic(){
//        File file = new File(Environment.getExternalStorageDirectory(),
//                "/test" + ".png");
//        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//        iv_canvas.setImageBitmap(bitmap);   //显示图片
//    }

//    private Bitmap bitmap;
//    private Bitmap drawableToBitamp(Drawable drawable)
//    {
//        int w = drawable.getMinimumWidth();
//        int h = drawable.getMinimumHeight();
////		int height = getWindow().getWindowManager().getDefaultDisplay().getHeight();
//        int wehit = getWindow().getWindowManager().getDefaultDisplay().getWidth();
//        System.out.println("Drawable转Bitmap");
//        Bitmap.Config config =
//                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
//                        : Bitmap.Config.RGB_565;
//        bitmap = Bitmap.createBitmap(wehit,500,config);
//        //注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
//        Canvas canvas = new Canvas(bitmap);
//        drawable.setBounds(0, 0, w, h);
//        drawable.draw(canvas);
//        return bitmap;
//    }
//}

