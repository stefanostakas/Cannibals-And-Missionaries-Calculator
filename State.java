import java.util.ArrayList;



/* 
 * State is a representation of a node
 * Each State holds various bits of data which help to model a specific state.
 * 
 */

public class State {

	public int nMiss, nCan; // nMiss : Number of Missionaries , nCan : Number of Cannibals -> on the Left side
	public boolean Side; // Side of boat -> True for left / False for right
	public int nEachStart; // Number of people on each 'team'
	private int stateLevel; // State Level is the level from the root to the current state
	private int BoatCapacity; 
	public State Parent; // The previous State


// Root State Constructor

public State(int nEachStart, int BoatCapacity) {

	this.nMiss = nEachStart;
	this.nCan = nEachStart;
	this.Side = true;
	this.nEachStart = nEachStart;
	this.BoatCapacity = BoatCapacity;
	this.stateLevel = 0; // Root State Level
	this.Parent = null; // Root doesn't have a Parent
}

// State Constructor

public State(int nMiss, int nCan, boolean Side, int nEachStart, int BoatCapacity, int stateLevel) {

	this.nMiss = nMiss;
	this.nCan = nCan;
	this.Side = Side;
	this.nEachStart = nEachStart;
	this.BoatCapacity = BoatCapacity;
	this.stateLevel = stateLevel;
	this.Parent = null;
	
}

/** 
 * This function calculates the f(n).
 * f(n) as f_score -> is the sum of h(n) and g(n).
 * h(n) as h_score -> is the heuristic function.
 * g(n) as getStateLevel -> is the distance between the root state and this state.
 * @return the f_score
 */

public double fscore() {
	
	int h_score = 0; // Case 0: No people left. Goal Case

	
	// Case 1: Boat is right and there is at least one person on the left.
	if (!this.Side && this.getN() > 0) {
		h_score = 2 * this.getN();
	} 
	// Case 2: Boat is left and one person is on the left
	else if (this.Side  && this.getN() == 1) {
		h_score = 1;
	}
	// Case 3: Boat is left and at least 2 people on the left.
	else if (this.Side && this.getN() > 1) {
		h_score = 2 * this.getN() - 3;
	}
	
	return h_score + getStateLevel();
	
}

//Returns this State Level

public int getStateLevel()
{
return this.stateLevel;
}


// Returns the Total Amount of People in the Left Side

public int getN() {
	
	return this.nMiss + this.nCan;
}

// Returns the Missionaries on the Other Side

public int getOtherMiss() {
	
	return this.nEachStart - this.nMiss;
}

// Returns the Cannibals on the Other Side

public int getOtherCan() {
	
	return this.nEachStart - this.nCan;
}

// Returns true if this state is a valid state otherwise at least one of our rules has been violated

public boolean InvalidState(int c, int m) {
	
	int Cannibals = this.nCan - c;
	int Missionaries = this.nMiss - m;
	int sideCann = this.nEachStart - Cannibals;
	int sideMiss = this.nEachStart - Missionaries;

	// if cannibals are more - at least in one side - its invalid	
	
	if ((Cannibals > Missionaries && Missionaries > 0 ) || (sideCann > sideMiss && sideMiss > 0 )) return false;	

	return true;
}

/*
 * Generates the children-states of the this state
 * Each child-state is created by making one (and only one) possible move in the "board"
 */

public ArrayList <State> getChildren() {
	
	// Change boat side for the children
	
	boolean opSide = !this.Side;
	
	ArrayList<State> children = new ArrayList<State>();
	
	// Finds if there is any Missionaries on the boat only combinations and adds them in the children ArrayList.
	MissOnly (children, opSide);
	
	// Finds if there is any Cannibals on the boat only combinations and adds them in the children ArrayList.
	CannOnly (children, opSide);

	// Finds all the Cross combinations and adds them in the children ArrayList.
	CannNMiss (children, opSide);

	   		
	return children;
	
}


// Combinations Only with Missionaries in the boat 

public void MissOnly (ArrayList <State> children, Boolean opSide) {
	
	int sideMiss;
	int sign;
	
	State child = new State(this.nMiss, this.nCan, opSide, this.nEachStart, this.BoatCapacity, this.stateLevel+1); 
	
	if (opSide) { // If the boat is on the right side
		
		sideMiss = getOtherMiss(); 
		sign = -1;
		
	}else // Boat on the left side
	{
		sideMiss = this.nMiss;
		sign = 1;
	}
		
		
	if (sideMiss != 0) {
		
		for (int m=1; m <= sideMiss && m <= this.BoatCapacity; m++) {
			// Check if its valid
		if (InvalidState(0,m * sign)){
			child.nMiss = this.nMiss - (m * sign);
			children.add(child);
			child = new State(this.nMiss, this.nCan, opSide, this.nEachStart, this.BoatCapacity, this.stateLevel+1); //restore
		}
		}	
		
	}
}

//Combinations Only with Cannibals in the boat 

public void CannOnly (ArrayList <State> children, Boolean opSide) {
	
	int sideCan;
	int sign;
	
	State child = new State(this.nMiss, this.nCan, opSide, this.nEachStart, this.BoatCapacity, this.stateLevel+1); 
	
	if (opSide) { // If the boat is on the right side
		
		sideCan =  getOtherCan();
		sign = -1;
		
	}else { // Boat on the left side
		
		sideCan = this.nCan;
		sign = 1;
	}
	
	if (sideCan != 0) {
		for (int c=1; c <= sideCan && c <= this.BoatCapacity; c++) {
			// Check if its valid
		if (InvalidState(c * sign,0)){
			child.nCan = this.nCan - (c * sign);
			children.add(child);
			child = new State(this.nMiss, this.nCan, opSide, this.nEachStart, this.BoatCapacity, this.stateLevel+1); //restore
				}
		}
	}
}



// Combinations with Cannibals and Missionaries in the boat

public void CannNMiss (ArrayList <State> children, Boolean opSide) {
	
	int sideCan;
	int sideMiss;
	int sign;
	
	State child = new State(this.nMiss, this.nCan, opSide, this.nEachStart, this.BoatCapacity, this.stateLevel+1); 
	
	if (opSide) { // If the boat is on the right side
		
		sideCan = getOtherCan();
		sideMiss = getOtherMiss();
		sign = -1;
	}
	else { // Boat is on the left side
		
		sideCan = this.nCan;
		sideMiss = this.nMiss;
		sign = 1;
	}
	
	
	if(sideCan +sideMiss >= this.BoatCapacity) {
		
			for (int c = 1; c <= sideCan; c++) {
				for (int m = 1; m <= sideMiss; m++) {
					if (c+m <= this.BoatCapacity) {
						
						if (InvalidState(c * sign,m * sign)) {
							child.nCan = this.nCan - (c * sign);
							child.nMiss = this.nMiss - (m * sign);
							children.add(child);
							child = new State(this.nMiss, this.nCan, opSide, this.nEachStart, this.BoatCapacity, this.stateLevel+1); //restore
						}
						
					}else break;
				}
			}
	}else if (!opSide) { 
		// We have less people than our boat capacity, so we can send them all to the other side / when we are in the right side there is no need for this combination.
		
		child.nCan = 0;
		child.nMiss = 0;
		children.add(child);
		
	}
}


// Returns true if this is a terminal state , terminal state is the state where there is no people on the left

public boolean isTerminal() {
	if (this.nCan==0 && this.nMiss==0) {
		return true;
	}
	return false;
}

@Override
public String toString() {
	if (!this.Side){
       return "Boat is on the right side," + " on the left side there are " + nCan + " Cannibals and " + nMiss + " Missionaries." + " On The right side there are " + getOtherCan() + " Cannibals and " + getOtherMiss() + " Missionaries.\n"  ; 
    }else {
    	return "Boat is on the left side, " + "on the left side there are " + nCan + " Cannibals and " + nMiss + " Missionaries." + " On The right side there are " + getOtherCan() + " Cannibals and " + getOtherMiss() + " Missionaries. \n"; 
    }
	
}
}