package lo2.search.algorithms;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.EnumSet;
import lo2.search.framework.SearchBase;
import lo2.search.framework.SearchStrategy;

public class DepthFirstSearch<S, A> extends SearchStrategy<S, A> {
	
	public DepthFirstSearch(SearchBase<S, A> searchImp) {
		super(searchImp, EnumSet.of(GoalTestStrategy.expansion), Collections.asLifoQueue(new ArrayDeque<>()));
	}
}
