package team.gif.lib;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Button;

/**
 * A {@link Button} that gets its state from the d-pad of {@link GenericHID}.
 */
public class SimplePOVButton extends Button {

    public enum Direction {
        NORTH(0),
        NORTHEAST(45),
        EAST(90),
        SOUTHEAST(135),
        SOUTH(180),
        SOUTHWEST(225),
        WEST(270),
        NORTHWEST(315),
        NEUTRAL(-1);

        private final int degree;
        Direction(int degree) {
            this.degree = degree;
        }
    }

    private final GenericHID joystick;
    private final Direction direction;

    /**
     * Create an axis button for triggering commands.
     *
     * Returns true when the axis passes a specified threshold.
     *
     * @param joystick {@link GenericHID} that has the axis (e.g. Joystick, KinectStick, etc)
     * @param direction direction of the d-pad (see {@link GenericHID#getPOV(int)})
     */
    public SimplePOVButton(GenericHID joystick, Direction direction) {
        this.joystick = joystick;
        this.direction = direction;
    }

    /**
     * Gets the boolean value of the d-pad button.
     * Returns true when the specified direction is active
     *
     * @return the value of the chosen d-pad direction
     */
    public boolean get() {
        return joystick.getPOV() == direction.degree;
    }
}