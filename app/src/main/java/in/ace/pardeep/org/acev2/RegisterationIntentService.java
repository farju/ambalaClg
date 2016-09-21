package in.ace.pardeep.org.acev2;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hp 8 on 21-03-2016.
 */
public class RegisterationIntentService extends IntentService {

    private static final String TAG="RegIntentService";
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String GCM_TOKEN = "gcmToken";

    private static final String[] TOPICS = {"global","Ace"};
    SharedPreferences sharedPreferences;

    public RegisterationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);



        try {
            InstanceID instanceID=InstanceID.getInstance(this);
            String senderId=getResources().getString(R.string.gcmProject_defaultSenderId);
            String token=instanceID.getToken(senderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE,null);

           // sharedPreferences.edit().putString(GCM_TOKEN, token).apply();

            Log.d(TAG, "REgisteration Token id " + token);
           // System.out.println("Reg id       :" + token);

                    //int flag1=sharedPreferences.getInt("serverReg", 0);
            if(sharedPreferences.contains(QuickstartPreferences.SENT_TOKEN_TO_SERVER)==false) {
                sendRegisterationToServer(token);
                subscribeTopics(token);

            }

            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true).apply();

        } catch (IOException e) {
            e.printStackTrace();

            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();

        }

        Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void subscribeTopics(String token) {
        GcmPubSub gcmPubSub=GcmPubSub.getInstance(this);
        for(String topic :TOPICS){
            try {
                gcmPubSub.subscribe(token,"/topics/" + topic,null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private void sendRegisterationToServer(final String token) {

        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, true).apply();
        System.out.println("Token..................."+token);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://xdeveloper.royalwebhosting.net/gcmuserreg.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                System.out.println("response gcm................" + s);
                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("network problem...................."+volleyError);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("register", token);
                return param;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
