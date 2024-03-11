package gr.aueb.cf.schoolpro.dao;

import gr.aueb.cf.schoolpro.model.City;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class CityDAOImpl extends AbstractDAO<City> implements ICityDAO {

    public CityDAOImpl() {
        this.setPersistentClass(City.class);
    }
}
