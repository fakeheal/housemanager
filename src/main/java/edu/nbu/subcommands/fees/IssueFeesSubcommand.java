package edu.nbu.subcommands.fees;

import edu.nbu.services.FeeService;
import jakarta.inject.Inject;
import picocli.CommandLine;

import java.time.YearMonth;

@CommandLine.Command(name = "fees:issue", description = "Issue fees for all apartments in a building")
public class IssueFeesSubcommand implements Runnable {
    @CommandLine.Spec
    public CommandLine.Model.CommandSpec spec;

    @Inject
    FeeService feeService;

    @CommandLine.Option(names = "--buildingId", description = "ID of the building.", required = true)
    Long buildingId;
    @CommandLine.Option(names = "--period", description = "Period to issue fees for (format: YYYY-mm)", required = true)
    YearMonth period;

    @Override
    public void run() {
        try {
            feeService.issueFees(period, buildingId);
            spec.commandLine().getOut().println("Fees issued successfully for building ID: " + buildingId);
        } catch (Exception e) {
            spec.commandLine().getErr().println("Error issuing fees: " + e.getMessage());
        }
    }
}
