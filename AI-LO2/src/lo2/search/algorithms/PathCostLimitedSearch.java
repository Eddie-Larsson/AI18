package lo2.search.algorithms;

import ch.rfin.ai.search.Node;
import lo2.search.framework.SearchBase;

public class PathCostLimitedSearch<S, A> extends UniformCostSearch<S, A> {
	private final double limit;
	private boolean cutoffOccured = false;
	private double leastPathCostDiscarded = Double.POSITIVE_INFINITY;
	
	public PathCostLimitedSearch(double limit, SearchBase<S, A> searchImp) {
		super(searchImp);
		this.limit = limit;
	}
	
	public boolean cutoffOccured() {
		return cutoffOccured;
	}
	
	@Override
	public boolean continueWithBranch(Node<S, A> node) {
		if(node.pathCost > limit) {
			cutoffOccured = true;
			if(node.pathCost < leastPathCostDiscarded) leastPathCostDiscarded = node.pathCost;
			return false;
		}
		return true;
	}
	
	public double getLowestDiscarded() {
		return leastPathCostDiscarded;
	}
}
