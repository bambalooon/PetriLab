package pl.edu.agh.eis.petrilab.gui.jung;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;
import pl.edu.agh.eis.petrilab.model.Arc;
import pl.edu.agh.eis.petrilab.model.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model.Place;
import pl.edu.agh.eis.petrilab.model.Transition;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * Name: PetriNetVertexRenderer
 * Description: PetriNetVertexRenderer
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class PetriNetVertexRenderer implements Renderer.Vertex<PetriNetVertex, Arc> {
    private static final int DEFAULT_CIRCLE_RADIUS = 10;
    private static final int DEFAULT_RECT_WIDTH = 10;
    private static final int DEFAULT_RECT_HEIGHT = 30;
    private static final Color DEFAULT_STATE_COLOR = new Color(255, 0, 0);
    private static final Color DEFAULT_TRANSITION_COLOR = new Color(90, 120, 255);
    private final int stateCircleRadius;
    private final int transitionRectWidth;
    private final int transitionRectHeight;
    private final Color stateColor;
    private final Color transitionColor;

    public PetriNetVertexRenderer() {
        this(DEFAULT_CIRCLE_RADIUS, DEFAULT_RECT_WIDTH, DEFAULT_RECT_HEIGHT,
                DEFAULT_STATE_COLOR, DEFAULT_TRANSITION_COLOR);
    }

    public PetriNetVertexRenderer(int stateCircleRadius, int transitionRectWidth, int transitionRectHeight,
                                  Color stateColor, Color transitionColor) {
        this.stateCircleRadius = stateCircleRadius;
        this.transitionRectWidth = transitionRectWidth;
        this.transitionRectHeight = transitionRectHeight;
        this.stateColor = stateColor;
        this.transitionColor = transitionColor;
    }

    @Override
    public void paintVertex(RenderContext<PetriNetVertex, Arc> rc,
                            Layout<PetriNetVertex, Arc> layout,
                            PetriNetVertex vertex) {
        GraphicsDecorator graphicsContext = rc.getGraphicsContext();
        Point2D center = layout.transform(vertex);
        Shape shape;
        Color color;
        if (vertex instanceof Place) {
            shape = new Ellipse2D.Double(
                    center.getX() - stateCircleRadius, center.getY() - stateCircleRadius,
                    stateCircleRadius * 2, stateCircleRadius * 2);
            color = stateColor;
        } else if (vertex instanceof Transition) {
            shape = new Rectangle(
                    (int) (center.getX() - transitionRectWidth / 2), (int) (center.getY() - transitionRectHeight / 2),
                    transitionRectWidth, transitionRectHeight);
            color = transitionColor;
        } else {
            throw new IllegalArgumentException("Cannot render vertex of type: " + vertex);
        }
        graphicsContext.setPaint(color);
        graphicsContext.fill(shape);
    }
}
