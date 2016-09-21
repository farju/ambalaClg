package in.ace.pardeep.org.acev2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by pardeep on 27-05-2016.
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        Thread thread =new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent=new Intent(SplashScreen.this,HomeScreen.class);
                    startActivity(intent);
                }
            }
        };thread.start();

    }
}
