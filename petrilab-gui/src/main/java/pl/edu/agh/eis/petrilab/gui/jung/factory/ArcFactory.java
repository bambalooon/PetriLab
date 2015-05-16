package pl.edu.agh.eis.petrilab.gui.jung.factory;

import org.apache.commons.collections15.Factory;
import pl.edu.agh.eis.petrilab.model2.Arc;

/**
 * Name: ArcFactory
 * Description: ArcFactory
 * Date: 2015-05-16
 * Created by BamBalooon
 */
public class ArcFactory implements Factory<Arc> {
    @Override
    public Arc create() {
        return new Arc();
    }
}
