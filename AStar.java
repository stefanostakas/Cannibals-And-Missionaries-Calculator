

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Used to perform the A-Star (A*) Algorithm to find the shortest path from a start to a target node.
 */
public class AStar {
    
	public static State AStarSearch(State root, int k) {
		
		Set <State> explored = new HashSet <State>();
		
		PriorityQueue <State> queue = new PriorityQueue <State> ( new Comparator <State>() {		

		// Override compare method.
		public int compare (State i, State j) {
			if (i.fscore() > j.fscore()) {
				return 1;
			}
			
			else if (i.fscore() < j.fscore()) {
				return -1;
			}
			
			else {
				return 0;
			}
		}
		}
		);
	
		// Add Root to the Queue
		queue.add(root);
		
		boolean found = false; // Found is true when we are in the terminal State.
		
		State current = null;
		
		while((!queue.isEmpty())) {
			
			//The State with the lowest F Score
			 current = queue.poll();
			
			explored.add(current);
			
			// Check if it is the Goal State
			if (current.isTerminal()) {
				break;
			}
			
			// Check Every Child of the Current State
			for (int i = 0; i < current.getChildren().size(); i++) {
				
				State child = current.getChildren().get(i);
				
				// Child State has been Visited and the Newer F Score is Higher or State Level is bigger than K , Skip.
				if (((explored.contains(child)) && 
				(current.fscore() >= child.fscore())) || child.getStateLevel() > k ) {
					continue;
				}
				
				// Child State is not in Queue or Newer F Score is Lower
				else if((!queue.contains(child)) || 
						(current.fscore() < child.fscore())){
							
				        child.Parent = current;
				        
				        if (queue.contains(child)) {
				        	queue.remove();
				        }
				        
				        queue.add(child);
						}
			}
			
		}
	
		return current;
	}
}