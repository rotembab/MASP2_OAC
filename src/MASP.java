
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map.Entry;

/*
 * the masp class wraps the constraint tables (HashMap<VarTuple, ConsTable> cons_tables).
 * this class is queried when constructing the agents and their private information.
 * you are required to add the necessary fields and methods to this class,
 * in order to enable extracting this information
 */
public class MASP {

	// required fields - the constraints tables and the domain size
	private HashMap<VarTuple, ConsTable> cons_tables;
	private boolean typeOfGame;
	int n;
	
	/*
	 * constructor
	 */
	public MASP(HashMap<VarTuple, ConsTable> cons_tables, int agents,boolean typeOfGame) {
		this.cons_tables = cons_tables;
		this.n = agents;
		this.typeOfGame = typeOfGame;
	}

	public HashMap<Integer, ConsTable> tablesOf(int i) {
		
		HashMap<Integer, ConsTable> tables = new HashMap<Integer, ConsTable>();
		SortedSet<Integer> neighbors = neighborsOf(i);
		for (int j: neighbors) {
			for (Entry<VarTuple, ConsTable> entry : cons_tables.entrySet()) {
				VarTuple vt = entry.getKey();
				if ((vt.getI() == i && vt.getJ() == j) || (vt.getJ() == i && vt.getI() == j)) {
					tables.put(j, cons_tables.get(vt));
				}
			}
		}
		return tables;
	}
	
	private SortedSet<Integer> neighborsOf(int i) {
		SortedSet<Integer> neighbors = new TreeSet<Integer>();
		for (Entry<VarTuple, ConsTable> entry : cons_tables.entrySet()) {
			if (entry.getKey().getI() == i) {
				neighbors.add(entry.getKey().getJ());
			}
			if (entry.getKey().getJ() == i) {
				neighbors.add(entry.getKey().getI());
			}
		}
		return neighbors;
	}
	
	// print a MASP
	public void print() {
		for (Entry<VarTuple, ConsTable> entry : cons_tables.entrySet()) {
			System.out.println("table of " + entry.getKey().getI() + " and " + entry.getKey().getJ() + ":");
			entry.getValue().print();
			System.out.println();
		}
	}
}
