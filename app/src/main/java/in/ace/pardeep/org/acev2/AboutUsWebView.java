package in.ace.pardeep.org.acev2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class AboutUsWebView extends AppCompatActivity {

    WebView webView;
    Button button;
    TextView textView;
    private static String fileUrl="";
    private static String upperActionBarTextView="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_web_view);

        button=(Button)findViewById(R.id.backButton);
        webView=(WebView)findViewById(R.id.webViewHistoryAce);
        textView=(TextView)findViewById(R.id.textViewAboutUsWebView);
        if(!upperActionBarTextView.equalsIgnoreCase("")){
            textView.setText(upperActionBarTextView);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        webView.setWebViewClient(new WebViewClient());

        String filepath=fileUrl;
        File file=new File(fileUrl);
        if(file.exists()){
            Log.d("File path :",filepath);
        }
        webView.loadUrl(fileUrl);
    }

    public void setFileUrl(String url){
        fileUrl=url;
    }
    public void changeActionBarText(String text){
        upperActionBarTextView=text;
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }
}
