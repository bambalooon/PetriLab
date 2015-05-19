package pl.edu.agh.eis.petrilab.gui.jung.transform;

import org.apache.commons.collections15.Transformer;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.Transition;
import pl.edu.agh.eis.petrilab.model2.jung.PetriNetGraph;

import java.awt.Color;
import java.awt.Paint;

/**
 * Name: SimulationVertexShapeTransformer
 * Description: SimulationVertexShapeTransformer
 * Date: 2015-05-19
 * Created by BamBalooon
 */
public class SimulationVertexFillPaintTransformer implements Transformer<PetriNetVertex, Paint> {
    private static final Color DEFAULT_ACTIVE_TRANSITION_COLOR = Color.green;
    private final VertexFillPaintTransformer transformer;
    private final PetriNetGraph petriNetGraph;
    private final Color activeTransitionColor;

    public SimulationVertexFillPaintTransformer(VertexFillPaintTransformer transformer, PetriNetGraph petriNetGraph) {
        this(transformer, petriNetGraph, DEFAULT_ACTIVE_TRANSITION_COLOR);
    }

    public SimulationVertexFillPaintTransformer(VertexFillPaintTransformer transformer,
                                                PetriNetGraph petriNetGraph,
                                                Color activeTransitionColor) {
        this.transformer = transformer;
        this.petriNetGraph = petriNetGraph;
        this.activeTransitionColor = activeTransitionColor;
    }

    @Override
    public Paint transform(PetriNetVertex petriNetVertex) {
        if (petriNetVertex instanceof Transition) {
            Transition transition = (Transition) petriNetVertex;
            if (petriNetGraph.isTransitionActive(transition)) {
                return activeTransitionColor;
            }
        }
        return transformer.transform(petriNetVertex);
    }
}
