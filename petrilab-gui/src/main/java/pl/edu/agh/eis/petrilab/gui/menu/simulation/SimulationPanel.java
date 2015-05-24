package pl.edu.agh.eis.petrilab.gui.menu.simulation;

import pl.edu.agh.eis.petrilab.gui.PetriLabApplication;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

/**
 * Name: SimulationPanel
 * Description: SimulationPanel
 * Date: 2015-05-19
 * Created by BamBalooon
 */
public class SimulationPanel extends JPanel implements Observer {
    private final JLabel markingLabel = new JLabel();

    public SimulationPanel() {
        PetriLabApplication.getInstance().getModeManager().addObserver(this);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JLabel markingDesc = new JLabel("Aktualne znakowanie:");
        add(markingDesc, gbc);
        gbc.fill = GridBagConstraints.NONE;
        add(markingLabel, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    }

    public void initMarking() {
        Integer[] marking = PetriLabApplication.getInstance().getSimulationGraph().getMarking();
        markingLabel.setText(Arrays.toString(marking));
    }

    public JLabel getMarkingLabel() {
        return markingLabel;
    }

    @Override
    public void update(Observable o, Object arg) {
        initMarking();
    }
}
