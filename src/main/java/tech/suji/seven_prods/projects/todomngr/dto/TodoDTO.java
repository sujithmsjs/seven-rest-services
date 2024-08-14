package tech.suji.seven_prods.projects.todomngr.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TodoDTO {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String title;

    @Size(max = 255)
    private String description;
    
    
    @Pattern(regexp = "^[0-5]\\d:[0-5]\\d$", message = "Time must be in HH:MM:SS format")
    private String timeRequired;	

    //private LocalDate dueDate;
    @NotNull	
    private Integer priority;

    //private TodoStatusType status;

   
    @JsonProperty("isDone")
    private Boolean isDone;

}
