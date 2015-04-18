package pl.edu.agh.eis.petrilab.model;

import com.google.common.base.Preconditions;

/**
 * Name: Arc
 * Description: Arc
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public abstract class Arc {
    private static final int DEFAULT_WEIGHT = 1;
    private State state;
    private Transition transition;
    private int weight;

    public Arc(State state, Transition transition) {
        this(state, transition, DEFAULT_WEIGHT);
    }

    public Arc(State state, Transition transition, int weight) {
        Preconditions.checkNotNull(state, "State has to be set.");
        Preconditions.checkNotNull(transition, "Transition has to be set.");
        Preconditions.checkArgument(weight > 0, "Arc weight has to be greater than 0.");
        this.state = state;
        this.transition = transition;
        this.weight = weight;
    }

    protected void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    protected State getState() {
        return state;
    }

    protected void setState(State state) {
        this.state = state;
    }

    protected Transition getTransition() {
        return transition;
    }

    protected void setTransition(Transition transition) {
        this.transition = transition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Arc)) return false;

        Arc arc = (Arc) o;

        if (weight != arc.weight) return false;
        if (!state.equals(arc.state)) return false;
        if (!transition.equals(arc.transition)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = state.hashCode();
        result = 31 * result + transition.hashCode();
        result = 31 * result + weight;
        return result;
    }
}
