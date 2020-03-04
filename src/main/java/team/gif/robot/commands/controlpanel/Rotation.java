/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package team.gif.robot.commands.controlpanel;

import edu.wpi.first.wpilibj2.command.CommandBase;
import team.gif.robot.subsystems.ColorSensor;

/**
 * An example command that uses an example subsystem.
 */
public class Rotation extends CommandBase {

    /**
     * Creates a new ExampleCommand.
     *
     */
    public Rotation() {
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }
    public enum WheelColor {YELLOW, BLUE, GREEN, RED};
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        // run the color sensor algorithm
        /* may not need if we can run this from teleopPeriodic */
        //ColorSensor.getInstance().periodic();

        // turn the motor on
        ColorSensor.getInstance().setColorSensorSpeed(0.5);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        ColorSensor.getInstance().setColorSensorSpeed(0.0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}