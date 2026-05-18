package taskmaster_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record TaskRequestDTO(

        @NotBlank(message = "O campo 'titulo' é obrigatório e não pode estar em branco.")  
        @Size(min = 5, max = 100, message = "O campo 'titulo' deve conter entre 5 e 100 caracteres.")
        String titulo,
        
                @Size(max = 250, message = "O campo 'descricao' não deve passar de 250 caracteres.")
        String descricao,

        @NotBlank(message = "O campo 'categoria' é obrigatório e não pode estar em branco.") 
        String categoria,

        @NotNull(message = "O campo data limite é obrigatório.") 
        LocalDate dataLimite)

{
}
