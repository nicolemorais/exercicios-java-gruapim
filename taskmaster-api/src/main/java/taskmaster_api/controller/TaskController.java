package taskmaster_api.controller;

import taskmaster_api.dto.TaskRequestDTO;
import taskmaster_api.dto.TaskResponseDTO;
import taskmaster_api.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@Validated
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public Page<TaskResponseDTO> listar(
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "O número da página não pode ser menor que zero.") int page,

            @RequestParam(defaultValue = "10") @Min(value = 1, message = "O tamanho da página deve ser de pelo menos 1 item.") int size,

            @RequestParam(defaultValue = "titulo") String sort,

            @RequestParam(defaultValue = "asc") String direction) {

        if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
            direction = "asc";
        }

        Sort.Direction sortDirection = Sort.Direction.fromString(direction.toLowerCase());

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));

        return taskService.listarTarefas(pageable);
    }

    @GetMapping("/search")
    public Page<TaskResponseDTO> buscarPorCategoria(
            @RequestParam String categoria,
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "O número da página não pode ser menor que zero.") int page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "O tamanho da página deve ser de pelo menos 1 item.") int size,
            @RequestParam(defaultValue = "titulo") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return taskService.listarTarefasPorCategoria(categoria, pageable);
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> criar(@Valid @RequestBody TaskRequestDTO dto) {
        TaskResponseDTO resposta = taskService.criarTarefa(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDTO dto) {

        TaskResponseDTO resposta = taskService.atualizarTarefa(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        taskService.excluirTarefa(id);

        return ResponseEntity.noContent().build();
    }

}
