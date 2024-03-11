package gr.aueb.cf.schoolpro.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeacherReadOnlyDTO extends BaseDTO {

    private int ssn;
    private String firstname;
    private String lastname;

    public TeacherReadOnlyDTO(@NotNull Long id, int ssn, String firstname, String lastname) {
        this.setId(id);
        this.ssn = ssn;
        this.firstname = firstname;
        this.lastname = lastname;
    }

}
