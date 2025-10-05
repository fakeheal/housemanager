package edu.nbu;

import io.micronaut.configuration.picocli.PicocliRunner;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
        name = "housemanager",
        description = "...",
        mixinStandardHelpOptions = true,
        subcommands = {
                edu.nbu.subcommands.company.SetupCompanySubcommand.class,
                edu.nbu.subcommands.employees.CreateEmployeeSubcommand.class,
                edu.nbu.subcommands.employees.DeleteEmployeeSubcommand.class,
                edu.nbu.subcommands.employees.ListEmployeesSubcommand.class,
                edu.nbu.subcommands.buildings.CreateBuildingSubcommand.class
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
