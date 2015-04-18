package pl.edu.agh.eis.petrilab.model;

import java.util.Collection;

/**
 * Name: Transition
 * Description: Transition
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public interface Transition {
    String getName();
    void setName(String name);
    Collection<ArcToTransition> getInArcs();
    Collection<ArcToState> getOutArcs();
    void addInArc(ArcToTransition arc);
    void addOutArc(ArcToState arc);
    void removeInArc(ArcToTransition arc);
    void removeOutArc(ArcToState arc);
}
