package edu.nbu.subcommands.apartments;

import edu.nbu.entities.Apartment;
import edu.nbu.services.ApartmentService;
import jakarta.inject.Inject;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "apartments:delete", description = "Delete an apartment by ID.")
public class DeleteApartmentSubcommand implements Runnable {
    @CommandLine.Spec
    public CommandLine.Model.CommandSpec spec;

    @Inject
    ApartmentService apartmentService;

    @CommandLine.Option(names = "--id", description = "ID of the apartment.", required = true)
    Long id;
    @Override
    public void run() {
        try {
            apartmentService.delete(id);
            spec.commandLine().getOut().println("Apartment deleted successfully!");
        } catch (Exception e) {
            spec.commandLine().getErr().println("Error: " + e.getMessage());
        }
    }
}
