package pl.edu.agh.eis.petrilab.model;

import com.google.common.base.Preconditions;

import java.util.Collection;
import java.util.Collections;

/**
 * Name: GeneralPlace
 * Description: GeneralPlace
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class GeneralPlace implements Place {
    private Collection<TransitionToPlaceArc> inArcs = Collections.emptySet();
    private Collection<PlaceToTransitionArc> outArcs = Collections.emptySet();
    private String name;
    private int marking;

    public GeneralPlace(String name) {
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
    public Collection<TransitionToPlaceArc> getInArcs() {
        return inArcs;
    }

    @Override
    public Collection<PlaceToTransitionArc> getOutArcs() {
        return outArcs;
    }

    protected void setInArcs(Collection<TransitionToPlaceArc> arcs) {
        Preconditions.checkNotNull(arcs);
        inArcs = arcs;
    }

    protected void setOutArcs(Collection<PlaceToTransitionArc> arcs) {
        Preconditions.checkNotNull(arcs);
        outArcs = arcs;
    }
}
