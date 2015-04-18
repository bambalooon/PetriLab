package pl.edu.agh.eis.petrilab.gui;

import javax.swing.*;

/**
 * Name: PetriLabApplication
 * Description: PetriLabApplication
 * Date: 2015-04-18
 * Created by BamBalooon
 */
public class PetriLabApplication {
    public static final String TITLE = "PetriLab";
    private static PetriLabApplication INSTANCE;
    public static PetriLabApplication getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PetriLabApplication();
        }
        return INSTANCE;
    }
    private PetriLabGui petriLabGui;

    public void startGui() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                petriLabGui = new PetriLabGui();
                petriLabGui.setVisible(true);
            }
        });
    }

    public static void main(String... args) {
        PetriLabApplication.getInstance().startGui();
    }

}
