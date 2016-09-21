package in.ace.pardeep.org.acev2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Departments extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    //private static String categoryDepartment="";
    Button button;
    TextView textView;
    private static String upperTitleDept="";
    private static String webUrl="";
    TextView textView1;
    WebView webView;

   // private static String[] departmentContent={"Mission/Vision","HOD Message","Rubrics", "Faculty","Societies", "R & D"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments);
       // listView=(ListView)findViewById(R.id.listViewDepartments);
        button=(Button)findViewById(R.id.backButton);
        textView=(TextView)findViewById(R.id.textViewUpperActionDepartment);
        textView1=(TextView)findViewById(R.id.textViewLoad);

        button.setOnClickListener(this);
        textView.setText(upperTitleDept);
        webView=(WebView)findViewById(R.id.webViewDepartment);
        if(!webUrl.equalsIgnoreCase("")){
            webView.loadUrl(webUrl);
        }

        webView.setWebViewClient(new WebViewClient());


       // ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,departmentContent);
       // listView.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_departments, menu);
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
   /* public void setDepartmentCategory(String id){
        categoryDepartment=id;
    }*/
    public void setUpperTitleDept(String title){
        upperTitleDept=title;
    }

    @Override
    public void onClick(View v) {
        if(v==button){
            onBackPressed();
        }
    }
    public void setWebUrl(String url){
        webUrl=url;
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }
}
