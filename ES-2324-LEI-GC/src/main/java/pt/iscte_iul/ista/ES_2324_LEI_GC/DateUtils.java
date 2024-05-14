package pt.iscte_iul.ista.ES_2324_LEI_GC;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateUtils {
    public static String getDayOfWeekName(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        Locale locale = new Locale("pt", "BR"); // Use Locale for Portuguese (Brazil)
        return dayOfWeek.getDisplayName(TextStyle.FULL, locale);
    }
    public static LocalDate dateFormatter(String dateString) {
    	//System.out.println( dateString);        // Define the date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Parse the date string into a LocalDate object
        LocalDate date = LocalDate.parse(dateString, formatter);

        return date;
    }
    
}
