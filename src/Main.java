import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		// extract parameters
		int n = Integer.valueOf(args[0]).intValue();
		boolean gameSelected = Boolean.valueOf(args[1]);// true for PD and false for BoS
		double p1 = Double.valueOf(args[2]).doubleValue();
		int wifePlayers = 0;
		if(!gameSelected){ //if false then Bos
			wifePlayers = Integer.valueOf(args[3]).intValue();
		}


		// generate and print CSP
		Generator gen = new Generator(n, gameSelected, p1, wifePlayers);
		MASP masp = gen.generateMASP();
		masp.print();

		// initialize mailer
		Mailer mailer = new Mailer();
		for (int i = 0; i < n; i++) {
			mailer.put(i);
		}		

		// create agents
		
		//TODO: CREATE A DATA TYPE FOR STORING THE AGENT'S ASSIGNMENT FROM LAST TURN (FIRST IS RANDOM).
		int[] assignments = new int[n];
		Random r = new Random();
		//first initialization
		for(int i : assignments) {
			i = r.nextInt(2);//Either 1 or 2
		}
		int turns = 0;
		boolean isFinished = false;
		int totalSum=0;
		//TODO: MAKE THE AGENTS SPAWN EACH TURN WITH THE LAST TURNS ASSGINMENTS.
		//IN THE FIRST TURN THEY WILL HAVE RANDOM VALUES.
		//KEEP THE LOOP OF TURNS GOING AS LONG AS SOMEONE CHANGED THEIR CHOISE IN THIS TURN.
		while(!isFinished) {
			ArrayList<Agent> agents = new ArrayList<Agent>();
			ArrayList<Thread> threads = new ArrayList<Thread>();
			for (int i = 0; i < n; i++) {
				HashMap<Integer, ConsTable> private_information = masp.tablesOf(i);
				Agent a = new Agent(i, mailer, private_information, n, gameSelected,assignments[i]);
				agents.add(a);
				Thread t = new Thread(a);
				threads.add(t);
			}
				// run agents as threads
				for (Thread t : threads) {
					t.start();
				}

				// wait for all agents to terminate
				for (Thread t : threads) {
					t.join();
				}
				
			//TODO: GO THROUGH ALL AGENTS, UPDATE THE ASSIGNMENTS AND CHECK IF SOMETHING CHANGED
			for (int i = 0; i < n; i++) {
				assignments[i]=agents.get(i).getAssignment();//UPDATE ASSIGNMENTS
			}
			totalSum=agents.get(n-1).getSum(); //The last agent's sum which is the total.
			isFinished = !(agents.get(n-1).getIsChanged()); //The last agent's is changed which is the collective is changed.
			turns++;//update the number of turns
		}

		
		System.out.println("Number of turns:"+ turns+ ", total gain: "+ totalSum);

		
		//TODO: PRINT THE NUMBER OF TURNS AND THE TOTAL GAIN
	}
}
