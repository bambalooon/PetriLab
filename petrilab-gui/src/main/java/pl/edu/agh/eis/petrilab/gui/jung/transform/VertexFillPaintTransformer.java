package pl.edu.agh.eis.petrilab.gui.jung.transform;

import org.apache.commons.collections15.Transformer;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.Place;
import pl.edu.agh.eis.petrilab.model2.Transition;

import java.awt.*;

/**
 * Name: VertexFillPaintTransformer
 * Description: VertexFillPaintTransformer
 * Date: 2015-04-19
 * Created by BamBalooon
 */
public class VertexFillPaintTransformer implements Transformer<PetriNetVertex, Paint> {
    private static final Color DEFAULT_STATE_COLOR = Color.red;
    private static final Color DEFAULT_TRANSITION_COLOR = new Color(90, 120, 255);
    private final Color stateColor;
    private final Color transitionColor;

    public VertexFillPaintTransformer() {
        this(DEFAULT_STATE_COLOR, DEFAULT_TRANSITION_COLOR);
    }

    public VertexFillPaintTransformer(Color stateColor, Color transitionColor) {
        this.stateColor = stateColor;
        this.transitionColor = transitionColor;
    }

    @Override
    public Paint transform(PetriNetVertex vertex) {
        if (vertex instanceof Place) {
            return stateColor;
        } else if (vertex instanceof Transition) {
            return transitionColor;
        } else {
            throw new IllegalArgumentException("Cannot transform vertex of this type: " + vertex);
        }
    }
}
