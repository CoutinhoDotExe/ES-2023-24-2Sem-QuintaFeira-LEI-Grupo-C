package pt.iscte_iul.ista.ES_2324_LEI_GC;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;


public class ScheduleViewer extends JFrame {
    private JTabbedPane tabbedPane;
    private JTextField[] filterFields;
    private JTextField[] filterFields2;
    private JTextField[] filterFields3;
    
    public ScheduleViewer(List<ScheduleEntry> schedule) {
        setTitle("Schedule Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        // Create and add tabs
        tabbedPane.addTab("Schedule", createSchedulePanel(schedule));
        tabbedPane.addTab("Rooms", createRoomsPanel());
        tabbedPane.addTab("Reschedule", rescheduleClassPanel());
        tabbedPane.addTab("New Class", createNewClassPanel());
        tabbedPane.addTab("Save", createSavePanel());
        getContentPane().add(tabbedPane);
    }

	private JPanel createSchedulePanel(List<ScheduleEntry> schedule) {
        JPanel schedulePanel = new JPanel(new BorderLayout());
        // Create table model with column names
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Curso", "UnidadeCurricular", "Turno", "Turma", "Inscritos", "Dia Da Semana", "Hora de Inicio", "Hora de Fim", "Dia", "Carateristica Sala", "Sala Atribuida"});
        // Populate table model with schedule data
        for (ScheduleEntry entry : schedule) {
            String[] helper = entry.toRow();
            model.addRow(new Object[]{ helper[0], helper[1], helper[2], helper[3], helper[4], helper[5], helper[6], helper[7], helper[8], helper[9], helper[10] });
        }
        // Create schedule table with table model
        JTable scheduleTable = new JTable(model);
        scheduleTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        schedulePanel.add(scrollPane, BorderLayout.CENTER);

        // Create filter panel
        JPanel filterPanel = new JPanel(new GridLayout(1, model.getColumnCount()));
        filterFields = new JTextField[model.getColumnCount()];
        for (int i = 0; i < model.getColumnCount(); i++) {
            JTextField filterField = new JTextField();
            filterField.addActionListener(new FilterActionListener(i, scheduleTable));
            filterFields[i] = filterField;
            filterPanel.add(filterField);
        }
        schedulePanel.add(filterPanel, BorderLayout.NORTH);
        return schedulePanel;
    }
	private JPanel rescheduleClassPanel() {
	    JPanel rescheduleClassPanel = new JPanel(new BorderLayout());

	    // Create labels and text fields for input
	    JLabel cursoLabel = new JLabel("Curso:");
	    JTextField cursoField = new JTextField();

	    JLabel unidadeCurricularLabel = new JLabel("Unidade Curricular:");
	    JTextField unidadeCurricularField = new JTextField();

	    JLabel turnoLabel = new JLabel("Turno:");
	    JTextField turnoField = new JTextField();

	    JLabel turmaLabel = new JLabel("Turma:");
	    JTextField turmaField = new JTextField();

	    JLabel inscritosLabel = new JLabel("Inscritos:");
	    JTextField inscritosField = new JTextField();

	    JLabel diaSemanaLabel = new JLabel("Dia semana:");
	    JTextField diaSemanaField = new JTextField();
	    
	    JLabel horaInicioLabel = new JLabel("Hora Inicio:");
	    JTextField horaInicioField = new JTextField();
	    
	    JLabel horaFimLabel = new JLabel("Hora Fim:");
	    JTextField horaFimField = new JTextField();
	    
	    JLabel diaLabel = new JLabel("Dia:");
	    JTextField diaField = new JTextField();

	    JLabel salaCaracteristicasLabel = new JLabel("Caraterísticas da Sala:");
	    JTextField salaCaracteristicasField = new JTextField();
	    
	    JLabel salaLabel = new JLabel("Sala:");
	    JTextField salaField = new JTextField();
	    // Create button to load open slots
	    // Arrange components using layout manager
	    JPanel inputPanel = new JPanel(new GridLayout(6, 2));
	    inputPanel.add(cursoLabel);
	    inputPanel.add(cursoField);
	    inputPanel.add(unidadeCurricularLabel);
	    inputPanel.add(unidadeCurricularField);
	    inputPanel.add(turnoLabel);
	    inputPanel.add(turnoField);
	    inputPanel.add(turmaLabel);
	    inputPanel.add(turmaField);
	    inputPanel.add(inscritosLabel);
	    inputPanel.add(inscritosField);
	    inputPanel.add(diaSemanaLabel);
	    inputPanel.add(diaSemanaField);
	    inputPanel.add(horaInicioLabel);
	    inputPanel.add(horaInicioField);
	    inputPanel.add(horaFimLabel);
	    inputPanel.add(horaFimField);
	    inputPanel.add(diaLabel);
	    inputPanel.add(diaField);
	    inputPanel.add(salaCaracteristicasLabel);
	    inputPanel.add(salaCaracteristicasField);
	    inputPanel.add(salaLabel);
	    inputPanel.add(salaField);
	    // Create table model with column names
	    DefaultTableModel modelITO2 = new DefaultTableModel();
	    modelITO2.setColumnIdentifiers(new String[]{"Sala", "Dia da Semana", "Hora do slot", "Data"});
	    JButton loadOpenSlotsButton = new JButton("Load Open Slots");
	    loadOpenSlotsButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	List<String[]> auxiliar = ScheduleData.getOpenSlots(); //row 0 ano, row 1 dia em contagem do ano, row 2 é slot, row 3 é sala 
	        	for (String[] row : auxiliar) {
	        		modelITO2.addRow(new Object[]{ row[3], LocalDate.ofYearDay(Integer.parseInt(row[0]), Integer.parseInt(row[1]))
                            .getDayOfWeek()
                            .getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("pt-BR"))   , row[2], (LocalDate.ofYearDay( Integer.parseInt(row[0]), Integer.parseInt(row[1]))).toString()  });

	        	}
	        	modelITO2.fireTableDataChanged();
	        }
	    });
	    // Create new class table with table model
	    JTable newClassTable = new JTable(modelITO2);
	    newClassTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

	    JScrollPane scrollPane = new JScrollPane(newClassTable);  
	    // Create the filter panel
        JPanel filterPanel = new JPanel(new GridLayout(1, modelITO2.getColumnCount()));
        filterFields3 = new JTextField[modelITO2.getColumnCount()];
        for (int i = 0; i < modelITO2.getColumnCount(); i++) {
            JTextField filterField = new JTextField();
            filterField.addActionListener(new FilterActionListener(i, newClassTable));
            filterFields3[i] = filterField;
            filterPanel.add(filterField);
        }
        JButton addButton = new JButton("Reschedule Class");
	    addButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // Retrieve values from text fields
	            String curso = cursoField.getText();
	            //System.out.println(curso);
	            String unidadeCurricular = unidadeCurricularField.getText();
	           // System.out.println(unidadeCurricular);
	            String turno = turnoField.getText();
	           // System.out.println(turno);
	            String turma = turmaField.getText();
	          //  System.out.println(turma);
	            String inscritos = inscritosField.getText();
	         //   System.out.println(inscritos);
	            String diaSemana = diaSemanaField.getText();
	         //   System.out.println(diaSemana);
	            String horaInicio = horaInicioField.getText();
	         //   System.out.println(horaInicio);
	            String horaFim = horaFimField.getText();
	        //    System.out.println(horaFim);
	            String dia = diaField.getText();
	           // System.out.println(dia);
	            String salaCaracteristicas = salaCaracteristicasField.getText();
	        //    System.out.println(salaCaracteristicas);
	            String sala = salaField.getText();
	          //  System.out.println(sala);
	            Class nova =  new Class( curso, unidadeCurricular, turno, turma, Integer.parseInt(inscritos), salaCaracteristicas);  //Class(String curso, String unidadeCurricular, String turno, String turma, int inscritos, String caraSala)
            	nova.setDiaDaSemana(diaSemana);
            	nova.setHoraInicio(horaInicio);
            	nova.setHoraFim(horaFim);
            	nova.setDataDaAula(dia);
            	nova.setSalaAtribuida(sala);
            	LocalDate date = DateUtils.dateFormatter(dia);
            	int dayOfYear = date.getDayOfYear();
            	int year = date.getYear();
            	auxilaryAddClass( nova, dayOfYear, year, horaInicio, sala);
            	//auxilaryRemoveClass();                              /////asdasdas
	            cursoField.setText("");
	            unidadeCurricularField.setText("");
	            turnoField.setText("");
	            turmaField.setText("");
	            inscritosField.setText("");
	            diaSemanaField.setText("");
	            horaInicioField.setText("");
	            horaFimField.setText("");
	            diaField.setText("");
	            salaCaracteristicasField.setText("");
	            salaField.setText("");
	        }
	    });
	    //Add input panel, filter panel, table, buttons to the new class panel
	    rescheduleClassPanel.add(inputPanel, BorderLayout.NORTH);
	    rescheduleClassPanel.add(scrollPane, BorderLayout.CENTER);
	    rescheduleClassPanel.add(addButton, BorderLayout.EAST);
	    rescheduleClassPanel.add(loadOpenSlotsButton, BorderLayout.WEST); // Add the button to the left side
	    rescheduleClassPanel.add(filterPanel, BorderLayout.SOUTH);
	    return rescheduleClassPanel;
	}
	
	private JPanel createNewClassPanel() {
	    JPanel newClassPanel = new JPanel(new BorderLayout());

	    // Create labels and text fields for input
	    JLabel cursoLabel = new JLabel("Curso:");
	    JTextField cursoField = new JTextField();

	    JLabel unidadeCurricularLabel = new JLabel("Unidade Curricular:");
	    JTextField unidadeCurricularField = new JTextField();

	    JLabel turnoLabel = new JLabel("Turno:");
	    JTextField turnoField = new JTextField();

	    JLabel turmaLabel = new JLabel("Turma:");
	    JTextField turmaField = new JTextField();

	    JLabel inscritosLabel = new JLabel("Inscritos:");
	    JTextField inscritosField = new JTextField();

	    JLabel diaSemanaLabel = new JLabel("Dia semana:");
	    JTextField diaSemanaField = new JTextField();
	    
	    JLabel horaInicioLabel = new JLabel("Hora Inicio:");
	    JTextField horaInicioField = new JTextField();
	    
	    JLabel horaFimLabel = new JLabel("Hora Fim:");
	    JTextField horaFimField = new JTextField();
	    
	    JLabel diaLabel = new JLabel("Dia:");
	    JTextField diaField = new JTextField();

	    JLabel salaCaracteristicasLabel = new JLabel("Caraterísticas da Sala:");
	    JTextField salaCaracteristicasField = new JTextField();
	    
	    JLabel salaLabel = new JLabel("Sala:");
	    JTextField salaField = new JTextField();
	    // Create button to load open slots
	    // Arrange components using layout manager
	    JPanel inputPanel = new JPanel(new GridLayout(6, 2));
	    inputPanel.add(cursoLabel);
	    inputPanel.add(cursoField);
	    inputPanel.add(unidadeCurricularLabel);
	    inputPanel.add(unidadeCurricularField);
	    inputPanel.add(turnoLabel);
	    inputPanel.add(turnoField);
	    inputPanel.add(turmaLabel);
	    inputPanel.add(turmaField);
	    inputPanel.add(inscritosLabel);
	    inputPanel.add(inscritosField);
	    inputPanel.add(diaSemanaLabel);
	    inputPanel.add(diaSemanaField);
	    inputPanel.add(horaInicioLabel);
	    inputPanel.add(horaInicioField);
	    inputPanel.add(horaFimLabel);
	    inputPanel.add(horaFimField);
	    inputPanel.add(diaLabel);
	    inputPanel.add(diaField);
	    inputPanel.add(salaCaracteristicasLabel);
	    inputPanel.add(salaCaracteristicasField);
	    inputPanel.add(salaLabel);
	    inputPanel.add(salaField);
	    // Create table model with column names
	    DefaultTableModel modelITO = new DefaultTableModel();
	    modelITO.setColumnIdentifiers(new String[]{"Sala", "Dia da Semana", "Hora do slot", "Data"});
	    JButton loadOpenSlotsButton = new JButton("Load Open Slots");
	    
	    
	    loadOpenSlotsButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	List<String[]> auxiliar = ScheduleData.getOpenSlots(); //row 0 ano, row 1 dia em contagem do ano, row 2 é slot, row 3 é sala 
	        	for (String[] row : auxiliar) {
	        		modelITO.addRow(new Object[]{ row[3], LocalDate.ofYearDay(Integer.parseInt(row[0]), Integer.parseInt(row[1]))
                            .getDayOfWeek()
                            .getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("pt-BR"))   , row[2], (LocalDate.ofYearDay( Integer.parseInt(row[0]), Integer.parseInt(row[1]))).toString()  });

	        	}
	        	modelITO.fireTableDataChanged();
	        }
	    });
	    
	    
	    // Create new class table with table model
	    JTable newClassTable = new JTable(modelITO);
	    newClassTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

	    JScrollPane scrollPane = new JScrollPane(newClassTable);  
	    // Create the filter panel
        JPanel filterPanel = new JPanel(new GridLayout(1, modelITO.getColumnCount()));
        filterFields2 = new JTextField[modelITO.getColumnCount()];
        for (int i = 0; i < modelITO.getColumnCount(); i++) {
            JTextField filterField = new JTextField();
            filterField.addActionListener(new FilterActionListener(i, newClassTable));
            filterFields2[i] = filterField;
            filterPanel.add(filterField);
        }
        JButton addButton = new JButton("Add New Class");
	    addButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	        //ScheduleEntry selectedEntry = filteredSchedule.get(selectedRowIndex);
	        	        // Process the selected entry here...//FALLALASLDLASLDALSDASDFDSFDSSDFSFASDFGSADGSDFGSDGFSDGF 
	            // Retrieve values from text fields
	            String curso = cursoField.getText();
	            //System.out.println(curso);
	            String unidadeCurricular = unidadeCurricularField.getText();
	           // System.out.println(unidadeCurricular);
	            String turno = turnoField.getText();
	           // System.out.println(turno);
	            String turma = turmaField.getText();
	          //  System.out.println(turma);
	            String inscritos = inscritosField.getText();
	         //   System.out.println(inscritos);
	            String diaSemana = diaSemanaField.getText();
	         //   System.out.println(diaSemana);
	            String horaInicio = horaInicioField.getText();
	         //   System.out.println(horaInicio);
	            String horaFim = horaFimField.getText();
	        //    System.out.println(horaFim);
	            String dia = diaField.getText();
	            System.out.println(dia);
	            String salaCaracteristicas = salaCaracteristicasField.getText();
	        //    System.out.println(salaCaracteristicas);
	            String sala = salaField.getText();
	          //  System.out.println(sala);

	            // Validate inputs (you can add more validation as needed)
	            // Create a new class or perform any necessary action with the input data
	            // For example:
	            Class nova =  new Class( curso, unidadeCurricular, turno, turma, Integer.parseInt(inscritos), salaCaracteristicas);  //Class(String curso, String unidadeCurricular, String turno, String turma, int inscritos, String caraSala)
            	nova.setDiaDaSemana(diaSemana);
            	nova.setHoraInicio(horaInicio);
            	nova.setHoraFim(horaFim);
            	nova.setDataDaAula(dia);
            	nova.setSalaAtribuida(sala);
            	LocalDate date = DateUtils.dateFormatter(dia);
            	int dayOfYear = date.getDayOfYear();
            	int year = date.getYear();
            	auxilaryAddClass( nova, dayOfYear, year, horaInicio, sala);
	            auxilaryRemoveClass( nova, dayOfYear, year, horaInicio, sala);
	            cursoField.setText("");
	            unidadeCurricularField.setText("");
	            turnoField.setText("");
	            turmaField.setText("");
	            inscritosField.setText("");
	            diaSemanaField.setText("");
	            horaInicioField.setText("");
	            horaFimField.setText("");
	            diaField.setText("");
	            salaCaracteristicasField.setText("");
	            salaField.setText("");
	        }
	    });
	    //Add input panel, filter panel, table, buttons to the new class panel
	    newClassPanel.add(inputPanel, BorderLayout.NORTH);
	    newClassPanel.add(scrollPane, BorderLayout.CENTER);
	    newClassPanel.add(addButton, BorderLayout.EAST);
	    newClassPanel.add(loadOpenSlotsButton, BorderLayout.WEST); // Add the button to the left side
	    newClassPanel.add(filterPanel, BorderLayout.SOUTH);
	    return newClassPanel;
	}
	public void auxilaryRemoveClass( Class classe, int dia, int ano, String hora, String sala ) {
		ScheduleData.getYear(ano).getDia(dia).removeClass(hora, classe, sala);
		for( int i = 0; i < ScheduleData.schedule.size(); i++) {
			if( ScheduleData.schedule.get(i).getDayOfYear() == dia && ScheduleData.schedule.get(i).getYear() == ano && ScheduleData.schedule.get(i).getScheduledClass().getCurso() == classe.getCurso( )
						&& ScheduleData.schedule.get(i).getScheduledClass().getTurno() == classe.getTurno( ) && ScheduleData.schedule.get(i).getScheduledClass().getTurma() == classe.getTurma()
						&& ScheduleData.schedule.get(i).getScheduledClass().getInscritos() == classe.getInscritos() && ScheduleData.schedule.get(i).getScheduledClass().getCaraSala() == classe.getCaraSala() ){
				 ScheduleData.schedule.remove(i);
				 return;
			}
		}
	}
	public void auxilaryAddClass(Class classe, int dia, int ano, String hora, String sala ) {
    	Ano ano2 = ScheduleData.getYear(ano);
    	Dia dia2 = ano2.getDia(dia );
    	ScheduleEntry epic = new ScheduleEntry(dia, ano, classe);
    	ScheduleData.schedule.add(epic);
        dia2.addClass(hora, classe, sala);
	}
    private JPanel createRoomsPanel() {
        JPanel roomsPanel = new JPanel(new BorderLayout());

        // Create table model with column names
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Edifício", "Nome Sala", "Características"});
        // Populate table model with room data from CSV
        for (Room room : ScheduleData.rooms) {
            model.addRow(new Object[]{room.getEdificio(), room.getNomeSala(), room.getCarateristica()});
        }
        // Create rooms table with table model
        JTable roomsTable = new JTable(model);
        roomsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(roomsTable);
        roomsPanel.add(scrollPane, BorderLayout.CENTER);
        return roomsPanel;
    }

    private JPanel createReschedulePanel() { //////////////////////////////////////////imitar newclass
        JPanel reschedulePanel = new JPanel(new BorderLayout());
        // Create panel for reschedule tab
        JPanel inputPanel = new JPanel(new GridLayout(0, 2)); // Two columns
        // Add a label to display the selected row information
        JLabel selectedRowLabel = new JLabel("Selected Row: ");
        inputPanel.add(selectedRowLabel);
        // Add fields for rescheduling
        inputPanel.add(new JLabel("New Date:"));
        JTextField dateField = new JTextField();
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("New Time:"));
        JTextField timeField = new JTextField();
        inputPanel.add(timeField);
        inputPanel.add(new JLabel("New Room:"));
        JTextField roomField = new JTextField();
        inputPanel.add(roomField);
        reschedulePanel.add(inputPanel, BorderLayout.CENTER);
        // Create the "Reschedule" button
        JButton rescheduleButton = new JButton("Reschedule");
        rescheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle rescheduling here...
            }
        });
        reschedulePanel.add(rescheduleButton, BorderLayout.SOUTH);

        return reschedulePanel;
    }
    
    private JPanel createSavePanel() {
    	JPanel savePanel = new JPanel(new BorderLayout());
        JButton saveButton = new JButton("Save Schedule as .CSV");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               ScheduleData.writeListToCsvFile();
            }
        });
        savePanel.add(saveButton, BorderLayout.CENTER);
        JButton saveButtonJSON = new JButton("Save Schedule as JSON");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScheduleData.writeListToJsonFile();
            }
        });
        savePanel.add(saveButtonJSON, BorderLayout.SOUTH);
    	return savePanel;
    }

    private class FilterActionListener implements ActionListener {
        private int columnIndex;
        private JTable table;

        public FilterActionListener(int columnIndex, JTable table) {
            this.columnIndex = columnIndex;
            this.table = table;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String text = filterFields[columnIndex].getText();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
            table.setRowSorter(sorter);
            if (text.contains("&&") || text.contains("||")) {
                List<RowFilter<DefaultTableModel, Object>> filters = new ArrayList<>();
                String[] parts = text.split("\\s*(&&|\\|\\|)\\s*");
                for (String part : parts) {
                    try {
                        RowFilter<DefaultTableModel, Object> filter = RowFilter.regexFilter("(?i)" + part, columnIndex);
                        filters.add(filter);
                    } catch (java.util.regex.PatternSyntaxException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid filter expression: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                RowFilter<DefaultTableModel, Object> combinedFilter = null;
                if (text.contains("&&")) {
                    combinedFilter = RowFilter.andFilter(filters);
                } else if (text.contains("||")) {
                    combinedFilter = RowFilter.orFilter(filters);
                }
                sorter.setRowFilter(combinedFilter);
            } else {
                RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter("(?i)" + text, columnIndex);
                sorter.setRowFilter(rowFilter);
            }
        }
    }
}
