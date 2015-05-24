package pl.edu.agh.eis.petrilab.model2.matrix;

import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraphInitializer;

import java.awt.geom.Point2D;
import java.util.Map;

/**
 * Name: PetriNetMatrixWithCoordinates
 * Description: PetriNetMatrixWithCoordinates
 * Date: 2015-05-24
 * Created by BamBalooon
 */
public class PetriNetMatrixWithCoordinates extends PetriNetMatrix {
    private final Map<String, Point2D> placesCoordinates;
    private final Map<String, Point2D> transitionsCoordinates;

    public PetriNetMatrixWithCoordinates(int[][] inMatrix, int[][] outMatrix,
                                         int[] markingVector, int[] capacityVector,
                                         String[] placesNames, String[] transitionsNames,
                                         Map<String, Point2D> placesCoordinates,
                                         Map<String, Point2D> transitionsCoordinates) {

        super(inMatrix, outMatrix, markingVector, capacityVector, placesNames, transitionsNames);
        this.placesCoordinates = placesCoordinates;
        this.transitionsCoordinates = transitionsCoordinates;
    }

    public Map<String, Point2D> getPlacesCoordinates() {
        return placesCoordinates;
    }

    public Map<String, Point2D> getTransitionsCoordinates() {
        return transitionsCoordinates;
    }

    public static PetriNetMatrixWithCoordinates fromMatrix(PetriNetMatrix matrix,
                                                           PetriNetGraphInitializer initializer) {

        return new PetriNetMatrixWithCoordinates(
                matrix.getInMatrix(), matrix.getOutMatrix(),
                matrix.getMarkingVector(), matrix.getCapacityVector(),
                matrix.getPlacesNames(), matrix.getTransitionsNames(),
                initializer.getPlacesCoordinates(),
                initializer.getTransitionsCoordinates());
    }
}
