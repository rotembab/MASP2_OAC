
import java.util.HashMap;

public class Generator {
	
	private int n, wifePlayers;
	private boolean typeOfGame;
	private double p1;

	/*
	 * constructor parameters -
	 * n, number of agents
	 * p1, probability that two agents are constrained
	 * typeOfGame, the type of the game that is played, true = PD, false = BoS
	 * wifePlayers, number of agents to be assigned as wife
	 */
	public Generator(int n, boolean typeOfGame, double p1, int wives) {
		this.n = n;
		this.typeOfGame = typeOfGame;
		this.p1 = p1;
		this.wifePlayers=wives;
	}
	
	// generate MASP
	public MASP generateMASP() {
		int wifeCounter=0;
		boolean isWife = false;
		HashMap<VarTuple, ConsTable> cons_tables = new HashMap<VarTuple, ConsTable>();
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if(wifeCounter<this.wifePlayers) {
					isWife = true;
					wifeCounter++;
				}
				if (Math.random() < p1) {
					VarTuple at = new VarTuple(i, j);
					ConsTable ct = new ConsTable(typeOfGame,isWife);//Is the left one a wife
					cons_tables.put(at, ct);
				}
			}
			isWife = false;
		}
		
		return new MASP(cons_tables, n,typeOfGame);
	}
}
