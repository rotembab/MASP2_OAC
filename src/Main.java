import java.util.ArrayList;
import java.util.HashMap;

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
		ArrayList<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < n; i++) {
			// use the csp to extract the private information of each agent
			
			HashMap<Integer, ConsTable> private_information = masp.tablesOf(i);
			Thread t = new Thread(new Agent(i, mailer, private_information, n, gameSelected));
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
		
	}
}
