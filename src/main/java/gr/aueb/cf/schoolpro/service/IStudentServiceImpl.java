package gr.aueb.cf.schoolpro.service;

import gr.aueb.cf.schoolpro.dao.IStudentDAO;
import gr.aueb.cf.schoolpro.dto.StudentInsertDTO;
import gr.aueb.cf.schoolpro.dto.StudentUpdateDTO;
import gr.aueb.cf.schoolpro.mapper.Mapper;
import gr.aueb.cf.schoolpro.model.Student;
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
public class IStudentServiceImpl implements IStudentService{

    private static final Logger logger = LoggerFactory.getLogger(ITeacherServiceImpl.class);

    // Auto-wiring
    @Inject
    private IStudentDAO studentDAO;

    @Override
    public Student insertStudent(StudentInsertDTO studentInsertDTO) throws Exception {
        Student student = null;

        try {
            JPAHelper.beginTransaction();
            student = Mapper.mapToStudent(studentInsertDTO);
            student = studentDAO.insert(student);

            if (student.getId() == null) {
                throw new Exception("Insert Error");
            }

            JPAHelper.commitTransaction();
            logger.info("Student with id " + student.getId() + "was inserted");
        } catch (Exception e) {
            JPAHelper.rollbackTransaction();
            logger.error("Error - student not inserted");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return student;
    }

    @Override
    public Student updateStudent(StudentUpdateDTO studentUpdateDTO) throws EntityNotFoundException {
        Student studentToUpdate = null;
        Student updatedStudent = null;

        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(studentDAO.getById(studentUpdateDTO.getId()))
                    .orElseThrow(() -> new EntityNotFoundException(Teacher.class, studentUpdateDTO.getId()));

            studentToUpdate = Mapper.mapToStudent(studentUpdateDTO);
            updatedStudent = studentDAO.update(studentToUpdate);
            JPAHelper.commitTransaction();
            logger.info("Student with id " + updatedStudent.getId() + "was updated");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.error("Error - student was not found -- " + e.getMessage());
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return updatedStudent;
    }

    @Override
    public void deleteStudent(Long id) throws EntityNotFoundException {
        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(studentDAO.getById(id))
                    .orElseThrow(() -> new EntityNotFoundException(Teacher.class, id));
            studentDAO.delete(id);
            JPAHelper.commitTransaction();
            logger.info("Student with id " + id + "was deleted");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - student was not found -- " + e.getMessage());
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<Student> getStudentsByLastname(String lastname) throws EntityNotFoundException {
        List<Student> students;

        try {
            JPAHelper.beginTransaction();
            Map<String, Object> criteria = new HashMap<>();
            criteria.put("lastname", lastname);
            students = Optional.of(studentDAO.getByCriteria(Student.class, criteria))
                    .orElseThrow(() -> new EntityNotFoundException(List.class, 0L));
            JPAHelper.commitTransaction();
            logger.info("Student were found");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - student were not found -- " + e.getMessage());
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return students;
    }

    @Override
    public Student getStudentById(Long id) throws EntityNotFoundException {
        Student student = null;

        try {
            JPAHelper.beginTransaction();
            student = Optional.ofNullable(studentDAO.getById(id))
                    .orElseThrow(() -> new EntityNotFoundException(Teacher.class, id));
            JPAHelper.commitTransaction();
            logger.info("Student with id" + id + "was found");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Warning - student with id " + id + "was not found " + e.getMessage());
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return student;
    }
}
