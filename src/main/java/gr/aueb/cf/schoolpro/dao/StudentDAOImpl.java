package gr.aueb.cf.schoolpro.dao;

import gr.aueb.cf.schoolpro.model.Student;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class StudentDAOImpl extends AbstractDAO<Student> implements IStudentDAO {

    public StudentDAOImpl() {
        this.setPersistentClass(Student.class);
    }
}
