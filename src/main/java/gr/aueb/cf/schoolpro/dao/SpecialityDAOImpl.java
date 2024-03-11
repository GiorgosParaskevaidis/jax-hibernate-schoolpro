package gr.aueb.cf.schoolpro.dao;

import gr.aueb.cf.schoolpro.model.Speciality;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class SpecialityDAOImpl extends AbstractDAO<Speciality> implements ISpecialityDAO {

    public SpecialityDAOImpl() {
        this.setPersistentClass(Speciality.class);
    }
}
