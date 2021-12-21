public class Station {
    String name;
    int process_time;
    Actor client = null;
    Actor operator = null;
    SimState simState;
    
    public Station(String name, int process_time, SimState simState) {
        this.name = name;
        this.process_time = process_time;
        this.simState = simState;
    }

    public Event send_event(String name)
    {
    	Event e = new Event(name, this,simState.simTime + process_time);
    	simState.addEvent(e);
        return e;
    }
}
