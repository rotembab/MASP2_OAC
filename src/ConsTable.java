
/*
 * represents a constraints Game between two agents
 */
public class ConsTable {

	private TupleForGame[][] table;
	private boolean gameType;
	private TupleForGame[] i1 = new TupleForGame[2];
	private TupleForGame[] i2 = new TupleForGame[2];
	private boolean player1;//true if a man false if a woman
	
	// create a constraint game with the selected game type
	public ConsTable(boolean gameType,boolean player1) {
		this.table = new TupleForGame[2][2];
		this.player1=player1;
		this.gameType=gameType;
		if(gameType) {
			i1[0]= new TupleForGame(5,5);
			i1[1]= new TupleForGame(10,0);
			i2[0]= new TupleForGame(0,10);
			i2[1]= new TupleForGame(8,8);
		}else if(!player1) {//the classic one:  Man vs Woman
			i1[0]= new TupleForGame(3,1);
			i1[1]= new TupleForGame(0,0);
			i2[0]= new TupleForGame(0,0);
			i2[1]= new TupleForGame(1,3);
		}else {//Man vs a Man
			i1[0]= new TupleForGame(1,1);
			i1[1]= new TupleForGame(1,3);
			i2[0]= new TupleForGame(3,1);
			i2[1]= new TupleForGame(3,3);
		}
		
		table[0] = i1;
		table[1] = i2;
		
	}
	
	// print a constraint table
	public void print() {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				System.out.print(table[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public TupleForGame check(int i, int j) { //TODO: WORK ON THIS
		return table[i][j];
	}
}
