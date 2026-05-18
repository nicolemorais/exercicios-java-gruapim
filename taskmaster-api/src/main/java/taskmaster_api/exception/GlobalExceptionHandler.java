package taskmaster_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Trata o Critério de Aceitação 2: Campos obrigatórios ausentes ou inválidos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            erros.put(fieldName, errorMessage);
        });

        ValidationErrorResponse response = new ValidationErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Validação",
                "Um ou mais campos obrigatórios não foram preenchidos corretamente.",
                erros);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Trata o Critério de Aceitação 3: Tipos de dados inválidos
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ValidationErrorResponse> handleInvalidFormatException(HttpMessageNotReadableException ex) {
        Map<String, String> erros = new HashMap<>();

        String mensagemDetalhada = "O formato de algum campo enviado está incorreto. Verifique tipos de dados como datas (AAAA-MM-DD) ou números.";
        if (ex.getMessage() != null && ex.getMessage().contains("java.time.LocalDate")) {
            erros.put("dataLimite", "O formato da data deve ser explicitamente AAAA-MM-DD.");
            mensagemDetalhada = "Falha na conversão do campo de data.";
        }

        ValidationErrorResponse response = new ValidationErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Sintaxe",
                mensagemDetalhada,
                erros);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
