package pl.edu.agh.eis.petrilab.gui.jung.transform;

import org.apache.commons.collections15.Transformer;
import pl.edu.agh.eis.petrilab.model.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model.Place;
import pl.edu.agh.eis.petrilab.model.Transition;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Name: VertexIconTransformer
 * Description: VertexIconTransformer
 * Date: 2015-05-14
 * Created by BamBalooon
 */
public class VertexIconTransformer implements Transformer<PetriNetVertex, Icon> {
    private static final int DEFAULT_CIRCLE_RADIUS = 10;
    private static final Color DEFAULT_STATE_COLOR = Color.red;
    private static final Color DEFAULT_MARKING_COLOR = Color.black;

    private final int stateCircleRadius;
    private final Color stateColor;
    private final Color markingColor;

    public VertexIconTransformer() {
        this(DEFAULT_CIRCLE_RADIUS, DEFAULT_STATE_COLOR, DEFAULT_MARKING_COLOR);
    }

    public VertexIconTransformer(int stateCircleRadius, Color stateColor, Color markingColor) {
        this.stateCircleRadius = stateCircleRadius;
        this.stateColor = stateColor;
        this.markingColor = markingColor;
    }

    @Override
    public Icon transform(PetriNetVertex vertex) {
        if (vertex instanceof Place) {
            BufferedImage image = new BufferedImage(
                    stateCircleRadius * 2,
                    stateCircleRadius * 2,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            try {
                g2d.setPaint(stateColor);
                g2d.fill(new Ellipse2D.Double(0, 0, stateCircleRadius * 2, stateCircleRadius * 2));
                g2d.setPaint(markingColor);
                TextLayout textLayout = new TextLayout(
                        String.valueOf(((Place) vertex).getMarking()),
                        g2d.getFont(),
                        g2d.getFontRenderContext());
                Rectangle2D textBounds = textLayout.getBounds();
                textLayout.draw(
                        g2d,
                        (float) (stateCircleRadius - textBounds.getWidth() / 2),
                        (float) (stateCircleRadius + textBounds.getHeight() / 2));
                return new ImageIcon(image);
            } finally {
                g2d.dispose();
            }
        } else if (vertex instanceof Transition) {
            return null;
        } else {
            throw new IllegalArgumentException("Cannot transform vertex of this type: " + vertex);
        }
    }
}
