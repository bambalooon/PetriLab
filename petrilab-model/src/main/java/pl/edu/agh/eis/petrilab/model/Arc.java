package pl.edu.agh.eis.petrilab.model;

import com.google.common.base.Preconditions;

/**
 * Name: Arc
 * Description: Arc
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public abstract class Arc {
    private static final int WEIGHT_MIN = 1;
    private static final int WEIGHT_MAX = Integer.MAX_VALUE;
    private static final int WEIGHT_DEFAULT = WEIGHT_MIN;
    private Place place;
    private Transition transition;
    private int weight;

    public Arc(Place place, Transition transition) {
        this(place, transition, WEIGHT_DEFAULT);
    }

    public Arc(Place place, Transition transition, int weight) {
        setPlace(place);
        setTransition(transition);
        setWeight(weight);
    }

    public int getWeight() {
        return weight;
    }

    protected void setWeight(int weight) {
        Preconditions.checkArgument(weight >= WEIGHT_MIN, "Arc weight has to be greater or equal to 1.");
        this.weight = weight;
    }

    public Place getPlace() {
        return place;
    }

    protected void setPlace(Place place) {
        Preconditions.checkNotNull(place, "Place has to be set.");
        this.place = place;
    }

    public Transition getTransition() {
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
        if (!place.equals(arc.place)) return false;
        if (!transition.equals(arc.transition)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = place.hashCode();
        result = 31 * result + transition.hashCode();
        result = 31 * result + weight;
        return result;
    }

    public static class Builder {
        private Arc baseArc;
        private PetriNetVertex to;
        private PetriNetVertex from;
        private Integer weight;

        public Builder fromArc(Arc arc) {
            baseArc = arc;
            weight = arc.getWeight();
            if (arc instanceof PlaceToTransitionArc) {
                from = arc.getPlace();
                to = arc.getTransition();
            } else if (arc instanceof TransitionToPlaceArc) {
                from = arc.getTransition();
                to = arc.getPlace();
            } else {
                throw new IllegalArgumentException("Provided arc is of unsupported type: " + arc);
            }
            return this;
        }

        public Builder from(PetriNetVertex vertex) {
            from = vertex;
            return this;
        }

        public Builder to(PetriNetVertex vertex) {
            to = vertex;
            return this;
        }

        public Builder withWeight(int weight) {
            this.weight = weight;
            return this;
        }

        public Arc build() {
            if (from instanceof Place && to instanceof Transition) {
                return weight == null
                        ? new PlaceToTransitionArc((Place) from, (Transition) to)
                        : new PlaceToTransitionArc((Place) from, (Transition) to, weight);
            } else if (from instanceof Transition && to instanceof Place) {
                return weight == null
                        ? new TransitionToPlaceArc((Transition) from, (Place) to)
                        : new TransitionToPlaceArc((Transition) from, (Place) to, weight);
            }
            throw new IllegalStateException("Arc cannot be created with this settings.");
        }

        public Arc modify() {
            if (weight != null) {
                baseArc.setWeight(weight);
            }
            if (from instanceof Place && to instanceof Transition) {
                baseArc.setPlace((Place) from);
                baseArc.setTransition((Transition) to);
            } else if (from instanceof Transition && to instanceof Place) {
                baseArc.setTransition((Transition) from);
                baseArc.setPlace((Place) to);
            } else {
                throw new IllegalStateException("Arc cannot be modified with this settings.");
            }
            return baseArc;
        }
    }
}
