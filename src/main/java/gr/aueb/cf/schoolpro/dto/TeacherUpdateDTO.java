package gr.aueb.cf.schoolpro.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeacherUpdateDTO extends BaseDTO {

    @NotNull
    @Size(min = 6)
    private int ssn;

    @NotNull
    @Size(min = 2, max = 255)
    private String firstname;

    @NotNull
    @Size(min = 2, max = 255)
    private String lastname;

    public TeacherUpdateDTO(Long id, int ssn, String firstname, String lastname) {
        this.setId(id);
        this.ssn = ssn;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
