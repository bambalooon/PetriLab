package pl.edu.agh.eis.petrilab.model2.jung;

import com.google.common.collect.Lists;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.Place;
import pl.edu.agh.eis.petrilab.model2.Transition;
import pl.edu.agh.eis.petrilab.model2.matrix.PetriNetMatrix;

import java.util.List;

/**
 * Name: PetriNetGraph
 * Description: PetriNetGraph
 * Date: 2015-05-16
 * Created by BamBalooon
 */
public class PetriNetGraph extends DirectedSparseGraph<PetriNetVertex, Arc> {
    @Override
    public boolean addEdge(Arc edge, Pair<? extends PetriNetVertex> endpoints, EdgeType edgeType) {
        if ((endpoints.getFirst() instanceof Transition && endpoints.getSecond() instanceof Transition)
                || (endpoints.getFirst() instanceof Place && endpoints.getSecond() instanceof Place)) {
            return false;
        }
        return super.addEdge(edge, endpoints, edgeType);
    }

    public static PetriNetGraph fromMatrix(PetriNetMatrix matrix) {
        PetriNetGraph graph = new PetriNetGraph();

        String[] placesNames = matrix.getPlacesNames();
        String[] transitionsNames = matrix.getTransitionsNames();
        int[] markingVector = matrix.getMarkingVector();
        int[] capacityVector = matrix.getCapacityVector();

        List<Place> places = Lists.newArrayList();
        for (int i = 0; i < placesNames.length; i++) {
            Place place = new Place(placesNames[i], markingVector[i], capacityVector[i]);
            places.add(place);
            graph.addVertex(place);
        }
        List<Transition> transitions = Lists.newArrayList();
        for (String transitionsName : transitionsNames) {
            Transition transition = new Transition(transitionsName);
            transitions.add(transition);
            graph.addVertex(transition);
        }

        int[][] inMatrix = matrix.getInMatrix();
        int[][] outMatrix = matrix.getOutMatrix();

        for (int i = 0; i < transitions.size(); i++) {
            for (int j = 0; j < places.size(); j++) {
                int inWeight = inMatrix[i][j];
                int outWeight = outMatrix[i][j];
                if (inWeight > 0) {
                    Arc inArc = new Arc(inWeight);
                    graph.addEdge(inArc, transitions.get(i), places.get(j));
                }
                if (outWeight > 0) {
                    Arc outArc = new Arc(outWeight);
                    graph.addEdge(outArc, places.get(j), transitions.get(i));
                }
            }
        }

        return graph;
    }
}
