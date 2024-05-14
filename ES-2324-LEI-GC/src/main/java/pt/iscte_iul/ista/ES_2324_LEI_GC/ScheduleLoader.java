package pt.iscte_iul.ista.ES_2324_LEI_GC;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ScheduleLoader {
    private static final String DELIMITER = ";";

    public static List<ScheduleEntry> loadScheduleFromCSV(String filePath) {
        List<ScheduleEntry> schedule = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(DELIMITER);
                String curso = data[0];
                String unidadeCurricular = data[1];
                String turno = data[2];
                String turma = data[3];
                String inscritos = data[4];
                String diaDaSemana = data[5];
                String horaInicio = data[6];
                String horaFim = data[7];
                String diaNum = "SD";
                String carateristicaSala = "SC";
                String salaAtribuida = "SS";
                if( data.length > 8) {
                    diaNum = data[8];
                if (data.length > 9) {
                    carateristicaSala = data[9];
                    if (data.length > 10)
                        salaAtribuida = data[10];
                }
                }
                Class myClass = new Class(curso, unidadeCurricular, turno, turma, Integer.parseInt(inscritos), carateristicaSala);
                int dayOfYear = -1;
                int year = -1;
                if( diaNum != "SD") {
                	LocalDate date = DateUtils.dateFormatter(diaNum);
                	dayOfYear = date.getDayOfYear();
                	year = date.getYear();
                	// Get or create Ano and Dia objects
                	Ano ano = getOrCreateAno(year);
                	Dia dia = findDia(dayOfYear, ano);
                	myClass.setDiaDaSemana(diaDaSemana);
                	myClass.setHoraInicio(horaInicio);
                	myClass.setHoraFim(horaFim);
                	myClass.setDataDaAula(diaNum);
                	myClass.setSalaAtribuida(salaAtribuida);
                	dia.addClass(horaInicio, myClass, salaAtribuida);
                }
                ScheduleData.addClass(myClass);
                //System.out.println(myClass.toString());
                ScheduleEntry entry = new ScheduleEntry( year, dayOfYear , myClass);
                //String[] helper = entry.toRow();
                schedule.add(entry);
               	ScheduleData.schedule = schedule;
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle file reading error
        }

        return schedule;
    }
    // Method to load room data from CSV
    public static List<Room> loadRoomsFromCSV(String filePath) {
        ScheduleData.rooms = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String[] iniLine = br.readLine().split(";"); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                String edificio = data[0];
                String nomeSala = data[1];
                List<String> caracteristicas = new ArrayList<>();
                for (int i = 5; i < data.length; i++) {
                    if (data[i].equals("X")) {
                        caracteristicas.add(iniLine[i - 1]);
                    }
                }

                Room room = new Room(edificio, nomeSala, caracteristicas);
                ScheduleData.rooms.add(room);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle file reading error
        }

        return ScheduleData.rooms;
    }
    public static Ano getOrCreateAno(int year) {
        if (ScheduleData.hasYear(year)) {
            return ScheduleData.getYear(year);
        } else {
            Ano ano = new Ano(year);
            ScheduleData.addAno(ano);
            return ano;
        }
    }
    public static Dia findDia(int dayOfYear, Ano ano) {
        for (Dia dia : ano.getDias()) {
            if (dia.getDia() == dayOfYear) {
                return dia;
            }
        }
        return null; // Dia not found for the given dayOfYear
    }
}
