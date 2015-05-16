package pl.edu.agh.eis.petrilab.gui.menu;

import pl.edu.agh.eis.petrilab.gui.menu.action.Action;

import javax.swing.Icon;
import java.awt.Component;

/**
 * Name: TabComponent
 * Description: TabComponent
 * Date: 2015-05-15
 * Created by BamBalooon
 */
public class TabComponent<C extends Component> {
    private final String name;
    private final Icon icon;
    private final C component;
    private final String tip;
    private final Action tabComponentSelectedAction;
    private final Action tabComponentDeselectedAction;

    public TabComponent(String name, C component) {
        this(name, null, component, null, Action.NO_ACTION, Action.NO_ACTION);
    }

    public TabComponent(String name, C component,
                        Action tabComponentSelectedAction, Action tabComponentDeselectedAction) {
        this(name, null, component, null, tabComponentSelectedAction, tabComponentDeselectedAction);
    }

    public TabComponent(String name, Icon icon, C component, String tip,
                        Action tabComponentSelectedAction, Action tabComponentDeselectedAction) {
        this.name = name;
        this.icon = icon;
        this.component = component;
        this.tip = tip;
        this.tabComponentSelectedAction = tabComponentSelectedAction;
        this.tabComponentDeselectedAction = tabComponentDeselectedAction;
    }

    public String getName() {
        return name;
    }

    public Icon getIcon() {
        return icon;
    }

    public C getComponent() {
        return component;
    }

    public String getTip() {
        return tip;
    }

    public void onTabSelected() {
        tabComponentSelectedAction.invoke();
    }

    public void onTabDeselected() {
        tabComponentDeselectedAction.invoke();
    }
}
