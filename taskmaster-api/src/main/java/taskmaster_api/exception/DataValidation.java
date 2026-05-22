package taskmaster_api.exception;

public class DataValidation extends RuntimeException{
    public DataValidation(String message){
        super(message);
    }
}
