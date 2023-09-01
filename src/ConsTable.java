
/*
 * represents a constraints table between two agents
 */
public class ConsTable {

	private TupleForGame[][] table;
	private boolean gameType;
	
	// create a constraint table with corresponding domain size and p2
	public ConsTable(boolean gameType) {

		table = new TupleForGame[2][2];
		this.gameType=gameType;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				//TODO: GENERATE TABLE WITH VALUES- RANDOM??
			}
		}
	}
	
	// print a constraint table
	public void print(int d) {
		for (int i = 0; i < d; i++) {
			for (int j = 0; j < d; j++) {
				System.out.print(table[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public boolean check(int i, int j) {
		return table[i][j];
	}
}
