package lo2.search.algorithms;

import ch.rfin.ai.search.Node;
import lo2.search.framework.SearchBase;

public class DepthLimitedSearch<S, A> extends DepthFirstSearch<S, A>{
	private final int limit;
	private boolean cutoffOccured = false;
	
	public DepthLimitedSearch(int limit, SearchBase<S, A> searchImp) {
		super(searchImp);
		this.limit = limit;
	}
	
	public boolean cutoffOccured() {
		return cutoffOccured;
	}
	
	@Override
	public boolean continueWithBranch(Node<S, A> node) {
		if(node.depth >= limit) {
			cutoffOccured = true;
			return false;
		}
		return true;
	}
}
