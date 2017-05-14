package in.ace.pardeep.org.acev2;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pardeep on 03-02-2017.
 */
public class HomeScreenGridAdapter extends BaseAdapter {

    private static int[] images={R.drawable.news,R.drawable.events,R.drawable.training,R.drawable.notices};
    private static String[] textOnImage={"Latest News","Upcoming Events","Training & Placements","Notices"};
    private static String[] colorbckg={"#30A9F4","#ffff8800","#3F51B5","#F44336"};


    @Override
    public int getCount() {
        return images.length;
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
        View view=layoutInflater.inflate(R.layout.home_screen_grid_view, parent, false);

        TextView textView=(TextView)view.findViewById(R.id.textview);
        ImageView imageView=(ImageView)view.findViewById(R.id.imageview);
       // LinearLayout linearLayout=(LinearLayout)view.findViewById(R.id.linearLayout);

        //linearLayout.setBackgroundColor(colorbckg[position]);

        FrameLayout frameLayout=(FrameLayout)view.findViewById(R.id.frameLayout);
        frameLayout.setBackgroundColor(Color.parseColor(colorbckg[position]));
        textView.setText(textOnImage[position]);
        imageView.setImageResource(images[position]);



        return view;
    }
}
