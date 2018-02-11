package lo2.search.algorithms;

import ch.rfin.ai.search.Heuristic;
import ch.rfin.ai.search.InformedSearchAlgorithm;
import ch.rfin.ai.search.SearchAlgorithm;
import ch.rfin.ai.search.algorithms.SearchAlgorithms;
import lo2.search.framework.GraphSearch;
import lo2.search.framework.TreeSearch;;

public class SearchAlgorithmsImp<S, A> implements SearchAlgorithms<S, A> {
	
	public SearchAlgorithm<S,A> bfsTree() {
		return new BreadthFirstSearch<S, A>(new TreeSearch<>());
	}
	
	public SearchAlgorithm<S, A> bfsGraph() {
		return new BreadthFirstSearch<S, A>(new GraphSearch<>());
	}
	
	public SearchAlgorithm<S,A> dfsTree() {
		return new DepthFirstSearch<S, A>(new TreeSearch<>());
	}
	
	public SearchAlgorithm<S,A> dfsGraph() {
		return new DepthFirstSearch<S, A>(new GraphSearch<>());
	}
	
	public SearchAlgorithm<S,A> iterativeDfsTree(int maxDepth) {
		return new IterativeDeepeningSearch<S, A>(maxDepth, new TreeSearch<>());
	}
	
	public SearchAlgorithm<S,A> iterativeDfsGraph(int maxDepth) {
		return new IterativeDeepeningSearch<S, A>(maxDepth, new GraphSearch<>());
	}
	
	public SearchAlgorithm<S,A> ucsTree() {
		return new UniformCostSearch<S, A>(new TreeSearch<>());
	}
	
	public SearchAlgorithm<S,A> ucsGraph() {
		return new UniformCostSearch<S, A>(new GraphSearch<>());
	}
	
	public SearchAlgorithm<S,A> greedyTree(Heuristic<S> h) {
		return InformedSearchAlgorithm.curry(new GreedyBestFirstSearch<S, A>(new TreeSearch<>()), h);
	}
	
	public SearchAlgorithm<S,A> greedyGraph(Heuristic<S> h) {
		return InformedSearchAlgorithm.curry(new GreedyBestFirstSearch<S, A>(new GraphSearch<>()), h);
	}
	
	public SearchAlgorithm<S,A> astarTree(Heuristic<S> h) {
		return InformedSearchAlgorithm.curry(new AStarSearch<S, A>(new TreeSearch<>()), h);
	}

	public InformedSearchAlgorithm<S,A> astarTree() {
		return new AStarSearch<S, A>(new TreeSearch<>());
	}
	
	public SearchAlgorithm<S,A> astarGraph(Heuristic<S> h) {
		return InformedSearchAlgorithm.curry(new AStarSearch<S, A>(new GraphSearch<>()), h);
	}
	
	public InformedSearchAlgorithm<S,A> astarGraph() {
		return new AStarSearch<S, A>(new GraphSearch<>());
	}
	
	public SearchAlgorithm<S, A> iterativeLengtheningGraph() {
		return new IterativeLengtheningSearch<S, A>(new GraphSearch<>());
	}
	
	public SearchAlgorithm<S, A> iterativeLengtheningTree() {
		return new IterativeLengtheningSearch<S, A>(new TreeSearch<>());
	}
}
