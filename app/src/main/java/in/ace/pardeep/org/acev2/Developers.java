package in.ace.pardeep.org.acev2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Developers extends AppCompatActivity implements View.OnClickListener {

    ImageButton fbButton,googleButton,linkedInButton,nextFbButton,nextGoogleButton,nextLinkedInButton;
    Button buttonToBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);
        buttonToBack=(Button)findViewById(R.id.backButton);
        fbButton=(ImageButton)findViewById(R.id.myFbButton);
        googleButton=(ImageButton)findViewById(R.id.myGoogle);
        linkedInButton=(ImageButton)findViewById(R.id.myLinkedIn);
        nextFbButton=(ImageButton)findViewById(R.id.nextfb);
        nextGoogleButton=(ImageButton)findViewById(R.id.nextg);
        nextLinkedInButton=(ImageButton)findViewById(R.id.nextlinkedin);
        googleButton.setOnClickListener(this);
        fbButton.setOnClickListener(this);
        linkedInButton.setOnClickListener(this);
        nextFbButton.setOnClickListener(this);
        nextFbButton.setVisibility(View.GONE);
        nextGoogleButton.setOnClickListener(this);
        nextLinkedInButton.setOnClickListener(this);
        buttonToBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void openMyFb(){
        goToUrl("https://www.facebook.com/love.raswant");
    }

    private void goToUrl(String s) {
        Uri uri=Uri.parse(s);
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_developers, menu);
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

    @Override
    public void onClick(View v) {
        if(v==fbButton){
            openMyFb();
        }
        else if(v==googleButton){
            goToUrl("https://plus.google.com/103286582925696425239");
        }
        else if(v==linkedInButton){
            goToUrl("https://in.linkedin.com/in/pardeep-kumar-225012b6");
        }
        else if(v==nextFbButton){
            goToUrl("https://www.facebook.com/devashish.gupta.7");
        }
        else if(v==nextGoogleButton){
            goToUrl("https://plus.google.com/113474274771106877980");
        }
        else if(v==nextLinkedInButton){
            goToUrl("https://in.linkedin.com/in/devashish-kumar-335b1422");
        }
    }
}
