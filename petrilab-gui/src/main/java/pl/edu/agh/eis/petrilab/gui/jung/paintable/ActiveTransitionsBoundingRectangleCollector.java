package pl.edu.agh.eis.petrilab.gui.jung.paintable;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.layout.BoundingRectangleCollector;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.Transition;
import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraph;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Name: ActiveTransitionsBoundingRectangleCollector
 * Description: ActiveTransitionsBoundingRectangleCollector
 * Date: 2015-05-20
 * Created by BamBalooon
 */
public class ActiveTransitionsBoundingRectangleCollector extends BoundingRectangleCollector<PetriNetVertex, Arc> {
    private static final int DEFAULT_BORDER = 3;
    private int border = DEFAULT_BORDER;

    public ActiveTransitionsBoundingRectangleCollector(RenderContext<PetriNetVertex, Arc> rc, Layout<PetriNetVertex, Arc> layout) {
        super(rc, layout);
        compute();
    }

    @Override
    public void compute() {
        rectangles.clear();

        for(Transition transition : ((PetriNetGraph) graph).getActiveTransitions()) {
            Shape shape = rc.getVertexShapeTransformer().transform(transition);
            Point2D p = layout.transform(transition);
            float x = (float)p.getX();
            float y = (float)p.getY();
            AffineTransform xform = AffineTransform.getTranslateInstance(x, y);
            shape = xform.createTransformedShape(shape);
            Rectangle2D bounds = shape.getBounds2D();
            bounds.setRect(bounds.getX() - border, bounds.getY() - border, bounds.getWidth() + 2 * border, bounds.getHeight() + 2 * border);
            rectangles.add(bounds);
        }
    }
}
