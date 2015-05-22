package pl.edu.agh.eis.petrilab.gui.menu.edition;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.gui.PetriLabApplication;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.Transition;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Dimension;

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
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT));
        add(nameField);
        add(acceptButton);
        add(removeButton);
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
}
