package tech.suji.seven_prods.projects.empmgnt.model;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DepartmentDTO {

	private Long id;
	
    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String location;

    private boolean active;
    
    private LocalDateTime lastUpdated;
}
