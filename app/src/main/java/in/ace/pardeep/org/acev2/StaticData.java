package in.ace.pardeep.org.acev2;

/**
 * Created by pardeep on 06-06-2016.
 */
public class StaticData {
    private static String historyData;
    public static String missionVission="" +
            "<html><body>" +
            " <font color=\"red\"><h3>Institute Vision : </h3></font> " +
            "To become a source of technology and start an Incubation Centre for entrepreneurs resulting in this region" +
            " developing into a vibrant industrial hub with many startup" +
            " companies dealing with new technology" +
            "<font color=\"Blue\"><h3>Institute Mission</h3></font>" +
            "\n" +
            "\n" +

            " <ul>   <li>To impart quality engineering education to students" +
            " through quality teaching, hands on training, and applied" +
            " research in practical and product oriented projects.\n</li></br>" +
            " <li>To impart such education that passing out students are ready" +
            " with good theoretical and practical knowledge to suite the current need of industry.\n</li></br>" +
            "   <li> To expose students to applied research, especially the fact that research does" +
            " not require much money but does require great persistence.\n</li></br>" +
            "   <li> To sow the seed of entrepreneurship in them so that our engineers" +
            " become job providers and not job seekers\n</li></br>" +
            "  <li>  To train students as a complete person through extracurricular" +
            " activities and with an exposure to a transparent system based on ethics so" +
            " that they believe that a successful institution and a successful business" +
            " can be run with ethics without corruption.\n</li></br>" +
            "</ul>" +
            "</body></html>";


    public StaticData(){
       // setHistoryData("");
    }

    public static String getHistoryData() {
        return historyData;
    }

    public static void setHistoryData(String historyData) {
        StaticData.historyData = " ";
    }
}
