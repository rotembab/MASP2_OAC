
import java.util.HashMap;

public class Generator {
	
	private int n;
	private boolean typeOfGame;
	private double p1,wifeChance;

	/*
	 * constructor parameters -
	 * n, number of agents
	 * p1, probability that two agents are constrained
	 * typeOfGame, the type of the game that is played, true = PD, false = BoS
	 * wifePlayers, number of agents to be assigned as wife
	 */
	public Generator(int n, boolean typeOfGame, double p1, double wives) {
		this.n = n;
		this.typeOfGame = typeOfGame;
		this.p1 = p1;
		this.wifeChance=wives;
	}
	
	// generate MASP
	public MASP generateMASP() {
		HashMap<VarTuple, ConsTable> cons_tables = new HashMap<VarTuple, ConsTable>();
		for (int i = 0; i < n; i++) {
			boolean player1=false;//True if is a woman
			boolean player2=false;//True if is a woman
			if (Math.random() < wifeChance) {
				player1 = true;
			}
			for (int j = i + 1; j < n; j++) {
				if (Math.random() < wifeChance) {
					player2 = true;
				}
				if (Math.random() < p1) {
					VarTuple at = new VarTuple(i, j);
					ConsTable ct = new ConsTable(typeOfGame,player1,player2);//Is the left one a wife
					cons_tables.put(at, ct);
				}
			}
		}
		
		return new MASP(cons_tables, n,typeOfGame);
	}
}
