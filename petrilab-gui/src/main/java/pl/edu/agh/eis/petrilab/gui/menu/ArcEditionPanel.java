package pl.edu.agh.eis.petrilab.gui.menu;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.model.Arc;
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
public class ArcEditionPanel extends JPanel implements ActionListener {
    private static final String ACCEPT_BUTTON_LABEL = "ok";
    private static final String ACCEPT_BUTTON_CMD = "ACCEPT_BUTTON_CMD";
    private final JSpinner weightSpinner;
    private final VisualizationViewer<PetriNetVertex, Arc> graphViewer;
    private Arc arc;

    public ArcEditionPanel(VisualizationViewer<PetriNetVertex, Arc> graphViewer) {
        setVisible(false);
        this.graphViewer = graphViewer;
        SpinnerNumberModel weightModel = new SpinnerNumberModel(
                Arc.WEIGHT_DEFAULT,
                Arc.WEIGHT_MIN,
                Arc.WEIGHT_MAX,
                1);
        weightSpinner = new JSpinner(weightModel);
        weightSpinner.setPreferredSize(new Dimension(
                PetriNetEditionMenu.SPINNER_WIDTH, PetriNetEditionMenu.SPINNER_HEIGHT));
        JButton acceptButton = new JButton();
        acceptButton.setActionCommand(ACCEPT_BUTTON_CMD);
        acceptButton.addActionListener(this);
        acceptButton.setText(ACCEPT_BUTTON_LABEL);
        add(weightSpinner);
        add(acceptButton);
    }

    public void edit(Arc arc) {
        setVisible(true);
        this.arc = arc;
        weightSpinner.setValue(arc.getWeight());
        revalidate();
    }

    public void accept() {
        new Arc.Builder()
                .fromArc(arc)
                .withWeight((Integer) weightSpinner.getValue())
                .modify();
        graphViewer.repaint();
    }

    public void cancel() {
        arc = null;
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
