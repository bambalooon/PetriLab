package pl.edu.agh.eis.petrilab.model;

/**
 * Name: PlaceToTransitionArc
 * Description: PlaceToTransitionArc
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class PlaceToTransitionArc extends Arc {
    public PlaceToTransitionArc(Place fromPlace, Transition toTransition) {
        super(fromPlace, toTransition);
    }

    public PlaceToTransitionArc(Place fromPlace, Transition toTransition, int weight) {
        super(fromPlace, toTransition, weight);
    }

    public Place getStartState() {
        return getPlace();
    }

    public Transition getEndTransition() {
        return getTransition();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceToTransitionArc)) return false;

        return super.equals(o);
    }
}
