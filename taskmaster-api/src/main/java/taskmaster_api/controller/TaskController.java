package taskmaster_api.controller;

import taskmaster_api.dto.TaskRequestDTO;
import taskmaster_api.dto.TaskResponseDTO;
import taskmaster_api.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Tarefas", description = "Endpoints para gerenciamento do ciclo de vida das tarefas")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @Operation(summary = "Listar tarefas paginadas", description = "Retorna uma lista paginada e ordenada de todas as tarefas cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
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
    @Operation(summary = "Filtrar tarefas por categoria", description = "Busca tarefas de forma paginada filtrando pelo nome da categoria (case-insensitive)")
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    public Page<TaskResponseDTO> buscarPorCategoria(
            @RequestParam String categoria,
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "O número da página não pode ser menor que zero.") int page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "O tamanho da página deve ser de pelo menos 1 item.") int size,
            @RequestParam(defaultValue = "titulo") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return taskService.listarTarefasPorCategoria(categoria, pageable);
    }

    @PostMapping
    @Operation(summary = "Criar nova tarefa", description = "Registra uma nova tarefa no banco de dados com validação de data limite")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos ou data retroativa")
    })
    public ResponseEntity<TaskResponseDTO> criar(@Valid @RequestBody TaskRequestDTO dto) {
        TaskResponseDTO resposta = taskService.criarTarefa(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma tarefa existente", description = "Modifica os atributos de uma tarefa existente com base no ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou tentativa de inserir data no passado"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada para o ID fornecido")
    })
    public ResponseEntity<TaskResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDTO dto) {

        TaskResponseDTO resposta = taskService.atualizarTarefa(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma tarefa", description = "Remove permanentemente uma tarefa do sistema através do ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarefa excluída com sucesso (Sem conteúdo de retorno)"),
            @ApiResponse(responseCode = "404", description = "Tentativa de exclusão de uma tarefa inexistente")
    })
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        taskService.excluirTarefa(id);

        return ResponseEntity.noContent().build();
    }

}
