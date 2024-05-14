package pt.iscte_iul.ista.ES_2324_LEI_GC;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleData {
    private static final int INITIAL_CAPACITY = 10;
    private static Ano[] anos = new Ano[INITIAL_CAPACITY];
    private static int lastNonNullYearIndex = -1;
    static Class[] aulas = new Class[INITIAL_CAPACITY];
    private static int lastNonNullClassIndex = -1;
    public static List<Room> rooms;
    public static List<ScheduleEntry> schedule;

    public static void addAno(Ano ano) {
        if (lastNonNullYearIndex == anos.length - 1) {
            // Resize the array if it's full
            int newCapacity = anos.length * 2;
            Ano[] newArray = new Ano[newCapacity];
            System.arraycopy(anos, 0, newArray, 0, anos.length);
            anos = newArray;
        }
        // Add the new Ano and update the lastNonNullYearIndex
        anos[++lastNonNullYearIndex] = ano;
    }

    public static void addClass(Class aula) {
        if (lastNonNullClassIndex == aulas.length - 1) {
            // Resize the array if it's full
            int newCapacity = aulas.length * 2;
            Class[] newArray = new Class[newCapacity];
            System.arraycopy(aulas, 0, newArray, 0, aulas.length);
            aulas = newArray;
        }
        // Add the new Class and update the lastNonNullClassIndex
        aulas[++lastNonNullClassIndex] = aula;
    }

    public static boolean hasYear(int year) {
        for (int i = 0; i <= lastNonNullYearIndex; i++) {
            if (anos[i].getAno() == year) {
                return true;
            }
        }
        return false;
    }

    public static Ano getYear(int year) {
        for (int i = 0; i <= lastNonNullYearIndex; i++) {
            if (anos[i].getAno() == year) {
                return anos[i];
            }
        }
        return null;
    }
    public static List<String[]> getOpenSlots(){// System.out.println("Choeugou2");
    	List<String[]> openSlots = new ArrayList<>();
    	for( int i = 0; i <= lastNonNullYearIndex ; i++) {
    		for( int j = 0; j < anos[i].getDias().length ; j++) {
    			openSlots.addAll(anos[i].getDia(j).findEmptySlots());
    		}
    	}
    	return openSlots;
    }
    public static void writeListToCsvFile() { //List<ScheduleEntry> list, String filename
        try (FileWriter fileWriter = new FileWriter("toCSV")) {
            // Iterate over each object in the list
            for (ScheduleEntry obj : schedule) {
                // Convert object to CSV and write to file
                String csv = obj.toPrint();
                fileWriter.write(csv);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeListToJsonFile() { //List<ScheduleEntry> list, String filename
        try (FileWriter fileWriter = new FileWriter("toJSON")) {
            // Start JSON array
            fileWriter.write("[\n");

            // Iterate over each object in the list
            for (int i = 0; i < schedule.size(); i++) {
            	ScheduleEntry obj = schedule.get(i);
                // Convert object to JSON and write to file
                String json = obj.toJson();
                fileWriter.write(json);

                // Add comma if not last object
                if (i < schedule.size() - 1) {
                    fileWriter.write(",\n");
                }
            }

            // End JSON array
            fileWriter.write("\n]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
