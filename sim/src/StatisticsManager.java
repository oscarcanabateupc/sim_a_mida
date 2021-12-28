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
    	System.out.println("Temps d'espera mitja Fase 1: " + (sumaTempsEsperaF1 / (clientsTotalsF1 - clientsPerdutsF1)));
    	System.out.println("Temps d'espera mitja Fase 2: " + (sumaTempsEsperaF2 / (clientsTotalsF2 - clientsPerdutsF2)));
    	System.out.println("Temps d'espera mitja Fase 3: " + (sumaTempsEsperaF3 / (clientsTotalsF3 - clientsPerdutsF3)));

    }
    public void getAverage(int simTime){
		System.out.println("Average clients tractats Fase 1: " + ((clientsTotalsF1 - clientsPerdutsF1) / simTime));
    	System.out.println("Average clients tractats Fase 2: " + ((clientsTotalsF2 - clientsPerdutsF2) / simTime));
    	System.out.println("Average clients tractats Fase 3: " + ((clientsTotalsF3 - clientsPerdutsF3) / simTime));
		System.out.println("Average clients tractats TOTALS: " + (((clientsTotalsF1 - clientsPerdutsF1) + (clientsTotalsF2 - clientsPerdutsF2) +(clientsTotalsF3 - clientsPerdutsF3)) / simTime));
    }
    public void getMedian(){
		String frase;
		if (clientsPerdutsF1 > clientsTotalsF1 - clientsPerdutsF1) {
			frase = "Clients insatisfets";
		}
		else {
			frase = "Clients satisfets";
		}
		System.out.println("Majoria de clients Fase 1: " + frase);

		if (clientsPerdutsF2 > clientsTotalsF2 - clientsPerdutsF2) {
			frase = "Clients insatisfets";
		}
		else {
			frase = "Clients satisfets";
		}
		System.out.println("Majoria de clients Fase 2: " + frase);

		if (clientsPerdutsF3 > clientsTotalsF3 - clientsPerdutsF3) {
			frase = "Clients insatisfets";
		}
		else {
			frase = "Clients satisfets";
		}
		System.out.println("Majoria de clients Fase 3: " + frase);
    }
    public void getPercentage(){
		System.out.println("Average clients tractats Fase 1: " + (((clientsTotalsF1 - clientsPerdutsF1) / Math.max(1, clientsTotalsF1)) / 100.00));
    	System.out.println("Average clients tractats Fase 2: " + (((clientsTotalsF2 - clientsPerdutsF2) / Math.max(1, clientsTotalsF2)) / 100.00));
    	System.out.println("Average clients tractats Fase 3: " + (((clientsTotalsF3 - clientsPerdutsF3) / Math.max(1, clientsTotalsF3)) / 100.00));
		System.out.println("Average clients tractats TOTALS: " + ((((clientsTotalsF1 - clientsPerdutsF1) + (clientsTotalsF2 - clientsPerdutsF2) +(clientsTotalsF3 - clientsPerdutsF3)) / (clientsTotalsF1 + clientsTotalsF2 + clientsTotalsF3))  / 100.00));
    }
}
