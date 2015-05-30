package pl.edu.agh.eis.petrilab.gui.jung.mouse;

import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import org.apache.commons.collections15.Factory;
import pl.edu.agh.eis.petrilab.gui.menu.edition.PickingPanel;

/**
 * Name: CustomEditingModalGraphMouse
 * Description: CustomEditingModalGraphMouse
 * Date: 2015-05-30
 * Created by BamBalooon
 */
public class CustomEditingModalGraphMouse<V, E> extends EditingModalGraphMouse<V, E> {

    public CustomEditingModalGraphMouse(RenderContext<V, E> rc, Factory<V> vertexFactory, Factory<E> edgeFactory) {
        super(rc, vertexFactory, edgeFactory);
    }

    public void setEditionPanel(PickingPanel editionPanel) {
        ((CustomPickingGraphMousePlugin) pickingPlugin).setEditionPanel(editionPanel);
    }

    @Override
    protected void loadPlugins() {
        super.loadPlugins();
        pickingPlugin = new CustomPickingGraphMousePlugin<>();
    }
}
