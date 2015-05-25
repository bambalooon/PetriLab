package pl.edu.agh.eis.petrilab.gui.menu.edition;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import pl.edu.agh.eis.petrilab.model2.Arc;
import pl.edu.agh.eis.petrilab.model2.PetriNetVertex;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Insets;
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
    private static final String ACCEPT_BUTTON_CMD = "ACCEPT_BUTTON_CMD";
    private static final String ACCEPT_BUTTON_RES = "/icons/16x16/tick.png";
    private static final String REMOVE_BUTTON_CMD = "REMOVE_BUTTON_CMD";
    private static final String REMOVE_BUTTON_RES = "/icons/16x16/cross.png";

    protected JButton acceptButton;
    protected JButton removeButton;
    protected final VisualizationViewer<PetriNetVertex, Arc> graphViewer;
    private T item;

    protected AbstractEditionPanel(VisualizationViewer<PetriNetVertex, Arc> graphViewer) {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setVisible(false);
        this.graphViewer = graphViewer;
        acceptButton = createButton(ACCEPT_BUTTON_CMD, ACCEPT_BUTTON_RES);
        removeButton = createButton(REMOVE_BUTTON_CMD, REMOVE_BUTTON_RES);
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

    protected JButton createButton(String action, String resource) {
        JButton button = new JButton(new ImageIcon(getClass().getResource(resource)));
        button.setActionCommand(action);
        button.addActionListener(this);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setContentAreaFilled(false);
        return button;
    }
}
