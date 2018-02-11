package lo2.search.algorithms;

import java.util.Comparator;
import java.util.Optional;

import ch.rfin.ai.search.Heuristic;
import ch.rfin.ai.search.InformedSearchAlgorithm;
import ch.rfin.ai.search.Node;
import ch.rfin.ai.search.Problem;
import lo2.search.framework.NodeWrapper;
import lo2.search.framework.PriorityQueueSearch;
import lo2.search.framework.SearchBase;

public class AStarSearch<S, A> implements InformedSearchAlgorithm<S, A> {
	private final SearchBase<S, A> searchImp;
	
	public AStarSearch(SearchBase<S, A> searchImp) {
		this.searchImp = searchImp;
	}
	@Override
	public Optional<Node<S, A>> solve(Problem<S, A> problem, Heuristic<S> heuristic) {
		PriorityQueueSearch<S, A> search = new PriorityQueueSearch<>(searchImp.copy(), makeComparator(heuristic));
		return search.solve(problem);
	}
	
	private static <S, A> Comparator<NodeWrapper<S, A>> makeComparator(Heuristic<S> h) {
		return new Comparator<NodeWrapper<S, A>>() {
			@Override
			public int compare(NodeWrapper<S, A> o1, NodeWrapper<S, A> o2) {
				return Double.compare(h.estimatedCostAt(o1.node.state) + o1.node.pathCost, h.estimatedCostAt(o2.node.state) +  + o2.node.pathCost);
			}
			
		};
	}
}
