package pl.edu.agh.eis.petrilab.model2.jung;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.Place;
import pl.edu.agh.eis.petrilab.model2.Transition;
import pl.edu.agh.eis.petrilab.model2.matrix.PetriNetMatrix;

import java.util.Collection;
import java.util.Comparator;
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

    public boolean isPetriNetCorrect() {
        return !FluentIterable.from(getTransitions())
                .anyMatch(new Predicate<Transition>() {
                    @Override
                    public boolean apply(Transition transition) {
                        return getInEdges(transition).isEmpty() || getOutEdges(transition).isEmpty();
                    }
                });
    }

    public boolean arePlacesNamesUnique() {
        return !FluentIterable.from(getPlaces())
                .anyMatch(new Predicate<Place>() {
                    @Override
                    public boolean apply(final Place place) {
                        return FluentIterable.from(getPlaces())
                                .anyMatch(new Predicate<Place>() {
                                    @Override
                                    public boolean apply(Place otherPlace) {
                                        return place != otherPlace && place.getName().equals(otherPlace.getName());
                                    }
                                });
                    }
                });
    }

    public boolean areTransitionsNamesUnique() {
        return !FluentIterable.from(getTransitions())
                .anyMatch(new Predicate<Transition>() {
                    @Override
                    public boolean apply(final Transition transition) {
                        return FluentIterable.from(getTransitions())
                                .anyMatch(new Predicate<Transition>() {
                                    @Override
                                    public boolean apply(Transition otherTransition) {
                                        return transition != otherTransition
                                                && transition.getName().equals(otherTransition.getName());
                                    }
                                });
                    }
                });
    }

    public List<Place> getPlaces() {
        return FluentIterable.from(getVertices())
                .filter(Place.class)
                .toList();
    }

    public List<Transition> getTransitions() {
        return FluentIterable.from(getVertices())
                .filter(Transition.class)
                .toList();
    }

    public boolean isTransitionActive(Transition transition) {
        Collection<Arc> inArcs = getInEdges(transition);
        Collection<Arc> outArcs = getOutEdges(transition);
        for (Arc inArc : inArcs) {
            Place inPlace = (Place) getOpposite(transition, inArc);
            if (inPlace.getMarking() < inArc.getWeight()) {
                return false;
            }
        }
        for (Arc outArc : outArcs) {
            Place outPlace = (Place) getOpposite(transition, outArc);
            if ((outPlace.getMarking() + outArc.getWeight()) > outPlace.getCapacity()) {
                return false;
            }
        }
        return true;
    }

    public void fireTransition(Transition transition) {
        if (isTransitionActive(transition)) {
            Collection<Arc> inArcs = getInEdges(transition);
            Collection<Arc> outArcs = getOutEdges(transition);
            for (Arc inArc : inArcs) {
                Place inPlace = (Place) getOpposite(transition, inArc);
                inPlace.setMarking(inPlace.getMarking() - inArc.getWeight());
            }
            for (Arc outArc : outArcs) {
                Place outPlace = (Place) getOpposite(transition, outArc);
                outPlace.setMarking(outPlace.getMarking() + outArc.getWeight());
            }
        }
    }

    public List<Transition> getActiveTransitions() {
        return FluentIterable.from(getTransitions())
                .filter(new Predicate<Transition>() {
                    @Override
                    public boolean apply(Transition transition) {
                        return isTransitionActive(transition);
                    }
                }).toList();
    }

    public Integer[] getMarking() {
        final List<Place> sortedPlaces = FluentIterable
                .from(getPlaces())
                .toSortedList(new Comparator<Place>() {
                    @Override
                    public int compare(Place o1, Place o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
        return FluentIterable.from(sortedPlaces)
                .transform(new Function<Place, Integer>() {
                    @Override
                    public Integer apply(Place place) {
                        return place.getMarking();
                    }
                }).toArray(Integer.class);
    }

    public void setMarking(Integer[] marking) {
        List<Place> sortedPlaces = FluentIterable
                .from(getPlaces())
                .toSortedList(new Comparator<Place>() {
                    @Override
                    public int compare(Place o1, Place o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
        int i = 0;
        for (Place place : sortedPlaces) {
            place.setMarking(marking[i++]);
        }
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
