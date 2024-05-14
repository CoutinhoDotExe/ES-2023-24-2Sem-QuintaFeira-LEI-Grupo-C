package pt.iscte_iul.ista.ES_2324_LEI_GC;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScheduleEntryTest {

    @Test
    void testToRow() {
        // Criar uma instância de Class para usar como ScheduledClass
        Class scheduledClass = new Class("Engenharia", "Matemática", "Manhã", "A1", 50, "Sala 101");
        // Definir outros atributos da ScheduledClass conforme necessário
        scheduledClass.setDiaDaSemana("Segunda-feira");
        scheduledClass.setHoraInicio("08:00");
        scheduledClass.setHoraFim("10:00");
        scheduledClass.setSalaAtribuida("Sala 101");

        // Criar uma instância de ScheduleEntry
        ScheduleEntry scheduleEntry = new ScheduleEntry(2024, 150, scheduledClass); // Substitua os parâmetros conforme necessário

        // Obter a representação em forma de linha
        String[] row = scheduleEntry.toRow();

        // Verificar se a representação em forma de linha contém os valores esperados
        assertEquals("Engenharia", row[0]);
        assertEquals("Matemática", row[1]);
        assertEquals("Manhã", row[2]);
        assertEquals("A1", row[3]);
        assertEquals("50", row[4]);
        assertEquals("Segunda-feira", row[5]);
        assertEquals("08:00", row[6]);
        assertEquals("10:00", row[7]);
        assertEquals("29/05/2024", row[8]); // O dia 150 do ano 2024 corresponde a 30 de maio de 2024
        assertEquals("Sala 101", row[9]);
        assertEquals("Sala 101", row[10]);
    }

    @Test
    void testToPrint() {
        // Criar uma instância de Class para usar como ScheduledClass
        Class scheduledClass = new Class("Engenharia", "Matemática", "Manhã", "A1", 50, "Sala 101");
        // Definir outros atributos da ScheduledClass conforme necessário
        scheduledClass.setDiaDaSemana("Segunda-feira");
        scheduledClass.setHoraInicio("08:00");
        scheduledClass.setHoraFim("10:00");
        scheduledClass.setSalaAtribuida("Sala 101");

        // Criar uma instância de ScheduleEntry
        ScheduleEntry scheduleEntry = new ScheduleEntry(2024, 150, scheduledClass); // Substitua os parâmetros conforme necessário

        // Obter a representação em forma de linha
        String output = scheduleEntry.toPrint();

        // Verificar se a representação em forma de linha está correta
        assertEquals("Engenharia;Matemática;Manhã;A1;50;Segunda-feira;08:00;10:00;29/05/2024;Sala 101;Sala 101;\n", output);
    }

    @Test
    void testToJson() {
        // Criar uma instância de Class para usar como ScheduledClass
        Class scheduledClass = new Class("Engenharia", "Matemática", "Manhã", "A1", 50, "Sala 101");
        // Definir outros atributos da ScheduledClass conforme necessário
        scheduledClass.setDiaDaSemana("Segunda-feira");
        scheduledClass.setHoraInicio("08:00");
        scheduledClass.setHoraFim("10:00");
        scheduledClass.setSalaAtribuida("Sala 101");

        // Criar uma instância de ScheduleEntry
        ScheduleEntry scheduleEntry = new ScheduleEntry(2024, 150, scheduledClass); // Substitua os parâmetros conforme necessário

        // Obter a representação em formato JSON
        String jsonOutput = scheduleEntry.toJson();

        // Verificar se a representação em formato JSON está correta
        assertEquals("{\"Curso\":\"Engenharia\",\"Unidade Curricular\":\"Matemática\",\"Turno\":\"Manhã\",\"Turma\":\"A1\",\"Inscritos\":50,\"Dia da Semana\":\"Segunda-feira\",\"Hora de Inicio\":\"08:00\",\"Hora de Fim\":\"10:00\",\"Dia\":\"29/05/2024\",\"Carateristicas da Sala\":\"Sala 101\",\"Sala Atribuida\":\"Sala 101\"}", jsonOutput);
    }
}