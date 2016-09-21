package in.ace.pardeep.org.acev2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pardeep on 18-07-2016.
 */
public class AdmissionGridViewAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return AdmissionGridViewContent.getTextData.length;
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
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.admission_grid_view, parent, false);

        TextView textView=(TextView)view.findViewById(R.id.textview_adnission_gridview);
        ImageView imageView=(ImageView)view.findViewById(R.id.imageview_admission_gridview);
        textView.setText(AdmissionGridViewContent.getTextData[position]);
        imageView.setImageResource(AdmissionGridViewContent.getImageData[position]);

        return view;
    }
}
