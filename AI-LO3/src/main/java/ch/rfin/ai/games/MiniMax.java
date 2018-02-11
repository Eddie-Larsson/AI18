package main.java.ch.rfin.ai.games;

import ch.rfin.ai.games.AdversarialSearchAlgorithm;
import ch.rfin.ai.games.EvalFunction;
import ch.rfin.ai.games.Game;
import ch.rfin.jutil.Pair;

/**
 * An implementation of the MiniMax algorithm with pruning, also known as
 * Alpha-Beta-Search.
 * Note that, for this exercise, you should ignore the evaluation function.
 * (The tests assume that your algorithm uses the utilities at the leaves.)
 * One way to accomplish this is to simply set the cutoff such that the
 * evaluation function becomes irrelevant. That way you don't have to create
 * two versions of the same algorithm - a "real" and a nerfed one. You
 * just have to use a suitable parameter to control the behavior.
 *
 * @author Christoffer Fink
 */
public class MiniMax<S,A> implements AdversarialSearchAlgorithm<S,A> {
	private final int depthLimit;
	
	public MiniMax(int depthLimit) {
		this.depthLimit = depthLimit;
	}
  @Override
  public A bestMove(Game<S,A> game, EvalFunction<S> eval, S state) {
	  return maxValue(game, eval, state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, depthLimit).first;
  }
  
  private Pair<A, Double> maxValue(Game<S,A> game, EvalFunction<S> eval, S state, double alpha, double beta, int depth) {
	  if(cutoffTest(game, state, depth)) {
		  if(game.terminal(state)) return Pair.of(null, game.utilityOf(state));
		  return Pair.of(null, eval.utilityOf(state));
	  }
	  double v = Double.NEGATIVE_INFINITY;
	  
	  A bestAction = null;
	  for(A action : game.possibleActionsIn(state)) {
		  Pair<A, Double> res = minValue(game, eval, game.transition(state, action), alpha, beta, depth-1);
		  if(res.second > v) {
			  v = res.second;
			  bestAction = action;
		  }
		  if(v >= beta) return Pair.of(bestAction, v);
		  alpha = Math.max(alpha, v);
	  }
	  return Pair.of(bestAction, v);
  }
  
  private Pair<A, Double> minValue(Game<S,A> game, EvalFunction<S> eval, S state, double alpha, double beta, int depth) {
	  if(cutoffTest(game, state, depth)) {
		  if(game.terminal(state)) return Pair.of(null, game.utilityOf(state));
		  return Pair.of(null, eval.utilityOf(state));
	  }
	  double v = Double.POSITIVE_INFINITY;
	  A bestAction = null;
	  
	  for(A action : game.possibleActionsIn(state)) {
		  Pair<A, Double> res = maxValue(game, eval, game.transition(state, action), alpha, beta, depth-1);
		  if(res.second < v) {
			  v = res.second;
			  bestAction = action;
		  }
		  if(v <= alpha) return Pair.of(bestAction, v);
		  beta = Math.min(beta, v);
	  }
	  return Pair.of(bestAction, v);
  }
  
  private boolean cutoffTest(Game<S,A> game, S state, int depth) {
	  return depth <= 0 || game.terminal(state);
  }
}
