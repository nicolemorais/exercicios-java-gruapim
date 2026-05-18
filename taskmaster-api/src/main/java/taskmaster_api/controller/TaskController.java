package taskmaster_api.controller;

import taskmaster_api.dto.TaskRequestDTO;
import taskmaster_api.dto.TaskResponseDTO;
import taskmaster_api.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> criar(@Valid @RequestBody TaskRequestDTO dto) {
        TaskResponseDTO resposta = taskService.criarTarefa(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }
}
