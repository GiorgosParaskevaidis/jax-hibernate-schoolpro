package gr.aueb.cf.schoolpro.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentUpdateDTO extends BaseDTO {

    @NotNull
    @Size(min = 2, max = 45)
    private String firstname;

    @NotNull
    @Size(min = 2, max = 45)
    private String lastname;

    @NotNull
    @Size(min = 1, max = 1)
    private String gender;

    @NotNull
    @Past
    private Date birthDate;

    public StudentUpdateDTO(Long id, String firstname, String lastname, String gender, Date birthDate) {
        this.setId(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.birthDate = birthDate;
    }
}
