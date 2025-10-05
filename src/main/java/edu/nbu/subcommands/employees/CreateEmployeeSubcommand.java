package edu.nbu.subcommands.employees;

import edu.nbu.services.EmployeeService;
import jakarta.inject.Inject;
import picocli.CommandLine;

@CommandLine.Command(name = "employees:create", description = "Add a new employee to the company")
public class CreateEmployeeSubcommand implements Runnable {
    @CommandLine.Spec
    public CommandLine.Model.CommandSpec spec;

    @Inject
    EmployeeService employeeService;

    @CommandLine.Option(names = "--firstName", description = "First name of the new employee")
    String firstName;

    @CommandLine.Option(names = "--lastName", description = "Last name of the new employee")
    String lastName;

    @Override
    public void run() {
        try {
            employeeService.create(firstName, lastName);
            spec.commandLine().getOut().println("Employee created successfully!");
        } catch (Exception e) {
            spec.commandLine().getErr().println("Error: " + e.getMessage());
        }
    }
}
