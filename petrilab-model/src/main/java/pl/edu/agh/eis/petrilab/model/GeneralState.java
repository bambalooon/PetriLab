package pl.edu.agh.eis.petrilab.model;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Collections;

/**
 * Name: GeneralState
 * Description: GeneralState
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class GeneralState implements State {
    private Collection<TransitionToStateArc> inArcs = Collections.emptySet();
    private Collection<StateToTransitionArc> outArcs = Collections.emptySet();
    private String name;
    private int marking;

    public GeneralState(String name) {
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
    public int getMarking() {
        return marking;
    }

    protected void setMarking(int marking) {
        this.marking = marking;
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

    protected void setInArcs(Collection<TransitionToStateArc> arcs) {
        Preconditions.checkNotNull(arcs);
        inArcs = arcs;
    }

    protected void setOutArcs(Collection<StateToTransitionArc> arcs) {
        Preconditions.checkNotNull(arcs);
        outArcs = arcs;
    }
}
