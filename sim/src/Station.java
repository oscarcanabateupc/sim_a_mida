public class Station {
    String name;
    float process_time;
    SimState simState;
    boolean hasOperator;

    public Station(String name, float process_time, SimState simState, boolean hasOperator) {
        this.name = name;
        this.process_time = process_time;
        this.simState = simState;
        this.hasOperator = hasOperator;
    }

    public void send_event(String name)
    {
        Event e = new Event(name);
        simState.addEvent(e);
    }
}
