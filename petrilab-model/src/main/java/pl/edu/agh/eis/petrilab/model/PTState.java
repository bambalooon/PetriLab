package pl.edu.agh.eis.petrilab.model;

import com.google.common.base.Preconditions;

import java.util.Collection;

/**
 * Name: PTState
 * Description: PTState
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class PTState extends GeneralState {
    private int capacity = Integer.MAX_VALUE;

    public PTState(String name) {
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
        private PTState baseState;
        private String name;
        private Integer marking;
        private Integer capacity;
        private Collection<TransitionToStateArc> inArcs;
        private Collection<StateToTransitionArc> outArcs;

        public Builder fromState(PTState state) {
            baseState = state;
            name = state.getName();
            marking = state.getMarking();
            capacity = state.getCapacity();
            inArcs.addAll(state.getInArcs());
            outArcs.addAll(state.getOutArcs());
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
            if (arc instanceof TransitionToStateArc) {
                inArcs.add((TransitionToStateArc) arc);
            } else if (arc instanceof StateToTransitionArc) {
                outArcs.add((StateToTransitionArc) arc);
            } else {
                throw new IllegalArgumentException("Provided arc is of unsupported type: " + arc);
            }
            return this;
        }

        public PTState build() {
            PTState state = new PTState(name);
            state.setMarking(marking);
            state.setCapacity(capacity);
            state.setInArcs(inArcs);
            state.setOutArcs(outArcs);
            return state;
        }

        public PTState modify() {
            if (name != null) {
                baseState.setName(name);
            }
            if (marking != null) {
                baseState.setMarking(marking);
            }
            if (capacity != null) {
                baseState.setCapacity(capacity);
            }
            baseState.setInArcs(inArcs);
            baseState.setOutArcs(outArcs);
            return baseState;
        }

    }
}
