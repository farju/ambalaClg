package in.ace.pardeep.org.acev2;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by pardeep on 31-08-2016.
 */
public class DownloadAssignmentsAdapter extends BaseAdapter implements View.OnClickListener {

   DownloadAssignments downloadAssignments;
    Context context;

    ViewHolder viewHolder=null;
    ConnectivityManager connectivityManager;
    ListView listView;

    DownloadManager downloadManager;
    View downloadView;

    ArrayList<ViewHolder> viewArrayList=new ArrayList<ViewHolder>();



    public interface CustomButtonListener {
        public void onButtonClick(int position,String url,String filename);
    }


    private static ArrayList<DownloadAssignmentParameters> assignmentParametersArrayList=null;

    public void setCustomButtonAssignmentListener(Context context, DownloadAssignments downloadAssignments, ListView listView) {
        this.context=context;
        this.downloadAssignments = downloadAssignments;
        this.listView=listView;
    }

    public static ArrayList<DownloadAssignmentParameters> getAssignmentParameterses() {
        return assignmentParametersArrayList;
    }

    public static void setAssignmentParameterses(ArrayList<DownloadAssignmentParameters> assignmentParameterses) {
        DownloadAssignmentsAdapter.assignmentParametersArrayList = assignmentParameterses;
    }



    @Override
    public int getCount() {
        return assignmentParametersArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView textViewDate;
        public TextView textViewContent;
        public TextView textViewfacultyName;
        public ImageButton downloadButton,buttonToExpand,buttonToClose;
        // public Button cardtButton;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        downloadView=null;
        if(convertView==null){
            LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
            downloadView=layoutInflater.inflate(R.layout.download_assignment_listview_layout,parent,false);

            viewHolder=new ViewHolder();

            viewHolder.textViewDate=(TextView)downloadView.findViewById(R.id.textViewListDate);
            viewHolder.textViewfacultyName=(TextView)downloadView.findViewById(R.id.textViewListSender);
            viewHolder.textViewContent=(TextView)downloadView.findViewById(R.id.descriptionlistview);

            viewHolder.downloadButton=(ImageButton)downloadView.findViewById(R.id.dowmloadButtonlistview);
            viewHolder.buttonToExpand=(ImageButton)downloadView.findViewById(R.id.expandButton);

            viewHolder.buttonToClose=(ImageButton)downloadView.findViewById(R.id.closeButton);

            viewHolder.textViewContent.setVisibility(View.GONE);
            viewHolder.buttonToClose.setVisibility(View.GONE);

            viewHolder.downloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // downloadFile(assignmentParametersArrayList.get(position).getFileUrl(),assignmentParametersArrayList.get(position).getFileName());
                    if(downloadAssignments!=null){
                        AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
                        alertDialog.setTitle(assignmentParametersArrayList.get(position).getFileName());
                        alertDialog.setMessage("Do you want to Download this file.?");
                        alertDialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(!assignmentParametersArrayList.get(position).getFileUrl().equalsIgnoreCase("")) {
                                    downloadAssignments.getDownloadFile(assignmentParametersArrayList.get(position).getFileUrl(), assignmentParametersArrayList.get(position).getFileName());
                                }
                                else {
                                    Toast.makeText(context, "Invalid Url!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        alertDialog.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alert=alertDialog.create();
                        alert.show();
                         }
                }
            });

           /* ;*/
            downloadView.setTag(viewHolder);
        }
        else {
            downloadView=convertView;
            viewHolder=(ViewHolder)downloadView.getTag();

           // downloadView.setOnClickListener(this);




        }

        viewHolder.textViewDate.setText("Upload Date :"+assignmentParametersArrayList.get(position).getUploadDate());
        viewHolder.textViewfacultyName.setText("By :"+assignmentParametersArrayList.get(position).getSenderName());

        String filetype=assignmentParametersArrayList.get(position).getFileType();
        viewHolder.textViewContent.setText(assignmentParametersArrayList.get(position).getFileDescription() + "\n" + filetype);
        viewHolder.textViewContent.setTag(position);
        viewHolder.buttonToExpand.setTag(position);
        viewHolder.buttonToClose.setTag(position);
        viewArrayList.add(viewHolder);

        viewHolder.buttonToExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadAssignmentParameters downloadAssignmentParameter=assignmentParametersArrayList.get(position);

                viewArrayList.get(position).textViewContent.setVisibility(View.VISIBLE);
                viewArrayList.get(position).buttonToClose.setVisibility(View.VISIBLE);

                /*viewHolder.textViewContent.getTag()
                viewHolder.textViewContent.setVisibility(View.VISIBLE);
                viewHolder.buttonToClose.setVisibility(View.VISIBLE);
*/
                /*View parentRow = (View) v.getParent();
                final int position = listView.getPositionForView(parentRow);
                System.out.println(position);
                System.out.println(viewHolder.textViewContent.getText());
                viewHolder.textViewContent.getTag();
                viewHolder.textViewContent.setVisibility(View.VISIBLE);*/
                    /*String filetype=assignmentParametersArrayList.get(position).getFileType();

                    *//**//* viewHolder.textViewContent.setVisibility(View.VISIBLE);
                    viewHolder.buttonToClose.setVisibility(View.VISIBLE);*//**//*

                    *//**//*View parentView=(View)downloadView.getParent();

                    TextView content=(TextView)parentView.findViewById(R.id.descriptionlistview);
                    content.setVisibility(View.VISIBLE);
                    content.setText(assignmentParametersArrayList.get(position).getFileDescription() + "\n" + filetype);
                    viewHolder.buttonToClose=(ImageButton)parent.findViewById(R.id.closeButton);
                    viewHolder.buttonToClose.setVisibility(View.VISIBLE);*//**//*

                    viewHolder.textViewContent=(TextView) downloadView.findViewById(R.id.descriptionlistview);
                    viewHolder.textViewContent.setVisibility(View.VISIBLE);
*/


            }
        });

        viewHolder.buttonToClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* viewHolder.textViewContent.setVisibility(View.GONE);
                viewHolder.buttonToClose.setVisibility(View.GONE);*/
                viewArrayList.get(position).textViewContent.setVisibility(View.GONE);
                viewArrayList.get(position).buttonToClose.setVisibility(View.GONE);
            }
        });


        return downloadView;
    }

    private void downloadFile(String fileUrl, String fileName) {
        new DownloadAssignments().getDownloadFile(fileUrl, fileName);

        }
    @Override
    public void onClick(final View v) {
        v.post(new Runnable() {
            @Override
            public void run() {
                ViewHolder viewHolder1=(ViewHolder)v.getTag();
                viewHolder1.textViewContent.setVisibility(View.VISIBLE);
                viewHolder1.buttonToClose.setVisibility(View.VISIBLE);
            }
        });
    }


}
