package pl.edu.agh.eis.petrilab.gui.menu.analysis;

import pl.edu.agh.eis.petrilab.model2.matrix.PetriNetMatrix;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.Dimension;

import static java.lang.System.arraycopy;

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

        JPanel inMatrixPanel = new JPanel(new BorderLayout());

            JLabel inMatrixLabel = new JLabel("Macierz wejściowa:");
            inMatrixPanel.add(inMatrixLabel, BorderLayout.PAGE_START);

            JTable inMatrixTable = new MatrixTable(
                    transformMatrixToTableData(petriNetMatrix.getInMatrix()),
                    transformPlaceNamesToColumnNames(petriNetMatrix.getPlacesNames()));
            JScrollPane inMatrixTablePanel = new JScrollPane(inMatrixTable);
            inMatrixTablePanel.setPreferredSize(MATRIX_TABLE_SIZE);
            inMatrixPanel.add(inMatrixTablePanel, BorderLayout.CENTER);

        add(inMatrixPanel);

        JPanel outMatrixPanel = new JPanel(new BorderLayout());

            JLabel outMatrixLabel = new JLabel("Macierz wyjściowa:");
            outMatrixPanel.add(outMatrixLabel, BorderLayout.PAGE_START);

            JTable outMatrixTable = new MatrixTable(
                    transformMatrixToTableData(petriNetMatrix.getOutMatrix()),
                    transformPlaceNamesToColumnNames(petriNetMatrix.getPlacesNames()));
            JScrollPane outMatrixTablePanel = new JScrollPane(outMatrixTable);
            outMatrixTablePanel.setPreferredSize(MATRIX_TABLE_SIZE);
            outMatrixPanel.add(outMatrixTablePanel, BorderLayout.CENTER);

        add(outMatrixPanel);
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
