package in.ace.pardeep.org.acev2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivityAboutUs extends AppCompatActivity {
    private TextView upperTextView;
    private TextView textView;
    private TextView textView1;
    private ImageView imageView;
    private Button imageButton;

    //private String upperText;
    //private int imageId;
    //private String qualificationText;
    //private String descriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity_about_us);
        upperTextView=(TextView)findViewById(R.id.textUpperDetailListAboutUs);
        upperTextView.setText(DetailListAboutUsContent.upperText);
        imageView=(ImageView)findViewById(R.id.imageDetailListAboutUs);
        imageView.setImageResource(DetailListAboutUsContent.imageId);
        textView=(TextView)findViewById(R.id.qualificationTextDetailList);
        textView.setText(DetailListAboutUsContent.qualificationText);
        textView1=(TextView)findViewById(R.id.descriptionDetailListAboutUs);
        textView1.setText(DetailListAboutUsContent.descriptionText);

        //BackButton
        imageButton=(Button)findViewById(R.id.backButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

   /* public DetailActivityAboutUs(String descriptionText, String upperText, int imageId, String qualificationText) {
        this.descriptionText = descriptionText;
        this.upperText = upperText;
        this.imageId = imageId;
        this.qualificationText = qualificationText;
    }*/
}
