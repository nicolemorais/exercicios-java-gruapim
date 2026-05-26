package taskmaster_api.service;

import taskmaster_api.model.Task;
import taskmaster_api.dto.TaskRequestDTO;
import taskmaster_api.dto.TaskResponseDTO;
import taskmaster_api.exception.DataValidation;
import taskmaster_api.exception.ResourceNotFoundException;
import taskmaster_api.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    @DisplayName("Deve criar uma tarefa com sucesso quando os dados forem válidos")
    void criarTarefa() {
        TaskRequestDTO requestDTO = new TaskRequestDTO("Academia as 10:00", "Treino de superiores", "Saúde",
                LocalDate.now().plusDays(5));

        Task tarefaSalva = new Task();
        tarefaSalva.setId(1L);
        tarefaSalva.setTitulo(requestDTO.titulo());
        tarefaSalva.setDescricao(requestDTO.descricao());
        tarefaSalva.setCategoria(requestDTO.categoria());
        tarefaSalva.setDataLimite(requestDTO.dataLimite());

        Mockito.when(taskRepository.save(any(Task.class))).thenReturn(tarefaSalva);

        TaskResponseDTO resultado = taskService.criarTarefa(requestDTO);

        assertNotNull(resultado, "O resultado não deveria ser nulo");
        assertEquals(1L, resultado.id(), "O ID retornado deveria ser 1");
        assertEquals("Academia as 10:00", resultado.titulo());
        assertEquals("Saúde", resultado.categoria());

        Mockito.verify(taskRepository, Mockito.times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar atualizar tarefa com ID inexistente")
    void atualizarTarefaComIdInexistenteLancaExcecao() {

        Long idInexistente = 99L;

        TaskRequestDTO requestDTO = new TaskRequestDTO(
                "Alterar Titulo",
                "Alterar Descricao",
                "Trabalho",
                LocalDate.now().plusDays(2));

        Mockito.when(taskRepository.findById(idInexistente)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.atualizarTarefa(idInexistente, requestDTO);
        });

        Mockito.verify(taskRepository, Mockito.never()).save(any(Task.class));
    }

    @Test
    @DisplayName("Deve lançar excessão (DataValidation) ao tentar criar uma tarefa com data no passado")
    void criarTarefaComDataNoPassado(){

        TaskRequestDTO requestDTO = new TaskRequestDTO(
            "Ler a página 15",
            "Tarefa com data no passado", 
            "Estudos", 
            LocalDate.now().minusDays(1));

            assertThrows(DataValidation.class, () -> {
                taskService.criarTarefa(requestDTO);
            });

            Mockito.verify(taskRepository, Mockito.never()).save(any(Task.class));
    }

    @Test
    @DisplayName("Deve excluir uma tarefa com sucesso quando o ID existir")
    void excluirTarefa() {

        Long id = 1L;

        Mockito.when(taskRepository.existsById(id)).thenReturn(true);

        taskService.excluirTarefa(id);

        Mockito.verify(taskRepository, Mockito.times(1)).existsById(id);
        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(id);

    }
}