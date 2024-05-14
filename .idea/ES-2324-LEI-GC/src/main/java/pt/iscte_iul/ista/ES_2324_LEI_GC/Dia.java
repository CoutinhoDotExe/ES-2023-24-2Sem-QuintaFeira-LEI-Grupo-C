package pt.iscte_iul.ista.ES_2324_LEI_GC;




import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dia {
    private Ano ano;
    private int dia;
    private String diaNome;
    private Map<String, Map<String, List<Class>>> schedule = new HashMap<>();    // Timeslot, Sala - Aula;

    public Dia(int dia, String diaNome, Ano ano) {
        this.ano = ano;
        this.dia = dia;
        this.diaNome = diaNome;
        this.schedule = new HashMap<>();
        initializeTimeSlots();
    }

    private void initializeTimeSlots() {
        for (int i = 0; i < 30; i++) {
            String timeSlot = new TimeSlot(i).getSlot();
            Map<String, List<Class>> roomMap = new HashMap<>();
            
            // Add rooms to the roomMap
            for ( int j = 0; j < ScheduleData.rooms.size(); j++) {
                // Create an empty list of classes for each room
                roomMap.put( (ScheduleData.rooms.get(i)).getNomeSala(), new ArrayList<>());
            }
            
            // Add the roomMap to the schedule for the current time slot
            schedule.put(timeSlot, roomMap);
        }
    }

    public Ano getAno() {
        return ano;
    }
    public String getDiaNome() {
    	return diaNome;
    }
    public int getDia() {
    	return dia;
    }
    
    public String getDate() {
        return LocalDate.ofYearDay(ano.getAno(), dia).toString();
    }
    public void removeClass(String timeSlot, Class c, String room) {
    	schedule.get(timeSlot).remove(c, room);
    }
    public void addClass(String timeSlot, Class c, String room) {
        if (schedule.containsKey(timeSlot)) {
            Map<String, List<Class>> roomAvailability = schedule.get(timeSlot);
            if (roomAvailability.containsKey(room)) {
                // Ensure the list is mutable
                List<Class> classes = new ArrayList<>(roomAvailability.get(room));
                // Add the new class
                classes.add(c);
                // Update the room availability with the modified list
                roomAvailability.put(room, classes);
            } else {
                // Create a new mutable list for the room if it doesn't exist
                List<Class> classes = new ArrayList<>();
                classes.add(c);
                roomAvailability.put(room, classes);
            }
        }
    }
    public List<String[]> findEmptySlots() {
        List<String[]> emptySlots = new ArrayList<>();
        // Iterate over the schedule map
        for (Map.Entry<String, Map<String, List<Class>>> timeSlotEntry : schedule.entrySet()) {
            String timeSlot = timeSlotEntry.getKey();
            Map<String, List<Class>> roomMap = timeSlotEntry.getValue();

            // Iterate over the rooms
            for (String room : roomMap.keySet()) {
                // If there are no classes associated with the room in the current timeslot, add it to the list
                if (roomMap.get(room).isEmpty()) {
                    emptySlots.add(new String[]{ Integer.toString(ano.getAno()), Integer.toString(dia), timeSlot, room});
                }
            }
        }
        return emptySlots;
    }
    public boolean isRoomAvailable(String timeSlot, String room) {
        return schedule.containsKey(timeSlot) && schedule.get(timeSlot).containsKey(room);
    }

    // Other methods for managing time slots and rooms can be added here
}
