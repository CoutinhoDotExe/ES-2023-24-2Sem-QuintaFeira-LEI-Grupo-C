package pt.iscte_iul.ista.ES_2324_LEI_GC;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ScheduleManagerGUI extends JFrame {
    private static final String CSV_DELIMITER = ",";
    private static final String[] COLUMN_HEADERS = {
            "Curso", "Unidade Curricular", "Turno", "Turma", "Inscritos no turno",
            "Dia da semana", "Hora início da aula", "Hora fim da aula", "Data da aula",
            "Características da sala pedida para a aula", "Sala atribuída à aula"
    };
    private JTable table;
    private JTextField searchField;
    private JComboBox<String> columnComboBox;
    private JCheckBox keepColumnCheckBox;
    
    public ScheduleManagerGUI() {
        setTitle("Schedule Manager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create table with default table model
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (String header : COLUMN_HEADERS) {
            model.addColumn(header);
        }
        table = new JTable(model);
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        // Create load button
        JButton loadButton = new JButton("Load Schedule");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSchedule();
            }
        });

        // Create search field
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 25));
        searchField.setToolTipText("Search...");
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTable(searchField.getText());
            }
        });

        // Create column combo box
        columnComboBox = new JComboBox<>(COLUMN_HEADERS);
        columnComboBox.setPreferredSize(new Dimension(200, 25));
        columnComboBox.setToolTipText("Select column to hide");

        // Create hide column button
        JButton hideColumnButton = new JButton("Hide Column");
        hideColumnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedColumn = (String) columnComboBox.getSelectedItem();
                if (selectedColumn != null && !selectedColumn.isEmpty()) {
                    hideColumn(selectedColumn);
                }
            }
        });

        // Create keep column checkbox
        keepColumnCheckBox = new JCheckBox("Keep Column Visible");
        keepColumnCheckBox.setSelected(false);
        // Add components to content pane
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.add(loadButton);
        panel.add(searchField);
        panel.add(columnComboBox);
        panel.add(hideColumnButton);
        panel.add(keepColumnCheckBox);
        getContentPane().add(panel, BorderLayout.SOUTH);
    }

    private void filterTable(String searchText) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
    }

    private void hideColumn(String columnName) {
        TableColumn column = table.getColumn(columnName);
        if (column != null) {
            if (!keepColumnCheckBox.isSelected()) {
                table.removeColumn(column);
            }
        }
    }

    private void loadSchedule() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear existing rows

            try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(";");
                    if (parts.length == COLUMN_HEADERS.length) {
                        model.addRow(parts);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to load schedule from CSV file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ScheduleManagerGUI frame = new ScheduleManagerGUI();
                frame.setVisible(true);
            }
        });
    }
}


