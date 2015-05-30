package pl.edu.agh.eis.petrilab.gui.jung.keyboard;

import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Name: ModeKeyAdapter
 * Description: ModeKeyAdapter
 * Date: 2015-05-30
 * Created by BamBalooon
 */
public class ModeKeyAdapter extends KeyAdapter {
    private static final char DEFAULT_MODE_KEY = '\u001B';
    private static final ModalGraphMouse.Mode DEFAULT_MODE = ModalGraphMouse.Mode.PICKING;
    private static final int DEFAULT_CURSOR = Cursor.HAND_CURSOR;

    protected ModalGraphMouse graphMouse;
    private char pickingModeKey = '1';
    private char editingModeKey = '2';
    private char transformingModeKey = '3';
    private char annotatingModeKey = '4';

    public ModeKeyAdapter(ModalGraphMouse graphMouse) {
        this.graphMouse = graphMouse;
    }

    public ModeKeyAdapter(ModalGraphMouse graphMouse,
                          char pickingModeKey, char editingModeKey, char transformingModeKey, char annotatingModeKey) {
        this.graphMouse = graphMouse;
        this.pickingModeKey = pickingModeKey;
        this.editingModeKey = editingModeKey;
        this.transformingModeKey = transformingModeKey;
        this.annotatingModeKey = annotatingModeKey;
    }

    @Override
    public void keyTyped(KeyEvent event) {

        char keyChar = event.getKeyChar();
        if(keyChar == transformingModeKey) {
            ((Component)event.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        } else if(keyChar == pickingModeKey) {
            ((Component)event.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
        } else if(keyChar == editingModeKey) {
            ((Component)event.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            graphMouse.setMode(ModalGraphMouse.Mode.EDITING);
        } else if(keyChar == annotatingModeKey) {
            ((Component)event.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            graphMouse.setMode(ModalGraphMouse.Mode.ANNOTATING);
        } else if (keyChar == DEFAULT_MODE_KEY) {
            ((Component)event.getSource()).setCursor(Cursor.getPredefinedCursor(DEFAULT_CURSOR));
            graphMouse.setMode(DEFAULT_MODE);
        }
    }
}
