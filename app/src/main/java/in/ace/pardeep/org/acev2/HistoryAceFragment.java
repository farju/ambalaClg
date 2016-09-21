package in.ace.pardeep.org.acev2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

/**
 * Created by pardeep on 27-05-2016.
 */
public class HistoryAceFragment extends Activity {

   //TextView textView;
    Button button;
    WebView webView;
    private static String textHistory="";
    private static String htmlData="<html><body style=\"text-align:justify\"> %s </body></Html>";
    private static String imageUrl="";

    public HistoryAceFragment(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historyace);
       // textView=(TextView)findViewById(R.id.textViewHistoryAce);
       // textView.setText(textHistory);

        webView=(WebView)findViewById(R.id.webViewHistoryAce);
        webView.loadData(String.format(htmlData,textHistory),"text/html","utf-8");

        button=(Button)findViewById(R.id.backButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void setTextCustom(String textView1){
     textHistory=textView1;
    }
    public void setTextLayout(String textLayout){
        htmlData=textLayout;
    }
    public void openWebView(String url){
        
    }

}


