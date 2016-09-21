package in.ace.pardeep.org.acev2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pardeep on 02-06-2016.
 */
public class DashboardAdapter extends BaseAdapter {

    ArrayList<DashboardContentStudent> arrayList=new ArrayList<DashboardContentStudent>();

    @Override
    public int getCount() {
        return DashboardContentStudent.titles.length;
    }

    @Override
    public Object getItem(int position) {
        return getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(view==null){
            LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
            view=layoutInflater.inflate(R.layout.dashboardcontentstudent,parent,false);
        }
        ImageView imageView=(ImageView)view.findViewById(R.id.imageViewDashboard);
        imageView.setImageResource(DashboardContentStudent.dashboardImages[position]);

        TextView textView=(TextView)view.findViewById(R.id.textViewDashboard);
        textView.setText(DashboardContentStudent.titles[position]);




        return view;
    }
}
