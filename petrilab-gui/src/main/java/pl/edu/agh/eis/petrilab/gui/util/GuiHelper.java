package pl.edu.agh.eis.petrilab.gui.util;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionListener;

/**
 * Name: GuiHelper
 * Description: GuiHelper
 * Date: 2015-05-27
 * Created by BamBalooon
 */
public class GuiHelper {
    public static final Insets MARGIN_SMALL = new Insets(1, 1, 1, 1);
    public static final Insets BUTTON_PADDING_MEDIUM = new Insets(5, 5, 5, 5);
    public static final Insets BUTTON_PADDING_SMALL = new Insets(1, 1, 1, 1);
    public static final Insets BUTTON_PADDING_NONE = new Insets(0, 0, 0, 0);

    public static JButton createButton(ActionListener actionListener, String action, String resource) {
        return createButton(actionListener, action, resource, BUTTON_PADDING_NONE);
    }

    public static JButton createButton(ActionListener actionListener, String action, String resource, Insets padding) {
        JButton button = new JButton(new ImageIcon(GuiHelper.class.getResource(resource)));
        button.setActionCommand(action);
        button.addActionListener(actionListener);
        button.setMargin(padding);
        button.setContentAreaFilled(false);
        return button;
    }
}
