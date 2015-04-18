package pl.edu.agh.eis.petrilab.model;

/**
 * Name: TransitionToStateArc
 * Description: TransitionToStateArc
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class TransitionToStateArc extends Arc {
    public TransitionToStateArc(State state, Transition transition) {
        super(state, transition);
    }

    public TransitionToStateArc(State state, Transition transition, int weight) {
        super(state, transition, weight);
    }

    public Transition getStartTransition() {
        return getTransition();
    }

    public State getEndState() {
        return getState();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransitionToStateArc)) return false;

        return super.equals(o);
    }
}
