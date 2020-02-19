package team.gif.robot.commands.indexer;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
//import jdk.jshell.Snippet;
import team.gif.robot.subsystems.Indexer;
import team.gif.robot.subsystems.Intake;

public class IndexerScheduler extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Indexer index = Indexer.getInstance();
    //private final CommandScheduler commandScheduler = CommandScheduler.getInstance();
    private CommandBase currentCommand = null;

    /*CommandBase stageTwo = new StageTwo();
    CommandBase stageThree = new StageThree();
    CommandBase stageFour = new StageFour();
    CommandBase stageFive = new StageFive();*/

    public IndexerScheduler() {
        // Use addRequirements() here to declare subsystem dependencies.
        //addRequirements(Indexer.getInstance());
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        currentCommand = null;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    /*
        SmartDashboard.putBoolean("One", index.getState()[1]);
        SmartDashboard.putBoolean("Two", index.getState()[2]);
        SmartDashboard.putBoolean("Three", index.getState()[3]);
        SmartDashboard.putBoolean("Four", index.getState()[4]);
        SmartDashboard.putBoolean("Five", index.getState()[5]);
*/
        if(currentCommand != null && currentCommand.isFinished() == true) {
            currentCommand = null;
        }
        //System.out.println("entering indexer");
        if(currentCommand == null) {
            //System.out.println("                          Current Command Null");
            if((index.getState()[4] == true) && (index.getState()[5] == false)) {
                currentCommand = new StageFive();
                CommandScheduler.getInstance().schedule(currentCommand);
            }
            if((index.getState()[3] == true) && (index.getState()[4] == false)) {
                currentCommand = new StageFour();
                CommandScheduler.getInstance().schedule(currentCommand);
            }
            if((index.getState()[2] == true) && (index.getState()[3] == false)) {
                currentCommand = new StageThree();
                CommandScheduler.getInstance().schedule(currentCommand);
            }
            if((index.getState()[1] == true) && (index.getState()[2] == false)) {
                currentCommand = new StageTwo();
                CommandScheduler.getInstance().schedule(currentCommand);
            }
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
