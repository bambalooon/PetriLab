package pl.edu.agh.eis.petrilab.model;

import com.google.common.collect.Sets;

import java.util.Collection;

/**
 * Name: GeneralTransition
 * Description: GeneralTransition
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class GeneralTransition implements Transition {
    private final Collection<StateToTransitionArc> inArcs = Sets.newHashSet();
    private final Collection<TransitionToStateArc> outArcs = Sets.newHashSet();
    private String name;

    @Override
    public String getName() {
        return name;
    }

    protected void setName(String name) {
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

    protected void addInArc(StateToTransitionArc arc) {
        inArcs.add(arc);
    }

    protected void addOutArc(TransitionToStateArc arc) {
        outArcs.add(arc);
    }

    protected void removeInArc(StateToTransitionArc arc) {
        inArcs.remove(arc);
    }

    protected void removeOutArc(TransitionToStateArc arc) {
        outArcs.remove(arc);
    }
}
