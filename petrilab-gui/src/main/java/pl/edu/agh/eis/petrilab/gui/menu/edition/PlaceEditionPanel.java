package pl.edu.agh.eis.petrilab.gui.menu.edition;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.gui.PetriLabApplication;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;
import pl.edu.agh.eis.petrilab.model2.Place;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

/**
 * Name: PlaceEditionPanel
 * Description: PlaceEditionPanel
 * Date: 2015-04-19
 * Created by BamBalooon
 */
public class PlaceEditionPanel extends AbstractEditionPanel<Place> {
    private final JTextField nameField;
    private final JSpinner markingSpinner;
    private final JCheckBox capacityCheckbox;
    private final JSpinner capacitySpinner;

    public PlaceEditionPanel(VisualizationViewer<PetriNetVertex, Arc> graphViewer) {
        super(graphViewer);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Nazwa:");
        gbc.gridwidth = 1;
        add(nameLabel, gbc);

        nameField = new JTextField();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(nameField, gbc);

        JLabel markingLabel = new JLabel("Znakowanie:");
        gbc.gridwidth = 1;
        add(markingLabel, gbc);

        SpinnerNumberModel markingModel = new SpinnerNumberModel(
                Place.MARKING_DEFAULT, Place.MARKING_MIN, Place.MARKING_MAX, 1);
        markingSpinner = new JSpinner(markingModel);
        markingSpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(markingSpinner, gbc);

        capacityCheckbox = new JCheckBox("Pojemność:");
        gbc.gridwidth = 1;
        add(capacityCheckbox, gbc);

        SpinnerNumberModel capacityModel = new SpinnerNumberModel(
                Place.CAPACITY_DEFAULT, Place.CAPACITY_MIN, Place.CAPACITY_MAX, 1);
        capacitySpinner = new JSpinner(capacityModel);
        capacitySpinner.setPreferredSize(new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT));
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(capacitySpinner, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridwidth = 1;
        add(acceptButton, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(removeButton, gbc);
    }

    @Override
    protected void loadItemState(Place place) {
        nameField.setText(place.getName());
        markingSpinner.setValue(place.getMarking());
        capacitySpinner.setValue(place.getCapacity());
    }

    @Override
    protected void modify(Place place) {
        String newName = nameField.getText();
        if (newName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Miejsce musi mieć nazwę.");
        } else {
            String oldName = place.getName();
            place.setName(newName);
            if (!PetriLabApplication.getInstance().getPetriNetGraph().arePlacesNamesUnique()) {
                place.setName(oldName);
                JOptionPane.showMessageDialog(this, "Nazwa miejsca musi być unikalna.");
            }
        }
        place.setMarking((Integer) markingSpinner.getValue());
        place.setCapacity((Integer) capacitySpinner.getValue());
    }

    @Override
    protected void remove(Place place) {
        graphViewer.getGraphLayout().getGraph().removeVertex(place);
    }
}
