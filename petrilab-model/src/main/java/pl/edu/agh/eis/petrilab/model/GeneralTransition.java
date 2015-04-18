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
    private final Collection<ArcToTransition> inArcs = Sets.newHashSet();
    private final Collection<ArcToState> outArcs = Sets.newHashSet();
    private String name;

    @Override
    public String getName() {
        return name;
    }

    protected void setName(String name) {
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

    protected void addInArc(ArcToTransition arc) {
        inArcs.add(arc);
    }

    protected void addOutArc(ArcToState arc) {
        outArcs.add(arc);
    }

    protected void removeInArc(ArcToTransition arc) {
        inArcs.remove(arc);
    }

    protected void removeOutArc(ArcToState arc) {
        outArcs.remove(arc);
    }
}
