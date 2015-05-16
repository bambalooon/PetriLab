package pl.edu.agh.eis.petrilab.gui.menu.action;

/**
 * Name: Action
 * Description: Action
 * Date: 2015-05-15
 * Created by BamBalooon
 */
public interface Action {
    void invoke();

    Action NO_ACTION = new Action() {
        @Override
        public void invoke() {
            //do nothing
        }
    };
}
