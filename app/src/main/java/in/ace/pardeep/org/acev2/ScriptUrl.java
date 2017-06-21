package in.ace.pardeep.org.acev2;

/**
 * Created by pardeep on 19-04-2016.
 */
public class ScriptUrl {

    public static String placementUrl="http://139.59.74.116:3000/list/placementlist?token="+ContstantsTokens.getPlacementToken();
    private static String noticeUrl="http://139.59.74.116:3000/list/noticelist";
    private static String studentDataBaseUrl="http://xdeveloper.royalwebhosting.net/StudentForm.php";
    private static String dataManageer="http://xdeveloper.royalwebhosting.net/manageData.php";
    private static String placementListUpdate="http://xdeveloper.royalwebhosting.net/placement.php";
    private static String syllabusBtechUrl="http://139.59.74.116:3000/academic/syllabus";
    private static String academicCalenderUrl="http://139.59.74.116:3000/academic/calender";
    private static String impLinks="http://139.59.74.116:3000/api/getLinks";

    private static String newsUrl="http://aceapp-pardeep16.rhcloud.com/list/api/news";

    public static String getNewsUrl() {
        return newsUrl;
    }

    public static void setNewsUrl(String newsUrl) {
        ScriptUrl.newsUrl = newsUrl;
    }

    public static String getImpLinks() {
        return impLinks;
    }

    public static void setImpLinks(String impLinks) {
        ScriptUrl.impLinks = impLinks;
    }

    private static String eventsGetUrl="http://139.59.74.116:3000/list/events";

    public ScriptUrl() {
    }

    public static String getNoticeUrl() {
        return noticeUrl;
    }

    public static String getAcademicCalenderUrl() {
        return academicCalenderUrl;
    }

    public static String getStudentDataBaseUrl() {
        return studentDataBaseUrl;
    }

    public static String getPlacementUrl() {
        return placementUrl;
    }
   public static String getDataManageer(){
       return dataManageer;
   }

    public static String getPlacementListUpdate() {
        return placementListUpdate;
    }

    public static String getSyllabusBtechUrl() {
        return syllabusBtechUrl;
    }

    public static String getEventsGetUrl() {
        return eventsGetUrl;
    }
}
