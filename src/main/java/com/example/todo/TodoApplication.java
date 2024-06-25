package com.example.todo;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.example.todo.resources.TaskResource;

public class TodoApplication extends Application<TodoConfiguration> {
    public static void main(String[] args) throws Exception {
        System.out.println("Harshit is here");
        new TodoApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<TodoConfiguration> bootstrap) {
        // Initialization if needed
    }

    @Override
    public void run(TodoConfiguration configuration, Environment environment) {
        // Register resources
        final TaskResource resource = new TaskResource();
        environment.jersey().register(resource);
    }
}
