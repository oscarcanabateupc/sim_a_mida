import java.util.*;

public class Actor{
    String name;
    List<Event> remainingEvents;
    SimState simState;
    int spawnTime;
    int enterSimTime;

    public Actor(String name, List<Event> remainingEvents, SimState simState, int spawnTime) {
        this.name = name;
        this.remainingEvents = remainingEvents;
        this.simState = simState;
        this.spawnTime = spawnTime;
        this.enterSimTime = 0;
    }

   
}
