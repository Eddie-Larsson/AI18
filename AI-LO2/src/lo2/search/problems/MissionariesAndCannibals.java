package lo2.search.problems;

import java.util.Collection;
import java.util.HashSet;

import ch.rfin.ai.search.AbstractSearchProblem;
import ch.rfin.ai.search.Heuristic;

public class MissionariesAndCannibals extends AbstractSearchProblem<MissionariesAndCannibals.State, MissionariesAndCannibals.MoveAction> {
	private static final State goalState = new State(0, 0, Direction.Right);
	private final int totalPeople;
	
	private static enum Direction {
		Left,
		Right
	}
	
	public MissionariesAndCannibals(int numPeople) {
		super(new State(numPeople, numPeople, Direction.Left), goalState);
		this.totalPeople = numPeople;
	}
	
	public static class State {
		public final int cannibalsLeft;
		public final int missionariesLeft;
		public final Direction boatLocation;
		
		public State(int cannibalsLeft, int missionariesLeft, Direction boatLocation) {
			this.cannibalsLeft = cannibalsLeft;
			this.missionariesLeft = missionariesLeft;
			this.boatLocation = boatLocation;
		}

		@Override
		public String toString() {
			return "State [cannibalsLeft=" + cannibalsLeft + ", missionariesLeft=" + missionariesLeft
					+ ", boatLocation=" + boatLocation + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((boatLocation == null) ? 0 : boatLocation.hashCode());
			result = prime * result + cannibalsLeft;
			result = prime * result + missionariesLeft;
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
			if (boatLocation != other.boatLocation)
				return false;
			if (cannibalsLeft != other.cannibalsLeft)
				return false;
			if (missionariesLeft != other.missionariesLeft)
				return false;
			return true;
		}
	}
	
	public static class MoveAction {
		public final int cannibals;
		public final int missionaries;
		public final Direction direction;
		
		public MoveAction(int cannibals, int missionaries, Direction direction) {
			this.missionaries = missionaries;
			this.cannibals = cannibals;
			this.direction = direction;
		}

		@Override
		public String toString() {
			return "MoveAction [cannibals=" + cannibals + ", missionaries=" + missionaries + ", direction=" + direction
					+ "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + cannibals;
			result = prime * result + ((direction == null) ? 0 : direction.hashCode());
			result = prime * result + missionaries;
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
			MoveAction other = (MoveAction) obj;
			if (cannibals != other.cannibals)
				return false;
			if (direction != other.direction)
				return false;
			if (missionaries != other.missionaries)
				return false;
			return true;
		}
	}
	
	private State result(State state, MoveAction action) {
		if(action.direction == Direction.Left) {
			return resultMoveLeft(state, action);
		} else {
			return resultMoveRight(state, action);
		}
	}
	
	private State resultMoveLeft(State state, MoveAction action) {
		return new State(state.cannibalsLeft + action.cannibals, state.missionariesLeft + action.missionaries, Direction.Left);
	}
	
	private State resultMoveRight(State state, MoveAction action) {
		
		return new State(state.cannibalsLeft - action.cannibals, state.missionariesLeft - action.missionaries, Direction.Right);
	}

	@Override
	public State transition(State state, MoveAction action) {
		return result(state, action);
	}

	@Override
	public Collection<MoveAction> possibleActionsIn(State state) {
		Collection<MoveAction> actions = new HashSet<>();
		
		Direction boatSide = state.boatLocation;
		Direction moveDirection = boatSide == Direction.Left ? Direction.Right : Direction.Left;
		int numCannibals = boatSide == Direction.Left ? state.cannibalsLeft : totalPeople - state.cannibalsLeft;
		int numMissionaries = boatSide == Direction.Left ? state.missionariesLeft : totalPeople - state.missionariesLeft;
		int numCannibalsO = boatSide == Direction.Right ? state.cannibalsLeft : totalPeople - state.cannibalsLeft;
		int numMissionariesO = boatSide == Direction.Right ? state.missionariesLeft : totalPeople - state.missionariesLeft;
		
		if(numCannibals >= 2 && (numMissionariesO >= 2 + numCannibalsO || numMissionariesO == 0)) actions.add(new MoveAction(2, 0, moveDirection));
		if(numCannibals >= 1 && (numMissionariesO >= 1 + numCannibalsO || numMissionariesO == 0)) actions.add(new MoveAction(1, 0, moveDirection));
		if(numCannibals >= 1 && numMissionaries >= 1 && (numMissionariesO + 1 >= numCannibalsO + 1)) actions.add(new MoveAction(1, 1, moveDirection));
		if(numMissionaries >= 1 && (numMissionaries - 1 >= numCannibals || numMissionaries == 1) && (numMissionariesO + 1 >= numCannibalsO)) actions.add(new MoveAction(0, 1, moveDirection));
		if(numMissionaries >= 2 && (numMissionaries - 2 >= numCannibals || numMissionaries == 2) && (numMissionariesO + 2 >= numCannibalsO)) actions.add(new MoveAction(0, 2, moveDirection));
		return actions;
	}
	
	public static class PeopleRemainingHeuristic implements Heuristic<State> {

		@Override
		public double estimatedCostAt(State state) {
			return ((double)(state.cannibalsLeft + state.missionariesLeft))/2.0;
		}
		
	}
}
