package edu.nbu;

import io.micronaut.configuration.picocli.PicocliRunner;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
        name = "housemanager",
        description = "...",
        mixinStandardHelpOptions = true,
        subcommands = {
                // Setup
                edu.nbu.subcommands.company.SetupCompanySubcommand.class,

                // Employees
                edu.nbu.subcommands.employees.CreateEmployeeSubcommand.class,
                edu.nbu.subcommands.employees.DeleteEmployeeSubcommand.class,
                edu.nbu.subcommands.employees.ListEmployeesSubcommand.class,

                // Buildings
                edu.nbu.subcommands.buildings.CreateBuildingSubcommand.class,
                edu.nbu.subcommands.buildings.ListBuildingsSubcommand.class,
                edu.nbu.subcommands.buildings.UpdateBuildingSubcommand.class,

                // Apartments
                edu.nbu.subcommands.apartments.CreateApartmentSubcommand.class,
                edu.nbu.subcommands.apartments.ListApartmentsSubcommand.class,
                edu.nbu.subcommands.apartments.UpdateApartmentSubcommand.class,
                edu.nbu.subcommands.apartments.DeleteApartmentSubcommand.class,

                // Fees
                edu.nbu.subcommands.fees.IssueFeesSubcommand.class,
        }
)
public class HousemanagerCommand implements Runnable {

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(HousemanagerCommand.class, args);
    }

    public void run() {
        if (verbose) {
            System.out.println("Hi!");
        }
    }
}
