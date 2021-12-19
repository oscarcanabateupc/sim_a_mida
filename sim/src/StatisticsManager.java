public class StatisticsManager {

    int clientsTotals;
    int clientsSatisfets;
    int clientsNoSatisfets;
    float tempsTotal;

    public void getAverage(){
        tempsTotal = getTime();
        return float(clientsSatisfets/tempsTotal);
    }
    public void getMedian(){
        if (clientsSatisfets>clientsNoSatisfets) return "Clients Satisfets";
        else return; "Clients Insatisfets";
    }
    public void getPercentage(){
        return float (clientsSatisfets/clientsTotals);
    }
}
