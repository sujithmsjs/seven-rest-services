package tech.suji.seven_prods.projects.expencecals.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ExpenceDTO {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String reason;

    @NotNull
    private Long amount;

    private LocalDate date;

    private ExpenseCategory category;

}
