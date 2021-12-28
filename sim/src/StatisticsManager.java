public class StatisticsManager {

    float sumaTempsEsperaF1;
    float sumaTempsEsperaF2;  
    float sumaTempsEsperaF3;   
    int clientsTotalsF1 = 0;
    int clientsTotalsF2 = 0;
    int clientsTotalsF3 = 0;
    int clientsPerdutsF1 = 0;
    int clientsPerdutsF2 = 0;
    int clientsPerdutsF3 = 0;
    public StatisticsManager() {
	}
    
    public void addClientPerdut(int simTime)
    {
    	if (simTime >= 0 && simTime <= 180)
    	{
    		clientsPerdutsF1 += 1;
    	}
    	else if (simTime >= 180 && simTime <= 420)
    	{
    		clientsPerdutsF2 += 1;
    	}
    	else if (simTime >= 420 && simTime <= 720)
    	{
    		clientsPerdutsF3 += 1;
    	}
    }
    public void addClientTotal(int simTime)
    {
    	if (simTime >= 0 && simTime <= 180)
    	{
    		clientsTotalsF1 += 1;
    	}
    	else if (simTime >= 180 && simTime <= 420)
    	{
    		clientsTotalsF2 += 1;
    	}
    	else if (simTime >= 420 && simTime <= 720)
    	{
    		clientsTotalsF3 += 1;
    	}
    }
    public void addSumaTempsEspera(int simTime, int enterSimTime)
    {
    	if (simTime >= 0 && simTime <= 180)
    	{
    		sumaTempsEsperaF1 += (simTime - enterSimTime);
    	}
    	else if (simTime >= 180 && simTime <= 420)
    	{
    		sumaTempsEsperaF2 += (simTime - enterSimTime);
    	}
    	else if (simTime >= 420 && simTime <= 720)
    	{
    		sumaTempsEsperaF3 += (simTime - enterSimTime);
    	}
    }
    public void printPerdutsPerFase()
    {
    	System.out.println("Clients perduts per hora Fase 1: " + (clientsPerdutsF1 / 3));
    	System.out.println("Clients perduts per hora Fase 2: " + (clientsPerdutsF2 / 4));
    	System.out.println("Clients perduts per hora Fase 3: " + (clientsPerdutsF3 / 5));
    }
    public void printTempsEsperaFase()
    {
    	System.out.println("Temps d'espera mitjà Fase 1: " + (sumaTempsEsperaF1 / (clientsTotalsF1 - clientsPerdutsF1)));
    	System.out.println("Temps d'espera mitjà Fase 2: " + (sumaTempsEsperaF2 / (clientsTotalsF2 - clientsPerdutsF2)));
    	System.out.println("Temps d'espera mitjà Fase 3: " + (sumaTempsEsperaF3 / (clientsTotalsF3 - clientsPerdutsF3)));

    }
    public void getAverage(){
//        tempsTotal = getTime();
//        return float(clientsSatisfets/tempsTotal);
    }
    public void getMedian(){
//        if (clientsSatisfets>clientsNoSatisfets) return "Clients Satisfets";
//        else return; "Clients Insatisfets";
    }
    public void getPercentage(){
//        return float (clientsSatisfets/clientsTotals);
    }
}
