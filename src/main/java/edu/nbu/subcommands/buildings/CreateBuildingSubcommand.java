package edu.nbu.subcommands.buildings;


import edu.nbu.services.BuildingService;
import jakarta.inject.Inject;
import picocli.CommandLine;

@CommandLine.Command(name = "buildings:create", description = "Creates a new building")
public class CreateBuildingSubcommand implements Runnable {
    @CommandLine.Spec
    public CommandLine.Model.CommandSpec spec;

    @Inject
    BuildingService buildingService;

    @CommandLine.Option(names = "--name", description = "Name of the new building", required = true)
    String name;

    @CommandLine.Option(names = "--address", description = "Address of the new building", required = true)
    String address;

    @CommandLine.Option(names = "--employeeId", description = "Employee that will mange the building.", required = true)
    String employeeId;

    @Override
    public void run() {
        try {
            buildingService.create(name, address, Long.parseLong(employeeId));
            spec.commandLine().getOut().println("Building created successfully!");
        } catch (Exception e) {
            spec.commandLine().getErr().println("Error creating building: " + e.getMessage());
        }
    }
}
