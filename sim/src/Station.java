public class Station {
    String name;
    int process_time;
    Actor client;
    Actor operator;
    Boolean hasOperator = false;
    Boolean hasClient = false;
    SimState simState;
    
    public Station(String name, int process_time, SimState simState) {
        this.name = name;
        this.process_time = process_time;
        this.simState = simState;
    }

    public Event send_event(String name)
    {
    	Event e = new Event(name, this,simState.simTime + process_time);
    	hasClient = true;
    	hasOperator = true;
    	simState.addEvent(e);    	     
        return e;
    }
    
    public void clear()
    {
        simState.waitingOperators.add(operator);       
    	hasOperator = false;
    	hasClient = false;    	
    }
    public void printTimestamp(){
        if (!name.contains("wait")) System.out.println("on station: " + name + " with client: " + client.name + " with operator: " + operator.name);
        else System.out.println("on station: " + name + " with client: " + client.name);
    }
}
