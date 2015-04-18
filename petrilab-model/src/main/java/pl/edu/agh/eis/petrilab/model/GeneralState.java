package pl.edu.agh.eis.petrilab.model;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.util.Collection;

/**
 * Name: GeneralState
 * Description: GeneralState
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class GeneralState implements State {
    private final Collection<TransitionToStateArc> inArcs = Sets.newHashSet();
    private final Collection<StateToTransitionArc> outArcs = Sets.newHashSet();
    private String name;
    private int marking;

    @Override
    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    @Override
    public int getMarking() {
        return marking;
    }

    @Override
    public void decreaseMarking(int difference) {
        Preconditions.checkArgument(marking >= difference, "Cannot decrease marking below 0.");
        marking -= difference;
    }

    @Override
    public void increaseMarking(int difference) {
        marking += difference;
    }

    @Override
    public Collection<TransitionToStateArc> getInArcs() {
        return inArcs;
    }

    @Override
    public Collection<StateToTransitionArc> getOutArcs() {
        return outArcs;
    }

    protected void addInArc(TransitionToStateArc arc) {
        inArcs.add(arc);
    }

    protected void addOutArc(StateToTransitionArc arc) {
        outArcs.add(arc);
    }

    protected void removeInArc(TransitionToStateArc arc) {
        inArcs.remove(arc);
    }

    protected void removeOutArc(StateToTransitionArc arc) {
        outArcs.remove(arc);
    }
}
