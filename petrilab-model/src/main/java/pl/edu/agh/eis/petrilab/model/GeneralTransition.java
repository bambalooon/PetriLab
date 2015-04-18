package pl.edu.agh.eis.petrilab.model;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Collections;

/**
 * Name: GeneralTransition
 * Description: GeneralTransition
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class GeneralTransition implements Transition {
    private Collection<StateToTransitionArc> inArcs = Collections.emptySet();
    private Collection<TransitionToStateArc> outArcs = Collections.emptySet();
    private String name;

    public GeneralTransition(String name) {
        setName(name);
    }

    @Override
    public String getName() {
        return name;
    }

    protected void setName(String name) {
        Preconditions.checkNotNull(name);
        this.name = name;
    }

    @Override
    public Collection<StateToTransitionArc> getInArcs() {
        return inArcs;
    }

    @Override
    public Collection<TransitionToStateArc> getOutArcs() {
        return outArcs;
    }

    protected void setInArcs(Collection<StateToTransitionArc> arcs) {
        Preconditions.checkNotNull(arcs);
        inArcs = arcs;
    }

    protected void setOutArcs(Collection<TransitionToStateArc> arcs) {
        Preconditions.checkNotNull(arcs);
        outArcs = arcs;
    }

    public static class Builder {
        private GeneralTransition baseTransition;
        private String name;
        private Collection<StateToTransitionArc> inArcs = Sets.newHashSet();
        private Collection<TransitionToStateArc> outArcs = Sets.newHashSet();

        public Builder fromTransition(GeneralTransition transition) {
            baseTransition = transition;
            name = transition.getName();
            inArcs.addAll(transition.getInArcs());
            outArcs.addAll(transition.getOutArcs());
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withArc(Arc arc) {
            if (arc instanceof StateToTransitionArc) {
                inArcs.add((StateToTransitionArc) arc);
            } else if (arc instanceof TransitionToStateArc) {
                outArcs.add((TransitionToStateArc) arc);
            } else {
                throw new IllegalArgumentException("Provided arc is of unsupported type: " + arc);
            }
            return this;
        }

        public GeneralTransition build() {
            GeneralTransition transition = new GeneralTransition(name);
            transition.setInArcs(inArcs);
            transition.setOutArcs(outArcs);
            return transition;
        }

        public GeneralTransition modify() {
            if (name != null) {
                baseTransition.setName(name);
            }
            baseTransition.setInArcs(inArcs);
            baseTransition.setOutArcs(outArcs);
            return baseTransition;
        }
    }
}
