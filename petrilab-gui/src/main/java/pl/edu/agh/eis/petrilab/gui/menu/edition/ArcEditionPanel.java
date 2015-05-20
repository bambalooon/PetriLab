package pl.edu.agh.eis.petrilab.gui.menu.edition;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Dimension;

/**
 * Name: GeneralTransitionEditionPanel
 * Description: GeneralTransitionEditionPanel
 * Date: 2015-04-19
 * Created by BamBalooon
 */
public class ArcEditionPanel extends AbstractEditionPanel<Arc> {
    private final JSpinner weightSpinner;

    public ArcEditionPanel(VisualizationViewer<PetriNetVertex, Arc> graphViewer) {
        super(graphViewer);
        SpinnerNumberModel weightModel = new SpinnerNumberModel(
                Arc.WEIGHT_DEFAULT,
                Arc.WEIGHT_MIN,
                Arc.WEIGHT_MAX,
                1);
        weightSpinner = new JSpinner(weightModel);
        weightSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        add(weightSpinner);
        add(acceptButton);
        add(removeButton);
    }

    @Override
    protected void loadItemState(Arc arc) {
        weightSpinner.setValue(arc.getWeight());
    }

    @Override
    protected void modify(Arc arc) {
        arc.setWeight((Integer) weightSpinner.getValue());
    }

    @Override
    protected void remove(Arc arc) {
        graphViewer.getGraphLayout().getGraph().removeEdge(arc);
    }
}
