package code.generic;

public abstract class State implements Comparable<State> {
	public abstract boolean equals(State other);

	public abstract String toString();

	public abstract State clone();

	public abstract String toHash();
	
}
