package lo2.search.algorithms;

import java.util.Optional;

import ch.rfin.ai.search.Node;
import ch.rfin.ai.search.Problem;
import ch.rfin.ai.search.SearchAlgorithm;
import lo2.search.framework.SearchBase;

public class IterativeDeepeningSearch<S, A> implements SearchAlgorithm<S, A> {
	private final int maxDepth;
	private final SearchBase<S, A> searchImp;
	
	public IterativeDeepeningSearch(int maxDepth, SearchBase<S, A> searchImp) {
		this.searchImp = searchImp;
		this.maxDepth = maxDepth;
	}
	@Override
	public Optional<Node<S, A>> solve(Problem<S, A> searchProblem) {
		DepthLimitedSearch<S, A> dls;
		
		Optional<Node<S, A>> result;
		for(int i = 0; i < maxDepth; ++i) {
			dls = new DepthLimitedSearch<>(i, searchImp.copy());
			result = dls.solve(searchProblem);
			if(!result.isPresent() && !dls.cutoffOccured()) return result;
			if(result.isPresent()) return result;
		}
		return Optional.empty();
	}

}
