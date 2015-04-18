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
    int getMarking();
    void decreaseMarking(int difference);
    void increaseMarking(int difference);
    Collection<ArcToState> getInArcs();
    Collection<ArcToTransition> getOutArcs();
}
