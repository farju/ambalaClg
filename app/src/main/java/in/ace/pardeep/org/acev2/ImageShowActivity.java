package in.ace.pardeep.org.acev2;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageShowActivity extends AppCompatActivity {

    private static Bitmap bitmap=null;
    PhotoViewAttacher photoViewAttacher;
    ImageView imageView;

    public static void setBitmap(Bitmap bitmap) {
        ImageShowActivity.bitmap = bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);
        imageView=(ImageView)findViewById(R.id.imageViewActivity);
        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
            photoViewAttacher=new PhotoViewAttacher(imageView);
        }
        else {
            Toast.makeText(ImageShowActivity.this, "No image to show!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_show, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
