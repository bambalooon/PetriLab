package pl.edu.agh.eis.petrilab.gui.menu;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.model.Arc;
import pl.edu.agh.eis.petrilab.model.PetriNetVertex;

import javax.swing.*;
import java.awt.*;

/**
 * Name: GeneralTransitionEditionPanel
 * Description: GeneralTransitionEditionPanel
 * Date: 2015-04-19
 * Created by BamBalooon
 */
public class ArcEditionPanel extends AbstractEditionPanel<Arc> {
    private static final String ACCEPT_BUTTON_LABEL = "ok";
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
        JButton acceptButton = new JButton();
        acceptButton.setActionCommand(ACCEPT_BUTTON_CMD);
        acceptButton.addActionListener(this);
        acceptButton.setText(ACCEPT_BUTTON_LABEL);
        add(weightSpinner);
        add(acceptButton);
    }

    @Override
    protected void loadItemState(Arc arc) {
        weightSpinner.setValue(arc.getWeight());
    }

    @Override
    protected void modify(Arc arc) {
        new Arc.Builder()
                .fromArc(arc)
                .withWeight((Integer) weightSpinner.getValue())
                .modify();
    }
}
