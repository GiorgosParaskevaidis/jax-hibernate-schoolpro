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
public class CityReadOnlyDTO extends BaseDTO {

    private String cityName;

    public CityReadOnlyDTO(@NotNull Long id, String cityName) {
        this.setId(id);
        this.cityName = cityName;
    }
}
