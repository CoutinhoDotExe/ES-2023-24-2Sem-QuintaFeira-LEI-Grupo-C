package pt.iscte_iul.ista.ES_2324_LEI_GC;

import java.time.LocalDate;

public class Ano {
    int ano;
    Dia[] dias;

    public Ano(int ano) {
        this.ano = ano;
        dias = new Dia[365];
        LocalDate startDate = LocalDate.ofYearDay(ano, 1);
        if (startDate.isLeapYear()) {
            dias = new Dia[366];
        }
        for (int i = 0; i < dias.length; i++) {
            startDate = LocalDate.ofYearDay(ano, i + 1);
            dias[i] = new Dia(startDate.getDayOfYear(), DateUtils.getDayOfWeekName(startDate), this);
        }
    }

    public Dia[] getDias() {
        return dias;
    }

    public int getAno() {
        return ano;
    }
    public Dia getDia( int dia) {
    	return dias[dia];
    }
}