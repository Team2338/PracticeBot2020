package team.gif.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import team.gif.robot.commands.drivetrain.DriveStraight;
import team.gif.robot.commands.drivetrain.GyroTurn;

public class PIDMobility extends SequentialCommandGroup {

    public PIDMobility() {
        System.out.println("PIDMobility");
        addCommands(
                new DriveStraight(55000) // Based on conversion: 55,000 ticks = ~5.357 m
                // new GyroTurn(90)
        );
    }
}
