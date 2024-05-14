package pt.iscte_iul.ista.ES_2324_LEI_GC;

import javax.swing.*;
import java.util.List;

public class ScheduleMain {

    public static void main(String[] args) {
        String filePath = "horario.csv";
        ScheduleLoader.loadRoomsFromCSV("CS.csv");
        List<ScheduleEntry> schedule = ScheduleLoader.loadScheduleFromCSV(filePath);
        ScheduleData.schedule = schedule;
        // Create and display the ScheduleViewer frame
        SwingUtilities.invokeLater(() -> new ScheduleViewer(ScheduleData.schedule).setVisible(true));
    }
}