package edu.nbu.subcommands.employees;

import edu.nbu.services.EmployeeService;
import jakarta.inject.Inject;
import picocli.CommandLine;


@CommandLine.Command(name = "employees:delete", description = "Delete an employee by ID")
public class DeleteEmployeeSubcommand implements Runnable {
    @CommandLine.Spec
    public CommandLine.Model.CommandSpec spec;

    @Inject
    EmployeeService employeeService;

    @CommandLine.Option(names = "--id", description = "Id of the employee to delete", required = true)
    String id;

    @Override
    public void run() {
        try {
            employeeService.delete(Long.parseLong(id));
            spec.commandLine().getOut().println("Employee deleted successfully!");
        } catch (Exception e) {
            spec.commandLine().getErr().println("Error: " + e.getMessage());
        }
    }
}
