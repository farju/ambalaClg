package in.ace.pardeep.org.acev2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by pardeep on 30-04-2016.
 */
public class DrawerHeader extends Activity {
    CircleImageView circleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_header);
        circleImageView=(CircleImageView)findViewById(R.id.studentImage);
    }
    public void setImage(Bitmap bitmap){
        circleImageView.setImageBitmap(bitmap);
    }
}
