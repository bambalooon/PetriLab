package pl.edu.agh.eis.petrilab.gui.menu.analysis;

import pl.edu.agh.eis.petrilab.model2.matrix.PetriNetMatrix;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import static java.awt.GridBagConstraints.REMAINDER;
import static java.lang.System.arraycopy;
import static pl.edu.agh.eis.petrilab.gui.util.GuiHelper.MARGIN_SMALL;

/**
 * Name: MatrixPanel
 * Description: MatrixPanel
 * Date: 2015-05-27
 * Created by BamBalooon
 */
public class MatrixPanel extends JPanel {
    private static final Dimension MATRIX_TABLE_SIZE = new Dimension(400, 280);
    private final PetriNetMatrix petriNetMatrix;

    public MatrixPanel(PetriNetMatrix petriNetMatrix) {
        this.petriNetMatrix = petriNetMatrix;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = REMAINDER;
        gbc.insets = MARGIN_SMALL;

        JLabel inMatrixLabel = new JLabel("Macierz wejściowa:");
        add(inMatrixLabel, gbc);

        JTable inMatrixTable = new MatrixTable(
                transformMatrixToTableData(petriNetMatrix.getInMatrix()),
                transformPlaceNamesToColumnNames(petriNetMatrix.getPlacesNames()));
        JScrollPane inMatrixPanel = new JScrollPane(inMatrixTable);
        inMatrixPanel.setPreferredSize(MATRIX_TABLE_SIZE);
        add(inMatrixPanel, gbc);

        JLabel outMatrixLabel = new JLabel("Macierz wyjściowa:");
        add(outMatrixLabel, gbc);

        JTable outMatrixTable = new MatrixTable(
                transformMatrixToTableData(petriNetMatrix.getOutMatrix()),
                transformPlaceNamesToColumnNames(petriNetMatrix.getPlacesNames()));
        JScrollPane outMatrixPanel = new JScrollPane(outMatrixTable);
        outMatrixPanel.setPreferredSize(MATRIX_TABLE_SIZE);
        add(outMatrixPanel, gbc);
    }

    public String[] transformPlaceNamesToColumnNames(String[] placeNames) {
        String[] columnNames = new String[1 + placeNames.length];
        columnNames[0] = "";
        arraycopy(placeNames, 0, columnNames, 1, placeNames.length);
        return columnNames;
    }

    public Object[][] transformMatrixToTableData(int[][] matrix) {
        Object[][] cells = new Object[matrix.length][1 + matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            cells[i][0] = petriNetMatrix.getTransitionsNames()[i];
            for (int j = 0; j < matrix[i].length; j++) {
                cells[i][1 + j] = matrix[i][j];
            }
        }
        return cells;
    }

    private class MatrixTable extends JTable {
        public MatrixTable(Object[][] rowData, Object[] columnNames) {
            super(rowData, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
