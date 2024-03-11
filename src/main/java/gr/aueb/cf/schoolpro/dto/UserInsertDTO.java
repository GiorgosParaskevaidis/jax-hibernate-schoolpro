package gr.aueb.cf.schoolpro.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInsertDTO {

    @NotNull(message = "Required field")
    @Size(min = 8, max = 45, message = "Error in username length")
    private String username;

    @NotNull(message = "Required field")
    @Size(min = 8, max = 255, message = "Error in password length")
    private String password;

    @NotNull(message = "Required field")
    @Size(min = 2, max = 45, message = "Error in role length")
    private String role;
}
