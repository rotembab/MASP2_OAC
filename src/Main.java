import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		// extract parameters
		int n = Integer.valueOf(args[0]).intValue();
		boolean gameSelected = args[1].equals("PD");// True for PD and False for BoS
		double p1 = Double.valueOf(args[2]).doubleValue();
		double wifeChance = 0;
		if(!gameSelected){ //if false then Bos.
			wifeChance = Double.valueOf(args[3]).doubleValue();
		}


		// generate and print CSP
		Generator gen = new Generator(n, gameSelected, p1, wifeChance);
		MASP masp = gen.generateMASP();
		masp.print();

		// initialize mailer
		Mailer mailer = new Mailer();
		for (int i = 0; i < n; i++) {
			mailer.put(i);
		}		

		// create agents
		
		int[] assignments = new int[n];
		Random r = new Random();
		
		//First round
		for(int i : assignments) {
			i = r.nextInt(2); //Either 0 or 1 - Defect Or Cooperate - Theater or Soccer.
		}
		
	
		boolean isFinished = false;
		int totalSum=0;
		ArrayList<Agent> agents = new ArrayList<Agent>();
		ArrayList<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < n; i++) {
			HashMap<Integer, ConsTable> private_information = masp.tablesOf(i);
			Agent a = new Agent(i, mailer, private_information, n, gameSelected,assignments[i]);
			agents.add(a);
			Thread t = new Thread(a);
			threads.add(t);
		}
		int turns = 1;//The first round was to set every agent with a randomized strategy.
		//TODO: MAKE THE AGENTS SPAWN EACH TURN WITH THE LAST TURN'S ASSGINMENTS.
		//IN THE FIRST TURN THEY WILL HAVE RANDOM VALUES.
		//KEEP THE LOOP OF TURNS GOING AS LONG AS SOMEONE CHANGED THEIR CHOISE IN THIS TURN.
		while(!isFinished) {
			turns++;//update the number of turns, the start of round 2 and more
				// run agents as threads
				for (Thread t : threads) {
					t.start();
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//TODO: SEND AGENT 0 THE FIRST MESSAGE TO BEGIN
				mailer.send(0, new TurnMessage());
				
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
			
		}

		
		System.out.println("Num_Iterations - "+ turns+ "\n SW - "+ totalSum);

		
		//TODO: PRINT THE NUMBER OF TURNS AND THE TOTAL GAIN
	}
}
