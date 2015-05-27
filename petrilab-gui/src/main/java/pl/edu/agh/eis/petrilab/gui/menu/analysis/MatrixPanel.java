package pl.edu.agh.eis.petrilab.gui.menu.analysis;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import pl.edu.agh.eis.petrilab.model2.matrix.PetriNetMatrix;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;

import static java.awt.GridBagConstraints.REMAINDER;
import static pl.edu.agh.eis.petrilab.gui.util.GuiHelper.LINE_BORDER;
import static pl.edu.agh.eis.petrilab.gui.util.GuiHelper.MARGIN_SMALL;

/**
 * Name: MatrixPanel
 * Description: MatrixPanel
 * Date: 2015-05-27
 * Created by BamBalooon
 */
public class MatrixPanel extends JPanel {
    private final PetriNetMatrix matrix;

    public MatrixPanel(PetriNetMatrix matrix) {
        this.matrix = matrix;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = REMAINDER;
        gbc.insets = MARGIN_SMALL;

        JLabel inMatrixLabel = new JLabel("Macierz wejściowa:");
        add(inMatrixLabel, gbc);

        JLabel inMatrixContent = new JLabel(TEXT_TO_HTML_TRANSFORM
                .apply(MATRIX_TO_STRING_TRANSFORM.apply(matrix.getInMatrix())));
        inMatrixContent.setBorder(LINE_BORDER);
        add(inMatrixContent, gbc);

        JLabel outMatrixLabel = new JLabel("Macierz wyjściowa:");
        add(outMatrixLabel, gbc);

        JLabel outMatrixContent = new JLabel(TEXT_TO_HTML_TRANSFORM
                .apply(MATRIX_TO_STRING_TRANSFORM.apply(matrix.getOutMatrix())));
        outMatrixContent.setBorder(LINE_BORDER);
        add(outMatrixContent, gbc);
    }

    private static final Function<int[][], String> MATRIX_TO_STRING_TRANSFORM = new Function<int[][], String>() {
        @Override
        public String apply(final int[][] matrix) {
            return FluentIterable.from(Arrays.asList(matrix))
                    .transform(new Function<int[], String>() {
                        @Override
                        public String apply(int[] matrixRow) {
                            String matrixRowString = Arrays.toString(matrixRow);
                            return matrixRowString.substring(1, matrixRowString.length() - 1);
                        }
                    }).join(Joiner.on('\n'));
        }
    };

    private static final Function<String, String> TEXT_TO_HTML_TRANSFORM = new Function<String, String>() {
        @Override
        public String apply(String text) {
            return "<html>" + text.replace("\n", "<br/>") + "</html>";
        }
    };
}
