 /**
 * 
 */
package pt.iscte_iul.ista.ES_2324_LEI_GC;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Component;
import java.awt.Container;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;

/**
 * 
 */
class ScheduleManagerGUITest extends ScheduleManagerGUI {

	private ScheduleManagerGUI scheduleManagerGUI;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		SwingUtilities.invokeLater(() -> {
            scheduleManagerGUI = new ScheduleManagerGUI();
            scheduleManagerGUI.setVisible(true);
        });
        try {
            Thread.sleep(1000); // Espera 1 segundo para que a GUI seja completamente inicializada
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		if (scheduleManagerGUI != null) {
            scheduleManagerGUI.dispose();
        }
	}
	@Test
    public void testLoadSchedule() {
        // Teste para garantir que o carregamento do horário funciona corretamente
        assertNotNull(scheduleManagerGUI); // Verifica se a instância da GUI não é nula
        scheduleManagerGUI.loadSchedule();
        // Inserir mais asserções conforme necessário para verificar se o carregamento foi bem-sucedido
    }

    @Test
    public void testSaveSchedule() {
        // Teste para garantir que o salvamento do horário em formato CSV funciona corretamente
        assertNotNull(scheduleManagerGUI); // Verifica se a instância da GUI não é nula
        JButton saveButton = findButtonByText(scheduleManagerGUI, "Save Schedule as .CSV");
        assertNotNull(saveButton); // Verifica se o botão de salvamento em CSV está presente
        saveButton.doClick(); // Simula o clique no botão de salvamento em CSV
        // Inserir mais asserções conforme necessário para verificar se o salvamento foi bem-sucedido
    }

    @Test
    public void testSaveScheduleAsJson() {
        // Teste para garantir que o salvamento do horário em formato JSON funciona corretamente
        assertNotNull(scheduleManagerGUI); // Verifica se a instância da GUI não é nula
        JButton saveButtonJSON = findButtonByText(scheduleManagerGUI, "Save Schedule as JSON");
        assertNotNull(saveButtonJSON); // Verifica se o botão de salvamento em JSON está presente
        saveButtonJSON.doClick(); // Simula o clique no botão de salvamento em JSON
        // Inserir mais asserções conforme necessário para verificar se o salvamento foi bem-sucedido
    }
 // Método auxiliar para encontrar um botão em uma GUI Swing pelo texto do botão
    private JButton findButtonByText(Container container, String text) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals(text)) {
                    return button;
                }
            } else if (component instanceof Container) {
                JButton button = findButtonByText((Container) component, text);
                if (button != null) {
                    return button;
                }
            }
        }
        return null;
    }
    public static void assertNotNull(Object object) {
        if (object == null) {
            throw new AssertionError("Expected non-null object, but found null");
        }
    }

}
