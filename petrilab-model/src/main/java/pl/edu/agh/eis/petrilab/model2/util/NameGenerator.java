package pl.edu.agh.eis.petrilab.model2.util;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.Place;
import pl.edu.agh.eis.petrilab.model2.Transition;
import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraph;

import java.util.List;

/**
 * Name: NameGenerator
 * Description: NameGenerator
 * Date: 2015-05-22
 * Created by BamBalooon
 */
public class NameGenerator {
    private static final String PLACE_NAME_FORMAT = "P%02d";
    private static final String TRANSITION_NAME_FORMAT = "T%02d";
    private final PetriNetGraph graph;


    public NameGenerator(PetriNetGraph graph) {
        this.graph = graph;
    }

    public String getPlaceName() {
        List<Place> places = graph.getPlaces();
        int placeIndex = 1;
        String placeName;
        do {
            placeName = String.format(PLACE_NAME_FORMAT, placeIndex++);
        } while (FluentIterable.from(places).anyMatch(new VertexNameEqualPredicate(placeName)));
        return placeName;
    }

    public String getTransitionName() {
        List<Transition> transitions = graph.getTransitions();
        int transitionIndex = 1;
        String transitionName;
        do {
            transitionName = String.format(TRANSITION_NAME_FORMAT, transitionIndex++);
        } while (FluentIterable.from(transitions).anyMatch(new VertexNameEqualPredicate(transitionName)));
        return transitionName;
    }

    private class VertexNameEqualPredicate implements Predicate<PetriNetVertex> {
        private final String name;

        private VertexNameEqualPredicate(String name) {
            this.name = name;
        }

        @Override
        public boolean apply(PetriNetVertex vertex) {
            return name.equals(vertex.getName());
        }
    }
}
