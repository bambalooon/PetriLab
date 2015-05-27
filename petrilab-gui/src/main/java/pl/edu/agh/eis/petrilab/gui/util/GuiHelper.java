package pl.edu.agh.eis.petrilab.gui.util;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;

/**
 * Name: GuiHelper
 * Description: GuiHelper
 * Date: 2015-05-27
 * Created by BamBalooon
 */
public class GuiHelper {
    public static final int COMPONENT_DEFAULT_WIDTH = 100;
    public static final int COMPONENT_DEFAULT_HEIGHT = 20;
    public static final Dimension COMPONENT_DEFAULT_SIZE =
            new Dimension(COMPONENT_DEFAULT_WIDTH, COMPONENT_DEFAULT_HEIGHT);
    public static final Insets MARGIN_SMALL = new Insets(1, 1, 1, 1);
    public static final Insets MARGIN_NONE = new Insets(0, 0, 0, 0);
    public static final Insets BUTTON_PADDING_MEDIUM = new Insets(5, 5, 5, 5);
    public static final Insets BUTTON_PADDING_SMALL = new Insets(1, 1, 1, 1);
    public static final Insets BUTTON_PADDING_NONE = new Insets(0, 0, 0, 0);
    public static final Border LINE_BORDER = new LineBorder(Color.black);

    public static JButton createIconButton(ActionListener actionListener, String action, String resource) {
        return createIconButton(actionListener, action, resource, BUTTON_PADDING_NONE);
    }

    public static JButton createIconButton(ActionListener actionListener, String action, String resource, Insets padding) {
        JButton button = new JButton(new ImageIcon(GuiHelper.class.getResource(resource)));
        button.setActionCommand(action);
        button.addActionListener(actionListener);
        button.setMargin(padding);
        button.setContentAreaFilled(false);
        return button;
    }

    public static JButton createTextButton(ActionListener actionListener, String action, String text) {
        return createTextButton(actionListener, action, text, BUTTON_PADDING_NONE);
    }

    public static JButton createTextButton(ActionListener actionListener, String action, String text, Insets padding) {
        JButton button = new JButton(text);
        button.setActionCommand(action);
        button.addActionListener(actionListener);
        button.setMargin(padding);
        return button;
    }
}
