package taskmaster_api.dto;

import taskmaster_api.model.Task;
import java.time.LocalDate;

public record TaskResponseDTO(
        Long id,
        String titulo,
        String descricao,
        String categoria,
        LocalDate dataLimite) {

    public TaskResponseDTO(Task task) {
        this(task.getId(), task.getTitulo(), task.getDescricao(), task.getCategoria(), task.getDataLimite());
    }
}
