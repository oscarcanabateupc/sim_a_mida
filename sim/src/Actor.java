public class Actor implements Comparable<Actor>{
    String name;
    Event[] remainingEvents;
    SimState simState;
    Float spawnTime;

    public Actor(String name, Event[] remainingEvents, SimState simState, float spawnTime) {
        this.name = name;
        this.remainingEvents = remainingEvents;
        this.simState = simState;
        this.spawnTime = spawnTime;
    }

    @Override
    public int compareTo(Actor a) {
        return this.spawnTime.compareTo(a.spawnTime);
    }
}
