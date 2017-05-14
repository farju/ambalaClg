package in.ace.pardeep.org.acev2;

/**
 * Created by pardeep on 19-04-2016.
 */
public class ScriptUrl {

    public static String placementUrl="http://aceapp-pardeep16.rhcloud.com/list/placementlist?token="+ContstantsTokens.getPlacementToken();
    private static String noticeUrl="http://aceapp-pardeep16.rhcloud.com/list/noticelist";
    private static String studentDataBaseUrl="http://xdeveloper.royalwebhosting.net/StudentForm.php";
    private static String dataManageer="http://xdeveloper.royalwebhosting.net/manageData.php";
    private static String placementListUpdate="http://xdeveloper.royalwebhosting.net/placement.php";
    private static String syllabusBtechUrl="http://aceapp-pardeep16.rhcloud.com/academic/syllabus";
    private static String academicCalenderUrl="http://aceapp-pardeep16.rhcloud.com/academic/calender";
    private static String impLinks="http://aceapp-pardeep16.rhcloud.com/api/getLinks";

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

    private static String eventsGetUrl="http://aceapp-pardeep16.rhcloud.com/list/events";

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
