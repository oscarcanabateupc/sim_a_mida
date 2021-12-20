public class Main {
    public static void main(String[] args) {
        Event a = new Event("event1");
        a.printTimestamp("hola");
        SimState s = new SimState();
        Orquestrator o = new Orquestrator(0,720,s,0);
        Event[] e = new Event[1];
        o.generateActorsInterval(e);
    }
}
