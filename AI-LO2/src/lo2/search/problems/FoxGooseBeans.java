package lo2.search.problems;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.function.Function;

import ch.rfin.ai.search.AbstractSearchProblem;
import ch.rfin.ai.search.Heuristic;

public class FoxGooseBeans extends AbstractSearchProblem<FoxGooseBeans.State, FoxGooseBeans.Action> {
	private static final Action EMPTY_TRANSPORT = new Action(-1);
	public FoxGooseBeans(int numToTransport) {
		super(new State(new boolean[numToTransport], false));
	}
	
	@Override
	public boolean isGoal(State state) {
		boolean[] objects = state.getObjects();
		
		for(boolean val : objects) {
			if(!val) return false;
		}
		return state.farmerAcross;
	}
	public static class State {
		private final boolean[] objectsAcross;
		public final boolean farmerAcross;
		
		public State(boolean[] objects, boolean farmerPosition) {
			this.objectsAcross = (boolean[]) objects.clone();
			this.farmerAcross = farmerPosition;
		}
		
		public boolean[] getObjects() {
			return (boolean[]) objectsAcross.clone();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (farmerAcross ? 1231 : 1237);
			
			result = prime * result + ((objectsAcross == null) ? 0 : Arrays.hashCode(objectsAcross));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			State other = (State) obj;
			if (farmerAcross != other.farmerAcross)
				return false;
			if (objectsAcross == null) {
				if (other.objectsAcross != null)
					return false;
			} else if (!Arrays.equals(objectsAcross, other.objectsAcross))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "State [objectsAcross=" + Arrays.toString(objectsAcross) + ", farmerAcross=" + farmerAcross + "]";
		}
	}
	
	public static class Action {
		public final int toTransport;
		
		public Action(int toTransport) {
			this.toTransport = toTransport;
		}

		@Override
		public int hashCode() {
			return toTransport;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Action other = (Action) obj;
			if (toTransport != other.toTransport)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Action [toTransport=" + toTransport + "]";
		}
	}

	@Override
	public State transition(State state, Action action) {
		boolean[] objects = state.getObjects();
		if(action.toTransport >= 0) objects[action.toTransport] = !objects[action.toTransport];
		return new State(objects, !state.farmerAcross);
	}
	
	private boolean isValid(boolean toCheck, boolean[] set) {
		int prev = -1;
			for (int i = 0; i < set.length; ++i) {
				if(set[i] != toCheck) continue;
			     if(prev >= 0) {
			    	 if(i - prev == 1) return false;
			     }
			     prev = i;
			 }
		
		return true;
	}

	@Override
	public Collection<Action> possibleActionsIn(State state) {
		Collection<Action> actions = new HashSet<Action>();
		boolean[] objects = state.getObjects();
		boolean[] tstState = (boolean[]) objects.clone();
		
		for(int i = 0; i < objects.length; ++i) {
			if(objects[i] != state.farmerAcross) continue;
				tstState[i] = !tstState[i];
				if(isValid(state.farmerAcross, tstState)) actions.add(new Action(i));
				tstState[i] = !tstState[i];
		}
		if(isValid(state.farmerAcross, objects)) actions.add(EMPTY_TRANSPORT);
		return actions;
	}
	
	public static class ObjectsRemainingHeuristic implements Heuristic<State> {

		@Override
		public double estimatedCostAt(State state) {
			boolean[] objects = state.getObjects();
			int minimum = Collections.frequency(Arrays.asList(objects), true)*2;
			if(!state.farmerAcross && minimum > 0) minimum -= 1;
			if(minimum == 0 && !state.farmerAcross) minimum = 1;
			return minimum;
		}
		
	}
}
