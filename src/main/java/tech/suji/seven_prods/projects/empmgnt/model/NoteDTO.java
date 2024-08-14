package tech.suji.seven_prods.projects.empmgnt.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NoteDTO {

    private Long id;

    @NotNull
    @Size(max = 40)
    private String title;

    @NotNull
    @Size(max = 255)
    private String content;

    private Long employee;

}
