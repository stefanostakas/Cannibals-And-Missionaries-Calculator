# Cannibals-Missionaries-AI-Calculator

- **Description:**
A simple program that finds the best solution (if there is any) for the famous boat puzzle Cannibals & Missionaries using the A* algorithm in Java made for my AI class.

- **Input:**
The program asks for 3 paramaters, the number of people on each team (Not the total!), the boat capacity (How many people can get into the boat) and the maximum moves in order to stop the A* algorithm to keep searching for a path.

- **Output:**
The program returns the total moves needed for the best solution, a list with the moves made and the time needed for the A* algorithm to solve the problem.

- **Program Architecture:**
I used 3 classes for this program the Main, State and AStar class.
The State class contains what it needs to describe every state of the game also you can find there my heuristic functions and how i have implemented the restrictions of the puzzle. 
In the AStar you can find the AStar implementation for the program.

- **The Problem:**
https://www.wikiwand.com/en/Missionaries_and_cannibals_problem
