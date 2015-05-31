package pl.edu.agh.eis.petrilab.api;

import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import pl.edu.agh.eis.petrilab.model2.Place;
import pl.edu.agh.eis.petrilab.model2.Transition;
import pl.edu.agh.eis.petrilab.model2.matrix.Marking;
import pl.edu.agh.eis.petrilab.model2.matrix.PetriNetMatrix;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import static pl.edu.agh.eis.petrilab.model2.matrix.Marking.sum;
import static pl.edu.agh.eis.petrilab.model2.matrix.Marking.toDoubleArray;

/**
 * Created by PW on 31-05-2015.
 * cool
 */
public class Properties {

    public static boolean isTransitionAlive (Transition transition, PetriNetMatrix matrix) {
        int transitionIndex = Arrays.asList(matrix.getTransitionsNames()).indexOf(transition.getName());
        return matrix.isTransitionActive(true, transitionIndex, toDoubleArray(matrix.getMarkingVector()));
    }

    public static boolean isNetAlive (PetriNetMatrix matrix) {
        int transitionNumber = matrix.getInMatrix().length;
        for(int i = 0; i < transitionNumber; i++) {
            if(!matrix.isTransitionActive(true, i, toDoubleArray(matrix.getMarkingVector())))
                return false;
        }
        return true;
    }

    public static boolean isTransitionPotentiallyAlive (Transition transition, DirectedSparseGraph<Marking, Transition> coverabilityGraph) {
        return coverabilityGraph.containsEdge(transition);
    }

    public static boolean isNetPotentiallyAlive (DirectedSparseGraph<Marking, Transition> coverabilityGraph, PetriNetMatrix matrix) {
        int transitionNumber = matrix.getInMatrix().length;
        Transition currentTransition;
        for(int i = 0; i < transitionNumber; i++) {
            currentTransition = new Transition(matrix.getTransitionsNames()[i]);
            if(!isTransitionPotentiallyAlive(currentTransition, coverabilityGraph))
                return false;
        }
        return true;
    }

    public static boolean isNetConservative (DirectedSparseGraph<Marking, Transition> reachabilityGraph) {
        Collection<Marking> markings = reachabilityGraph.getVertices();
        Iterator<Marking> itr = markings.iterator();
        Double markingSum = sum(itr.next());
        while(itr.hasNext()) {
            if(sum(itr.next()) != markingSum)
                return false;
        }
        return true;
    }

    public static Double isPlaceKBounded (Place place, DirectedSparseGraph<Marking, Transition> coverabilityGraph, PetriNetMatrix matrix) {
        int placeIndex = Arrays.asList(matrix.getPlacesNames()).indexOf(place.getName());
        Collection<Marking> markings = coverabilityGraph.getVertices();
        Iterator<Marking> itr = markings.iterator();
        Double kBound = itr.next().getValue()[placeIndex];
        while(itr.hasNext()) {
            if(itr.next().getValue()[placeIndex] > kBound)
                kBound = itr.next().getValue()[placeIndex];
        }
        if(kBound == Double.POSITIVE_INFINITY)
            return -1.0;
        return kBound;
    }

    public static Double isNetKBounded (DirectedSparseGraph<Marking, Transition> coverabilityGraph, PetriNetMatrix matrix) {
        int placesNumber = matrix.getInMatrix()[0].length;
        Place currentPlace;
        Double kBound = 0.0;
        for(int i = 0; i < placesNumber; i++) {
            currentPlace = new Place(matrix.getPlacesNames()[i]);
            if(isPlaceKBounded(currentPlace, coverabilityGraph, matrix) == -1.0)
                return -1.0;
            if(isPlaceKBounded(currentPlace, coverabilityGraph, matrix) > kBound)
                kBound = isPlaceKBounded(currentPlace, coverabilityGraph, matrix);
        }
        return kBound;
    }

    public static boolean isPlaceSafe(Place place, DirectedSparseGraph<Marking, Transition> reachabilityGraph, PetriNetMatrix matrix) {
        int placeIndex = Arrays.asList(matrix.getPlacesNames()).indexOf(place.getName());
        Collection<Marking> markings = reachabilityGraph.getVertices();
        for (Marking marking : markings) {
            if (marking.getValue()[placeIndex] > 1.0)
                return false;
        }
        return true;
    }

    public static boolean isNetSafe(DirectedSparseGraph<Marking, Transition> reachabilityGraph, PetriNetMatrix matrix) {
        int placesNumber = matrix.getInMatrix()[0].length;
        Place currentPlace;
        for(int i = 0; i < placesNumber; i++) {
            currentPlace = new Place(matrix.getPlacesNames()[i]);
            if(!isPlaceSafe(currentPlace, reachabilityGraph, matrix))
            return false;
        }
        return true;
    }

    public static boolean isNetReversible(DirectedSparseGraph<Marking, Transition> coverabilityGraph, PetriNetMatrix matrix) {
        Marking initialMarking = new Marking(matrix.getMarkingVector());
        Collection<Marking> markings = coverabilityGraph.getVertices();
        Iterator<Marking> itr = markings.iterator();
        UnweightedShortestPath<Marking, Transition> shortestPath = new UnweightedShortestPath<>(coverabilityGraph);
        while(itr.hasNext()) {
            if(itr.next() != initialMarking && shortestPath.getDistance(itr.next(),initialMarking) != null)
                return false;
        }
        return true;
    }
}
