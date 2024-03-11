package gr.aueb.cf.schoolpro.service;


import gr.aueb.cf.schoolpro.dao.IUserDAO;
import gr.aueb.cf.schoolpro.dto.UserInsertDTO;
import gr.aueb.cf.schoolpro.dto.UserUpdateDTO;
import gr.aueb.cf.schoolpro.mapper.Mapper;
import gr.aueb.cf.schoolpro.model.Teacher;
import gr.aueb.cf.schoolpro.model.User;
import gr.aueb.cf.schoolpro.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolpro.service.util.JPAHelper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Provider
@ApplicationScoped
public class IUserServiceImpl implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(IUserServiceImpl.class);

    // Auto-wiring
    @Inject
    private IUserDAO userDAO;


    @Override
    public User insertUser(UserInsertDTO userInsertDTO) throws Exception {
        User user = null;

        try {
            JPAHelper.beginTransaction();
            user = Mapper.mapToUser(userInsertDTO);
            user = userDAO.insert(user);

            if (user.getId() == null) {
                throw new Exception("Insert Error");
            }

            JPAHelper.commitTransaction();
            logger.info("User with id " + user.getId() + "was inserted");
        } catch (Exception e) {
            JPAHelper.rollbackTransaction();
            logger.error("Error - user not inserted");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return user;
    }

    @Override
    public User updateUser(UserUpdateDTO userUpdateDTO) throws EntityNotFoundException {
        User userToUpdate = null;
        User updatedUser = null;

        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(userDAO.getById(userUpdateDTO.getId()))
                    .orElseThrow(() -> new EntityNotFoundException(User.class, userUpdateDTO.getId()));

            userToUpdate = Mapper.mapToUser(userUpdateDTO);
            updatedUser = userDAO.update(userToUpdate);
            JPAHelper.commitTransaction();
            logger.info("User with id " + updatedUser.getId() + "was updated");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.error("Error - user was not found -- " + e.getMessage());
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return updatedUser;
    }

    @Override
    public void deleteUser(Long id) throws EntityNotFoundException {
        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(userDAO.getById(id))
                    .orElseThrow(() -> new EntityNotFoundException(Teacher.class, id));
            userDAO.delete(id);
            JPAHelper.commitTransaction();
            logger.info("User with id " + id + "was deleted");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - user was not found -- " + e.getMessage());
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<User> getUsersByUsername(String username) throws EntityNotFoundException {
        List<User> users;

        try {
            JPAHelper.beginTransaction();
            Map<String, Object> criteria = new HashMap<>();
            criteria.put("username", username);
            users = Optional.of(userDAO.getByCriteria(User.class, criteria))
                    .orElseThrow(() -> new EntityNotFoundException(List.class, 0L));
            JPAHelper.commitTransaction();
            logger.info("User were found");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - user were not found -- " + e.getMessage());
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return users;
    }

    @Override
    public User getUserById(Long id) throws EntityNotFoundException {
        User user = null;

        try {
            JPAHelper.beginTransaction();
            user = Optional.ofNullable(userDAO.getById(id))
                    .orElseThrow(() -> new EntityNotFoundException(User.class, id));
            JPAHelper.commitTransaction();
            logger.info("User with id" + id + "was found");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - teacher with id " + id + "was not found " + e.getMessage());
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return user;
    }
}
