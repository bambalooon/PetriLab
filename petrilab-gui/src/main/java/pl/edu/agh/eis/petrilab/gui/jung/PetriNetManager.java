package pl.edu.agh.eis.petrilab.gui.jung;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import pl.edu.agh.eis.petrilab.model.*;

/**
 * Name: JungPetriNet
 * Description: JungPetriNet
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class PetriNetManager {
    private DirectedSparseGraph<PetriNetVertex, Arc> graph =
            new DirectedSparseGraph<>();
    private PetriNet petriNet;

    public PetriNetManager() {
        this(new PetriNet());
    }

    public PetriNetManager(PetriNet petriNet) {
        this.petriNet = petriNet;
        //TODO: create graph from provided Petri net
    }

    public DirectedSparseGraph<PetriNetVertex, Arc> getGraph() {
        return graph;
    }

    public PetriNet getPetriNet() {
        return petriNet;
    }

    public void addVertex(PetriNetVertex vertex) {
        if (vertex instanceof Place) {
            petriNet.addPlace((Place) vertex);
        } else if (vertex instanceof Transition) {
            petriNet.addTransition((Transition) vertex);
        } else {
            throw new IllegalArgumentException("Vertex of this type isn't supported: " + vertex);
        }
        graph.addVertex(vertex);
    }

    public void removeVertex(PetriNetVertex vertex) {
        //FIXME: remove vertex from PetriNet
        graph.removeVertex(vertex);
    }

    public void addEdge(Arc arc) {
        petriNet.addArc(arc);
        if (arc instanceof TransitionToPlaceArc) {
            graph.addEdge(arc, arc.getTransition(), arc.getPlace());
        } else if (arc instanceof PlaceToTransitionArc) {
            graph.addEdge(arc, arc.getPlace(), arc.getTransition());
        } else {
            throw new IllegalArgumentException("Provided arc is of unsupported type: " + arc);
        }
    }

    public void removeEdge(Arc arc) {
        //FIXME: remove arc from PetriNet
        if (arc instanceof TransitionToPlaceArc || arc instanceof PlaceToTransitionArc) {
            graph.removeEdge(arc);
        } else {
            throw new IllegalArgumentException("Provided arc is of unsupported type: " + arc);
        }
    }
}
