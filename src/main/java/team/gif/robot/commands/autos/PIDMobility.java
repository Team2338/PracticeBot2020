package team.gif.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import team.gif.robot.commands.drivetrain.DriveStraight;
import team.gif.robot.commands.drivetrain.GyroTurn;

public class PIDMobility extends SequentialCommandGroup {

    public PIDMobility() {
        System.out.println("PIDMobility");
        addCommands(
                new DriveStraight(50000) // Based on conversion: 50,000 ticks = ~4.87
                // new GyroTurn(90)
        );
    }
}
