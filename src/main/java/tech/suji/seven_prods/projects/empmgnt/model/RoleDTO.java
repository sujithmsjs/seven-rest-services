package tech.suji.seven_prods.projects.empmgnt.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RoleDTO {

    private Long id;

    @NotNull
    @Size(max = 30)
    private String name;

    private Long reportingTo;

    @Size(max = 255)
    private String description;

    private Integer level;

    private Boolean active;

}
