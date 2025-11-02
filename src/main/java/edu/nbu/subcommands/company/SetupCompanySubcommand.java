package edu.nbu.subcommands.company;


import edu.nbu.entities.Company;
import edu.nbu.services.CompanyService;
import jakarta.inject.Inject;
import picocli.CommandLine;

@CommandLine.Command(name = "company:setup", description = "Sets up a new company")
public class SetupCompanySubcommand implements Runnable {
    @Inject
    CompanyService companyService;
    @CommandLine.Spec
    public CommandLine.Model.CommandSpec spec;
    @CommandLine.Option(names = "--name", description = "Name of the company", required = true)
    String name;

    @CommandLine.Option(names = "--address", description = "Address of the Company (eg. 123 Main St, City, Country)", required = true)
    String address;

    @Override
    public void run() {
        try {
            Company company = companyService.create(name, address);
            spec.commandLine().getOut().println("Company '" + company.getName() + "' at '" + company.getAddress() + "' has been set up successfully.");
        } catch (Exception e) {
            spec.commandLine().getErr().println("Error: " + e.getMessage());
        }
    }
}
