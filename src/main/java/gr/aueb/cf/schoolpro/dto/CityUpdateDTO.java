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
public class CityUpdateDTO extends BaseDTO {

    @NotNull
    @Size(min = 3, max = 45)
    private String cityName;

    public CityUpdateDTO(@NotNull Long id, String cityName) {
        this.setId(id);
        this.cityName = cityName;
    }
}
