package pl.edu.agh.eis.petrilab.model;

/**
 * Name: ArcToState
 * Description: ArcToState
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class ArcToState extends Arc {
    public ArcToState(State state, Transition transition) {
        super(state, transition);
    }

    public ArcToState(State state, Transition transition, int weight) {
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
        if (!(o instanceof ArcToState)) return false;

        return super.equals(o);
    }
}
