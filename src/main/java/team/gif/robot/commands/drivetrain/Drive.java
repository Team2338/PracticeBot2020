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

    double leftSpeed;
    double rightSpeed;
    private final OI oi = OI.getInstance();

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        leftSpeed = oi.driver.getY(GenericHID.Hand.kLeft) - oi.driver.getX(GenericHID.Hand.kRight);
        rightSpeed = oi.driver.getY(GenericHID.Hand.kLeft) + oi.driver.getX(GenericHID.Hand.kRight);
        if (leftSpeed < 0.05 && leftSpeed > -0.05) {
            leftSpeed = 0;
        }
        if (rightSpeed < 0.05 && rightSpeed > -0.05) {
            rightSpeed = 0;
        }
        if (leftSpeed < -1 || leftSpeed > 1) {
            leftSpeed = leftSpeed / Math.abs(leftSpeed);
        }
        if (rightSpeed < -1 || rightSpeed > 1) {
            rightSpeed = rightSpeed / Math.abs(rightSpeed);
        }

        Drivetrain.getInstance().setSpeed(leftSpeed, rightSpeed);

        SmartDashboard.putNumber("Left Percent", leftSpeed);
        SmartDashboard.putNumber("Right Percent", rightSpeed);
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
