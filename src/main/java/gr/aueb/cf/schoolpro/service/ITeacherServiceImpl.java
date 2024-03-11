package gr.aueb.cf.schoolpro.service;

import gr.aueb.cf.schoolpro.dao.ITeacherDAO;
import gr.aueb.cf.schoolpro.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolpro.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolpro.mapper.Mapper;
import gr.aueb.cf.schoolpro.model.Teacher;
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
public class ITeacherServiceImpl implements ITeacherService{

    private static final Logger logger = LoggerFactory.getLogger(ITeacherServiceImpl.class);

    // Auto-wiring
    @Inject
    private ITeacherDAO teacherDAO;

    @Override
    public Teacher insertTeacher(TeacherInsertDTO teacherInsertDTO) throws Exception {
        Teacher teacher = null;

        try {
            JPAHelper.beginTransaction();
            teacher = Mapper.mapToTeacher(teacherInsertDTO);
            teacher = teacherDAO.insert(teacher);

            if (teacher.getId() == null) {
                throw new Exception("Insert Error");
            }

            JPAHelper.commitTransaction();
            logger.info("Teacher with id " + teacher.getId() + "was inserted");
        } catch (Exception e) {
            JPAHelper.rollbackTransaction();
            logger.error("Error - teacher not inserted");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return teacher;
    }

    @Override
    public Teacher updateTeacher(TeacherUpdateDTO teacherUpdateDTO) throws EntityNotFoundException {
        Teacher teacherToUpdate = null;
        Teacher updatedTeacher = null;

        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(teacherDAO.getById(teacherUpdateDTO.getId()))
                    .orElseThrow(() -> new EntityNotFoundException(Teacher.class, teacherUpdateDTO.getId()));

            teacherToUpdate = Mapper.mapToTeacher(teacherUpdateDTO);
            updatedTeacher = teacherDAO.update(teacherToUpdate);
            JPAHelper.commitTransaction();
            logger.info("Teacher with id " + updatedTeacher.getId() + "was updated");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.error("Error - teacher was not found -- " + e.getMessage());
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return updatedTeacher;
    }

    @Override
    public void deleteTeacher(Long id) throws EntityNotFoundException {
        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(teacherDAO.getById(id))
                    .orElseThrow(() -> new EntityNotFoundException(Teacher.class, id));
            teacherDAO.delete(id);
            JPAHelper.commitTransaction();
            logger.info("Teacher with id " + id + "was deleted");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - teacher was not found -- " + e.getMessage());
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<Teacher> getTeachersByLastname(String lastname) throws EntityNotFoundException {
        List<Teacher> teachers;

        try {
            JPAHelper.beginTransaction();
            Map<String, Object> criteria = new HashMap<>();
            criteria.put("lastname", lastname);
            teachers = Optional.of(teacherDAO.getByCriteria(Teacher.class, criteria))
                    .orElseThrow(() -> new EntityNotFoundException(List.class, 0L));
            JPAHelper.commitTransaction();
            logger.info("Teacher were found");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - teacher were not found -- " + e.getMessage());
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return teachers;
    }

    @Override
    public Teacher getTeacherById(Long id) throws EntityNotFoundException {
        Teacher teacher = null;

        try {
            JPAHelper.beginTransaction();
            teacher = Optional.ofNullable(teacherDAO.getById(id))
                    .orElseThrow(() -> new EntityNotFoundException(Teacher.class, id));
            JPAHelper.commitTransaction();
            logger.info("Teacher with id" + id + "was found");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - teacher with id " + id + "was not found " + e.getMessage());
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return teacher;
    }
}
