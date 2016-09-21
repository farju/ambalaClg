package in.ace.pardeep.org.acev2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pardeep on 22-08-2016.
 */
public class DownloadStudentQuesAdapter extends BaseAdapter{

    private static ArrayList<DownloadStudentsQues> downloadStudentsQuesArrayList=null;

    public static void setDownloadStudentsQuesArrayList(ArrayList<DownloadStudentsQues> downloadStudentsQuesArrayList) {
        DownloadStudentQuesAdapter.downloadStudentsQuesArrayList = downloadStudentsQuesArrayList;
    }

    public static ArrayList<DownloadStudentsQues> getDownloadStudentsQuesArrayList() {
        return downloadStudentsQuesArrayList;
    }

    @Override
    public int getCount() {
        return downloadStudentsQuesArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder{
        public  TextView textViewTitle;
        public TextView textViewDate;
        public TextView textViewContent;
        public TextView textViewfacultyName;
        // public Button cardtButton;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View downloadView=null;
        ViewHolder viewHolder=null;
        if(convertView==null){
            LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
            downloadView=layoutInflater.inflate(R.layout.student_download_imp_questions,parent,false);

            viewHolder=new ViewHolder();

            viewHolder.textViewTitle=(TextView)downloadView.findViewById(R.id.textViewtitle);
            viewHolder.textViewDate=(TextView)downloadView.findViewById(R.id.textViewdate);
            viewHolder.textViewContent=(TextView)downloadView.findViewById(R.id.textViewcontent);
            viewHolder.textViewfacultyName=(TextView)downloadView.findViewById(R.id.textViewname);

            downloadView.setTag(viewHolder);
        }
        else {
            downloadView=convertView;
            viewHolder=(ViewHolder)downloadView.getTag();

        }

        viewHolder.textViewTitle.setText(downloadStudentsQuesArrayList.get(position).getTitle());
        viewHolder.textViewDate.setText(downloadStudentsQuesArrayList.get(position).getDate());
        viewHolder.textViewContent.setText(downloadStudentsQuesArrayList.get(position).getContent());
        viewHolder.textViewfacultyName.setText(downloadStudentsQuesArrayList.get(position).getFacultyName());

        return downloadView;
    }
}
