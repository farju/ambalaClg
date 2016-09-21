package in.ace.pardeep.org.acev2;

/**
 * Created by pardeep on 27-07-2016.
 */
public class EventsListContent {
    private String eventTitle;
    private String eventDate;
    private String eventImageUrl;
    private String descriptionsOfEvent;


    public EventsListContent(String eventTitle, String eventDate, String eventImageUrl, String descriptionsOfEvent) {
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.eventImageUrl = eventImageUrl;
        this.descriptionsOfEvent = descriptionsOfEvent;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventImageUrl() {
        return eventImageUrl;
    }

    public void setEventImageUrl(String eventImageUrl) {
        this.eventImageUrl = eventImageUrl;
    }

    public String getDescriptionsOfEvent() {
        return descriptionsOfEvent;
    }

    public void setDescriptionsOfEvent(String descriptionsOfEvent) {
        this.descriptionsOfEvent = descriptionsOfEvent;
    }
}
