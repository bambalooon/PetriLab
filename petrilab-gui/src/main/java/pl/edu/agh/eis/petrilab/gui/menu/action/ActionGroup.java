package pl.edu.agh.eis.petrilab.gui.menu.action;

import java.util.Arrays;
import java.util.List;

/**
 * Name: ActionGroup
 * Description: ActionGroup
 * Date: 2015-05-19
 * Created by BamBalooon
 */
public class ActionGroup implements Action {
    private final List<Action> actions;

    public ActionGroup(List<Action> actions) {
        this.actions = actions;
    }

    public ActionGroup(Action... actions) {
        this.actions = Arrays.asList(actions);
    }

    @Override
    public void invoke() {
        for (Action action : actions) {
            action.invoke();
        }
    }
}
