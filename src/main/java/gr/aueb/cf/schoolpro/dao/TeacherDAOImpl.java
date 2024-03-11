package gr.aueb.cf.schoolpro.dao;

import gr.aueb.cf.schoolpro.model.Teacher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class TeacherDAOImpl extends AbstractDAO<Teacher> implements ITeacherDAO {

    public TeacherDAOImpl() {
        this.setPersistentClass(Teacher.class);
    }
}
