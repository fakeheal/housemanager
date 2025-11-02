package edu.nbu.subcommands.fees;

import edu.nbu.entities.Fee;
import edu.nbu.services.FeeService;
import jakarta.inject.Inject;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "fees:list", description = "List all fees for a building")
public class ListFeesSubcommand implements Runnable {
    @CommandLine.Spec
    public CommandLine.Model.CommandSpec spec;

    @Inject
    FeeService feeService;

    @CommandLine.Option(names = "--buildingId", description = "ID of the building.", required = true)
    Long buildingId;

    @Override
    public void run() {
        List<Fee> fees = feeService.list(buildingId);

        Object[][] table = new String[fees.size() + 1][];

        table[0] = new String[]{"ID", "Period", "Apartment", "Status", "Amount", "Paid On"};

        int i = 1;
        for (Fee fee : fees) {
            table[i] = new String[]{
                    fee.getId().toString(),
                    fee.getPeriod().toString(),
                    fee.getApartment().getFloor().getName() + "-" + fee.getApartment().getName(),
                    fee.getStatus().toString(),
                    fee.getAmount() + " BGN",
                    fee.getPaidOn() != null ? fee.getPaidOn().toString() : "N/A"
            };
            i++;
        }

        for (Object[] row : table) {
            spec.commandLine().getOut().printf("%-15s%-10s%-10s%-15s%-15s%-15s\n", row[0], row[1], row[2], row[3], row[4], row[5]);
        }
    }
}
