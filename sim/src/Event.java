import java.text.SimpleDateFormat;

public class Event {
    private String name;
    private float simTime;
	private int eventType;

    public Event(String name, float simTime, int eventType) 
    {
        this.name = name;
        this.simTime = simTime;
        this.eventType = eventType;
    }
    
    public void printTimestamp(String status)
    {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        System.out.println("event: " + name + " on " + timeStamp);
    }
}
