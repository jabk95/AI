import java.text.DecimalFormat;

/**
 * See comments below first.  Then, return back up here.
 *
 * STEP 9: Save the output of your program (STEP 8) to a text file, and include that file when you submit your assignment.
 *
 * STEP 10: Answer this question in this comment:
 *     Question: Part of your step 8 should confirm the effect we saw in class that a more informed heuristic leads to fewer state expansions.
 *               Does it also have an effect on memory usage (i.e., number of generated states)?
 *
 * EXTRA CREDIT INSTRUCTIONS:
 * You do not need to do all of the extra credit parts.  You can: (a) do none of them and thus get no extra credit, (b) do EXTRA CREDIT STEPS 11 and 12,
 * (c) do EXTRA CREDIT STEPS 13 through 16, or (d) do EXTRA CREDIT STEPS 11 through 16.  Those are the only valid combinations of extra credit parts.
 *
 * EXTRA CREDIT STEP 11: Add code to the main method below to compute the average CPU time, and to output a 3rd table for the average CPU time.
 *                       Make sure your output in STEP 9 includes this additional table.
 * EXTRA CREDIT STEP 12: Answer this question in this comment:
 *     Question: More informed heuristics lead to fewer state expansions, but more informed heuristics typically require more time to compute,
 *               so may or may not be better to use.  Based on your timing results, is the added cost of computing Manhattan Distance worthwhile?
 *
 * EVEN MORE EXTRA CREDIT STEP 13: Implement another heuristic function in the SlidingTilePuzzleSolver file, specifically this additional heuristic
 *    function should compute: a * h(s) where h(s) is the Manhattan Distance for state s, and where a >= 1.  The class that implements this heuristic
 *    function will need a constructor enabling passing a (you can't change the signature line of the h method).
 * EVEN MORE EXTRA CREDIT STEP 14: Add whatever code is necessary to your main method below to have an additional column for A*h3 to your tables
 *   (make sure you update the text file you submit for STEP 9).  You can decide on the value of a, but it should be relatively close to 1,
 *   such as 1.1, 1.2, etc.
 * EVEN MORE EXTRA CREDIT STEP 15: Desscribe in this comment any cost savings from using this heuristic relative to using Manhattan Distance.
 * EVEN MORE EXTRA CREDIT STEP 16: Also in this comment, indicate whether this heuristic is admissible.
 *
 */
public class SlidingTileComparison {


	public static void main(String[] args) {

		// STEP 8:  Implement whatever code is necessary to do the following.
		//   For each of the following optimal path lengths (2, 4, 6, 8, 10) generate 10 random 8-puzzles (see sample code in SomeSampleCode).
		//   At each of those lengths, average the number of states expanded by UniformCostSearch and average the number of states generated
		//   by Uniform Cost Search (if STEPS 3, 5, and 6 are correct, you can get these with the methods getNumExpandedStates and getNumGeneratedStates).
		//   Likewise, do the same for A* using number of misplaced tiles as the heuristic.
		//   And do the same for A* using Manhattan distance as the heuristic.
		//   NOTE: The puzzles you use with Uniform Cost Search should be the same as with A*.
		//
		//   Your output from this program should be in the form of two tables as follows (with the same or similar column headings).
		//   The tables can either be side by side like in the comment below, or one followed by the other.
		//
		//   Number of Expanded States     Number of Generated States
		//   L  UCS   A*h1   A*h2          L  UCS   A*h1   A*h2
		//   2                             2
		//   4                             4
		//   6                             6
		//   8                             8
		//   10                            10
		//
		//   The UCS column should have the average across the 10 puzzles at the relevant path length.
		//   The A*h1 likewise should have those averages for A* with number of misplaced tiles.
		//   The A*h2 likewise should have those averages for A* with manhattan distance.
		//
		//   After you run your program, you should find that the UCS number of expanded states is the highest, followed by
		//   A*h1, and A*h2 should have the lowest number of expanded states (assuming your STEPS 1 through 6 are correct)
		//   since number of misplaced tiles is more informed than no heuristic (UCS), and Manhattan distance is more informed
		//   than number of misplaced tiles.
		//
		//   REMINDER: An integer divided by an integer only gives you the whole number portion of the result, so be careful when
		//             computing the averages since number of expanded states and number of generated states are integers.
		//             If total is the sum of the number of expanded states across the 10 runs at a given length, and you do total/10
		//             you will get the wrong answer (if total is an int).

		int optimalInterval = 2;
		double ucsAvgExp = 0;
		double mpTileAvgExp = 0;
		double mtAvgExp = 0;
		double ucsAvgGen = 0;
		double mpTileAvgGen = 0;
		double mtAvgGen = 0;
		System.out.println("Number of Expanded States\tNumber of Generated States");
		System.out.println("L\tUCS\tA*h1\tA*h2\tL\tUCS\tA*h1\tA*h2");
		DecimalFormat df2 = new DecimalFormat(".##");

		while(optimalInterval <= 10){
				for(int i = 0; i < 10; i++){
					SlidingTilePuzzle puzzle = new SlidingTilePuzzle(3, 3, optimalInterval);
					SlidingTilePuzzleSolver slv = new SlidingTilePuzzleSolver();
					//UniformCostSearch
					slv.uniformCostSearch(puzzle);
					ucsAvgExp += slv.getNumExpandedStates();
					ucsAvgGen += slv.getNumGeneratedStates();
					//AStarSearchMisplacedTiles
					slv.AStarSearchMisplacedTiles(puzzle);
					mpTileAvgExp += slv.getNumExpandedStates();
					mpTileAvgGen += slv.getNumGeneratedStates();
					//AStarSearchManhattanDistance
					slv.AStarSearchManhattanDistance(puzzle);
					mtAvgExp += slv.getNumExpandedStates();
					mtAvgGen += slv.getNumGeneratedStates();
				}
				ucsAvgExp = ucsAvgExp/10;
				ucsAvgGen = ucsAvgGen/10;
				mpTileAvgExp = mpTileAvgExp/10;
				mpTileAvgGen = mpTileAvgGen/10;
				mtAvgExp = mtAvgExp/10;
				mtAvgGen = mtAvgGen/10;
				System.out.println(optimalInterval + "\t" + df2.format(ucsAvgExp) + "\t" + df2.format(mpTileAvgExp) + "\t" + df2.format(mtAvgExp) + "\t"+ optimalInterval + "\t" + df2.format(ucsAvgGen) + "\t" +df2.format(mpTileAvgGen)+ "\t"+ df2.format(mtAvgGen));
				optimalInterval+=2;

			}
		}
}
