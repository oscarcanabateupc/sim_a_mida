import java.sql.Array;
import java.util.List;

public class SimState {
	int simTime;
    List<Event> eventPool;
    List<Actor> sink;
    List<Actor> processing;
    List<Process> OngoingProcess;
    List<Actor> waiting;
    List<Station> waitingStations;
    List<Station> processingWashStations;
    List<Station> processingCutStations;

    public void addEvent(Event e)
    {
    	eventPool.add(e);
    }


}
