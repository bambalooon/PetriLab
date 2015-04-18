package pl.edu.agh.eis.petrilab.gui.jung;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import pl.edu.agh.eis.petrilab.model.*;

/**
 * Name: JungPetriNet
 * Description: JungPetriNet
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class PetriNet {
    private DirectedSparseGraph<PetriNetVertex, Arc> graph =
            new DirectedSparseGraph<PetriNetVertex, Arc>();

    public DirectedSparseGraph<PetriNetVertex, Arc> getGraph() {
        return graph;
    }

    public void addVertex(PetriNetVertex vertex) {
        graph.addVertex(vertex);
    }

    public void removeVertex(PetriNetVertex vertex) {
        graph.removeVertex(vertex);
    }

    public void addEdge(Arc arc) {
        if (arc instanceof TransitionToPlaceArc) {
            TransitionToPlaceArc definedArc = (TransitionToPlaceArc) arc;
            graph.addEdge(definedArc, definedArc.getStartTransition(), definedArc.getEndState());
        } else if (arc instanceof PlaceToTransitionArc) {
            PlaceToTransitionArc definedArc = (PlaceToTransitionArc) arc;
            graph.addEdge(definedArc, definedArc.getStartState(), definedArc.getEndTransition());
        } else {
            throw new IllegalArgumentException("Provided arc is of unsupported type: " + arc);
        }
    }

    public void removeEdge(Arc arc) {
        if (arc instanceof TransitionToPlaceArc || arc instanceof PlaceToTransitionArc) {
            graph.removeEdge(arc);
        } else {
            throw new IllegalArgumentException("Provided arc is of unsupported type: " + arc);
        }

    }
}
