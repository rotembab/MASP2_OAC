
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
	private int currentSum;
	private boolean isChanged=false;
	
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
	public int getSum() {
		return  this.currentSum;
	}
	public boolean getIsChanged() {
		return this.isChanged;
	}
	
	private int calcSumGain(int assign) {
		//This method will get an index and calculate from all the neighbors the gain it receives.
		//Return Summed gain from all neighbors.
		int funcSum =0;
		
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
				funcSum+=result.getGainRight();
			}else {//the left one == i
				funcSum+=result.getGainLeft();
			}
		}
		return funcSum;
		
	}
	
	private void sendAssignmentToNeighbors() {
		Message m = new AssignmentMessage(id, assignment);
		for (Entry<Integer, ConsTable> e: constraints.entrySet()) {
			mailer.send(e.getKey(), m);
		}
	}

	@Override
	public void run() {
		//Send the initial assignment
		this.sendAssignmentToNeighbors();



		//READ INCOMING ASSIGNMENTS
		while (assignments.size() < constraints.size()) {
			AssignmentMessage message = (AssignmentMessage) mailer.readOne(id);
			if (message == null) {
				continue;
			}

			assignments.put(message.getSender(), message.getAssignment());
		}
		try {
			Thread.sleep(1);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		


		//CHECK AND CHOOSE YOUR OWN ASSIGNMENT FROM THE GREATER SUM OF GAINS.

		//Enter wait to turn mode
		TurnMessage message=null;
		do {
			Message m1 =  mailer.readOne(id);
			if(m1 instanceof TurnMessage){
				message = (TurnMessage) m1;
			}else{
				if(m1 instanceof ChecksMessage){
					mailer.send(id,m1);
				}
				message=null;
			}

		}
		while(message==null);

		this.isChanged=false;
		this.currentSum= calcSumGain(assignment);
		int otherSum = calcSumGain(assignment^1);//if assignment Xor 1 (toggle between 1 and 0)
		if(otherSum >currentSum) {
//			System.out.println(id+"I'm changing my choice");
			assignment^=1;
			this.isChanged=true;
			this.currentSum=otherSum;
		}
		
		if (id != agents - 1) {
			ChecksMessage messageCheck = new ChecksMessage(this.currentSum,this.isChanged); //Sends current sum
			mailer.send(agents - 1, messageCheck);
			mailer.send(id+1, new TurnMessage());

		}
		else {
			int count = 0;
			while (count < agents - 1) {
				ChecksMessage messageCheck = (ChecksMessage) mailer.readOne(id);
				if (messageCheck == null) {
					continue;
				}
				count++;
				currentSum += messageCheck.getGain();//Sums up the sums from each agent
				if(messageCheck.getIsChanged()||this.isChanged) {
					isChanged=true;
				}

			}

		}
		}

}
