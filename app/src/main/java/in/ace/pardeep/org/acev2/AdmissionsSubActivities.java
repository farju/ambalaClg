package in.ace.pardeep.org.acev2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AdmissionsSubActivities extends AppCompatActivity {

    WebView webView;
    TextView textView;
    private static String url="";
    private static String upperText="";
    Button buttonToBack;

    public static void setUpperText(String upperText) {
        AdmissionsSubActivities.upperText = upperText;
    }

    public static void setUrl(String url) {
        AdmissionsSubActivities.url = url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admissions_sub_activities);
        textView=(TextView)findViewById(R.id.textViewAdmissionsSub);
        webView=(WebView)findViewById(R.id.webViewAdmissionsSubActivity);
        buttonToBack=(Button)findViewById(R.id.backButton);
        buttonToBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        webView.setWebViewClient(new WebViewClient());
        textView.setText(upperText);
        if(!url.equalsIgnoreCase("")){
            webView.loadUrl(url);
        }
        else {
            Toast.makeText(AdmissionsSubActivities.this, "Can't Load Try Again", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admissions_sub_activities, menu);
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
