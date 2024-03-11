package gr.aueb.cf.schoolpro.dao;

import gr.aueb.cf.schoolpro.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class UserDAOImpl extends AbstractDAO<User> implements IUserDAO {

    public UserDAOImpl() {
        this.setPersistentClass(User.class);
    }
}
