package in.ace.pardeep.org.acev2;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.special.ResideMenu.ResideMenu;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdmissionFragment extends Fragment {

    ResideMenu resideMenu;

    private static String[] listFee={"Annual Fee OF B.TECH And M.TECH Courses","College Bus Charges","Hostel Facility Charges"};
    private static String[] downloadList={"Fee Deposit Form","B.Tech Admission Form","M.tech Admission Form"};

    GridView gridView;
    View view;

    FeesActivity feesActivity;

    public AdmissionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_admission, container, false);

        gridView=(GridView)view.findViewById(R.id.gridViewAdmissionFragment);
        AdmissionGridViewAdapter admissionGridViewAdapter=new AdmissionGridViewAdapter();
        gridView.setAdapter(admissionGridViewAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(getActivity(),ApplyOnline.class));
                        break;
                    case 1:
                        AdmissionsSubActivities.setUpperText("Facilities");
                        AdmissionsSubActivities.setUrl("file:///android_asset/html/facilities.html");
                        startActivity(new Intent(getActivity(),AdmissionsSubActivities.class));
                        break;
                    case 2:
                        AdmissionsSubActivities.setUpperText("Procedure");
                        AdmissionsSubActivities.setUrl("file:///android_asset/html/procedure.html");
                        startActivity(new Intent(getActivity(),AdmissionsSubActivities.class));
                        break;
                    case 3:
                        showFeesDialog();
                        break;
                    case 4:
                        openDialogForFaq();
                        break;
                    case 5:
                        openDialogDownloadList();
                        break;
                }
            }
        });

       // setUpView();

        return  view;
    }

    private void openDialogDownloadList() {
        AlertDialog.Builder alertDownload=new AlertDialog.Builder(getActivity());
        alertDownload.setTitle("Select option");
        alertDownload.setItems(downloadList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               switch (which){
                   case 0:
                       downloadFormFromUrl("http://www.ambalacollege.com/docs/fees.pdf");
                       break;
                   case 1:
                       downloadFormFromUrl("http://www.ambalacollege.com/docs/BTECH_Form.pdf");
                       break;
                   case 2:
                       downloadFormFromUrl("http://www.ambalacollege.com/docs/MTECH_Form.pdf");
                       break;
               }
            }
        });
        alertDownload.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert=alertDownload.create();
        alert.show();
    }

    private void downloadFormFromUrl(String url) {
        Intent intentDown=new Intent(Intent.ACTION_VIEW,Uri.parse(url));
        startActivity(intentDown);
    }

    private void openDialogForFaq() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("FAQ");
        alertDialog.setMessage("Redirect to college website.. \nDo you want to Continue!");
        alertDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intentAction = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ambalacollege.com/info/admissionfaq"));
                startActivity(intentAction);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert=alertDialog.create();
        alert.show();
    }

    private void showFeesDialog() {

        AlertDialog.Builder dialogFees=new AlertDialog.Builder(getActivity());
        dialogFees.setTitle("Select option");
        dialogFees.setItems(listFee, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                switch (position) {
                    case 0:
                        FeesActivity.setLoadUrl("http://aceapp-pardeep16.rhcloud.com/api/fees?type=annual");
                        FeesActivity.setUpperText("Tution Fee");
                        startActivity(new Intent(getActivity(), FeesActivity.class));
                        break;
                    case 1:
                        FeesActivity.setLoadUrl("http://aceapp-pardeep16.rhcloud.com/api/fees?type=bus");
                        FeesActivity.setUpperText("Bus Fee");
                        startActivity(new Intent(getActivity(), FeesActivity.class));
                        break;
                    case 2:
                        FeesActivity.setLoadUrl("http://aceapp-pardeep16.rhcloud.com/api/fees?type=hostel");
                        FeesActivity.setUpperText("Hostel Fee");
                        startActivity(new Intent(getActivity(), FeesActivity.class));
                        break;
                    default:
                        Toast.makeText(getActivity(), "Under Working!", Toast.LENGTH_SHORT).show();


                }
            }
        });
        dialogFees.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert=dialogFees.create();
        alert.show();
    }

   /* private void setUpView() {
        HomeScreen homeScreen=(HomeScreen)getActivity();
        resideMenu=homeScreen.getResideMenu();

        view.findViewById(R.id.btn_open_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        FrameLayout frameLayout=(FrameLayout)view.findViewById(R.id.fragmentAdmissions);
        resideMenu.addIgnoredView(frameLayout);
    }*/


}
