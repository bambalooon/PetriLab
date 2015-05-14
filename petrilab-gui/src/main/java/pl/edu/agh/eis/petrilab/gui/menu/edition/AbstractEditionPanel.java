package pl.edu.agh.eis.petrilab.gui.menu.edition;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.model.Arc;
import pl.edu.agh.eis.petrilab.model.PetriNetVertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Name: AbstractEditionPanel
 * Description: AbstractEditionPanel
 * Date: 2015-04-19
 * Created by BamBalooon
 */
public abstract class AbstractEditionPanel<T> extends JPanel implements ActionListener {
    protected static final int BUTTON_WIDTH = 80;
    protected static final int BUTTON_HEIGHT = 20;
    protected static final int TEXT_FIELD_WIDTH = 50;
    protected static final int TEXT_FIELD_HEIGHT = 20;
    protected static final int SPINNER_WIDTH = 40;
    protected static final int SPINNER_HEIGHT = 20;
    private static final String ACCEPT_BUTTON_CMD = "ACCEPT_BUTTON_CMD";
    private static final String ACCEPT_BUTTON_LABEL = "ok";

    protected JButton acceptButton;
    private final VisualizationViewer<PetriNetVertex, Arc> graphViewer;
    private T item;

    protected AbstractEditionPanel(VisualizationViewer<PetriNetVertex, Arc> graphViewer) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setVisible(false);
        this.graphViewer = graphViewer;
        acceptButton = new JButton();
        acceptButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        acceptButton.setActionCommand(ACCEPT_BUTTON_CMD);
        acceptButton.addActionListener(this);
        acceptButton.setText(ACCEPT_BUTTON_LABEL);
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
