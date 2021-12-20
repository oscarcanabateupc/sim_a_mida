public class Actor{
    String name;
    Event[] remainingEvents;
    SimState simState;
    int spawnTime;

    public Actor(String name, Event[] remainingEvents, SimState simState, int spawnTime) {
        this.name = name;
        this.remainingEvents = remainingEvents;
        this.simState = simState;
        this.spawnTime = spawnTime;
    }

   
}
