package in.ace.pardeep.org.acev2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by pardeep on 27-07-2016.
 */
public class EventsListViewAdapter extends BaseAdapter {

    Context context;
    public ImageManager imageManager;

    private static ArrayList<EventsListContent> contentArrayList=new ArrayList<EventsListContent>();

    ImageView placeHolder;

    public  void setContentArrayList(ArrayList<EventsListContent> contentArrayList) {
        this.contentArrayList = contentArrayList;

    }

    public EventsListViewAdapter(Context context) {
        this.context=context;
        imageManager=new ImageManager(context);
    }

    @Override
    public int getCount() {
        return contentArrayList.size();
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
        public ImageView imageView;
        public TextView textViewDescription;
       // public Button cardtButton;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View eventView=null;
        ViewHolder viewHolder=null;
        if(convertView==null){
            LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
            eventView=layoutInflater.inflate(R.layout.eventlistcontent_layout,parent,false);

            viewHolder=new ViewHolder();

            viewHolder.textViewTitle=(TextView)eventView.findViewById(R.id.textViewEventTitle);
            viewHolder.textViewDate=(TextView)eventView.findViewById(R.id.textViewEventDate);
            viewHolder.imageView=(ImageView)eventView.findViewById(R.id.imageViewEventList);
            viewHolder.textViewDescription=(TextView)eventView.findViewById(R.id.textViewDescriptionEvent);
           // viewHolder.cardtButton=(Button)eventView.findViewById(R.id.buttonForSave);
            eventView.setTag(viewHolder);
        }
        else {
            eventView=convertView;
            viewHolder=(ViewHolder)eventView.getTag();

           // }

        }

        viewHolder.textViewTitle.setText(contentArrayList.get(position).getEventTitle());
        viewHolder.textViewDate.setText(contentArrayList.get(position).getEventDate());
        viewHolder.textViewDescription.setText(contentArrayList.get(position).getDescriptionsOfEvent());

        //
        // DownloadImage downloadImage=new DownloadImage(imageView);
       // DownloadedDrawable downloadedDrawable = new DownloadedDrawable(downloadImage);
        // imageView.setImageDrawable(downloadedDrawable);
        // downloadImage.execute(contentArrayList.get(position).getEventImageUrl());
       /* ProgressBar progressBar=(ProgressBar)eventView.findViewById(R.id.progressBarSpinner);
        progressBar.setVisibility(View.VISIBLE);

        progressBar.setVisibility(View.GONE);
*/
        //imageManager.displayImage(contentArrayList.get(position).getEventImageUrl(),viewHolder.imageView,R.drawable.ic_picture);
        if(!contentArrayList.get(position).getEventImageUrl().toString().equalsIgnoreCase("")) {
            System.out.println("load image");
            final ViewHolder finalViewHolder = viewHolder;
            Picasso.with(context).load(contentArrayList.get(position).getEventImageUrl()).noFade().into(viewHolder.imageView, new Callback() {
                @Override
                public void onSuccess() {
                    Picasso.with(context).load(contentArrayList.get(position).getEventImageUrl()).noFade().into(finalViewHolder.imageView);
                }

                @Override
                public void onError() {
                    Picasso.with(context).load(R.drawable.picture).into(finalViewHolder.imageView);
                }
            });
        }
        else {
            System.out.println("load default");
            Picasso.with(context).load(R.drawable.picture).into(viewHolder.imageView);
        }


        return eventView;
    }




    public class DownloadImage extends AsyncTask<String,Void,Bitmap>{

        WeakReference<ImageView> imageViewWeakReference;
        public DownloadImage(ImageView imageView){
          imageViewWeakReference=new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap=null;
            bitmap=downloadImage(params[0]);
            if(bitmap!=null) {
                return bitmap;
            }
            else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView=imageViewWeakReference.get();
            if(imageView!=null) {
                imageView.setImageBitmap(bitmap);
            }
        }

        private Bitmap downloadImage(String param) {
            Bitmap bitmap=null;
            try {
                URL url=new URL(param);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                InputStream inputStream=httpURLConnection.getInputStream();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;

                bitmap=BitmapFactory.decodeStream(inputStream,null,options);



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }
    }

    static class DownloadedDrawable extends ColorDrawable {
        private final WeakReference<DownloadImage> bitmapDownloaderTaskReference;

        public DownloadedDrawable(DownloadImage bitmapDownloaderTask) {
            super(Color.GRAY);
            bitmapDownloaderTaskReference =
                    new WeakReference<DownloadImage>(bitmapDownloaderTask);
        }

        public DownloadImage getBitmapDownloaderTask() {
            return bitmapDownloaderTaskReference.get();
        }
    }

}
