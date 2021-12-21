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
    
    public void printTimestamp(){
        String timeStamp = Integer.toString(simTime);
        System.out.println("event: " + name + " on " + timeStamp);
    }
    
    
    
}
