package pl.edu.agh.eis.petrilab.gui.jung.mouse;

import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import pl.edu.agh.eis.petrilab.gui.menu.edition.PickingPanel;

import java.awt.event.MouseEvent;

/**
 * Name: CustomPickingGraphMousePlugin
 * Description: CustomPickingGraphMousePlugin
 * Date: 2015-05-30
 * Created by BamBalooon
 */
public class CustomPickingGraphMousePlugin<V, E> extends PickingGraphMousePlugin<V, E> {
    private PickingPanel editionPanel;
    private boolean wasPicked;

    public void setEditionPanel(PickingPanel editionPanel) {
        this.editionPanel = editionPanel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        wasPicked = vertex != null || edge != null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (wasPicked && editionPanel != null) {
            editionPanel.requestFocus();
        }
    }
}
