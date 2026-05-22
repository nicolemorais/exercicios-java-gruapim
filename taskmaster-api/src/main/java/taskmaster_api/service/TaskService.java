package taskmaster_api.service;

import taskmaster_api.model.Task;
import taskmaster_api.dto.TaskRequestDTO;
import taskmaster_api.dto.TaskResponseDTO;
import taskmaster_api.exception.DataValidation;
import taskmaster_api.repository.TaskRepository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public TaskResponseDTO criarTarefa(TaskRequestDTO dto) {
        
        if (dto.dataLimite().isBefore(LocalDate.now())) {
            throw new DataValidation("Não é permitido criar tarefas com datas no passado.");
        }

        Task novaTarefa = new Task(
                dto.titulo(),
                dto.descricao(),
                dto.categoria(),
                dto.dataLimite());

        Task tarefaSalva = taskRepository.save(novaTarefa);
        return new TaskResponseDTO(tarefaSalva);
    }

    @Transactional(readOnly = true)
    public Page<TaskResponseDTO> listarTarefas(Pageable pageable) {
        Page<Task> tarefasPage = taskRepository.findAll(pageable);
        return tarefasPage.map(TaskResponseDTO::new);
    }
}
