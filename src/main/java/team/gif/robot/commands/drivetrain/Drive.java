/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package team.gif.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team.gif.robot.OI;
import team.gif.robot.Robot;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class Drive extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    //private final Drivetrain m_subsystem;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public Drive(Drivetrain subsystem) {
        //m_subsystem = subsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(Drivetrain.getInstance());
    }

    double Speed;
    double RotationSpeed;

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Speed = Robot.oi.driver.getY(GenericHID.Hand.kLeft) ;
        RotationSpeed =  Robot.oi.driver.getX(GenericHID.Hand.kRight);

        if (Robot.isCompBot) { // Comp Bot
            if (Speed < 0.075 && Speed > -0.075 ) {
                Speed = 0;
            }
            if (RotationSpeed < 0.075 && RotationSpeed > -0.075 ) {
                RotationSpeed = 0;
            }
        } else { // Practice Bot
            if (Speed < 0.05 && Speed > -0.05 ) {
                Speed = 0;
            }
            if (RotationSpeed < 0.05 && RotationSpeed > -0.05 ) {
                RotationSpeed = 0;
            }
        }
/*
        if (Speed < -1 || Speed > 1) {
            Speed = Speed / Math.abs(Speed);
        }
        if (RotationSpeed < -1 || RotationSpeed > 1) {
            RotationSpeed = RotationSpeed / Math.abs(RotationSpeed);
        }
*/
        Drivetrain.getInstance().driveArcade(Speed, RotationSpeed);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        Drivetrain.getInstance().setSpeed(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
