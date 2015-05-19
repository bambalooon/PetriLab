package pl.edu.agh.eis.petrilab.api;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import pl.edu.agh.eis.petrilab.model.PetriNet;
import pl.edu.agh.eis.petrilab.model2.matrix.PetriNetMatrix;

/**
 * Created by PW on 26-04-2015.
 */
public class CoverabilityGraph {
    private static int jeden = 1;

    public DirectedSparseGraph Generate(PetriNetMatrix net) {
        DirectedSparseGraph<String[], String> graph = new DirectedSparseGraph<>();
        int[] marking = net.getMarkingVector();


        //smieci
        //graph.addVertex(marking);
        //graph.addVertex(net3.getMarking());
        //graph.addEdge("T1", marking, net3.getMarking());
        return graph;
    }
//wyswietlanie nieskonczonosci


}
