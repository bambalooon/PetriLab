package pl.edu.agh.eis.petrilab.gui.menu;

import com.google.common.base.Preconditions;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.List;

/**
 * Name: MenuPanel
 * Description: MenuPanel
 * Date: 2015-05-15
 * Created by BamBalooon
 */
public class MenuPanel extends JTabbedPane implements ChangeListener {
    private final List<TabComponent> tabComponents;
    private TabComponent currentComponent;
    public MenuPanel(List<TabComponent> tabComponents) {
        Preconditions.checkArgument(!tabComponents.isEmpty(), "List of tab components cannot be empty.");
        this.tabComponents = tabComponents;
        initTabs();
        addChangeListener(this);
    }

    private void initTabs() {
        for (TabComponent tabComponent : tabComponents) {
            addTab(tabComponent.getName(), tabComponent.getIcon(), tabComponent.getComponent(), tabComponent.getTip());
        }
        currentComponent = tabComponents.get(0);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        currentComponent.onTabDeselected();
        currentComponent = tabComponents.get(getSelectedIndex());
        currentComponent.onTabSelected();
    }
}
