package pt.iscte_iul.ista.ES_2324_LEI_GC;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ScheduleLoaderTest {

    @Test
    public void testLoadScheduleFromCSV() {
        String filePath = "horario.csv"; 
        List<ScheduleEntry> schedule = ScheduleLoader.loadScheduleFromCSV(filePath);
        assertFalse(schedule.isEmpty());
    }

    @Test
    public void testLoadRoomsFromCSV() {
        String filePath = "CS.csv";
        List<Room> rooms = ScheduleLoader.loadRoomsFromCSV(filePath);
        assertFalse(rooms.isEmpty());
    }
}