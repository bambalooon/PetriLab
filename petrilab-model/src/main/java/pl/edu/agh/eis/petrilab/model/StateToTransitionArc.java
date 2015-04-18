package pl.edu.agh.eis.petrilab.model;

/**
 * Name: StateToTransitionArc
 * Description: StateToTransitionArc
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class StateToTransitionArc extends Arc {
    public StateToTransitionArc(State state, Transition transition) {
        super(state, transition);
    }

    public StateToTransitionArc(State state, Transition transition, int weight) {
        super(state, transition, weight);
    }

    public State getStartState() {
        return getState();
    }

    public Transition getEndTransition() {
        return getTransition();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StateToTransitionArc)) return false;

        return super.equals(o);
    }
}
