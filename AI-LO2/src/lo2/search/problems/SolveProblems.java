package lo2.search.problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ch.rfin.ai.search.Heuristic;
import ch.rfin.ai.search.Node;
import ch.rfin.ai.search.Problem;
import ch.rfin.ai.search.SearchAlgorithm;
import ch.rfin.ai.search.problems.romania.City;
import ch.rfin.ai.search.problems.romania.Heuristics;
import ch.rfin.ai.search.problems.romania.TouringRomania;
import ch.rfin.ai.search.util.InstrumentedProblem;
import lo2.search.algorithms.SearchAlgorithmsImp;

public class SolveProblems {

	public static void main(String[] args) {
		
		System.out.println("Solving missionaries and cannibals");
		System.out.println("-------------------------------------------------------------------------------------");
		Map<String, Heuristic<MissionariesAndCannibals.State>> cannibalsHeuristics = new HashMap<>();
		cannibalsHeuristics.put("People remaining", new MissionariesAndCannibals.PeopleRemainingHeuristic());
		solve(new MissionariesAndCannibals(3), cannibalsHeuristics);
		System.out.println("-------------------------------------------------------------------------------------");
		
		System.out.println("Solving fox, goose and beans");
		System.out.println("-------------------------------------------------------------------------------------");
		Map<String, Heuristic<FoxGooseBeans.State>> fgbHeuristics = new HashMap<>();
		fgbHeuristics.put("Objects remaining", new FoxGooseBeans.ObjectsRemainingHeuristic());
		solve(new FoxGooseBeans(3), fgbHeuristics);
		System.out.println("-------------------------------------------------------------------------------------");
		
		System.out.println("Solving touring romania");
		System.out.println("-------------------------------------------------------------------------------------");
		Map<String, Heuristic<City>> touringHeuristics = new HashMap<>();
		touringHeuristics.put("Straight-line distance", Heuristics.getStraightLineDistanceHeuristic());
		touringHeuristics.put("Min-node traversals", Heuristics.getMinNodesTraversedHeuristic());
		touringHeuristics.put("Manhattan distance", Heuristics.getManhattanDistanceHeuristic());
		solve(new TouringRomania(City.ARAD, City.BUCHAREST), touringHeuristics);
		System.out.println("-------------------------------------------------------------------------------------");
	}
	
	private static <S, A> void solve(Problem<S, A> problem) {
		SearchAlgorithmsImp<S, A> algorithms = new SearchAlgorithmsImp<>();
		solveAlgo("Breadth-first graph search", InstrumentedProblem.instrument(problem), algorithms.bfsGraph());
		solveAlgo("Depth-first graph search", InstrumentedProblem.instrument(problem), algorithms.dfsGraph());
		solveAlgo("Iterative-deepening graph search", InstrumentedProblem.instrument(problem), algorithms.iterativeDfsGraph(Integer.MAX_VALUE));
		solveAlgo("Uniform cost graph search", InstrumentedProblem.instrument(problem), algorithms.ucsGraph());
		solveAlgo("Uniform cost tree search", InstrumentedProblem.instrument(problem), algorithms.ucsTree());
		solveAlgo("Iterative lengthening graph search", InstrumentedProblem.instrument(problem), algorithms.iterativeLengtheningGraph());
		solveAlgo("Iterative lengthening tree search", InstrumentedProblem.instrument(problem), algorithms.iterativeLengtheningTree());
	}
	
	private static <S, A> void solve(Problem<S, A> problem, Map<String, Heuristic<S>> heuristics) {
		SearchAlgorithmsImp<S, A> algorithms = new SearchAlgorithmsImp<>();
		solveAlgo("Breadth-first graph search", InstrumentedProblem.instrument(problem), algorithms.bfsGraph());
		solveAlgo("Depth-first graph search", InstrumentedProblem.instrument(problem), algorithms.dfsGraph());
		solveAlgo("Iterative-deepening graph search", InstrumentedProblem.instrument(problem), algorithms.iterativeDfsGraph(Integer.MAX_VALUE));
		solveAlgo("Uniform cost graph search", InstrumentedProblem.instrument(problem), algorithms.ucsGraph());
		solveAlgo("Uniform cost tree search", InstrumentedProblem.instrument(problem), algorithms.ucsTree());
		solveAlgo("Iterative lengthening graph search", InstrumentedProblem.instrument(problem), algorithms.iterativeLengtheningGraph());
		solveAlgo("Iterative lengthening tree search", InstrumentedProblem.instrument(problem), algorithms.iterativeLengtheningTree());
		
		for(Map.Entry<String, Heuristic<S>> entry : heuristics.entrySet()) {
			Heuristic<S> h = entry.getValue();
			solveAlgo("Greedy best-first graph search (" + entry.getKey() + ")", InstrumentedProblem.instrument(problem), algorithms.greedyGraph(h));
			//solveAlgo("Greedy best-first tree search", InstrumentedProblem.instrument(problem), algorithms.greedyTree(h));
			
			solveAlgo("A* graph search (" + entry.getKey() + ")", InstrumentedProblem.instrument(problem), algorithms.astarGraph(h));
			solveAlgo("A* tree search (" + entry.getKey() + ")", InstrumentedProblem.instrument(problem), algorithms.astarTree(h));
		}
	}
	
	private static <S, A> void solveAlgo(String name, InstrumentedProblem<S, A> problem, SearchAlgorithm<S, A> algo) {
		Optional<Node<S, A>> res = algo.solve(problem);
		printResult(name, res, problem);
	}
	
	private static <S, A> void printResult(String algorithm, Optional<Node<S, A>> node, InstrumentedProblem<S, A> prob) {
		if(!node.isPresent()) {
			System.out.println(algorithm + " failed to find a solution.");
			return;
		}
		Node<S, A> _node = node.get();
		List<Node<S, A>> nodes = new ArrayList<>();
		if(_node.parent == null) nodes.add(_node);
		
		System.out.println(algorithm + " found a solution");
		System.out.println("Path cost=" + _node.pathCost);
		System.out.println("Depth="+_node.depth);
		System.out.println("Expanded nodes="+prob.getExpansions());
		System.out.println("Goal tests="+prob.getGoalTests());
		System.out.println("Milliseconds taken="+prob.getMilliSeconds());
		while(_node.parent != null) {
			nodes.add(_node);
			_node = _node.parent;
		}
		if(_node != null && !nodes.contains(_node)) nodes.add(_node);
		Collections.reverse(nodes);
		
		System.out.println("-------------------------------------------------------------------------------------");
		System.out.println("Suggested actions & states");
		for(int i = 0; i < nodes.size(); ++i) {
			_node = nodes.get(i);
			if(_node.action != null) System.out.println(_node.action);
			System.out.println(_node.state);
			
		}
		System.out.println("-------------------------------------------------------------------------------------");
		System.out.println();
	}
}
