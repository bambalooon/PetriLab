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
    private final Collection<ArcToState> inArcs = Sets.newHashSet();
    private final Collection<ArcToTransition> outArcs = Sets.newHashSet();
    private String name;
    private int marking;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
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
    public Collection<ArcToState> getInArcs() {
        return inArcs;
    }

    @Override
    public Collection<ArcToTransition> getOutArcs() {
        return outArcs;
    }

    @Override
    public void addInArc(ArcToState arc) {
        inArcs.add(arc);
    }

    @Override
    public void addOutArc(ArcToTransition arc) {
        outArcs.add(arc);
    }

    @Override
    public void removeInArc(ArcToState arc) {
        inArcs.remove(arc);
    }

    @Override
    public void removeOutArc(ArcToTransition arc) {
        outArcs.remove(arc);
    }
}
