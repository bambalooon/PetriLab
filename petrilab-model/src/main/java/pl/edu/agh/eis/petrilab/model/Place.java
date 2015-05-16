package pl.edu.agh.eis.petrilab.model;

import java.util.Collection;

/**
 * Name: Place
 * Description: Place
 * Date: 2015-04-18
 * Created by BamBalooon
 */
@Deprecated
public interface Place extends PetriNetVertex {
    int getMarking();
    void decreaseMarking(int difference);
    void increaseMarking(int difference);
    Collection<TransitionToPlaceArc> getInArcs();
    Collection<PlaceToTransitionArc> getOutArcs();
}
