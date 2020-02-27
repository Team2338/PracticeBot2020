package team.gif.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import team.gif.lib.AxisButton;
import team.gif.robot.commands.autoaim.Pivot;
import team.gif.robot.commands.hanger.HangerManualControl;
import team.gif.robot.commands.indexer.ReverseIndexScheduler;
import team.gif.robot.commands.indexer.ToggleIndexer;
import team.gif.robot.commands.intake.*;
import team.gif.robot.commands.shooter.Fire;
//import team.gif.robot.commands.shooter.LedModes;
import team.gif.robot.commands.shooter.RevFlywheel;
import edu.wpi.first.wpilibj.GenericHID;


public class OI {
    private static OI instance = null;

    public static OI getInstance() {
        if (instance == null) {
            instance = new OI();
        }

        return instance;
    }

    /*
     * TODO: Instantiate all joysticks/controllers and their buttons here
     *
     * Examples:
     * public final Joystick leftStick = new Joystick(0);
     * public final XboxController driver = new XboxController(0);
     *
     * private final JoystickButton leftTrigger = new JoystickButton(leftStick, 0);
     */

    public final XboxController driver = new XboxController(RobotMap.DRIVER_CONTROLLER_ID);
    public final XboxController aux = new XboxController(RobotMap.AUX_CONTROLLER_ID);

    public final JoystickButton dA = new JoystickButton(driver, 1);
    public final JoystickButton dB = new JoystickButton(driver, 2);
    public final JoystickButton dX = new JoystickButton(driver, 3);
    public final JoystickButton dY = new JoystickButton(driver, 4);
    public final JoystickButton dLB = new JoystickButton(driver, 5);
    public final JoystickButton dRB = new JoystickButton(driver, 6);
    public final JoystickButton dBack = new JoystickButton(driver, 7);
    public final JoystickButton dStart = new JoystickButton(driver, 8);
    public final JoystickButton dLS = new JoystickButton(driver, 9);
    public final JoystickButton dRS = new JoystickButton(driver, 10);
    public final AxisButton dRT = new AxisButton(aux,3,.05);
    public final AxisButton dLT = new AxisButton(aux,2,.05);

    public final POVButton dDPadUp = new POVButton(driver, 0);
    public final POVButton dDPadRight = new POVButton(driver, 90);
    public final POVButton dDPadDown = new POVButton(driver, 180);
    public final POVButton dDPadLeft = new POVButton(driver, 270);

    public final JoystickButton aA = new JoystickButton(aux, 1);
    public final JoystickButton aB = new JoystickButton(aux, 2);
    public final JoystickButton aX = new JoystickButton(aux, 3);
    public final JoystickButton aY = new JoystickButton(aux, 4);
    public final JoystickButton aLB = new JoystickButton(aux, 5);
    public final JoystickButton aRB = new JoystickButton(aux, 6);
    public final JoystickButton aBack = new JoystickButton(aux, 7);
    public final JoystickButton aStart = new JoystickButton(aux, 8);
    public final JoystickButton aLS = new JoystickButton(aux, 9);
    public final JoystickButton aRS = new JoystickButton(aux, 10);
    public final AxisButton aRT = new AxisButton(aux,3,.05);
    public final AxisButton aLT = new AxisButton(aux,2,.05);

    public OI() {
        /*
         * TODO: Define what each button does
         *
         * Examples:
         * leftTrigger.whenPressed(new CollectCommand());
         * rightTrigger.whileHeld(new EjectCommand());
         *
         */

        // Driver Controls
        dRB.whileHeld(new IntakeRun(true));
        dRB.whenPressed(new IntakeDown()); // Moves collector to down position at start of intake.
        dRB.whenReleased(new IntakeRun(false));
        dLB.whileHeld(new IntakeReverse());
        dDPadDown.whenPressed(new IntakeDown());
        dDPadLeft.whenPressed(new IntakeMid());
        dDPadUp.whenPressed(new IntakeUp().withTimeout(0.05));
        dB.whenPressed(new ReverseIndexScheduler());
        dY.toggleWhenActive(new ToggleIndexer());

        // Aux Controls
        aLB.whileHeld(new RevFlywheel(true));
        aLB.whenReleased(new RevFlywheel(false));
        aRT.whileHeld(new Fire(0,false));
        aLT.whileHeld(new Pivot());
        aY.whenPressed(new HangerManualControl());
    }

    public void setRumble(boolean rumble) {
        driver.setRumble(GenericHID.RumbleType.kLeftRumble, rumble ? 1.0 : 0.0);
        driver.setRumble(GenericHID.RumbleType.kRightRumble, rumble ? 1.0: 0.0);
        aux.setRumble(GenericHID.RumbleType.kLeftRumble, rumble ? 1.0 : 0.0);
        aux.setRumble(GenericHID.RumbleType.kRightRumble, rumble ? 1.0: 0.0);
    }
}