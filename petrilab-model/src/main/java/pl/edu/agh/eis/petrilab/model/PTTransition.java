package pl.edu.agh.eis.petrilab.model;

import com.google.common.collect.Sets;

import java.util.Collection;

/**
 * Name: PTTransition
 * Description: PTTransition
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class PTTransition implements Transition {
    private final Collection<ArcToTransition> inArcs = Sets.newHashSet();
    private final Collection<ArcToState> outArcs = Sets.newHashSet();
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Collection<ArcToTransition> getInArcs() {
        return inArcs;
    }

    @Override
    public Collection<ArcToState> getOutArcs() {
        return outArcs;
    }

    @Override
    public void addInArc(ArcToTransition arc) {
        inArcs.add(arc);
    }

    @Override
    public void addOutArc(ArcToState arc) {
        outArcs.add(arc);
    }

    @Override
    public void removeInArc(ArcToTransition arc) {
        inArcs.remove(arc);
    }

    @Override
    public void removeOutArc(ArcToState arc) {
        outArcs.remove(arc);
    }
}
