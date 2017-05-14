package in.ace.pardeep.org.acev2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by pardeep on 18-02-2017.
 */
public class NewsAdapter extends BaseAdapter {
    private String[] description;
    private String[] dates;
    private String[] titles;
    View newsView;

    TextView titleTextView,dateTextView,descriptionTextView;

    public NewsAdapter(String[] description, String[] dates, String[] titles) {
        this.description = description;
        this.dates = dates;
        this.titles = titles;
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
        View listViewItem = inflater.inflate(R.layout.newsadapterview, null, true);

        titleTextView=(TextView)listViewItem.findViewById(R.id.textViewNewsTitle);
        dateTextView=(TextView)listViewItem.findViewById(R.id.textViewNewsDate);
        descriptionTextView=(TextView)listViewItem.findViewById(R.id.textViewDescriptionNews);

        try {
            titleTextView.setText(titles[position]);
            dateTextView.setText(dates[position]);
            descriptionTextView.setText(description[position]);

        }
        catch (Exception e){
            e.printStackTrace();
        }


        return listViewItem;
    }
}
