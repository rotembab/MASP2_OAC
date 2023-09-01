
import java.util.HashMap;

public class Generator {
	
	private int n;
	private boolean typeOfGame;
	private double p1, wives;

	/*
	 * constructor parameters -
	 * n, number of agents
	 * d, domain size
	 * p1, probability that two agents are constrained
	 * p2, probability that a constraint takes the value "false"
	 */
	public Generator(int n, boolean typeOfGame, double p1, double wives) {
		this.n = n;
		this.typeOfGame = typeOfGame;
		this.p1 = p1;
		this.wives=wives; //Can be null
	// genetate masp
	public MASP generateMASP() {
		
		HashMap<VarTuple, ConsTable> cons_tables = new HashMap<VarTuple, ConsTable>();
		
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (Math.random() < p1) {
					VarTuple at = new VarTuple(i, j);
					ConsTable ct = new ConsTable(typeOfGame);
					cons_tables.put(at, ct);
				}
			}
		}
		
		return new MASP(cons_tables, d, n);
	}
}
