package pt.iscte_iul.ista.ES_2324_LEI_GC;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class ScheduleDataTest {

    private Ano ano1;
    private Ano ano2;
    private Class aula1;
    private Class aula2;

    @BeforeEach
    void setUp() {
    	ScheduleData.rooms = new ArrayList<Room>();
        ano1 = new Ano(2023); // Substitua os parâmetros conforme necessário
        ano2 = new Ano(2024); // Substitua os parâmetros conforme necessário
        aula1 = new Class("Engenharia", "Matemática", "Manhã", "A1", 50, "Sala 101"); // Substitua os parâmetros conforme necessário
        aula2 = new Class("Ciência da Computação", "Programação", "Tarde", "B2", 40, "Sala 201"); // Substitua os parâmetros conforme necessário
        aula1.setDiaDaSemana("Segunda-feira"); // Substitua conforme necessário
        aula1.setHoraInicio("08:00"); // Substitua conforme necessário
        aula1.setHoraFim("10:00"); // Substitua conforme necessário
        aula1.setDataDaAula("2024-05-20"); // Substitua conforme necessário
        aula1.setSalaAtribuida("Sala 101"); // Substitua conforme necessário
        aula2.setDiaDaSemana("Terça-feira"); // Substitua conforme necessário
        aula2.setHoraInicio("14:00"); // Substitua conforme necessário
        aula2.setHoraFim("16:00"); // Substitua conforme necessário
        aula2.setDataDaAula("2024-05-21"); // Substitua conforme necessário
        aula2.setSalaAtribuida("Sala 201"); // Substitua conforme necessário
    }

    @Test
    void testAddAno() {
        ScheduleData.addAno(ano1);
        ScheduleData.addAno(ano2);

        assertTrue(ScheduleData.hasYear(2023));
        assertTrue(ScheduleData.hasYear(2024));
    }

    @Test
    void testAddClass() {
        ScheduleData.addClass(aula1);
        ScheduleData.addClass(aula2);

        // Testar se as aulas foram adicionadas corretamente
        // Substitua conforme necessário
        assertEquals(aula1, ScheduleData.aulas[0]);
        assertEquals(aula2, ScheduleData.aulas[1]);
    }

    @Test
    void testHasYear() {
        ScheduleData.addAno(ano1);
        assertTrue(ScheduleData.hasYear(2023));
        assertFalse(ScheduleData.hasYear(2022)); // Ano que não foi adicionado
    }

    @Test
    void testGetYear() {
        ScheduleData.addAno(ano1);
        assertEquals(ano1, ScheduleData.getYear(2023));
        assertNull(ScheduleData.getYear(2022)); // Ano que não foi adicionado
    }

    @Test
    void testGetOpenSlots() {
        // Adicione testes para verificar se os slots abertos são obtidos corretamente
    }

    // Adicione mais testes conforme necessário para outras funcionalidades da classe ScheduleData
}