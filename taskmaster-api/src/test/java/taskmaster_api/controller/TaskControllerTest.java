package taskmaster_api.controller;

import taskmaster_api.model.Task;
import taskmaster_api.repository.TaskRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
    }

    @Test
    @DisplayName("Cenário 1: Deve criar uma tarefa com sucesso via POST e salvar no banco")
    void deveCriarTarefaComSucesso() throws Exception {
        
        String jsonRequest = """
            {
                "titulo": "Estudar Testes de Integração",
                "descricao": "Praticar MockMvc com banco de dados real",
                "categoria": "Estudos",
                "dataLimite": "%s"
            }
        """.formatted(LocalDate.now().plusDays(5).toString());

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.titulo").value("Estudar Testes de Integração"))
                .andExpect(jsonPath("$.categoria").value("Estudos"));

        assertEquals(1, taskRepository.count(), "O banco deveria ter exatamente 1 tarefa salva");
    }

    @Test
    @DisplayName("Cenário 2: Deve retornar a lista de tarefas via GET do banco de dados")
    void deveListarTarefasComSucesso() throws Exception {
        
        Task tarefaExistente = new Task();
        tarefaExistente.setTitulo("Tarefa do Banco");
        tarefaExistente.setDescricao("Essa veio direto do banco de dados");
        tarefaExistente.setCategoria("Trabalho");
        tarefaExistente.setDataLimite(LocalDate.now().plusDays(2));
        
        taskRepository.save(tarefaExistente);

        mockMvc.perform(get("/tasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].titulo").value("Tarefa do Banco"))
                .andExpect(jsonPath("$.content[0].categoria").value("Trabalho"));
    }
}
