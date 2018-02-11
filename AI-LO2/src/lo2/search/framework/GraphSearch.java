package lo2.search.framework;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import ch.rfin.ai.search.Node;
import ch.rfin.ai.search.Problem;
import lo2.search.framework.SearchStrategy.GoalTestStrategy;

public class GraphSearch<State, Action> implements SearchBase<State, Action> {
	private final Set<State> explored;
	
	public GraphSearch() {
		this.explored = new HashSet<>();
	}
	@Override
	public Optional<Node<State, Action>> solve(Problem<State, Action> problem, SearchStrategy<State, Action> strategy) {
		Node<State, Action> current = problem.getInitialNode();
		
		if(strategy.getGoalTestStrategy().contains(GoalTestStrategy.frontier) && problem.isGoal(current)) return Optional.of(current);
		if(!strategy.continueWithBranch(current)) return Optional.empty();
		strategy.addToFrontier(current);
			
		while(!strategy.isFrontierEmpty()) {
			current = strategy.popFrontier();
			if(strategy.getGoalTestStrategy().contains(GoalTestStrategy.expansion) && problem.isGoal(current)) return Optional.of(current);
			explored.add(current.state);
			for(Node<State,Action> child : problem.successorsOf(current)) {
				if(!explored.contains(child.state) && !strategy.inFrontier(child)) {
					if(strategy.getGoalTestStrategy().contains(GoalTestStrategy.frontier) && problem.isGoal(current)) return Optional.of(current);
					if(!strategy.continueWithBranch(child)) continue;
					strategy.addToFrontier(child);
				} else {
					if(!strategy.continueWithBranch(child)) continue;
					strategy.nodeWasDiscarded(child);
				}
			}
		}
		return Optional.empty();
	}

	@Override
	public SearchBase<State, Action> copy() {
		return new GraphSearch<State, Action>();
	}
	@Override
	public boolean uniqueFrontier() {
		return true;
	}
}
