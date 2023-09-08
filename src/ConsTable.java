
/*
 * represents a constraints Game between two agents
 */
public class ConsTable {

	private TupleForGame[][] table;
	private boolean gameType;

	private boolean player1;//true if a woman false if a man
	private boolean player2;//true if a woman false is a man
	
	private  TupleForGame[][] PDTable = new TupleForGame[2][2];//Table for PD
	private final TupleForGame[][] BoSMvM = new TupleForGame[2][2];//Table for Bos Men vs Men
	private final TupleForGame[][] BoSWvW = new TupleForGame[2][2];//Table for Bos Woman vs Woman
	private final TupleForGame[][] BoSClassic = new TupleForGame[2][2];//Table for the classic Man vs Woman
	
	// create a constraint game with the selected game type
	public ConsTable(boolean gameType,boolean player1,boolean player2) {
		this.table = new TupleForGame[2][2];
		this.player1=player1;
		this.player2=player2;
		this.gameType=gameType;
		if(gameType) {
			PDTable[0][0]=new TupleForGame(5,5);
			PDTable[0][1]=new TupleForGame(10,0);
			PDTable[1][0]=new TupleForGame(0,10);
			PDTable[1][1]=new TupleForGame(8,8);
			table = PDTable;
		}else if(player1!=player2) {//the classic one:  Man vs Woman
			if(player1) { //When left is a woman
			BoSClassic[0][0]= new TupleForGame(3,1);
			BoSClassic[0][1]= new TupleForGame(0,0);
			BoSClassic[1][0]= new TupleForGame(0,0);
			BoSClassic[1][1]= new TupleForGame(1,3);
			}else {//When left is a man
				BoSClassic[0][0]= new TupleForGame(1,3);
				BoSClassic[0][1]= new TupleForGame(0,0);
				BoSClassic[1][0]= new TupleForGame(0,0);
				BoSClassic[1][1]= new TupleForGame(3,1);
			}
			table = this.BoSClassic;
		}else if(player1=player2=true) {//woman vs Woman TODO:???
			BoSWvW[0][0]= new TupleForGame(3,3);
			BoSWvW[0][1]= new TupleForGame(3,1);
			BoSWvW[1][0]= new TupleForGame(1,3);
			BoSWvW[1][1]= new TupleForGame(1,1);
			table=this.BoSWvW;
		}else {//Man vs Man TODO:???
			BoSWvW[0][0]= new TupleForGame(1,1);
			BoSWvW[0][1]= new TupleForGame(1,3);
			BoSWvW[1][0]= new TupleForGame(3,1);
			BoSWvW[1][1]= new TupleForGame(3,3);
			table=this.BoSMvM;
		}
		
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
