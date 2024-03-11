package gr.aueb.cf.schoolpro.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class Student extends AbstractEntity {
    @NonNull
    @Column(name = "firstname", length = 45, nullable = false)
    private String firstname;

    @NonNull
    @Column(name = "lastname", length = 45, nullable = false)
    private String lastname;

    @NonNull
    @Column(name = "gender", length = 1, nullable = false)
    private String gender;

    @NonNull
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "student")
    @Getter(AccessLevel.PROTECTED)
    private Set<Meeting> meetings = new HashSet<>();

    public Set<Meeting> getAllMeetings() {
        return Collections.unmodifiableSet(meetings);
    }

    public void addMeetings(Meeting meeting) {
        meetings.add(meeting);
        meeting.setStudent(this);
    }
}
