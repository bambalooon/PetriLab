package pl.edu.agh.eis.petrilab.model;

import java.util.Collection;

/**
 * Name: Transition
 * Description: Transition
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public interface Transition extends PetriNetVertex {
    Collection<StateToTransitionArc> getInArcs();
    Collection<TransitionToStateArc> getOutArcs();
}
