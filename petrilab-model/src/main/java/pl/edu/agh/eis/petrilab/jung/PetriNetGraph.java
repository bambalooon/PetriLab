package pl.edu.agh.eis.petrilab.jung;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.Place;
import pl.edu.agh.eis.petrilab.model2.Transition;

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
}
