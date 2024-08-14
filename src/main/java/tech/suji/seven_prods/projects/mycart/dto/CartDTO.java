package tech.suji.seven_prods.projects.mycart.dto;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CartDTO {

    private Long id;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String quantity;

    private LocalDate date;

    private List<Long> product;

}
