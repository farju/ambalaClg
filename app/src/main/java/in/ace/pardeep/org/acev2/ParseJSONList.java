package in.ace.pardeep.org.acev2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hp 8 on 20-03-2016.
 */
public class ParseJSONList {
   public static String[] description;
    public static String[] date;

    public static  final String jsonArray_key="PlacementList";
    public static  final String description_key="description";
    public static final String date_key="date";

    private JSONArray jsonArray=null;
    private  String getJsonArray_key;

    public ParseJSONList(String getJsonArray_key){
        this.getJsonArray_key=getJsonArray_key;

    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject=new JSONObject(getJsonArray_key);
            jsonArray=jsonObject.getJSONArray(jsonArray_key);

            description=new String[jsonArray.length()];
            date=new String[jsonArray.length()];

            for(int i=0;i<jsonArray.length();i++){
                JSONObject childObject=jsonArray.getJSONObject(i);
                description[i]=childObject.getString(description_key);
                date[i]=childObject.getString(date_key);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
