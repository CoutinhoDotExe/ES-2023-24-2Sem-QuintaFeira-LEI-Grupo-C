/*package pt.iscte_iul.ista.ES_2324_LEI_GC;
import java.time.LocalDate;

public class Semana {
	int semanaLetiva = -1;
	Dia[] dias = new Dia[7];
	public Semana( int semana , int ano) {
		if( semana > 36 )
			semanaLetiva = semana - 35;
		if( semana < 20 && semana > 6) {
			semanaLetiva = semana - 5;
		}
		Dia[] dias = new Dia[7];
        LocalDate startDate = LocalDate.ofYearDay(ano, semana * 7);
        int i = startDate.getDayOfWeek().getValue() - 1;
        while ( i < dias.length) {
            LocalDate date = startDate.plusDays(i);
            dias[i] = new Dia(date.getDayOfMonth(), date.getDayOfWeek().toString() );
            i++;
        }
	}
	public Dia[] getDias() {
		return dias;
	}
	public boolean isSemanaLetiva() {
		return semanaLetiva != -1;
	}
}
*/