package in.ace.pardeep.org.acev2;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.special.ResideMenu.ResideMenu;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends android.support.v4.app.Fragment {
    private ListView listView;
    public static String[] listTitles={"History","Mission & Vission","Management/Trustees","Advisors","Director","Principal"};
    private int imageUrl;
    private String uppert;
    private String qualificationText;
    private String descriptionText;
    DetailListAboutUsContent detailListAboutUsContent;
    HomeScreen homeScreen;
    Button button;
    private View view;
    HistoryAceFragment historyAceFragment;
    AboutUsWebView aboutUsWebView;
    private ResideMenu resideMenu;
    public AboutUsFragment() {
        // Required empty public constructor
    }

   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_about_us, container, false);
       // setUpView();
        System.out.println("Frag1......................");

        System.out.println("Frag2......................");
        //listview
        listView=(ListView)view.findViewById(R.id.aboutUsListView);
       /* listTitles=new String[AboutUsListContent.aboutUsListContent.length];

        for (int i=0;i<listTitles.length;i++){
            listTitles[i]=AboutUsListContent.aboutUsListContent[i].getTitle();
        }*/
        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,listTitles);
        System.out.println("Frag3................");
        System.out.println("Size of list ................" + listTitles.length);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /* uppert = listTitles[position].toString();
                imageUrl = AboutUsListContent.aboutUsListContent[position].getImageId();
                qualificationText = AboutUsListContent.aboutUsListContent[position].getQualificationId();
                descriptionText = AboutUsListContent.aboutUsListContent[position].getDescriptionId();
                detailListAboutUsContent = new DetailListAboutUsContent(uppert, imageUrl, qualificationText, descriptionText);
               */
                //**********************************************//

             //   Intent intent = new Intent(getActivity(), DetailActivityAboutUs.class);
               // startActivity(intent);
                historyAceFragment=new HistoryAceFragment();
                aboutUsWebView=new AboutUsWebView();
                Intent intent;


                switch (position){
                    case 0:
                       // changeFragment(new HistoryAceFragment());
                        aboutUsWebView.setFileUrl("file:///android_asset/html/history.html");
                        aboutUsWebView.changeActionBarText(listTitles[0]);
                        intent=new Intent(getActivity(),AboutUsWebView.class);
                        startActivity(intent);
                        break;
                    case 1:
                        historyAceFragment.setTextCustom(StaticData.missionVission);
                        intent=new Intent(getActivity(),HistoryAceFragment.class);
                        startActivity(intent);
                        break;
                    case 2:
                        aboutUsWebView.setFileUrl("file:///android_asset/html/trustee.html");
                        aboutUsWebView.changeActionBarText(listTitles[2]);
                        intent=new Intent(getActivity(),AboutUsWebView.class);
                        startActivity(intent);
                        break;
                    case 3:
                        aboutUsWebView.setFileUrl("file:///android_asset/html/advisors.html");
                        aboutUsWebView.changeActionBarText(listTitles[3]);
                        intent=new Intent(getActivity(),AboutUsWebView.class);
                        startActivity(intent);
                        break;
                    case 4:
                        aboutUsWebView.setFileUrl("file:///android_asset/html/director.html");
                        aboutUsWebView.changeActionBarText(listTitles[4]);
                        intent=new Intent(getActivity(),AboutUsWebView.class);
                        startActivity(intent);
                        break;
                    case 5:
                        aboutUsWebView.setFileUrl("file:///android_asset/html/principal.html");
                        aboutUsWebView.changeActionBarText(listTitles[5]);
                        intent=new Intent(getActivity(),AboutUsWebView.class);
                        startActivity(intent);
                        break;

                    default:
                        Toast.makeText(getActivity(), "Under Working!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    /*private void setUpView() {
        HomeScreen homeScreen=(HomeScreen)getActivity();
        resideMenu=homeScreen.getResideMenu();

        view.findViewById(R.id.btn_open_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        FrameLayout frameLayout=(FrameLayout)view.findViewById(R.id.ignored_view);
        resideMenu.addIgnoredView(frameLayout);
    }*/
    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    private void changeFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.frameLayoutAboutUsFragment,fragment,"fragment").setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack("fragment").commit();
    }


}
