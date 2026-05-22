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

    // Trata campos obrigatórios ausentes ou inválidos
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

    // Trata erros de sintaxe e formato nos tipos de dados
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

    // Trata erros de validação nos parâmetros da URL (Ex: ?page=-1 ou ?size=0)
    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleConstraintViolationException(
            jakarta.validation.ConstraintViolationException ex) {
        Map<String, String> erros = new HashMap<>();

        ex.getConstraintViolations().forEach(violation -> {
            String propriedade = violation.getPropertyPath().toString();
            String nomeParametro = propriedade.substring(propriedade.lastIndexOf('.') + 1);
            erros.put(nomeParametro, violation.getMessage());
        });

        ValidationErrorResponse response = new ValidationErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Parâmetro",
                "Os parâmetros de paginação enviados na URL são inválidos.",
                erros);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Trata violações das regras de negócio vindas do Service - Data no passado
    @ExceptionHandler(DataValidation.class)
    public ResponseEntity<ValidationErrorResponse> HandleDataValidation(DataValidation ex) {
        Map<String, String> erros = new java.util.HashMap<>();
        erros.put("dataLimite", "A data informada está no passado");

        ValidationErrorResponse response = new ValidationErrorResponse(
                java.time.LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Violação da regra de negócio",
                ex.getMessage(),
                erros);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
