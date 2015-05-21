package pl.edu.agh.eis.petrilab.api;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import pl.edu.agh.eis.petrilab.model.PetriNet;
import pl.edu.agh.eis.petrilab.model2.matrix.PetriNetMatrix;

/**
 * Created by PW on 26-04-2015.
 */
public class CoverabilityGraph {

    public DirectedSparseGraph Generate(PetriNetMatrix net) {
        DirectedSparseGraph<int[], String> graph = new DirectedSparseGraph<>();

        int[] newVertex = net.getMarkingVector();
        if(net.getActiveTransitions(newVertex).isEmpty()) {
            graph.addVertex(newVertex);
            return graph;
        }

        // FluentIterable.from(Arrays.asList(net.getMarkingVector())).transform(Functions.toStringFunction()).toArray(String.class);
        //smieci
        //
        //graph.addVertex(net3.getMarking());
        //graph.addEdge("T1", marking, net3.getMarking());
        return graph;
    }
//wyswietlanie nieskonczonosci - to jest na razie problem

}
