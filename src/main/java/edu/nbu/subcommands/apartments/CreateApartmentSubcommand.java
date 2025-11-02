package edu.nbu.subcommands.apartments;


import edu.nbu.services.ApartmentService;
import jakarta.inject.Inject;
import picocli.CommandLine;

@CommandLine.Command(name = "apartments:create", description = "Creates a new apartment in a building")
public class CreateApartmentSubcommand implements Runnable {
    @CommandLine.Spec
    public CommandLine.Model.CommandSpec spec;

    @Inject
    ApartmentService apartmentService;

    @CommandLine.Option(names = "--buildingId", description = "ID of the building.", required = true)
    Long buildingId;

    @CommandLine.Option(names = "--name", description = "Name of the new apartment.", required = true)
    String name;

    @CommandLine.Option(names = "--floor", description = "The floor the apartment is on.", required = true)
    Integer address;

    @CommandLine.Option(names = "--area", description = "Area of the apartment.", required = true)
    Float area;

    @CommandLine.Option(names = "--residents", description = "Number of residents.", required = true)
    Integer residents;

    @CommandLine.Option(names = "--pets", description = "Number of pets.", required = true)
    Integer pets;

    @Override
    public void run() {
        try {
            apartmentService.create(buildingId, name, address, area, residents, pets);
            spec.commandLine().getOut().println("Apartment created successfully!");
        } catch (Exception e) {
            spec.commandLine().getErr().println("Error creating apartment: " + e.getMessage());
        }
    }
}
