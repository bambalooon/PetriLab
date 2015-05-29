package pl.edu.agh.eis.petrilab.gui.menu.analysis;

import javax.swing.JFrame;
import java.awt.Component;
import java.awt.Dimension;

/**
 * Name: GraphFrame
 * Description: GraphFrame
 * Date: 2015-05-29
 * Created by BamBalooon
 */
public class SingleComponentFrame extends JFrame {
    public static final String COVERABILITY_GRAPH_TITLE = "Graf pokrycia";
    public static final String REACHABILITY_GRAPH_TITLE = "Graf osiągalności";
    public static final String MATRIX_TITLE = "Reprezentacja macierzowa";
    private static final int MIN_WIDTH = 600;
    private static final int MIN_HEIGHT = 600;

    public SingleComponentFrame(String title, Component component) {
        super(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        getContentPane().add(component);
        pack();
        setVisible(true);
    }
}
