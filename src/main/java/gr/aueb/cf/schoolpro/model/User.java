package gr.aueb.cf.schoolpro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class User extends AbstractEntity {

    @NonNull
    @Column(name = "username", length = 45, nullable = false, unique = true)
    private String username;

    @NonNull
    @Column(name = "password", length = 256, nullable = false)
    private String password;

    @NonNull
    @Column(name = "role", length = 45, nullable = false)
    private String role;

    @OneToOne(mappedBy = "user")
    private Teacher teacher;

    @OneToOne(mappedBy = "user")
    private Student student;
}
