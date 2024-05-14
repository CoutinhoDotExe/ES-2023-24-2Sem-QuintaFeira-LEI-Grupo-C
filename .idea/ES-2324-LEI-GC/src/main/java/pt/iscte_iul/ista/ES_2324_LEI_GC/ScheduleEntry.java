package pt.iscte_iul.ista.ES_2324_LEI_GC;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ScheduleEntry {
    private int year;
    private int dayOfYear;
    private Class scheduledClass;

    public ScheduleEntry(int year, int dayOfYear, Class scheduledClass) {
        this.year = year;
        this.dayOfYear = dayOfYear;
        this.scheduledClass = scheduledClass;
    }

    // Getters and setters for year, dayOfYear, and scheduledClass

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public int getDayOfYear() {
        return dayOfYear;
    }

    public void setDayOfYear(int dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    public Class getScheduledClass() {
        return scheduledClass;
    }

    public void setScheduledClass(Class scheduledClass) {
        this.scheduledClass = scheduledClass;
    }
    public String[] toRow() {
        String[] row = new String[11]; // Assuming there are 11 attributes in the Class class
        row[0] = scheduledClass.getCurso();
        row[1] = scheduledClass.getUnidadeCurricular();
        row[2] = scheduledClass.getTurno();
        row[3] = scheduledClass.getTurma();
        row[4] = String.valueOf(scheduledClass.getInscritos());
        row[5] = scheduledClass.getDiaDaSemana();
        row[6] = scheduledClass.getHoraInicio();
        row[7] = scheduledClass.getHoraFim();
        if( year <= 0 || dayOfYear <= 0 || dayOfYear >= 366 ) {
        	row[8] = "NA";
        }else {
        	LocalDate date = LocalDate.ofYearDay(year, dayOfYear);
        	row[8] = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        row[9] = scheduledClass.getCaraSala();
        row[10] = scheduledClass.getSalaAtribuida();

        return row;
    }
    // Other methods as needed
    public String toPrint() {
    	String[] aux = this.toRow();
    	return aux[0]+";"+aux[1]+";"+aux[2]+";"+aux[3]+";"+aux[4]+";"+aux[5]+";"+aux[6]+";"+aux[7]+";"+aux[8]+";"+aux[9]+";"+aux[10]+";"+"\n";
    }
    public String toJson() {
        String[] aux = this.toRow();
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        jsonBuilder.append("\"Curso\":").append(quoteIfNeeded(aux[0])).append(",");
        jsonBuilder.append("\"Unidade Curricular\":").append(quoteIfNeeded(aux[1])).append(",");
        jsonBuilder.append("\"Turno\":").append(quoteIfNeeded(aux[2])).append(",");
        jsonBuilder.append("\"Turma\":").append(quoteIfNeeded(aux[3])).append(",");
        jsonBuilder.append("\"Inscritos\":").append(quoteIfNeeded(aux[4])).append(",");
        jsonBuilder.append("\"Dia da Semana\":").append(quoteIfNeeded(aux[5])).append(",");
        jsonBuilder.append("\"Hora de Inicio\":").append(quoteIfNeeded(aux[6])).append(",");
        jsonBuilder.append("\"Hora de Fim\":").append(quoteIfNeeded(aux[7])).append(",");
        jsonBuilder.append("\"Dia\":").append(quoteIfNeeded(aux[8])).append(",");
        jsonBuilder.append("\"Carateristicas da Sala\":").append(quoteIfNeeded(aux[9])).append(",");
        jsonBuilder.append("\"Sala Atribuida\":").append(quoteIfNeeded(aux[10]));
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    private String quoteIfNeeded(String value) {
        try {
            Integer.parseInt(value); // Check if the value is numeric
            return value; // Return value without quotes if it's numeric
        } catch (NumberFormatException e) {
            // If parsing fails, the value is not numeric, so quote it
            return "\"" + value + "\"";
        }
    }
}

