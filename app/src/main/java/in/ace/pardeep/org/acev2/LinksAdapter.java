package in.ace.pardeep.org.acev2;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by pardeep on 13-02-2017.
 */
public class LinksAdapter extends BaseAdapter {

    private String[] urls;
    private String[] description;

    public LinksAdapter(String[] urls, String[] description) {
        this.urls = urls;
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
        View listViewItem = inflater.inflate(R.layout.important_links, null, true);
        // TextView textViewId = (TextView) listViewItem.findViewById(R.id.textViewId);
        TextView textViewDescription = (TextView) listViewItem.findViewById(R.id.text_description_list);
        TextView textViewUrl = (TextView) listViewItem.findViewById(R.id.text_title_list);

        // textViewId.setText(ids[position]);
        textViewDescription.setText(description[position]);
        textViewUrl.setText(Html.fromHtml(urls[position]));
        textViewUrl.setMovementMethod(LinkMovementMethod.getInstance());

        return listViewItem;
    }
}
