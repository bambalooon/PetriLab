package pl.edu.agh.eis.petrilab.gui.menu;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.model.Arc;
import pl.edu.agh.eis.petrilab.model.GeneralTransition;
import pl.edu.agh.eis.petrilab.model.PetriNetVertex;

import javax.swing.*;
import java.awt.*;

/**
 * Name: GeneralTransitionEditionPanel
 * Description: GeneralTransitionEditionPanel
 * Date: 2015-04-19
 * Created by BamBalooon
 */
public class GeneralTransitionEditionPanel extends AbstractEditionPanel<GeneralTransition> {
    private final JTextField nameField;

    public GeneralTransitionEditionPanel(VisualizationViewer<PetriNetVertex, Arc> graphViewer) {
        super(graphViewer);
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT));
        add(nameField);
        add(acceptButton);
    }

    @Override
    protected void loadItemState(GeneralTransition transition) {
        nameField.setText(transition.getName());
    }

    @Override
    protected void modify(GeneralTransition transition) {
        new GeneralTransition.Builder()
                .fromTransition(transition)
                .withName(nameField.getText())
                .modify();
    }
}
