package lo2.search.algorithms;

import java.util.Optional;

import ch.rfin.ai.search.Node;
import ch.rfin.ai.search.Problem;
import ch.rfin.ai.search.SearchAlgorithm;
import lo2.search.framework.SearchBase;

public class IterativeLengtheningSearch<S, A> implements SearchAlgorithm<S, A> {
	private final double maxPathCost;
	private final SearchBase<S, A> searchImp;
	
	public IterativeLengtheningSearch(double maxPathCost, SearchBase<S, A> searchImp) {
		this.searchImp = searchImp;
		this.maxPathCost = maxPathCost;
	}
	
	public IterativeLengtheningSearch(SearchBase<S, A> searchImp) {
		this.searchImp = searchImp;
		this.maxPathCost = Double.POSITIVE_INFINITY;
	}
	@Override
	public Optional<Node<S, A>> solve(Problem<S, A> searchProblem) {
		PathCostLimitedSearch<S, A> pcls;
		
		Optional<Node<S, A>> result;
		
		double pathCostLimit = 1;
		while(pathCostLimit <= maxPathCost) {
			pcls = new PathCostLimitedSearch<>(pathCostLimit, searchImp.copy());
			result = pcls.solve(searchProblem);
			if(!result.isPresent() && !pcls.cutoffOccured()) return result;
			if(result.isPresent()) return result;
			pathCostLimit = pcls.getLowestDiscarded();
		}
		return Optional.empty();
	}
}
