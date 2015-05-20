package pl.edu.agh.eis.petrilab.gui.jung.paintable;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationServer;
import edu.uci.ics.jung.visualization.util.ChangeEventSupport;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;

/**
 * Name: ActiveTransitionsBoundingRectanglePaintable
 * Description: ActiveTransitionsBoundingRectanglePaintable
 * Date: 2015-05-20
 * Created by BamBalooon
 */
public class ActiveTransitionsBoundingRectanglePaintable implements VisualizationServer.Paintable {
    private static final Color DEFAULT_RECTANGLES_COLOR = Color.yellow;
    protected RenderContext<PetriNetVertex, Arc> rc;
    protected List<Rectangle2D> rectangles;
    protected Color rectanglesColor;

    public ActiveTransitionsBoundingRectanglePaintable(RenderContext<PetriNetVertex, Arc> rc, Layout<PetriNetVertex, Arc> layout) {
        this(rc, layout, DEFAULT_RECTANGLES_COLOR);
    }

    public ActiveTransitionsBoundingRectanglePaintable(RenderContext<PetriNetVertex, Arc> rc,
                                                       Layout<PetriNetVertex, Arc> layout,
                                                       Color rectanglesColor) {
        this.rc = rc;
        this.rectanglesColor = rectanglesColor;
        final ActiveTransitionsBoundingRectangleCollector brc = new ActiveTransitionsBoundingRectangleCollector(rc, layout);
        this.rectangles = brc.getRectangles();
        if(layout instanceof ChangeEventSupport) {
            ((ChangeEventSupport)layout).addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent e) {
                    brc.compute();
                    rectangles = brc.getRectangles();
                }});
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(rectanglesColor);

        for (Rectangle2D rectangle : rectangles) {
            g2d.fill(rc.getMultiLayerTransformer().transform(Layer.LAYOUT, rectangle));
        }
    }

    @Override
    public boolean useTransform() {
        return true;
    }
}
