package lo2.search.framework;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.PriorityQueue;

import ch.rfin.ai.search.Node;

public class PriorityQueueSearch<S, A> extends SearchStrategy<S, A> {
	
	public PriorityQueueSearch(SearchBase<S, A> searchImp) {
		super(searchImp, EnumSet.of(GoalTestStrategy.expansion), new PriorityQueue<>());
	}
	
	public PriorityQueueSearch(SearchBase<S, A> searchImp, Comparator<NodeWrapper<S, A>> comparator) {
		super(searchImp, EnumSet.of(GoalTestStrategy.expansion), new PriorityQueue<>(comparator));
	}
	
	@Override
	public void nodeWasDiscarded(Node<S, A> node) {
		frontier.replaceIfHigher(new NodeWrapper<>(node));
	}
}
