package edu.nbu.subcommands.fees;

import edu.nbu.entities.Fee;
import edu.nbu.services.FeeService;
import jakarta.inject.Inject;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "fees:pay", description = "Marks a fee as paid.")
public class PayFeeSubcommand implements Runnable {
    @CommandLine.Spec
    public CommandLine.Model.CommandSpec spec;

    @Inject
    FeeService feeService;

    @CommandLine.Option(names = "--id", description = "ID of the fee.", required = true)
    Long id;

    @Override
    public void run() {
        try {
            feeService.payFee(id);
            spec.commandLine().getOut().println("Fee with ID " + id + " has been marked as paid.");
        } catch (Exception e) {
            spec.commandLine().getErr().println("Error: " + e.getMessage());
        }
    }
}
