package gr.aueb.cf.schoolpro.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teachers")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class Teacher extends AbstractEntity {

    @NonNull
    @Column(name = "ssn", nullable = false, unique = true)
    private int ssn;


    @NonNull
    @Column(name = "firstname", length = 45, nullable = false)
    private String firstname;

    @NonNull
    @Column(name = "lastname", length = 45, nullable = false)
    private String lastname;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "speciality_id", referencedColumnName = "id")
    private Speciality speciality;

    @OneToMany(mappedBy = "teacher")
    @Getter(AccessLevel.PROTECTED)
    private Set<Meeting> meetings = new HashSet<>();

    public Set<Meeting> getAllMeetings() {
        return Collections.unmodifiableSet(meetings);
    }

    public void addMeetings(Meeting meeting) {
        meetings.add(meeting);
        meeting.setTeacher(this);
    }
}
