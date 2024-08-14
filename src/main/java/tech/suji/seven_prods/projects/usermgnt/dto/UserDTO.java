package tech.suji.seven_prods.projects.usermgnt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private Long id;

    @NotNull
    @Size(max = 25)
    private String name;

    @NotNull
    @Size(max = 16)
    private String username;

    //@NotNull
    @Size(max = 40)
    private String password;

    @Size(max = 40)
    private String email;

    @NotNull
    @JsonProperty("isEnabled")
    private Boolean isEnabled;

    private Long role;

}
