package edu.nbu.subcommands.buildings;


import picocli.CommandLine;

@CommandLine.Command(name = "buildings:create", description = "Creates a new building")
public class CreateBuildingSubcommand implements Runnable {
    @Override
    public void run() {
        System.out.println("CreateBuildingSubcommand executed");
    }
}
