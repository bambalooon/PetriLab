package pl.edu.agh.eis.petrilab.model;

/**
 * Name: TransitionToPlaceArc
 * Description: TransitionToPlaceArc
 * Date: 2015-04-18
 * Created by BamBalooon
 */
@Deprecated
public class TransitionToPlaceArc extends Arc {
    public TransitionToPlaceArc(Transition fromTransition, Place toPlace) {
        super(toPlace, fromTransition);
    }

    public TransitionToPlaceArc(Transition fromTransition, Place toPlace, int weight) {
        super(toPlace, fromTransition, weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransitionToPlaceArc)) return false;

        return super.equals(o);
    }
}
