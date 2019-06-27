import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.lang.Math;
/**
 * In this assignment, you will implement Uniform Cost Search as well as A* Search for Sliding Tile Puzzles (i.e., 8-puzzle, 15-puzzle, etc).
 * Find the comments that begin with "STEP 1" etc for detailed instructions on what you are to implement.
 *
 * You may work on this either individually or in pairs (your choice).  If working on this as a pair, both get same grade.
 *
 * STEPS 1 through 6 are in this Java file.  I recommend doing them in the order I've numbered them.
 *
 * STEP 7 (Strongly recommended, but optional step): Implement some code to test your searches.  The constructor of SlidingTilePuzzle has a parameter
 *    for specifying the distance the puzzle's start is from the goal.  Since both Uniform Cost Search and A* with an admissible heuristic
 *    are optimal, your method from STEP 3 should return a path of that length.  Additionally, if your STEPS 1, 2, and 4 are correct, then
 *    the methods I provided (which depend on those), AStarSearchMisplacedTiles and AStarSearchManhattanDistance, should also produce
 *    the optimal path.
 *
 * STEPS 8 through 10 are in the file SlidingTileComparison.
 *
 * @author Your Name(s) Goes Here.
 */
public class SlidingTilePuzzleSolver {

	/**
	 * Solves an instance of the Sliding Tile Puzzle using Uniform Cost Search.
	 *
	 * @param start The starting configuration of the Sliding Tile Puzzle to solve.
	 *
	 * @return The solution path, i.e., the sequence of states from the start state to the goal state.  Returns null if instance has no solution.
	 */
	public static ArrayList<SlidingTilePuzzle> uniformCostSearch(SlidingTilePuzzle start) {
		// STEP 3: Implement Uniform Cost Search.  Read the comment above carefully for what this method should return.
		numExpanded = 0;
		numGenerated = 0;
		SlidingTilePuzzle initial = start;
		MinHeapPQ<SlidingTilePuzzle> frontier = new MinHeapPQ<SlidingTilePuzzle>();
		ArrayList<SlidingTilePuzzle> successors = new ArrayList<SlidingTilePuzzle>();

		HashMap<SlidingTilePuzzle, Integer> gValues = new HashMap<SlidingTilePuzzle, Integer>();
		HashMap<SlidingTilePuzzle, SlidingTilePuzzle> back = new HashMap<SlidingTilePuzzle, SlidingTilePuzzle>();

		frontier.offer(initial,  0);

		while(!frontier.isEmpty()){
			int g = frontier.peekPriority();

			initial = frontier.poll();

			if(initial.isGoalState()){
				while(back.get(initial) != null){
					successors.add(0, initial);
					initial = back.get(initial);

				}

				numGenerated = gValues.size();
				return successors;
			}
			gValues.put(initial, g);
			numExpanded++;
			g++;

			for( SlidingTilePuzzle pzl : initial.getSuccessors() ){
				if(!gValues.containsKey(pzl)){
					frontier.offer(pzl, g);
					back.put(pzl,initial);
				}

			}

		}

		numGenerated = gValues.size();
		return null;
	}

	/**
	 * Solves an instance of the Sliding Tile Puzzle using A* Search.
	 *
	 * @param start The starting configuration of the Sliding Tile Puzzle to solve.
	 * @param h The heuristic for use by A*
	 *
	 * @return The solution path, i.e., the sequence of states from the start state to the goal state.  Returns null if instance has no solution.
	 */
	public static ArrayList<SlidingTilePuzzle> AStarSearch(SlidingTilePuzzle start, SlidingTilePuzzleHeuristic h) {
		// STEP 4: Implement A* Search.  Read the comment above carefully for what this method should return.
		numExpanded = 0;
		numGenerated = 0;
		SlidingTilePuzzle initial = start;
		MinHeapPQ<SlidingTilePuzzle> frontier = new MinHeapPQ<SlidingTilePuzzle>();
		ArrayList<SlidingTilePuzzle> successors = new ArrayList<SlidingTilePuzzle>();

		HashMap<SlidingTilePuzzle, Integer> fValues = new HashMap<SlidingTilePuzzle, Integer>();
		HashMap<SlidingTilePuzzle, SlidingTilePuzzle> back = new HashMap<SlidingTilePuzzle, SlidingTilePuzzle>();

		frontier.offer(initial,  h.h(start));
		back.put(start, null);
		while(!frontier.isEmpty()){
			int f = frontier.peekPriority();

			initial = frontier.poll();

			if(initial.isGoalState()){
				while(back.get(initial) != null){
					successors.add(0, initial);
					initial = back.get(initial);

				}

				numGenerated = fValues.size();
				return successors;
			}

			fValues.put(initial, f);

			numExpanded++;

			for( SlidingTilePuzzle pzl : initial.getSuccessors() ){
				int fTwo = fValues.get(initial) - h.h(initial) + 1 + h.h(pzl);
				if(!fValues.containsKey(pzl) || (frontier.inPQ(pzl) && (fValues.containsKey(pzl) && fValues.get(pzl) > fTwo)) || (fValues.containsKey(pzl) && fValues.get(pzl) > fTwo)){
					frontier.offer(pzl, fTwo);
					back.put(pzl,initial);
				}

			}
		}
		numGenerated = fValues.size();
		return null;
	}

	/**
	 * Solves an instance of the Sliding Tile Puzzle using A* Search using the heuristic number of misplaced tiles.
	 *
	 * @param start The starting configuration of the Sliding Tile Puzzle to solve.
	 *
	 * @return The solution path, i.e., the sequence of states from the start state to the goal state.  Returns null if instance has no solution.
	 */
	public static ArrayList<SlidingTilePuzzle> AStarSearchMisplacedTiles(SlidingTilePuzzle start) {
		// DON'T MODIFY THIS METHOD
		return AStarSearch(start, new NumMisplacedTiles());
	}

	/**
	 * Solves an instance of the Sliding Tile Puzzle using A* Search using the manhattan distance heuristic.
	 *
	 * @param start The starting configuration of the Sliding Tile Puzzle to solve.
	 *
	 * @return The solution path, i.e., the sequence of states from the start state to the goal state.  Returns null if instance has no solution.
	 */
	public static ArrayList<SlidingTilePuzzle> AStarSearchManhattanDistance(SlidingTilePuzzle start) {
		// DON'T MODIFY THIS METHOD
		return AStarSearch(start, new ManhattanDistance());
	}


	/**
	 * Gets the number of states expanded by the most recently executed search.
	 */
	public static int getNumExpandedStates() {
		// DON'T MODIFY THIS METHOD
		return numExpanded;
	}

	/**
	 * Gets the number of states generated by the most recently executed search.
	 */
	public static int getNumGeneratedStates() {
		// DON'T MODIFY THIS METHOD
		return numGenerated;
	}

	// DON'T MODIFY THESE FIELD DECLARATIONS
	private static int numExpanded;
	private static int numGenerated;

	// STEP 5: Once you get your searches working in STEPS 3 and 4, add whatever code is necessary to your methods from STEPS 3 and 4
	//         to count the number of stated that are expanded by the search.  Specifically, at the very beginning of those two methods
	//         set numExpanded to 0 (this is the purpose of that field I've declared above).  And then, at the point in your code
	//		   where you get the successors of a state, increase numExpanded by 1 (generating the successors of a state is expanding the state).

	// STEP 6: Once you get your searches working in STEPS 3 and 4, add whatever code is necessary to your methods from STEPS 3 and 4
	//         to keep track of the total number of states generated by the searches.  Memory usage is proportional to this.
	//         Specifically, at the very beginning of those two methods, set numGenerated to 0.
	//         What you need to do next depends on how you keep track of states generated, such as f values, backpointers, etc.
	//         If you followed my suggestions in the comments in the SomeSampleCode.java file, then you likely used a HashMap.
	//         You can get the number of key-value pairs in a HashMap with the size method.  E.g., if map is a HashMap, then map.size()
	//         is the number of key-value pairs.
	//         Assuming this is what you did, then at all places in your methods from STEPS 3 and 4 where you have a return statement
	//         (including a return null if the problem is unsolvable), you will set numGenerated to whatever the size of your HashMap is.
	//         If you used two HashMaps, one for the f values and one for the backpointers, then just get the size of one of these (they should
	//         both have the same number of pairs if you didn't make any mistakes).
}

class NumMisplacedTiles implements SlidingTilePuzzleHeuristic {

	@Override
	public int h(SlidingTilePuzzle state) {
		// STEP 1: This method should compute the number of misplaced tiles,  i.e., the number of tiles
		// that are not currently in the correct place.  The SlidingTilePuzzle class provides methods
		// for accessing the tile numbers (see the get method in that class) as well as the dimensions
		// of the puzzle (number of rows and columns).
		int rows = state.numRows();
		int columns = state.numColumns();
		int count = 0;
		int misplaced = 0;
		for (int i = 0; i < rows; i++){
			for (int j = 0; j < columns; j++){
				if ( state.getTile(i , j) != count){
					misplaced = misplaced+1;
					count++;
				}
				count++;
			}
		}
		return misplaced;
	}
}

class ManhattanDistance implements SlidingTilePuzzleHeuristic {

	@Override
	public int h(SlidingTilePuzzle state) {
		// STEP 2: This method should compute the sum of manhattan distance of the tiles from their
		// correct locations in the goal state.  The SlidingTilePuzzle class provides methods
		// for accessing the tile numbers (see the get method in that class) as well as the dimensions
		// of the puzzle (number of rows and columns).
		int rows = state.numRows();
		int columns = state.numColumns();
		int distance = 0;
		for (int i = 0; i < rows; i++){
			for (int j = 0; j < columns; j++){
				if ( state.getTile(i , j) != 0){
					int trueRow = (int) Math.floor(((state.getTile(i, j)- 1)/rows));
					int trueCol = (state.getTile(i, j)-1)%columns;

					distance += Math.abs((i - trueRow)) + Math.abs((j - trueCol));;
				}
			}
		}
		return distance;
	}
}

// DON'T MODIFY THIS INTERFACE
interface SlidingTilePuzzleHeuristic {

	/**
	 * Computes a heuruistic estimate of cost to get from state to goal.
	 */
	int h(SlidingTilePuzzle state);
}
