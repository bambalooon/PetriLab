package pl.edu.agh.eis.petrilab.gui.menu.edition;

import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Name: MainPanel
 * Description: MainPanel
 * Date: 2015-05-16
 * Created by BamBalooon
 */
public class MainPanel extends JPanel implements ItemListener {
    private JPanel cardsPanel = new JPanel(new CardLayout());

    public MainPanel(ModePanel modePanel, EditionPanel editionPanel, PickingPanel pickingPanel) {
        setLayout(new BorderLayout());
        add(modePanel, BorderLayout.PAGE_START);
        cardsPanel.add(editionPanel, ModalGraphMouse.Mode.EDITING.name());
        cardsPanel.add(pickingPanel, ModalGraphMouse.Mode.PICKING.name());
        cardsPanel.add(new JPanel(), ModalGraphMouse.Mode.TRANSFORMING.name());
        ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, ModalGraphMouse.Mode.PICKING.name());
        add(cardsPanel, BorderLayout.CENTER);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        ModalGraphMouse.Mode eventMode = (ModalGraphMouse.Mode) e.getItem();
        ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, eventMode.name());
    }
}
