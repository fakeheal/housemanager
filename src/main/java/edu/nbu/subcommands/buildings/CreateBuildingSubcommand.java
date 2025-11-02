package edu.nbu.subcommands.buildings;


import edu.nbu.services.BuildingService;
import jakarta.inject.Inject;
import picocli.CommandLine;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
    Long employeeId;

    @CommandLine.Option(names = "--commonArea", description = "Area of the building not included in apartments (in square meters).", required = true)
    Float commonArea;


    @CommandLine.Option(names = "--feePerSqM", description = "Fee per square meter.", required = true)
    Float feePerSqM;

    @CommandLine.Option(names = "--feePerResident", description = "Fee per resident.", required = true)
    Float feePerResident;

    @CommandLine.Option(names = "--feePerPet", description = "Fee per pet.", required = true)
    Float feePerPet;

    @Override
    public void run() {
        try {
            buildingService.create(
                    name,
                    address,
                    employeeId,
                    commonArea,
                    feePerSqM,
                    feePerResident,
                    feePerPet
            );
            spec.commandLine().getOut().println("Building created successfully!");
        } catch (Exception e) {
            spec.commandLine().getErr().println("Error creating building: " + e.getMessage());
        }
    }
}
