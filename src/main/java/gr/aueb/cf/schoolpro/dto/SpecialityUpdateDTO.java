package gr.aueb.cf.schoolpro.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SpecialityUpdateDTO extends BaseDTO {

    @NotNull
    @Size(min = 2, max = 45)
    private String speciality;

    public SpecialityUpdateDTO(Long id, String speciality) {
        this.setId(id);
        this.speciality = speciality;
    }
}
