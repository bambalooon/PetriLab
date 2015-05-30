package pl.edu.agh.eis.petrilab.gui.menu.edition;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.GridBagConstraints;

import static java.awt.GridBagConstraints.*;
import static pl.edu.agh.eis.petrilab.gui.util.GuiHelper.COMPONENT_DEFAULT_SIZE;
import static pl.edu.agh.eis.petrilab.gui.util.GuiHelper.MARGIN_SMALL;

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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = HORIZONTAL;
        gbc.insets = MARGIN_SMALL;

        JLabel weightLabel = new JLabel("Waga:");
        gbc.gridwidth = 1;
        add(weightLabel, gbc);

        SpinnerNumberModel weightModel = new SpinnerNumberModel(
                Arc.WEIGHT_DEFAULT, Arc.WEIGHT_MIN, Arc.WEIGHT_MAX, 1);
        weightSpinner = new JSpinner(weightModel);
        weightSpinner.setPreferredSize(COMPONENT_DEFAULT_SIZE);
        gbc.gridwidth = REMAINDER;
        add(weightSpinner, gbc);

        gbc.fill = NONE;
        gbc.anchor = EAST;
        gbc.gridwidth = 1;
        add(acceptButton, gbc);

        gbc.anchor = WEST;
        gbc.gridwidth = REMAINDER;
        add(removeButton, gbc);
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

    @Override
    public void requestFocus() {
        super.requestFocus();
        weightSpinner.requestFocus();
    }
}
