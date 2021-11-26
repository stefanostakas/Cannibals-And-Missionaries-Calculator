import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	private static long startTime;
	
	public static void main (String[] args) {
		
		// User inputs
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print(" Select number of people on each team : ");
		int n = sc.nextInt();
		
		System.out.print(" Select boat seats : ");
		int m = sc.nextInt();
		
		System.out.print(" Select maximum moves : ");
		int k = sc.nextInt();
		
		timeCapt(true); // Start time tracking
		
		printRes(n,m,k); // Finds and print the result of the search
		
		timeCapt(false); // End time tracking
	
	}
	
	// Creates the Root State with the User Parameters then it's Searching if there is a Root with the given Parameters. Prints the result.
	
	public static void printRes(int n, int m, int k) {
		
		State Root = new State(n,m);
		
		AStar SrcObj = new AStar();
		
		State Goal = SrcObj.AStarSearch(Root,k);
		
       int totalMoves = Goal.getStateLevel();
		
		
		if (Goal.isTerminal() && totalMoves <= k) {
		
		
		System.out.println("\n                                                                         *** MOVES MADE *** \n");
		
		while (Goal.Parent != null) {
		
			System.out.println("Move number "+Goal.getStateLevel()+ ": " + Goal);
			Goal = Goal.Parent;
		
		}
		
		System.out.println("Starting Point: " + Root); // Print the Root State, left out of the Above While Printing procession cause it has no Parent.
		
		System.out.println("Moves needed :  " + totalMoves + "/" + k);
		
		}else {
			
			System.out.println("\nPath with those parameters is not possible.");
		}
		
	}
	
	// Captures time -> if start is true it begins the capture else it ends the capture and print the result.
	
	public static void timeCapt(Boolean start) {
		
		if (start) {
			startTime = System.nanoTime();
		}else {
			// End Tracking Run Time
			long endTime   = System.nanoTime();
			
			// Print Total Time
			long totalTime = endTime - startTime;
			
			// 1 second = 1_000_000_000 nano seconds
	        double elapsedTimeInSecond = (double) totalTime / 1_000_000_000;

	        System.out.println("Run Time: " + elapsedTimeInSecond + " seconds.");
		}
	}
	
}

