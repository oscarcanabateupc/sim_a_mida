import java.text.SimpleDateFormat;

public class Event{
    String name;
    int simTime;
    SimState simState;
    Station currentStation;
    
    public Event(String name, Station station) {
        this.name = name;
        this.simTime = simState.simTime;
        this.currentStation = station;
    }

    public void printTimestamp(String status){
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        System.out.println("event: " + name + " on " + timeStamp);
    }
    
    
    
}
