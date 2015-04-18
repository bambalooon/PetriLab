package pl.edu.agh.eis.petrilab.model;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.util.Collection;

/**
 * Name: PTPlace
 * Description: PTPlace
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class PTPlace extends GeneralPlace {
    private int capacity = Integer.MAX_VALUE;

    public PTPlace(String name) {
        super(name);
    }

    public int getCapacity() {
        return capacity;
    }

    protected void setCapacity(int capacity) {
        Preconditions.checkArgument(capacity > 0, "Capacity has to be greater than 0.");
        this.capacity = capacity;
    }

    public static class Builder {
        private PTPlace basePlace;
        private String name;
        private Integer marking;
        private Integer capacity;
        private Collection<TransitionToPlaceArc> inArcs = Sets.newHashSet();
        private Collection<PlaceToTransitionArc> outArcs = Sets.newHashSet();

        public Builder fromPlace(PTPlace place) {
            basePlace = place;
            name = place.getName();
            marking = place.getMarking();
            capacity = place.getCapacity();
            inArcs.addAll(place.getInArcs());
            outArcs.addAll(place.getOutArcs());
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withMarking(int marking) {
            this.marking = marking;
            return this;
        }

        public Builder withCapacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder withArc(Arc arc) {
            if (arc instanceof TransitionToPlaceArc) {
                inArcs.add((TransitionToPlaceArc) arc);
            } else if (arc instanceof PlaceToTransitionArc) {
                outArcs.add((PlaceToTransitionArc) arc);
            } else {
                throw new IllegalArgumentException("Provided arc is of unsupported type: " + arc);
            }
            return this;
        }

        public PTPlace build() {
            PTPlace place = new PTPlace(name);
            if (marking != null) {
                place.setMarking(marking);
            }
            if (capacity != null) {
                place.setCapacity(capacity);
            }
            place.setInArcs(inArcs);
            place.setOutArcs(outArcs);
            return place;
        }

        public PTPlace modify() {
            if (name != null) {
                basePlace.setName(name);
            }
            if (marking != null) {
                basePlace.setMarking(marking);
            }
            if (capacity != null) {
                basePlace.setCapacity(capacity);
            }
            basePlace.setInArcs(inArcs);
            basePlace.setOutArcs(outArcs);
            return basePlace;
        }
    }
}