package edu.nbu.subcommands.buildings;

import edu.nbu.entities.Building;
import edu.nbu.services.BuildingService;
import jakarta.inject.Inject;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "buildings:list", description = "List all buildings")
public class ListBuildingsSubcommand implements Runnable {
    @CommandLine.Spec
    public CommandLine.Model.CommandSpec spec;

    @Inject
    BuildingService buildingService;

    @Override
    public void run() {
        List<Building> buildings = buildingService.list();

        Object[][] table = new String[buildings.size() + 1][];

        table[0] = new String[]{
                "ID",
                "Name",
                "Address",
                "Common Area",
                "Managed By",
                "Floors",
                "Fee (per m²)",
                "Fee (per Resident)",
                "Fee (per Pet)"
        };

        int i = 1;
        for (Building building : buildings) {
            table[i] = new String[]{
                    building.getId().toString(),
                    building.getName(),
                    building.getAddress(),
                    building.getCommonArea() + " m²",
                    building.getEmployee().getFirstName() + " " + building.getEmployee().getLastName(),
                    String.valueOf(building.getFloors().size()),
                    building.getFeePerSqM() + " BGN",
                    building.getFeePerResident() + " BGN",
                    building.getFeePerPet() + " BGN"
            };
            i++;
        }

        for (Object[] row : table) {
            spec.commandLine().getOut().printf(
                    "%-15s%-20s%-40s%-15s%-15s%-15s%-15s%-20s%-15s\n",
                    row[0],
                    row[1],
                    row[2],
                    row[3],
                    row[4],
                    row[5],
                    row[6],
                    row[7],
                    row[8]
            );
        }
    }
}
