import java.text.SimpleDateFormat;

public class Event{
    String name;
    int simTime;
    Station currentStation;
    
    public Event(String name, Station station, int simTime)
    {
        this.name = name;        
        this.simTime = simTime;
        this.currentStation = station;
    }
    
    public void printTimestamp(String status){
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        System.out.println("event: " + name + " on " + timeStamp);
    }
    
    
    
}
