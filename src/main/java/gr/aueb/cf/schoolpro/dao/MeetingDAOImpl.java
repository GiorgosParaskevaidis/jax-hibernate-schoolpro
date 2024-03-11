package gr.aueb.cf.schoolpro.dao;

import gr.aueb.cf.schoolpro.model.Meeting;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class MeetingDAOImpl extends AbstractDAO<Meeting> implements IMeetingDAO {

    public MeetingDAOImpl() {
        this.setPersistentClass(Meeting.class);
    }
}
