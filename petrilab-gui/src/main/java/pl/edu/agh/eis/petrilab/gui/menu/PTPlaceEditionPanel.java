package pl.edu.agh.eis.petrilab.gui.menu;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.model.Arc;
import pl.edu.agh.eis.petrilab.model.GeneralPlace;
import pl.edu.agh.eis.petrilab.model.PTPlace;
import pl.edu.agh.eis.petrilab.model.PetriNetVertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Name: PTPlaceEditionPanel
 * Description: PTPlaceEditionPanel
 * Date: 2015-04-19
 * Created by BamBalooon
 */
public class PTPlaceEditionPanel extends JPanel implements ActionListener {
    private static final String ACCEPT_BUTTON_LABEL = "ok";
    private static final String ACCEPT_BUTTON_CMD = "ACCEPT_BUTTON_CMD";
    private final JTextField nameField;
    private final JSpinner markingSpinner;
    private final JSpinner capacitySpinner;
    private final JButton acceptButton;
    private final VisualizationViewer<PetriNetVertex, Arc> graphViewer;
    private PTPlace place;

    public PTPlaceEditionPanel(VisualizationViewer<PetriNetVertex, Arc> graphViewer) {
        setVisible(false);
        this.graphViewer = graphViewer;
        nameField = new JTextField();
        nameField.setPreferredSize(
                new Dimension(PetriNetEditionMenu.TEXT_FIELD_WIDTH, PetriNetEditionMenu.TEXT_FIELD_HEIGHT));
        SpinnerNumberModel markingModel = new SpinnerNumberModel(
                GeneralPlace.MARKING_DEFAULT,
                GeneralPlace.MARKING_MIN,
                GeneralPlace.MARKING_MAX,
                1);
        markingSpinner = new JSpinner(markingModel);
        SpinnerNumberModel capacityModel = new SpinnerNumberModel(
                PTPlace.CAPACITY_DEFAULT,
                PTPlace.CAPACITY_MIN,
                PTPlace.CAPACITY_MAX,
                1);
        capacitySpinner = new JSpinner(capacityModel);
        acceptButton = new JButton();
        acceptButton.setActionCommand(ACCEPT_BUTTON_CMD);
        acceptButton.addActionListener(this);
        acceptButton.setText(ACCEPT_BUTTON_LABEL);
        add(nameField);
        add(markingSpinner);
        add(capacitySpinner);
        add(acceptButton);
    }

    public void edit(PTPlace place) {
        setVisible(true);
        this.place = place;
        nameField.setText(place.getName());
        markingSpinner.setValue(place.getMarking());
        capacitySpinner.setValue(place.getCapacity());
        revalidate();
    }

    public void accept() {
        new PTPlace.Builder()
                .fromPlace(place)
                .withName(nameField.getText())
                .withMarking((Integer) markingSpinner.getValue())
                .withCapacity((Integer) capacitySpinner.getValue())
                .modify();
        graphViewer.repaint();
    }

    public void cancel() {
        place = null;
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
