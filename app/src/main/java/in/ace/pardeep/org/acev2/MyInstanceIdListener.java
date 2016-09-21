package in.ace.pardeep.org.acev2;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by hp 8 on 21-03-2016.
 */
public class MyInstanceIdListener extends InstanceIDListenerService {
    @Override
    public void onTokenRefresh() {
        Intent intent=new Intent(this,RegisterationIntentService.class);
        startActivity(intent);
    }
}
