package pl.edu.agh.eis.petrilab.model;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import pl.edu.agh.eis.petrilab.model.util.NameGenerator;

import java.util.Collection;
import java.util.Collections;

/**
 * Name: GeneralTransition
 * Description: GeneralTransition
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class GeneralTransition implements Transition {
    private Collection<PlaceToTransitionArc> inArcs = Collections.emptySet();
    private Collection<TransitionToPlaceArc> outArcs = Collections.emptySet();
    private String name;

    public GeneralTransition(String name) {
        setName(name);
    }

    public GeneralTransition() {
        this(NameGenerator.TRANSITION.getName());
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
    public Collection<PlaceToTransitionArc> getInArcs() {
        return inArcs;
    }

    @Override
    public Collection<TransitionToPlaceArc> getOutArcs() {
        return outArcs;
    }

    protected void setInArcs(Collection<PlaceToTransitionArc> arcs) {
        Preconditions.checkNotNull(arcs);
        inArcs = arcs;
    }

    protected void setOutArcs(Collection<TransitionToPlaceArc> arcs) {
        Preconditions.checkNotNull(arcs);
        outArcs = arcs;
    }

    public static class Builder {
        private GeneralTransition baseTransition;
        private String name;
        private Collection<PlaceToTransitionArc> inArcs = Sets.newHashSet();
        private Collection<TransitionToPlaceArc> outArcs = Sets.newHashSet();

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
            if (arc instanceof PlaceToTransitionArc) {
                inArcs.add((PlaceToTransitionArc) arc);
            } else if (arc instanceof TransitionToPlaceArc) {
                outArcs.add((TransitionToPlaceArc) arc);
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
