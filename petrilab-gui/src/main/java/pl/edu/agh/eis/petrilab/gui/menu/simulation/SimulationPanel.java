package pl.edu.agh.eis.petrilab.gui.menu.simulation;

import pl.edu.agh.eis.petrilab.gui.PetriLabApplication;
import pl.edu.agh.eis.petrilab.gui.SimulationPlayer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import static pl.edu.agh.eis.petrilab.gui.util.GuiHelper.*;

/**
 * Name: SimulationPanel
 * Description: SimulationPanel
 * Date: 2015-05-19
 * Created by BamBalooon
 */
public class SimulationPanel extends JPanel implements ActionListener, Observer {
    private static final String STOP_BUTTON_ACTION = "STOP_BUTTON_ACTION";
    private static final String STOP_BUTTON_RES = "/icons/32x32/control_stop.png";
    private static final String PLAY_BUTTON_ACTION = "PLAY_BUTTON_ACTION";
    private static final String PLAY_BUTTON_RES = "/icons/32x32/control_play.png";
    private static final String PAUSE_BUTTON_ACTION = "PAUSE_BUTTON_ACTION";
    private static final String PAUSE_BUTTON_RES = "/icons/32x32/control_pause.png";
    private final JLabel markingLabel = new JLabel();
    private final SimulationPlayer simulationPlayer;

    public SimulationPanel() {
        PetriLabApplication.getInstance().getModeManager().addObserver(this);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = MARGIN_SMALL;

        JLabel markingDesc = new JLabel("Aktualne znakowanie:");
        add(markingDesc, gbc);
        add(markingLabel, gbc);

        JCheckBox autoSimulationCheckBox = new JCheckBox("Auto");
        add(autoSimulationCheckBox, gbc);
        this.simulationPlayer = new SimulationPlayer(this, autoSimulationCheckBox);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weighty = 1;
        JButton stopButton = createIconButton(this, STOP_BUTTON_ACTION, STOP_BUTTON_RES, BUTTON_PADDING_MEDIUM);
        add(stopButton, gbc);

        JButton playButton = createIconButton(this, PLAY_BUTTON_ACTION, PLAY_BUTTON_RES, BUTTON_PADDING_MEDIUM);
        add(playButton, gbc);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        JButton pauseButton = createIconButton(this, PAUSE_BUTTON_ACTION, PAUSE_BUTTON_RES, BUTTON_PADDING_MEDIUM);
        add(pauseButton, gbc);
    }

    public void updateMarking() {
        Integer[] marking = PetriLabApplication.getInstance().getSimulationGraph().getMarking();
        markingLabel.setText(Arrays.toString(marking));
    }

    @Override
    public void update(Observable o, Object arg) {
        updateMarking();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case PLAY_BUTTON_ACTION:
                simulationPlayer.play();
                break;
            case PAUSE_BUTTON_ACTION:
                simulationPlayer.pause();
                break;
            case STOP_BUTTON_ACTION:
                simulationPlayer.stop();
                break;
        }
    }
}
