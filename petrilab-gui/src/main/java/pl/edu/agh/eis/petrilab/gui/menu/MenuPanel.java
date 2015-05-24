package pl.edu.agh.eis.petrilab.gui.menu;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import pl.edu.agh.eis.petrilab.gui.ApplicationMode;
import pl.edu.agh.eis.petrilab.gui.PetriLabApplication;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Name: MenuPanel
 * Description: MenuPanel
 * Date: 2015-05-15
 * Created by BamBalooon
 */
public class MenuPanel extends JTabbedPane implements ChangeListener, Observer {
    private final List<TabComponent> tabComponents;
    private TabComponent currentComponent;
    public MenuPanel(List<TabComponent> tabComponents) {
        Preconditions.checkArgument(!tabComponents.isEmpty(), "List of tab components cannot be empty.");
        this.tabComponents = tabComponents;
        PetriLabApplication.getInstance().getModeManager().addObserver(this);
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

    @Override
    public void update(Observable o, Object arg) {
        final ApplicationMode currentMode = PetriLabApplication.getInstance().getModeManager().getCurrentMode();
        if (currentMode != currentComponent.getMode()) {
            Optional<TabComponent> tabComponent = FluentIterable.from(tabComponents)
                    .firstMatch(new Predicate<TabComponent>() {
                        @Override
                        public boolean apply(TabComponent tabComponent) {
                            return tabComponent.getMode() == currentMode;
                        }
                    });
            if (tabComponent.isPresent()) {
                currentComponent = tabComponent.get();
                setSelectedComponent(currentComponent.getComponent());
            }
        }
    }
}
