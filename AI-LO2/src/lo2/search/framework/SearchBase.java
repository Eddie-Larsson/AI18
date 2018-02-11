package lo2.search.framework;

import java.util.Optional;

import ch.rfin.ai.search.Node;
import ch.rfin.ai.search.Problem;

public interface SearchBase<State, Action> {
	public Optional<Node<State, Action>> solve(Problem<State, Action> problem, SearchStrategy<State, Action> strategy);
	
	public boolean uniqueFrontier();
	public SearchBase<State, Action> copy();
}
