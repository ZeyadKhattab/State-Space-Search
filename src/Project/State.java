package Project;

public abstract class State implements Comparable<State> {
	abstract public boolean equals(State other);
	abstract public String toString();
	abstract public State clone();

}
