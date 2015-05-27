package pl.edu.agh.eis.petrilab.gui.menu.edition;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static pl.edu.agh.eis.petrilab.gui.util.GuiHelper.createButton;

/**
 * Name: AbstractEditionPanel
 * Description: AbstractEditionPanel
 * Date: 2015-04-19
 * Created by BamBalooon
 */
public abstract class AbstractEditionPanel<T> extends JPanel implements ActionListener {
    private static final String ACCEPT_BUTTON_CMD = "ACCEPT_BUTTON_CMD";
    private static final String ACCEPT_BUTTON_RES = "/icons/16x16/tick.png";
    private static final String REMOVE_BUTTON_CMD = "REMOVE_BUTTON_CMD";
    private static final String REMOVE_BUTTON_RES = "/icons/16x16/cross.png";

    protected JButton acceptButton;
    protected JButton removeButton;
    protected final VisualizationViewer<PetriNetVertex, Arc> graphViewer;
    private T item;

    protected AbstractEditionPanel(VisualizationViewer<PetriNetVertex, Arc> graphViewer) {
        setLayout(new GridBagLayout());
        setVisible(false);
        this.graphViewer = graphViewer;
        acceptButton = createButton(this, ACCEPT_BUTTON_CMD, ACCEPT_BUTTON_RES);
        removeButton = createButton(this, REMOVE_BUTTON_CMD, REMOVE_BUTTON_RES);
    }

    public void edit(T item) {
        setVisible(true);
        this.item = item;
        loadItemState(item);
        revalidate();
    }

    public void cancel() {
        item = null;
        setVisible(false);
        revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case ACCEPT_BUTTON_CMD:
                modify(item);
                graphViewer.repaint();
                break;
            case REMOVE_BUTTON_CMD:
                remove(item);
                graphViewer.repaint();
                break;
        }
    }

    protected abstract void loadItemState(T item);

    protected abstract void modify(T item);

    protected abstract void remove(T item);
}
