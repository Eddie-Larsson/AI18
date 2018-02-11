package lo2.search.framework;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Queue;

import ch.rfin.ai.search.Node;
import ch.rfin.ai.search.Problem;
import ch.rfin.ai.search.SearchAlgorithm;

public abstract class SearchStrategy<State, Action> implements SearchAlgorithm<State, Action> {
	private final SearchBase<State, Action> searchImp;
	private final EnumSet<GoalTestStrategy> goalStrategy;
	protected final SearchQueue<NodeWrapper<State, Action>> frontier;
	
	public SearchStrategy(SearchBase<State, Action> searchImp, EnumSet<GoalTestStrategy> goalStrategy, Queue<NodeWrapper<State, Action>> frontier) {
		this.searchImp = searchImp;
		this.goalStrategy = goalStrategy;
		this.frontier = new SearchQueue<>(searchImp.uniqueFrontier(), frontier);
	}
	public enum GoalTestStrategy {
		expansion, frontier;

	    public static final EnumSet<GoalTestStrategy> ALL_OPTS = EnumSet.allOf(GoalTestStrategy.class);
	}
	public boolean isFrontierEmpty() {
		return frontier.isEmpty();
	}
	public Node<State, Action> popFrontier() {
		return frontier.poll().node;
	}
	
	public EnumSet<GoalTestStrategy> getGoalTestStrategy() {
		return goalStrategy.clone();
	}
	
	public void addToFrontier(Node<State, Action> node) {
		frontier.add(new NodeWrapper<>(node));
	}
	public boolean inFrontier(Node<State, Action> node) {
		return frontier.contains(node.state);
	}
	
	public void nodeWasDiscarded(Node<State, Action> node) {
		
	}
	public boolean continueWithBranch(Node<State, Action> node) {
		return true;
	}
	
	@Override
	public Optional<Node<State, Action>> solve(Problem<State, Action> searchProblem) {
		return searchImp.solve(searchProblem, this);
	}
}
