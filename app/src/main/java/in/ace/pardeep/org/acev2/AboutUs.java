package in.ace.pardeep.org.acev2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class AboutUs extends AppCompatActivity {
    private ListView listView;
    private Context context=this;
    public static String[] listTitles;
    private int imageUrl;
    private String uppert;
    private String qualificationText;
    private String descriptionText;
    DetailListAboutUsContent detailListAboutUsContent;
    HomeScreen homeScreen;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        //Toolbar toolbar=(Toolbar)findViewById(R.id.tool);
        //setSupportActionBar(toolbar);
        //ActionBar actionBar=getSupportActionBar();


        button=(Button)findViewById(R.id.title_bar_left_menu);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // homeScreen=new HomeScreen();
               // homeScreen.setUpMenu();
            }
        });


        //listview
        listView=(ListView)findViewById(R.id.aboutUsListView);
        listTitles=new String[AboutUsListContent.aboutUsListContent.length];
        for (int i=0;i<listTitles.length;i++){
            listTitles[i]=AboutUsListContent.aboutUsListContent[i].getTitle();
        }
        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,listTitles);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               uppert=listTitles[position].toString();
                imageUrl=AboutUsListContent.aboutUsListContent[position].getImageId();
                qualificationText=AboutUsListContent.aboutUsListContent[position].getQualificationId();
                descriptionText=AboutUsListContent.aboutUsListContent[position].getDescriptionId();
                detailListAboutUsContent=new DetailListAboutUsContent(uppert,imageUrl,qualificationText,descriptionText);



                Intent intent=new Intent(context,DetailActivityAboutUs.class);
                startActivity(intent);


            }
        });



    }



}
