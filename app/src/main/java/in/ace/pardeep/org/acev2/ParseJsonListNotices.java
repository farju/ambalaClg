package in.ace.pardeep.org.acev2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hp 8 on 26-03-2016.
 */
public class ParseJsonListNotices {
    public static String[] description;
    public static String[] date;

    public static  final String jsonArray_key="noticelist";
    public static  final String description_key="description";
    public static final String date_key="date";

    private JSONArray jsonArray=null;
    private  String getJsonArray_key;

    public ParseJsonListNotices(String getJsonArray_key){
        this.getJsonArray_key=getJsonArray_key;

    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject=new JSONObject(getJsonArray_key);
            jsonArray=jsonObject.getJSONArray(jsonArray_key);

            if(jsonArray.length()==0) {
                System.out.println("Null value");
                description=new String[1];
                date=new String[1];
                Date dateCurrent;
                dateCurrent = new Date();

                description[0] = "No Data to Show";
                date[0]=new SimpleDateFormat("dd-mm-yyyy").format(dateCurrent);
            }
            else {
                description = new String[jsonArray.length()];
                date = new String[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject childObject = jsonArray.getJSONObject(i);
                    description[i] = childObject.getString(description_key);
                    date[i] = childObject.getString(date_key);

                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
