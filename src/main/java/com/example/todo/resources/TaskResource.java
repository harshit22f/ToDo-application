package com.example.todo.resources;

import com.example.todo.model.Task;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {
    private final List<Task> taskList = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    @GET
    public List<Task> getTasks() {
        return taskList;
    }

    @GET
    @Path("/{id}")
    public Response getTask(@PathParam("id") Long id) {
        Optional<Task> task = taskList.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
        if (task.isPresent()) {
            return Response.ok(task.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response createTask(Task task) {
        task.setId(counter.incrementAndGet());
        task.setStatus("TODO"); // Ensure status is set to 'TODO'
        taskList.add(task);
        return Response.status(Response.Status.CREATED).entity(task).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateTask(@PathParam("id") Long id, Task updatedTask) {
        Optional<Task> existingTask = taskList.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            task.setDescription(updatedTask.getDescription());
            task.setStartDate(updatedTask.getStartDate());
            task.setTargetDate(updatedTask.getTargetDate());
            // Do not update status here
            return Response.ok(task).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}/status")
    public Response updateTaskStatus(@PathParam("id") Long id, @QueryParam("status") String status) {
        if (!"TODO".equals(status) && !"WIP".equals(status) && !"DONE".equals(status)) {
            throw new WebApplicationException("Invalid status", 400);
        }
        Optional<Task> existingTask = taskList.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            task.setStatus(status);
            return Response.ok(task).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTask(@PathParam("id") Long id) {
        boolean removed = taskList.removeIf(t -> t.getId().equals(id));
        if (removed) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
