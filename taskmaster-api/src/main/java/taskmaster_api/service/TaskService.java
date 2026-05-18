package taskmaster_api.service;

import taskmaster_api.model.Task;
import taskmaster_api.dto.TaskRequestDTO;
import taskmaster_api.dto.TaskResponseDTO;
import taskmaster_api.repository.TaskRepository;
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
        Task novaTarefa = new Task(
                dto.titulo(),
                dto.descricao(),
                dto.categoria(),
                dto.dataLimite());

        Task tarefaSalva = taskRepository.save(novaTarefa);
        return new TaskResponseDTO(tarefaSalva);
    }
}
