package pl.edu.agh.eis.petrilab.gui.menu;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.model.Arc;
import pl.edu.agh.eis.petrilab.model.PetriNetVertex;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Name: AbstractEditionPanel
 * Description: AbstractEditionPanel
 * Date: 2015-04-19
 * Created by BamBalooon
 */
public abstract class AbstractEditionPanel<T> extends JPanel implements ActionListener {
    protected static final int TEXT_FIELD_WIDTH = 50;
    protected static final int TEXT_FIELD_HEIGHT = 20;
    protected static final int SPINNER_WIDTH = 40;
    protected static final int SPINNER_HEIGHT = 20;
    protected static final String ACCEPT_BUTTON_CMD = "ACCEPT_BUTTON_CMD";
    private final VisualizationViewer<PetriNetVertex, Arc> graphViewer;
    private T item;

    protected AbstractEditionPanel(VisualizationViewer<PetriNetVertex, Arc> graphViewer) {
        setVisible(false);
        this.graphViewer = graphViewer;
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
        }
    }

    protected abstract void loadItemState(T item);

    protected abstract void modify(T item);
}
