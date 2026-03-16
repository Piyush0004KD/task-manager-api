package com.example.taskmanager.task.DTO;


import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
public class ErrorDTO {

    private int status;
    private LocalDateTime timestamp;
    private String message;
    private String error;
    private String path;
    private Map<String , String> fieldErrors;

    public static ErrorDTO of ( int status,
                                LocalDateTime timestamp,
                                String message,
                                String error,
                                String path){

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(status);
        errorDTO.setTimestamp(timestamp);
        errorDTO.setMessage(message);
        errorDTO.setError(error);
        errorDTO.setPath(path);

        return errorDTO;
    }
    public static ErrorDTO of ( int status,
                                LocalDateTime timestamp,
                                String message,
                                String error,
                                String path,
                                Map<String , String> fieldErrors){

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(status);
        errorDTO.setTimestamp(timestamp);
        errorDTO.setMessage(message);
        errorDTO.setError(error);
        errorDTO.setPath(path);
        errorDTO.setFieldErrors(fieldErrors);

        return errorDTO;
    }


}
