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
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;

public class ScheduleManagerGUI extends JFrame {
    private static final String CSV_DELIMITER = ";";
    private static final String[] COLUMN_HEADERS = {
            "Curso", "Unidade Curricular", "Turno", "Turma", "Inscritos no turno",
            "Dia da semana", "Hora início da aula", "Hora fim da aula", "Data da aula",
            "Características da sala pedida para a aula", "Sala atribuída à aula", "Semana", "Semana-Semestre"
    };
    private JTable table;
    private JTextField searchField;
    private JComboBox<String> columnComboBox;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Dimension screenSize = toolkit.getScreenSize();
    private TableRowSorter<DefaultTableModel> sorter;
    
    public ScheduleManagerGUI() {
        setTitle("Schedule Manager");
        setSize(1200, 800);
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
                filterTable(searchField.getText(), (String) columnComboBox.getSelectedItem());
            }
        });

        // Create column combo box
        columnComboBox = new JComboBox<>(COLUMN_HEADERS);
        columnComboBox.setPreferredSize(new Dimension(200, 25));
        columnComboBox.setToolTipText("Select column to hide/show");

        // Create hide column button
        JButton hideColumnButton = new JButton("Hide/Show Column");
        hideColumnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedColumn = (String) columnComboBox.getSelectedItem();
                if (selectedColumn != null && !selectedColumn.isEmpty()) {
                    hideColumn(selectedColumn);
                }
            }
        });
        // Create save button
        JButton saveButton = new JButton("Save Schedule as .CSV");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSchedule();
            }
        });
        JButton saveButtonJSON = new JButton("Save Schedule as JSON");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveScheduleAsJson();
            }
        });
        // Add components to content pane
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.add(loadButton);
        panel.add(saveButton);
        panel.add(saveButtonJSON);
        panel.add(searchField);
        panel.add(columnComboBox);
        panel.add(hideColumnButton);
        getContentPane().add(panel, BorderLayout.SOUTH);


     // Create a row sorter
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
    }
    
    
    
    
    
    private void filterTable(String searchText, String columnName) {        
        int columnIndex = getColumnIndex(columnName);
        if (columnIndex != -1) {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, columnIndex));
        } else {
            System.out.println("Column not found: " + columnName);
        }
    }


    private int getColumnIndex(String columnName) {
        for (int i = 0; i < COLUMN_HEADERS.length; i++) {
            if (COLUMN_HEADERS[i].equals(columnName)) {
                return i;
            }
        }
        return -1; 
    }

    private void hideColumn(String columnName) {
        TableColumn column = table.getColumn(columnName);
        if (column.getWidth() == 0) {
            column.setMinWidth(getWidth()/13);
            column.setMaxWidth(screenSize.width/13);
            column.setWidth(getWidth()/13);
            column.setPreferredWidth(screenSize.width/13);
        } else {

            if (column != null) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
                column.setWidth(0);
                column.setPreferredWidth(0);
            }
        }
    }

    private void loadSchedule() {
    	
//    	JFileChooser fileChooser = new JFileChooser();
//        int returnValue = fileChooser.showOpenDialog(this);
//        if (returnValue == JFileChooser.APPROVE_OPTION) {
//            DefaultTableModel model = (DefaultTableModel) table.getModel();
//            model.setRowCount(0); // Clear existing rows
//        }
    	 
            try (BufferedReader reader = new BufferedReader(new FileReader(/*fileChooser.getSelectedFile()*/"horario.csv"))) {
            	DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0); // Clear existing rows
                String line;
                boolean isFirst = true;  
                while ((line = reader.readLine()) != null) {
                	if( isFirst ) {
                		isFirst = false;
                	} else {
                    String[] parts = line.split(CSV_DELIMITER);
                    int week = 0;
                    int weekSemestre=0;
                    if (parts.length>8 && !isFirst && !parts[8].isEmpty() ) {   ///a cena do isFirst não sei se é ideal porque ao dar load a um horario gravado vai ignorar a primeira linha
                    	week = getWeek(parts[8]);
                    	weekSemestre = countWeeksBetween("13/09/2022", parts[8]);
                    }
                    
                    String [] partsUpdated = Arrays.copyOf(parts,13);
                    partsUpdated[11]= String.valueOf(week);
                    partsUpdated[12] = String.valueOf(weekSemestre);
                    model.addRow(partsUpdated);
                	}
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to load schedule from CSV file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
    }
    //Function that allows saving the schedule as a CSV
    private void saveSchedule() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try (FileWriter writer = new FileWriter(fileChooser.getSelectedFile() + ".csv")) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Object value = model.getValueAt(i, j);
                        if (value != null) {
                            writer.write(value.toString());
                        }
                        writer.write(CSV_DELIMITER); // Include delimiter for each cell
                    }
                    writer.write(System.lineSeparator());
                }
                JOptionPane.showMessageDialog(this, "Schedule saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to save schedule to CSV file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    //Function that allows saving the schedule as a JSON
    private void saveScheduleAsJson() {
    	JFileChooser fileChooser = new JFileChooser();
    	int returnValue = fileChooser.showSaveDialog(this);
    	if (returnValue == JFileChooser.APPROVE_OPTION) {
    		try (FileWriter writer = new FileWriter(fileChooser.getSelectedFile() + ".json")) {
    			DefaultTableModel model = (DefaultTableModel) table.getModel();
            	StringBuilder jsonBuilder = new StringBuilder("[\n");
            	for (int i = 0; i < model.getRowCount(); i++) {
            		jsonBuilder.append("  {\n");
            		for (int j = 0; j < model.getColumnCount(); j++) {
            			Object value = model.getValueAt(i, j);
            			String columnName = table.getColumnName(j);
            			jsonBuilder.append("    \"").append(columnName).append("\": ");
            			if (value != null) {
            				jsonBuilder.append("\"").append(value.toString()).append("\"");
            			} else {
            				jsonBuilder.append("null");
            			}
            			if (j < model.getColumnCount() - 1) {
            				jsonBuilder.append(",");
            			}
            		jsonBuilder.append("\n");
            		}
                jsonBuilder.append("  }");
                if (i < model.getRowCount() - 1) {
                    jsonBuilder.append(",");
                }
                jsonBuilder.append("\n");
            }
            jsonBuilder.append("]");
            writer.write(jsonBuilder.toString());
            JOptionPane.showMessageDialog(this, "Schedule saved successfully as JSON.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save schedule as JSON.", "Error", JOptionPane.ERROR_MESSAGE);
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
    
    public static int getWeek(String strdate) {
    	 try {
             SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
             java.util.Date date = sdf.parse(strdate);
             Calendar calendar = Calendar.getInstance();
             calendar.setTime(date);
             int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
             return weekNumber;
         } catch (ParseException e) {
             e.printStackTrace();
             return -1; 
         }
    }
    
    
    //Considerando que a primeira semana é a de 13-09
    public static int countWeeksBetween(String startDateStr, String endDateStr) {
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);
        
        long weeks = ChronoUnit.WEEKS.between(startDate, endDate);
        return (int) weeks;
    }}


