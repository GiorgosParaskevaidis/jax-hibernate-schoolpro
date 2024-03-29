package gr.aueb.cf.schoolpro.rest;

import gr.aueb.cf.schoolpro.dto.*;
import gr.aueb.cf.schoolpro.mapper.Mapper;
import gr.aueb.cf.schoolpro.model.Student;
import gr.aueb.cf.schoolpro.model.Teacher;
import gr.aueb.cf.schoolpro.service.IStudentService;
import gr.aueb.cf.schoolpro.service.ITeacherService;
import gr.aueb.cf.schoolpro.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolpro.validator.ValidatorUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Path("/students")
public class StudentRestController {

        @Inject
        private IStudentService studentService;

        @Path("/")
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Response getStudentsByLastname(@QueryParam("lastname") String lastname) {
            List<Student> students;

            try {
                students = studentService.getStudentsByLastname(lastname);
                List<StudentReadOnlyDTO> readOnlyDTOS = new ArrayList<>();

                for (Student student : students) {
                    readOnlyDTOS.add(Mapper.mapToStudentReadOnlyDTO(student));
                }
                return Response.status(Response.Status.OK).entity(readOnlyDTOS).build();
            } catch (EntityNotFoundException e) {
                return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
            }
        }

        @Path("/{id}")
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Response getOneStudent(@PathParam("id") Long id) {
            Student student;

            try {
                student = studentService.getStudentById(id);
                StudentReadOnlyDTO readOnlyDTO = Mapper.mapToStudentReadOnlyDTO(student);
                return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
            } catch (EntityNotFoundException e) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }

        @Path("/")
        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response addStudent(StudentInsertDTO dto, @Context UriInfo uriInfo) {
            List<String> errors = ValidatorUtil.validateDTO(dto);

            if (!errors.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
            }

            try {
                Student student = studentService.insertStudent(dto);
                StudentReadOnlyDTO readOnlyDTO = Mapper.mapToStudentReadOnlyDTO(student);
                UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
                return Response.status(Response.Status.CREATED)
                        .entity(readOnlyDTO)
                        .location(uriBuilder.path(Long.toString(readOnlyDTO.getId())).build())
                        .build();
            } catch (Exception e) {
                return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
            }
        }

        @Path("/{id}")
        @PUT
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response updateStudent(@PathParam("id") Long id, StudentUpdateDTO dto) {

            if (!Objects.equals(dto.getId(), id)) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized").build();
            }

            List<String> errors = ValidatorUtil.validateDTO(dto);

            if (!errors.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
            }

            try {
                Student student = studentService.updateStudent(dto);
                StudentReadOnlyDTO readOnlyDTO = Mapper.mapToStudentReadOnlyDTO(student);
                return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
            } catch (EntityNotFoundException e) {
                return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
            }
        }

        @Path("/{id}")
        @DELETE
        @Produces(MediaType.APPLICATION_JSON)
        public Response deleteStudent(@PathParam("id") Long id) {
            Student student;

            try {
                student = studentService.getStudentById(id);
                studentService.deleteStudent(id);
                StudentReadOnlyDTO readOnlyDTO = Mapper.mapToStudentReadOnlyDTO(student);
                return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
            } catch (EntityNotFoundException e) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
}

