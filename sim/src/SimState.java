import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class SimState {
	int simTime;
    List<Event> eventPool = new ArrayList<>();
    List<Actor> sink = new ArrayList<>();
    List<Actor> processing = new ArrayList<>();
    List<Actor> washing = new ArrayList<>();
    List<Actor> waiting = new ArrayList<>();
    List<Actor> waitingForACut = new ArrayList<>();
    List<Actor> waitingOperators = new ArrayList<>();
    List<Actor> waitingForAWash = new ArrayList<>();
    List<Station> waitingStations = new ArrayList<>();
    List<Station> processingWashStations = new ArrayList<>();
    List<Station> processingCutStations = new ArrayList<>();
    
    public SimState()
    {
    	simTime = 0;
    }
    public void addEvent(Event e)
    {
    	eventPool.add(e);
    }


}
