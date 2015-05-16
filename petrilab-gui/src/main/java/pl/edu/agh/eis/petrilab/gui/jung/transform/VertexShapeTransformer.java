package pl.edu.agh.eis.petrilab.gui.jung.transform;

import org.apache.commons.collections15.Transformer;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.Place;
import pl.edu.agh.eis.petrilab.model2.Transition;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Name: VertexShapeTransformer
 * Description: VertexShapeTransformer
 * Date: 2015-04-19
 * Created by BamBalooon
 */
public class VertexShapeTransformer implements Transformer<PetriNetVertex, Shape> {
    private static final int DEFAULT_CIRCLE_RADIUS = 10;
    private static final int DEFAULT_RECT_WIDTH = 10;
    private static final int DEFAULT_RECT_HEIGHT = 30;
    private final int stateCircleRadius;
    private final int transitionRectWidth;
    private final int transitionRectHeight;

    public VertexShapeTransformer() {
        this(DEFAULT_CIRCLE_RADIUS, DEFAULT_RECT_WIDTH, DEFAULT_RECT_HEIGHT);
    }

    public VertexShapeTransformer(int stateCircleRadius, int transitionRectWidth, int transitionRectHeight) {
        this.stateCircleRadius = stateCircleRadius;
        this.transitionRectWidth = transitionRectWidth;
        this.transitionRectHeight = transitionRectHeight;
    }

    @Override
    public Shape transform(PetriNetVertex vertex) {
        if (vertex instanceof Place) {
            return new Ellipse2D.Double(-stateCircleRadius, -stateCircleRadius,
                    stateCircleRadius * 2, stateCircleRadius * 2);
        } else if (vertex instanceof Transition) {
            return new Rectangle(-transitionRectWidth / 2, -transitionRectHeight / 2,
                    transitionRectWidth, transitionRectHeight);
        } else {
            throw new IllegalArgumentException("Cannot transform vertex of this type: " + vertex);
        }
    }
}
