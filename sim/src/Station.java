public class Station {
    String name;
    float process_time;
    Actor client = null;
    Actor operator = null;
    SimState simState;
    
    public Station(String name, float process_time, SimState simState) {
        this.name = name;
        this.process_time = process_time;
        this.simState = simState;
        send_event("EMPTY");
    }

    public void send_event(String name)
    {
        Event e = new Event(name, this);
    }
}
