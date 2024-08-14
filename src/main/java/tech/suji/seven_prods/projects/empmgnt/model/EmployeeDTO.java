package tech.suji.seven_prods.projects.empmgnt.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import tech.suji.seven_prods.model.Gender;


@Getter
@Setter
public class EmployeeDTO {

    @NotNull
    @Size(max = 40)
    private String name;

    @NotNull
    @Size(max = 30)
    private String username;

    @NotNull
    @Size(max = 30)
    private String email;

    @Size(max = 30)
    private String password;

    private Gender gender;

    private LocalDate hiredate;

    private Boolean active;

    private Long reportingTo;

    @Size(max = 30)
    private String country;

    @Size(max = 30)
    private String state;

    @Size(max = 30)
    private String city;

    @Digits(integer = 7, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "90.08")
    private BigDecimal salary;
    

    @Size(max = 255)
    private String image;

    private Long departmentId;

    private Long roleId;

}
