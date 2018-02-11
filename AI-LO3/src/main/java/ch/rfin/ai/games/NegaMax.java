package main.java.ch.rfin.ai.games;

import ch.rfin.ai.games.AdversarialSearchAlgorithm;
import ch.rfin.ai.games.EvalFunction;
import ch.rfin.ai.games.Game;
import ch.rfin.jutil.Pair;

public class NegaMax<S,A> implements AdversarialSearchAlgorithm<S,A> {
	private final int depthLimit;
	private Game<S,A> game;
	private EvalFunction<S> eval;
	private static final int[] PLAYER_VALUES = {1, -1};
	
	public NegaMax(int depthLimit) {
		this.depthLimit = depthLimit;
	}
	
	@Override
	public A bestMove(Game<S, A> game, EvalFunction<S> eval, S state) {
		this.game = game;
		this.eval = eval;
		return negaMax(state, 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, depthLimit).first;
	}
	
	private Pair<A, Double> negaMax(S state, int player, double alpha, double beta, int depth) {
		if(cutoffTest(state, depth)) {
			if(game.terminal(state)) return Pair.of(null, PLAYER_VALUES[player] * game.utilityOf(state));
			return Pair.of(null, PLAYER_VALUES[player] * eval.utilityOf(state));
		  }
		  double max = Double.NEGATIVE_INFINITY;
		  A bestAction = null;
		  
		  for(A action : game.possibleActionsIn(state)) {
			  Pair<A, Double> res = negaMax(game.transition(state, action), 1-player, -beta, -alpha, depth-1);
			  res = Pair.of(res.first, -res.second);
			  if(res.second > max) {
				  max = res.second;
				  bestAction = action;
			  }
			  if(res.second > alpha) alpha = res.second;
			  if(alpha >= beta) return Pair.of(bestAction, alpha);
		  }
		  return Pair.of(bestAction, max);
	}
	
	private boolean cutoffTest(S state, int depth) {
		  return depth <= 0 || game.terminal(state);
	}

}
