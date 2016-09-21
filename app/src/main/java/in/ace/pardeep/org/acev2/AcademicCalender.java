package in.ace.pardeep.org.acev2;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import uk.co.senab.photoview.PhotoViewAttacher;

public class AcademicCalender extends AppCompatActivity implements View.OnClickListener {

    private static Bitmap bitmap=null;
    ImageView imageView,imageView1;
    private PhotoViewAttacher photoViewAttacher;
    Button backButton,rightButton;

    public static void setBitmap(Bitmap bitmap) {
        AcademicCalender.bitmap = bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_calender);
        imageView=(ImageView)findViewById(R.id.imageViewAcademicCalender);
        backButton=(Button)findViewById(R.id.backButtonProfile);
        rightButton=(Button)findViewById(R.id.rightMenuButton);
        backButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);

        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
            photoViewAttacher=new PhotoViewAttacher(imageView);

        }
    }



    @Override
    public void onClick(View v) {
        if(v==backButton){
            onBackPressed();
        }
        else if(v==rightButton){
            showMenuList();
        }

    }

    private void showMenuList() {
        PopupMenu popupMenu=new PopupMenu(this,rightButton);
        popupMenu.getMenuInflater().inflate(R.menu.academiccalender,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.save:
                        saveImageToGallery();
                        return true;
                    default:
                        return false;
                }

            }
        });
        popupMenu.show();
    }

    private void saveImageToGallery() {
        if(bitmap!=null){
            String path= Environment.getExternalStorageDirectory().toString();
            File dir=new File(path+"/AceApp/Images");
            if(!dir.exists()){
                dir.mkdirs();
            }
            String fileName="IMG-"+System.currentTimeMillis()+".jpg";
            try {
                File file=new File(dir,fileName);
                FileOutputStream fileOutputStream=new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG,90,fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                Toast.makeText(AcademicCalender.this, "File Saved!", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(AcademicCalender.this, "Failed to save file!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();

            }
        }
        else {

        }
    }
}
