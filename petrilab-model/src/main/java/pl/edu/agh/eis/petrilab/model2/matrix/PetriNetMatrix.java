package pl.edu.agh.eis.petrilab.model2.matrix;

import com.google.common.collect.Maps;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.Place;
import pl.edu.agh.eis.petrilab.model2.Transition;
import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Name: PetriNetMatrix
 * Description: PetriNetMatrix
 * Date: 2015-05-17
 * Created by BamBalooon
 */
public class PetriNetMatrix {
    private final int[][] inMatrix;
    private final int[][] outMatrix;
    private final int[] markingVector;
    private final int[] capacityVector;
    private final String[] placesNames;
    private final String[] transitionsNames;

    protected PetriNetMatrix(int[][] inMatrix, int[][] outMatrix,
                             int[] markingVector, int[] capacityVector,
                             String[] placesNames, String[] transitionsNames) {
        this.inMatrix = inMatrix;
        this.outMatrix = outMatrix;
        this.markingVector = markingVector;
        this.capacityVector = capacityVector;
        this.placesNames = placesNames;
        this.transitionsNames = transitionsNames;
    }

    public int[][] getInMatrix() {
        return inMatrix;
    }

    public int[][] getOutMatrix() {
        return outMatrix;
    }

    public int[] getMarkingVector() {
        return markingVector;
    }

    public int[] getCapacityVector() {
        return capacityVector;
    }

    public String[] getPlacesNames() {
        return placesNames;
    }

    public String[] getTransitionsNames() {
        return transitionsNames;
    }

    public boolean isTransitionActive (int transitionIndex, Double[] marking) {
        int n = marking.length;
        for (int i = 0; i < n; i++) {
            if (marking[i] < this.getOutMatrix()[transitionIndex][i])
                return false;
            if (this.getInMatrix()[transitionIndex][i] + marking[i] > this.getCapacityVector()[i])
                return false;
        }
        return true;
    }

    public List<Integer> getActiveTransitions (Double[] marking) {
        int transitionsCount = this.getInMatrix().length;
        List<Integer> transitionsList = new ArrayList<>();
        for (int i = 0; i < transitionsCount; i++)
            if (isTransitionActive(i, marking))
                transitionsList.add(i);
        return transitionsList;
    }

    private static Map<Integer, Integer> sortAndCreateReconfigurationMap(String[] array) {
        String[] unorderedArray = array.clone();
        Arrays.sort(array);
        Map<Integer, Integer> reconfigurationMap = Maps.newHashMap();
        for (int oldIndex = 0; oldIndex < unorderedArray.length; oldIndex++) {
            for (int newIndex = 0; newIndex < array.length; newIndex++) {
                if (unorderedArray[oldIndex].equals(array[newIndex])) {
                    reconfigurationMap.put(newIndex, oldIndex);
                    break;
                }
            }
        }
        return reconfigurationMap;
    }

    private static void reconfigureVector(int[] vector, Map<Integer, Integer> reconfigurationMap) {
        int[] baseVector = vector.clone();
        for (Map.Entry<Integer, Integer> reconfigurationEntry : reconfigurationMap.entrySet()) {
            vector[reconfigurationEntry.getKey()] = baseVector[reconfigurationEntry.getValue()];
        }
    }

    private static void reconfigureMatrix(int[][] matrix,
                                          Map<Integer, Integer> rowReconfiguration,
                                          Map<Integer, Integer> columnReconfiguration) {

        int[][] baseMatrix = matrix.clone();
        for (Map.Entry<Integer, Integer> rowReconfigurationEntry : rowReconfiguration.entrySet()) {
            matrix[rowReconfigurationEntry.getKey()] = baseMatrix[rowReconfigurationEntry.getValue()];
        }

        baseMatrix = clone(matrix);
        for (Map.Entry<Integer, Integer> colReconfigurationEntry : columnReconfiguration.entrySet()) {
            int indexFrom = colReconfigurationEntry.getKey();
            int indexTo = colReconfigurationEntry.getValue();
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][indexFrom] = baseMatrix[i][indexTo];
            }
        }
    }

    private static int[][] clone(int[][] matrix) {
        int[][] clonedMatrix = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            clonedMatrix[i] = matrix[i].clone();
        }
        return clonedMatrix;
    }

    public static PetriNetMatrix generateMatrix(PetriNetGraph petriNetGraph) {
        List<Place> places = petriNetGraph.getPlaces();
        List<Transition> transitions = petriNetGraph.getTransitions();

        int placesCount = places.size();
        int transitionsCount = transitions.size();

        int[][] inMatrix = new int[transitionsCount][placesCount];
        int[][] outMatrix = new int[transitionsCount][placesCount];
        int[] markingVector = new int[placesCount];
        int[] capacityVector = new int[placesCount];
        String[] placesNames = new String[placesCount];
        String[] transitionsNames = new String[transitionsCount];

        int i = 0;
        for (Place place : places) {
            markingVector[i] = place.getMarking();
            capacityVector[i] = place.getCapacity();
            placesNames[i] = place.getName();
            i++;
        }

        i = 0;
        for (Transition transition : transitions) {
            int j = 0;
            for (Place place : places) {
                Arc inArc = petriNetGraph.findEdge(transition, place);
                if (inArc != null) {
                    inMatrix[i][j] = inArc.getWeight();
                }
                Arc outArc = petriNetGraph.findEdge(place, transition);
                if (outArc != null) {
                    outMatrix[i][j] = outArc.getWeight();
                }
                j++;
            }
            transitionsNames[i] = transition.getName();
            i++;
        }

        Map<Integer, Integer> placesReconfiguration = sortAndCreateReconfigurationMap(placesNames);
        reconfigureVector(markingVector, placesReconfiguration);
        reconfigureVector(capacityVector, placesReconfiguration);
        Map<Integer, Integer> transitionsReconfiguration = sortAndCreateReconfigurationMap(transitionsNames);
        reconfigureMatrix(inMatrix, transitionsReconfiguration, placesReconfiguration);
        reconfigureMatrix(outMatrix, transitionsReconfiguration, placesReconfiguration);

        return new PetriNetMatrix(inMatrix, outMatrix, markingVector, capacityVector, placesNames, transitionsNames);
    }
}
