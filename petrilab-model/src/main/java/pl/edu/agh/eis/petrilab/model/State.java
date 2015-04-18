package pl.edu.agh.eis.petrilab.model;

import java.util.Collection;

/**
 * Name: State
 * Description: State
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public interface State {
    String getName();
    void setName(String name);
    int getMarking();
    void decreaseMarking(int difference);
    void increaseMarking(int difference);
    Collection<ArcToState> getInArcs();
    Collection<ArcToTransition> getOutArcs();
    void addInArc(ArcToState arc);
    void addOutArc(ArcToTransition arc);
    void removeInArc(ArcToState arc);
    void removeOutArc(ArcToTransition arc);
}
