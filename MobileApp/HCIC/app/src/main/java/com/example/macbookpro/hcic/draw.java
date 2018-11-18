
package com.example.macbookpro.hcic;

        import android.app.Activity;
        import android.content.Intent;
        import android.database.Cursor;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.RemoteException;
        import android.provider.MediaStore;
        import android.util.Log;
        import android.view.MotionEvent;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.support.v7.app.AppCompatActivity;
        import android.widget.Toast;

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

        iv_canvas = (ImageView) findViewById(R.id.imageView);
        btn_resume = (Button) findViewById(R.id.resume);
        btn_resume.setOnClickListener(click);
        iv_canvas.setOnTouchListener(touch);

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode==RESULT_OK && data!=null){
            //retrive the return data , and uri address
            Uri selectedImage = data.getData();
            String[]filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn,null,null,null);
            cursor.moveToFirst();
            //retrive the picture path
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            //display the photo on the screen
            ImageView imageView =(ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }



}

