package pl.edu.agh.eis.petrilab.model;

/**
 * Name: ArcToTransition
 * Description: ArcToTransition
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class ArcToTransition extends Arc {
    public ArcToTransition(State state, Transition transition) {
        super(state, transition);
    }

    public ArcToTransition(State state, Transition transition, int weight) {
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
        if (!(o instanceof ArcToTransition)) return false;

        return super.equals(o);
    }
}
