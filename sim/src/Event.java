import java.text.SimpleDateFormat;

public class Event {
    String name;
    float simTime;

    public Event(String name) {
        this.name = name;
    }

    public void printTimestamp(String status){
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        System.out.println("event: " + name + " on " + timeStamp);
    }
}
