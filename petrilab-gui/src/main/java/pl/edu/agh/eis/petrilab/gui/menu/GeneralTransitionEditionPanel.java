package pl.edu.agh.eis.petrilab.gui.menu;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.model.Arc;
import pl.edu.agh.eis.petrilab.model.GeneralTransition;
import pl.edu.agh.eis.petrilab.model.PetriNetVertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Name: GeneralTransitionEditionPanel
 * Description: GeneralTransitionEditionPanel
 * Date: 2015-04-19
 * Created by BamBalooon
 */
public class GeneralTransitionEditionPanel extends JPanel implements ActionListener {
    private static final String ACCEPT_BUTTON_LABEL = "ok";
    private static final String ACCEPT_BUTTON_CMD = "ACCEPT_BUTTON_CMD";
    private final JTextField nameField;
    private final VisualizationViewer<PetriNetVertex, Arc> graphViewer;
    private GeneralTransition transition;

    public GeneralTransitionEditionPanel(VisualizationViewer<PetriNetVertex, Arc> graphViewer) {
        setVisible(false);
        this.graphViewer = graphViewer;
        nameField = new JTextField();
        nameField.setPreferredSize(
                new Dimension(PetriNetEditionMenu.TEXT_FIELD_WIDTH, PetriNetEditionMenu.TEXT_FIELD_HEIGHT));
        JButton acceptButton = new JButton();
        acceptButton.setActionCommand(ACCEPT_BUTTON_CMD);
        acceptButton.addActionListener(this);
        acceptButton.setText(ACCEPT_BUTTON_LABEL);
        add(nameField);
        add(acceptButton);
    }

    public void edit(GeneralTransition transition) {
        setVisible(true);
        this.transition = transition;
        nameField.setText(transition.getName());
        revalidate();
    }

    public void accept() {
        new GeneralTransition.Builder()
                .fromTransition(transition)
                .withName(nameField.getText())
                .modify();
        graphViewer.repaint();
    }

    public void cancel() {
        transition = null;
        setVisible(false);
        revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case ACCEPT_BUTTON_CMD:
                accept();
                break;
        }
    }
}
