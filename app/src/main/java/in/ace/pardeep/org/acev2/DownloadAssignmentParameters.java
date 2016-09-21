package in.ace.pardeep.org.acev2;

/**
 * Created by pardeep on 31-08-2016.
 */
public class DownloadAssignmentParameters {
    private String fileName;
    private String uploadDate;
    private String senderName;
    private String fileUrl;
    private String fileDescription;
    private String fileType;

    public DownloadAssignmentParameters(String fileName, String uploadDate, String senderName, String fileUrl, String fileDescription, String fileType) {
        this.fileName = fileName;
        this.uploadDate = uploadDate;
        this.senderName = senderName;
        this.fileUrl = fileUrl;
        this.fileDescription = fileDescription;
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
