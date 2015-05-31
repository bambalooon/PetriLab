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

/**
 * Created by PW on 31-05-2015.
 */
public class Properties {

    public static boolean isPotentiallyAlive (Transition transition, DirectedSparseGraph<Marking, Transition> coverabilityGraph) {
        if(coverabilityGraph.containsEdge(transition))
            return true;
        else return false;
    }

    public static boolean isNetPotentiallyAlive (DirectedSparseGraph<Marking, Transition> coverabilityGraph, PetriNetMatrix matrix) {
        int transitionNumber = matrix.getInMatrix().length;
        Transition currentTransition;
        for(int i = 0; i < transitionNumber; i++) {
            currentTransition = new Transition(matrix.getTransitionsNames()[i]);
            if(!isPotentiallyAlive(currentTransition, coverabilityGraph))
                return false;
        }
        return true;
    }

    public static boolean isNetConservative (DirectedSparseGraph<Marking, Transition> reachabilityGraph) {
        Collection<Marking> markings = reachabilityGraph.getVertices();
        Iterator<Marking> itr = markings.iterator();
        double markingSum = sum(itr.next());
        while(itr.hasNext()) {
            if(sum(itr.next()) != markingSum)
                return false;
        }
        return true;
    }

    public static double isKBounded (Place place, DirectedSparseGraph<Marking, Transition> coverabilityGraph) {
        //zostaje zrobienie ograniczonosci miejsca i sieci + ten wektor wag
        return 1.0;
    }

    public static boolean isSafe(Place place, DirectedSparseGraph<Marking, Transition> reachabilityGraph, PetriNetMatrix matrix) {
        int placeIndex = Arrays.asList(matrix.getPlacesNames()).indexOf(place.getName());
        Collection<Marking> markings = reachabilityGraph.getVertices();
        Iterator<Marking> itr = markings.iterator();
        while(itr.hasNext()) {
            if(itr.next().getValue()[placeIndex] > 1.0)
                return false;
        }
        return true;
    }

    public static boolean isNetSafe(DirectedSparseGraph<Marking, Transition> coverabilityGraph, PetriNetMatrix matrix) {
        int placesNumber = matrix.getInMatrix()[0].length;
        Place currentPlace;
        for(int i = 0; i < placesNumber; i++) {
            currentPlace = new Place(matrix.getPlacesNames()[i]);
            if(!isSafe(currentPlace, coverabilityGraph, matrix))
            return false;
        }
        return true;
    }

    public static boolean isReversible(DirectedSparseGraph<Marking, Transition> coverabilityGraph, PetriNetMatrix matrix) {
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
