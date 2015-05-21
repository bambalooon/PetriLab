package pl.edu.agh.eis.petrilab.gui.listener;

import pl.edu.agh.eis.petrilab.gui.Configuration;
import pl.edu.agh.eis.petrilab.gui.PetriLabApplication;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Name: ModalPickListener
 * Description: ModalPickListener
 * Date: 2015-05-21
 * Created by BamBalooon
 */
public class ModalPickListener implements ItemListener {
    private final Configuration configuration;
    private final ItemListener editionPickListener;
    private final ItemListener simulationPickListener;

    public ModalPickListener(ItemListener editionPickListener, ItemListener simulationPickListener) {
        this(PetriLabApplication.getInstance().getConfiguration(), editionPickListener, simulationPickListener);
    }

    public ModalPickListener(Configuration configuration,
                             ItemListener editionPickListener,
                             ItemListener simulationPickListener) {
        this.configuration = configuration;
        this.editionPickListener = editionPickListener;
        this.simulationPickListener = simulationPickListener;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (configuration.getProperty(Configuration.SIMULATION_MODE_ACTIVE)) {
            simulationPickListener.itemStateChanged(e);
        } else {
            editionPickListener.itemStateChanged(e);
        }
    }
}
