
import java.util.HashMap;

public class Generator {
	
	private int n;
	private boolean typeOfGame;
	private double p1,wifeChance;
	private boolean [] agentsGenders;

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
		this.agentsGenders= new boolean[n];
	}
	
	// generate MASP
	public MASP generateMASP() {
		//Initialize an array with each agent's ture or false value
		for(int i=0;i<n;i++){
			this.agentsGenders[i] = (Math.random() < wifeChance);//true if woman
		}
		HashMap<VarTuple, ConsTable> cons_tables = new HashMap<VarTuple, ConsTable>();
		for (int i = 0; i < n; i++) {
			boolean player1=agentsGenders[i];
			boolean player2;
			for (int j = i + 1; j < n; j++) {
				player2 = agentsGenders[j];
				if (Math.random() < p1) {
					VarTuple at = new VarTuple(i, j);
					ConsTable ct = new ConsTable(typeOfGame,player1,player2);
					cons_tables.put(at, ct);
				}
			}
		}
		
		return new MASP(cons_tables, n,typeOfGame);
	}
}
