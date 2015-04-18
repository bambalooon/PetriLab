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
        if (arc instanceof TransitionToStateArc) {
            TransitionToStateArc definedArc = (TransitionToStateArc) arc;
            graph.addEdge(definedArc, definedArc.getStartTransition(), definedArc.getEndState());
        } else if (arc instanceof StateToTransitionArc) {
            StateToTransitionArc definedArc = (StateToTransitionArc) arc;
            graph.addEdge(definedArc, definedArc.getStartState(), definedArc.getEndTransition());
        } else {
            throw new IllegalArgumentException("Provided arc is of unsupported type: " + arc);
        }
    }

    public void removeEdge(Arc arc) {
        if (arc instanceof TransitionToStateArc || arc instanceof StateToTransitionArc) {
            graph.removeEdge(arc);
        } else {
            throw new IllegalArgumentException("Provided arc is of unsupported type: " + arc);
        }

    }
}
