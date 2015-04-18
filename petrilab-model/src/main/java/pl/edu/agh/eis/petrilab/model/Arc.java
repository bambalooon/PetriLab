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

    public int getWeight() {
        return weight;
    }

    protected void setWeight(int weight) {
        Preconditions.checkArgument(weight > 0, "Arc weight has to be greater than 0.");
        this.weight = weight;
    }

    protected State getState() {
        return state;
    }

    protected void setState(State state) {
        Preconditions.checkNotNull(state, "State has to be set.");
        this.state = state;
    }

    protected Transition getTransition() {
        return transition;
    }

    protected void setTransition(Transition transition) {
        Preconditions.checkNotNull(transition, "Transition has to be set.");
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

    public static class Builder {
        private Arc baseArc;
        private Object to;
        private Object from;
        private Integer weight;

        public Builder fromArc(Arc arc) {
            this.baseArc = arc;
            this.weight = arc.getWeight();
            if (arc instanceof StateToTransitionArc) {
                StateToTransitionArc definedArc = (StateToTransitionArc) arc;
                from = definedArc.getStartState();
                to = definedArc.getEndTransition();
            } else if (arc instanceof TransitionToStateArc) {
                TransitionToStateArc definedArc = (TransitionToStateArc) arc;
                from = definedArc.getStartTransition();
                to = definedArc.getEndState();
            } else {
                throw new IllegalArgumentException("Provided arc is of unsupported type: " + arc);
            }
            return this;
        }

        public Builder from(State state) {
            from = state;
            return this;
        }

        public Builder from(Transition transition) {
            from = transition;
            return this;
        }

        public Builder to(State state) {
            to = state;
            return this;
        }

        public Builder to(Transition transition) {
            to = transition;
            return this;
        }

        public Builder withWeight(int weight) {
            this.weight = weight;
            return this;
        }

        public Arc create() {
            if (from instanceof State && to instanceof Transition) {
                return weight == null
                        ? new StateToTransitionArc((State) from, (Transition) to)
                        : new StateToTransitionArc((State) from, (Transition) to, weight);
            } else if (from instanceof Transition && to instanceof State) {
                return weight == null
                        ? new TransitionToStateArc((Transition) from, (State) to)
                        : new TransitionToStateArc((Transition) from, (State) to, weight);
            }
            throw new IllegalStateException("Arc cannot be created with this settings.");
        }

        public Arc modify() {
            if (weight != null) {
                baseArc.setWeight(weight);
            }
            if (from instanceof State && to instanceof Transition) {
                baseArc.setState((State) from);
                baseArc.setTransition((Transition) to);
            } else if (from instanceof Transition && to instanceof State) {
                baseArc.setTransition((Transition) from);
                baseArc.setState((State) to);
            } else {
                throw new IllegalStateException("Arc cannot be modified with this settings.");
            }
            return baseArc;
        }
    }
}
