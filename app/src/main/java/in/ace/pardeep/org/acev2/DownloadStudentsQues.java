package in.ace.pardeep.org.acev2;

/**
 * Created by pardeep on 22-08-2016.
 */
public class DownloadStudentsQues {
    private String title;
    private String date;
    private String content;
    private String facultyName;

    public DownloadStudentsQues(String title, String date, String content, String facultyName) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.facultyName = facultyName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }
}
