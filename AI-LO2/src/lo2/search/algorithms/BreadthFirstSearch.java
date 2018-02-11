package lo2.search.algorithms;

import java.util.ArrayDeque;
import java.util.EnumSet;


import lo2.search.framework.SearchBase;
import lo2.search.framework.SearchStrategy;

public class BreadthFirstSearch<S, A> extends SearchStrategy<S, A> {
	
	public BreadthFirstSearch(SearchBase<S, A> searchImp) {
		super(searchImp, EnumSet.of(GoalTestStrategy.frontier), new ArrayDeque<>());
	}

}
