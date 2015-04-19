package pl.edu.agh.eis.petrilab.gui.jung;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import pl.edu.agh.eis.petrilab.model.Arc;
import pl.edu.agh.eis.petrilab.model.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model.PlaceToTransitionArc;
import pl.edu.agh.eis.petrilab.model.TransitionToPlaceArc;

/**
 * Name: JungPetriNet
 * Description: JungPetriNet
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class PetriNetManager {
    private DirectedSparseGraph<PetriNetVertex, Arc> graph =
            new DirectedSparseGraph<>();

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
            graph.addEdge(arc, arc.getTransition(), arc.getPlace());
        } else if (arc instanceof PlaceToTransitionArc) {
            graph.addEdge(arc, arc.getPlace(), arc.getTransition());
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
