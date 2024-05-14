package pt.iscte_iul.ista.ES_2324_LEI_GC;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleViewerTest {

    private ScheduleViewer scheduleViewer;
    private Ano ano;
    private Dia dia;
    private Map<Integer, Dia> diaMap;
    

    @BeforeEach
    void setUp() {
    	ScheduleData.rooms = new ArrayList<>(); // Inicializa a lista de salas
        scheduleViewer = new ScheduleViewer(new ArrayList<>());
        ano = new Ano(2024);
        dia = new Dia(1, null, ano);
        ScheduleData.addAno(ano);
        ScheduleData.schedule = new ArrayList<>();
        diaMap = new HashMap<>();
    }

    @Test
    public void testCreateSchedulePanel() {
        // Testa se o painel de agendamento é criado corretamente
        JPanel schedulePanel = scheduleViewer.createSchedulePanel(new ArrayList<>());
        assertNotNull(schedulePanel);
        // Verifica se o painel contém uma tabela
        assertEquals(2, schedulePanel.getComponentCount()); // 1 JScrollPane e 1 JPanel (filterPanel)
    }

    @Test
    public void testRescheduleClassPanel() {
        // Testa se o painel de remanejamento de aula é criado corretamente
        JPanel reschedulePanel = scheduleViewer.rescheduleClassPanel();
        assertNotNull(reschedulePanel);
        // Verifica se o painel contém os componentes esperados
        assertEquals(5, reschedulePanel.getComponentCount()); // Verifica se o painel tem 5 componentes
    }

    @Test
    public void testCreateNewClassPanel() {
        // Testa se o painel de criação de nova aula é criado corretamente
        JPanel newClassPanel = scheduleViewer.createNewClassPanel();
        assertNotNull(newClassPanel);
        // Verifica se o painel contém os componentes esperados
        assertEquals(5, newClassPanel.getComponentCount()); // Verifica se o painel tem 5 componentes
    }

    @Test
    public void testCreateRoomsPanel() {
        // Testa se o painel de salas é criado corretamente
        JPanel roomsPanel = scheduleViewer.createRoomsPanel();
        assertNotNull(roomsPanel);
        // Verifica se o painel contém uma tabela
        assertEquals(1, roomsPanel.getComponentCount());
    }

    @Test
    public void testCreateSavePanel() {
        // Testa se o painel de salvamento é criado corretamente
        JPanel savePanel = scheduleViewer.createSavePanel();
        assertNotNull(savePanel);
        // Verifica se o painel contém os componentes esperados
        assertEquals(2, savePanel.getComponentCount()); // Verifica se o painel tem 2 componentes
    }

    @Test
    void testAuxilaryAddClass() {
        Class testClass = new Class("Curso", "UnidadeCurricular", "Turno", "Turma", 20, "Caracteristica Sala");
        String hora = "08:00";
        String sala = "Sala A";

        // Adiciona a classe de teste
        scheduleViewer.auxilaryAddClass(testClass, dia.getDia(), ano.getAno(), hora, sala);

        // Verifica se a classe foi adicionada corretamente
        assertTrue(ScheduleData.schedule.contains(new ScheduleEntry(dia.getDia(), ano.getAno(), testClass)));
    }
 
}