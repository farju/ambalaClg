package in.ace.pardeep.org.acev2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by hp 8 on 13-03-2016.
 */
public class PlacementListAdapter extends BaseAdapter{
    private String[] dates;
    private String[] description;
    public PlacementListAdapter(String[] dates, String[] description) {
        this.dates = dates;
        this.description = description;
    }

        @Override
        public int getCount() {
            return description.length;
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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listViewItem = inflater.inflate(R.layout.listcontents, null, true);
       // TextView textViewId = (TextView) listViewItem.findViewById(R.id.textViewId);
        TextView textViewDescription = (TextView) listViewItem.findViewById(R.id.text_description_list);
        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.text_title_list);

       // textViewId.setText(ids[position]);
        textViewDescription.setText(description[position]);
        textViewDate.setText(dates[position]);

        return listViewItem;
    }
}



