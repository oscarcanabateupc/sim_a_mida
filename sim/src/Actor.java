public class Actor {
    String name;
    Event[] remainingEvents;
    SimState simState;
    float spawnTime;

    public Actor(String name, Event[] remainingEvents, SimState simState, float spawnTime) {
        this.name = name;
        this.remainingEvents = remainingEvents;
        this.simState = simState;
        this.spawnTime = spawnTime;
    }
}
