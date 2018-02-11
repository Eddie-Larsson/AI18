package lo2.search.algorithms;


import lo2.search.framework.PriorityQueueSearch;
import lo2.search.framework.SearchBase;


public class UniformCostSearch<S, A> extends PriorityQueueSearch<S, A> {
	
	public UniformCostSearch(SearchBase<S, A> searchImp) {
		super(searchImp);
	}

}
