package pl.edu.agh.eis.petrilab.model2.matrix;

import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.Place;
import pl.edu.agh.eis.petrilab.model2.Transition;
import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraph;

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

    public static PetriNetMatrix generateMatrix(PetriNetGraph petriNetGraph) {
        Collection<PetriNetVertex> vertices = petriNetGraph.getVertices();

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
