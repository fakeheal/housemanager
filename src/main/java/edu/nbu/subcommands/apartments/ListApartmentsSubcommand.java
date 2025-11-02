package edu.nbu.subcommands.apartments;

import edu.nbu.entities.Apartment;
import edu.nbu.services.ApartmentService;
import jakarta.inject.Inject;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "apartments:list", description = "List all apartments in a building")
public class ListApartmentsSubcommand implements Runnable {
    @CommandLine.Spec
    public CommandLine.Model.CommandSpec spec;

    @Inject
    ApartmentService apartmentService;

    @CommandLine.Option(names = "--buildingId", description = "ID of the building.", required = true)
    Long buildingId;
    @Override
    public void run() {
        List<Apartment> apartments = apartmentService.list(buildingId);

        Object[][] table = new String[apartments.size() + 1][];

        table[0] = new String[]{"ID", "Name", "Floor", "Area", "Residents", "Pets"};

        int i = 1;
        for (Apartment apartment : apartments) {
            table[i] = new String[]{
                    apartment.getId().toString(),
                    apartment.getName(),
                    apartment.getFloor().getName().toString(),
                    apartment.getArea() + " m²",
                    apartment.getNumberOfResidents().toString(),
                    apartment.getNumberOfPets().toString()
            };
            i++;
        }

        for (Object[] row : table) {
            spec.commandLine().getOut().printf("%-15s%-20s%-40s%-15s%-15s%-15s\n", row[0], row[1], row[2], row[3], row[4], row[5]);
        }
    }
}
