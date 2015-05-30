package pl.edu.agh.eis.petrilab.gui.menu.edition;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.gui.PetriLabApplication;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.Transition;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;

import static java.awt.GridBagConstraints.*;
import static pl.edu.agh.eis.petrilab.gui.util.GuiHelper.COMPONENT_DEFAULT_SIZE;
import static pl.edu.agh.eis.petrilab.gui.util.GuiHelper.MARGIN_SMALL;

/**
 * Name: TransitionEditionPanel
 * Description: TransitionEditionPanel
 * Date: 2015-04-19
 * Created by BamBalooon
 */
public class TransitionEditionPanel extends AbstractEditionPanel<Transition> {
    private final JTextField nameField;

    public TransitionEditionPanel(VisualizationViewer<PetriNetVertex, Arc> graphViewer) {
        super(graphViewer);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = HORIZONTAL;
        gbc.insets = MARGIN_SMALL;

        JLabel nameLabel = new JLabel("Nazwa:");
        gbc.gridwidth = 1;
        add(nameLabel, gbc);

        nameField = new JTextField();
        nameField.setPreferredSize(COMPONENT_DEFAULT_SIZE);
        gbc.gridwidth = REMAINDER;
        add(nameField, gbc);

        gbc.fill = NONE;
        gbc.anchor = EAST;
        gbc.gridwidth = 1;
        add(acceptButton, gbc);

        gbc.anchor = WEST;
        gbc.gridwidth = REMAINDER;
        add(removeButton, gbc);
    }

    @Override
    protected void loadItemState(Transition transition) {
        nameField.setText(transition.getName());
    }

    @Override
    protected void modify(Transition transition) {
        String newName = nameField.getText();
        if (newName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Przejście musi mieć nazwę.");
        } else {
            String oldName = transition.getName();
            transition.setName(newName);
            if (!PetriLabApplication.getInstance().getPetriNetGraph().areTransitionsNamesUnique()) {
                transition.setName(oldName);
                JOptionPane.showMessageDialog(this, "Nazwa przejścia musi być unikalna.");
            }
        }
    }

    @Override
    protected void remove(Transition transition) {
        graphViewer.getGraphLayout().getGraph().removeVertex(transition);
    }

    @Override
    public void requestFocus() {
        super.requestFocus();
        nameField.requestFocus();
    }
}
