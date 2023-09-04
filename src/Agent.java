
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

public class Agent implements Runnable {

	private int id;
	private int  assignment, agents;
	private Mailer mailer;
	private HashMap<Integer, ConsTable> constraints;
	private HashMap<Integer, Integer> assignments = new HashMap<Integer, Integer>();
	private boolean gameSelected;
	
	/*
	 * constructor parameters -
	 * agent's id
	 * a reference to mailer
	 * private information from MASP object
	 */
	public Agent(int id, Mailer mailer, HashMap<Integer, ConsTable> constraints, int n, boolean gameSelected,int assignment) {
		this.id = id;
		this.mailer = mailer;
		this.constraints = constraints;
		this.agents = n;
		this.assignment = assignment;
	}
	
	public int getAssignment() {
		return this.assignment;
	}
	
	private int calcSumGain(int assign) {
		//The method will get an index and calculate from all the neighbors the gain it's receives.
		//Return Summed gain from all neighbors.
		int sum =0;
		
		//Iterate through all assignments, and calculate the sum.
		for (Entry<Integer, ConsTable> e: constraints.entrySet()) {
			int a1 = -1, a2 = -1;
			if (e.getKey() < id) {
				a1 = assignments.get(e.getKey());
				a2 = assign;
			}
			else {
				a2 = assignments.get(e.getKey());
				a1 = assign;
			}
			TupleForGame result = e.getValue().check(a1, a2);
			if(e.getKey() < id) {//the right one ==j
				sum+=result.getGainRight();
			}else {//the left one == i
				sum+=result.getGainLeft();
			}
		}
		return sum;
		
	}

	@Override
	public void run() {
		//SEND INITIAL ASSIGNMENT
		Message m = new AssignmentMessage(id, assignment);
		for (Entry<Integer, ConsTable> e: constraints.entrySet()) {
			mailer.send(e.getKey(), m);
		}
		
		//SAVE EACH NEIGHBOR'S ASSIGNMENT
		while (assignments.size() < constraints.size()) {
			AssignmentMessage message = (AssignmentMessage) mailer.readOne(id);
			if (message == null) {
				continue;
			}
			
			assignments.put(message.getSender(), message.getAssignment());
		}
		
		
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//CHECK AND CHOOSE YOUR OWN ASSIGNMENT FROM THE GREATER SUM OF GAINS.
		
		boolean isChanged=false;
		//int success = 0;
		int currentSum = calcSumGain(assignment);
		int otherSum = calcSumGain(assignment^1);//if assignment Xor 1 (toggle between 1 and 0)
		if(otherSum >currentSum) {
			assignment^=1;
			isChanged=true;
		}

		//System.out.println("id: " + id + ", assignment: " + assignment + ", successful constraint checks: " + success);
		
		if (id != agents - 1) {
			ChecksMessage message = new ChecksMessage(currentSum); //Sends current sum
			mailer.send(agents - 1, message);
		}
		else {
			int count = 0;
			while (count < agents - 1) {
				ChecksMessage message = (ChecksMessage) mailer.readOne(id);
				if (message == null) {
					continue;
				}
				count++;
				currentSum += message.getChecks();//Sums up the sums from each agent
			}
			
			//System.out.println("total number of constraint checks: " + success);
		}
	}

}
