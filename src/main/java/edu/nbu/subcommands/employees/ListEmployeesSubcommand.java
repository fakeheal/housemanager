package edu.nbu.subcommands.employees;

import edu.nbu.entities.Employee;
import edu.nbu.services.EmployeeService;
import jakarta.inject.Inject;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "employees:list", description = "List all current employees")
public class ListEmployeesSubcommand implements Runnable {
    @CommandLine.Spec
    public CommandLine.Model.CommandSpec spec;

    @Inject
    EmployeeService employeeService;

    @Override
    public void run() {
        List<Employee> employees = employeeService.list();

        Object[][] table = new String[employees.size() + 1][];

        table[0] = new String[]{"ID", "First Name", "Last Name", "Building"};

        int i = 1;
        for (Employee employee : employees) {
            table[i] = new String[]{
                    employee.getId().toString(),
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getBuilding() != null ? employee.getBuilding().getName() : "N/A"
            };
            i++;
        }

        for (Object[] row : table) {
            spec.commandLine().getOut().printf("%-15s%-15s%-15s%-15s\n", row[0], row[1], row[2], row[3]);
        }
    }
}
