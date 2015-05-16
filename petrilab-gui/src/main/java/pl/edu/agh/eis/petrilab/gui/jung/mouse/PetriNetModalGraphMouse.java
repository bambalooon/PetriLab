package pl.edu.agh.eis.petrilab.gui.jung.mouse;

import edu.uci.ics.jung.visualization.control.*;

import java.awt.event.ItemEvent;

/**
 * Name: ModalGraphMouse
 * Description: ModalGraphMouse
 * Date: 2015-04-18
 * Created by BamBalooon
 */
@Deprecated
public class PetriNetModalGraphMouse extends AbstractModalGraphMouse {
    private GraphMousePlugin editingPlugin;
    private GraphMousePlugin selectingPlugin;


    public PetriNetModalGraphMouse() {
        super(1.1f, 1 / 1.1f);
        loadPlugins();
        setModeKeyListener(new EditingModalGraphMouse.ModeKeyAdapter(this));
    }

    @Override
    protected void loadPlugins() {
        editingPlugin = new PetriNetEditingGraphMousePlugin();
        selectingPlugin = new PetriNetSelectGraphMousePlugin();
        pickingPlugin = new PickingGraphMousePlugin<>();
        translatingPlugin = new TranslatingGraphMousePlugin();
        animatedPickingPlugin = new AnimatedPickingGraphMousePlugin<>();
        scalingPlugin = new ScalingGraphMousePlugin(new CrossoverScalingControl(), 0, in, out);
        rotatingPlugin = new RotatingGraphMousePlugin();
        shearingPlugin = new ShearingGraphMousePlugin();

        add(scalingPlugin);
        setMode(Mode.EDITING);
    }

    public void setMode(Mode mode) {
        if(this.mode != mode) {
            fireItemStateChanged(new ItemEvent(this, ItemEvent.ITEM_STATE_CHANGED,
                    this.mode, ItemEvent.DESELECTED));
            this.mode = mode;
            if(mode == Mode.TRANSFORMING) {
                setTransformingMode();
            } else if(mode == Mode.PICKING) {
                setPickingMode();
            } else if (mode == Mode.EDITING) {
                setEditingMode();
            } else if (mode == Mode.ANNOTATING) {
                setOffMode();
            }
            if(modeBox != null) {
                modeBox.setSelectedItem(mode);
            }
            fireItemStateChanged(new ItemEvent(this, ItemEvent.ITEM_STATE_CHANGED, mode, ItemEvent.SELECTED));
        }
    }

    @Override
    protected void setPickingMode() {
        remove(editingPlugin);
        remove(selectingPlugin);
        super.setPickingMode();
    }

    @Override
    protected void setTransformingMode() {
        remove(editingPlugin);
        remove(selectingPlugin);
        super.setTransformingMode();
    }

    private void setEditingMode() {
        remove(pickingPlugin);
        remove(translatingPlugin);
        remove(animatedPickingPlugin);
        remove(rotatingPlugin);
        remove(shearingPlugin);
        remove(selectingPlugin);
        add(editingPlugin);
    }

    private void setOffMode() {
        remove(pickingPlugin);
        remove(translatingPlugin);
        remove(animatedPickingPlugin);
        remove(rotatingPlugin);
        remove(shearingPlugin);
        remove(editingPlugin);
        add(selectingPlugin);
    }
}
