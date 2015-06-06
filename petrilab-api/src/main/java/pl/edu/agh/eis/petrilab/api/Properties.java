package pl.edu.agh.eis.petrilab.api;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.SingularValueDecomposition;
import pl.edu.agh.eis.petrilab.model2.Place;
import pl.edu.agh.eis.petrilab.model2.Transition;
import pl.edu.agh.eis.petrilab.model2.matrix.Marking;
import pl.edu.agh.eis.petrilab.model2.matrix.PetriNetMatrix;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

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

    public static boolean isTransitionPotentiallyAlive (final Transition transition, DirectedSparseGraph<Marking, Transition> coverabilityGraph) {
        return FluentIterable.from(coverabilityGraph.getEdges())
                .anyMatch(new Predicate<Transition>() {
                    @Override
                    public boolean apply(Transition graphTransition) {
                        return graphTransition.getName().equals(transition.getName());
                    }
                });
    }

    public static boolean isNetPotentiallyAlive (DirectedSparseGraph<Marking, Transition> coverabilityGraph, PetriNetMatrix matrix) {
        for(int i = 0; i < matrix.getTransitionsNames().length; i++) {
            Transition currentTransition = new Transition(matrix.getTransitionsNames()[i]);
            if(!isTransitionPotentiallyAlive(currentTransition, coverabilityGraph))
                return false;
        }
        return true;
    }

    public static Double getPlaceBoundary(int placeIndex,
                                          DirectedSparseGraph<Marking, Transition> coverabilityGraph,
                                          PetriNetMatrix matrix) {

        int placeCapacity = matrix.getCapacityVector()[placeIndex];
        Double placeBoundary = 0.0;
        for (Marking marking : coverabilityGraph.getVertices()) {
            Double placeMarking = marking.getValue()[placeIndex];
            if (placeMarking > placeBoundary) {
                placeBoundary = placeMarking;
                if (placeCapacity == 0) {
                    if (placeBoundary.isInfinite()) {
                        return placeBoundary;
                    }
                } else {
                    if (placeBoundary > placeCapacity) {
                        return (double) placeCapacity;
                    }
                }
            }
        }
        return placeBoundary;
    }

    public static Double getNetBoundary(DirectedSparseGraph<Marking, Transition> coverabilityGraph,
                                        PetriNetMatrix matrix) {

        Double netBoundary = 0.0;
        for (int placeIndex = 0; placeIndex < matrix.getPlacesNames().length; placeIndex++) {
            Double placeBoundary = getPlaceBoundary(placeIndex, coverabilityGraph, matrix);
            if (placeBoundary > netBoundary) {
                netBoundary = placeBoundary;
                if (netBoundary.isInfinite()) {
                    return netBoundary;
                }
            }
        }
        return netBoundary;
    }

    public static boolean isNetSafe(DirectedSparseGraph<Marking, Transition> coverabilityGraph, PetriNetMatrix matrix) {
        Double netBoundary = getNetBoundary(coverabilityGraph, matrix);
        return netBoundary <= 1;
    }

    public static boolean isNetConservative(DirectedSparseGraph<Marking, Transition> coverabilityGraph,
                                            PetriNetMatrix matrix) {

        int[] capacityVector = matrix.getCapacityVector();
        Double markingSum = getMarkingSum(new Marking(matrix.getMarkingVector()), capacityVector);
        for (Marking marking : coverabilityGraph.getVertices()) {
            if (!markingSum.equals(getMarkingSum(marking, capacityVector))) {
                return false;
            }
        }
        return true;
    }

    private static Double getMarkingSum(Marking marking, int[] capacityVector) {
        Double[] markingVector = marking.getValue();
        Double sum = 0.0;
        for (int placeIndex = 0; placeIndex < markingVector.length; placeIndex++) {
            Double placeMarking = markingVector[placeIndex];
            int placeCapacity = capacityVector[placeIndex];
            sum += (placeCapacity != 0) && (placeMarking > placeCapacity)
                    ? placeCapacity
                    : placeMarking;
        }
        return sum;
    }

    //---------------------------------------------------------------------------------------------------------


//overload
public static double[] isNetRelativelyConservative(DirectedSparseGraph<Marking, Transition> coverabilityGraph, PetriNetMatrix matrix) {
    Collection<Marking> vertices = coverabilityGraph.getVertices();
    int a_cols = matrix.getMarkingVector().length;
    double[][] a = new double[coverabilityGraph.getVertexCount()][a_cols];
    double[] b = new double[a_cols];
    int[] capacityVector = matrix.getCapacityVector();

    int i = 0;
    for (Marking vertex : vertices) {
        Double[] vector = vertex.getValue();
        for (int j = 0; j < a_cols; j++) {
            Double marking = vector[j];
            int capacity = capacityVector[j];
            a[i][j] = marking.isInfinite()
                    ? capacity == 0
                            ? -1
                            : capacity
                    : marking;
            if (a[i][j] == -1) return null;
        }
        i++;
    }
    Arrays.fill(b, 1.0);

    RealMatrix coefficients = MatrixUtils.createRealMatrix(a);
    DecompositionSolver solver = new SingularValueDecomposition(coefficients).getSolver();
    RealVector constants = MatrixUtils.createRealVector(b);
    RealVector solution = solver.solve(constants);
    double[] weightVector = solution.toArray();
    if(isNetRelativelyConservative(coverabilityGraph, matrix, weightVector)) {
        return weightVector;
    }
    return null;
}

    public static boolean isNetRelativelyConservative(DirectedSparseGraph<Marking, Transition> coverabilityGraph,
                                            PetriNetMatrix matrix, double[] weightVector) {

        int[] capacityVector = matrix.getCapacityVector();
        Double markingSum = getMarkingSum(new Marking(matrix.getMarkingVector()), capacityVector, weightVector);
        for (Marking marking : coverabilityGraph.getVertices()) {
            Double sumDifference = Math.abs(markingSum - getMarkingSum(marking, capacityVector, weightVector));
            if (sumDifference > 0.00000001) {
                return false;
            }
        }
        return true;
    }

    private static Double getMarkingSum(Marking marking, int[] capacityVector, double[] weightVector) {
        Double[] markingVector = marking.getValue();
        Double sum = 0.0;
        for (int placeIndex = 0; placeIndex < markingVector.length; placeIndex++) {
            Double placeMarking = markingVector[placeIndex];
            int placeCapacity = capacityVector[placeIndex];
            Double weight = weightVector[placeIndex];
            sum += (placeCapacity != 0) && (placeMarking > placeCapacity)
                    ? placeCapacity*weight
                    : placeMarking*weight;
        }
        return sum;
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

    public static boolean isNetReversible(DirectedSparseGraph<Marking, Transition> coverabilityGraph, PetriNetMatrix matrix) {
        Marking initialMarking = new Marking(matrix.getMarkingVector());
        Collection<Marking> markings = coverabilityGraph.getVertices();
        Iterator<Marking> itr = markings.iterator();
        UnweightedShortestPath<Marking, Transition> shortestPath = new UnweightedShortestPath<>(coverabilityGraph);
        while(itr.hasNext()) {
            if(itr.next().equals(initialMarking) || shortestPath.getDistance(itr.next(),initialMarking) == null)
                return false;
        }
        return true;
    }
}
