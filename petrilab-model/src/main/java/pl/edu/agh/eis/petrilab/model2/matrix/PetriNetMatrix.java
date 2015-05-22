package pl.edu.agh.eis.petrilab.model2.matrix;

import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.Place;
import pl.edu.agh.eis.petrilab.model2.Transition;
import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public boolean isTransitionActive (int transitionNumber, int[] marking) {
        int n = marking.length;
        for (int i = 0; i < n; i++) {
            if (marking[i] < this.getOutMatrix()[i][transitionNumber])
                return false;
            if (this.getInMatrix()[i][transitionNumber] + marking[i] > this.getCapacityVector()[i])
                return false;
        }
        return true;
    }

    public List<Integer> getActiveTransitions (int[] marking) {
        int n = this.getInMatrix()[0].length;
        List<Integer> transitionsList = new ArrayList<>();
        for (int i = 0; i < n; i++)
            if (isTransitionActive(i, marking))
                transitionsList.add(i);
        return transitionsList;
    }

    public int[][] add (boolean positive, int[][] matrix1, int[][] matrix2) {
        int rows = matrix1.length, cols = matrix1[0].length;
        int[][] result = new int[rows][cols];
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++) {
                if (positive)
                    result[i][j] = matrix1[i][j] + matrix2[i][j];
                else
                    result[i][j] = matrix1[i][j] - matrix2[i][j];
            }
        return result;
    }

    public int[] add (boolean positive, int[] vector1, int[] vector2) {
        int length = vector1.length;
        int[] result = new int[length];
            for(int i = 0; i < length; i++) {
                if (positive)
                    result[i] = vector1[i] + vector2[i];
                else
                    result[i] = vector1[i] - vector2[i];
            }
        return result;
    }

    public int[] multiply (int[] vector, int scalar) {
        int length = vector.length;
        int[] result = new int[length];
        for(int i = 0; i < length; i++)
                result[i] = vector[i] * scalar;
        return result;
    }

    public int[] col (int[][] matrix, int index) {
        int rows = matrix.length;
        int[] result = new int[rows];
        for(int i = 0; i < rows; i++)
                    result[i] = matrix[i][index];
        return result;
    }

    public int[] row (int[][] matrix, int index) {
        int cols = matrix[0].length;
        int[] result = new int[cols];
        for(int i = 0; i < cols; i++)
            result[i] = matrix[index][i];
        return result;
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

        return new PetriNetMatrix(inMatrix, outMatrix, markingVector, capacityVector, placesNames, transitionsNames);
    }
}
