/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package team.gif.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class ColorSensor extends SubsystemBase {
  private static ColorSensor instance = null;

  /**
   * Change the I2C port below to match the connection of your color sensor
   */
  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private static final I2C colorSensor = new I2C(I2C.Port.kOnboard, RobotMap.COLOR_SENSOR);

  /**
   * A Rev Color Sensor V3 object is constructed with an I2C port as a
   * parameter. The device will be automatically initialized with default
   * parameters.
   */
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

  /**
   * A Rev Color Match object is used to register and detect known colors. This can
   * be calibrated ahead of time or during operation.
   *
   * This object uses a simple euclidian distance to estimate the closest match
   * with given confidence range.
   */
  private final ColorMatch m_colorMatcher = new ColorMatch();

  /**
   * Note: Any example colors should be calibrated as the user needs, these
   * are here as a basic example.
   */
  /**
   * Original
   */
//  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
//  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
//  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
//  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  /** Above table
   *
   */
  private final Color kBlueTarget = ColorMatch.makeColor(0.130, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.551, 0.250);
  private final Color kRedTarget = ColorMatch.makeColor(0.480, 0.370, 0.150);
  private final Color kYellowTarget = ColorMatch.makeColor(0.320, 0.550, 0.130);

  /**
   * Under Table
   */
//     private final Color kBlueTarget = ColorMatch.makeColor(0.198, 0.457, 0.345);
//     private final Color kGreenTarget = ColorMatch.makeColor(0.224, 0.510, 0.266);
//     private final Color kRedTarget = ColorMatch.makeColor(0.330, 0.442, 0.228);
//     private final Color kYellowTarget = ColorMatch.makeColor(0.286, 0.518, 0.195);

  public static ColorSensor getInstance() {
    if (instance == null) {
      instance = new ColorSensor();
    }

    return instance;
  }

  /**
   * Creates a new ExampleSubsystem.
   */
  public ColorSensor() {
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);
    System.out.println("Color Senor Initiated");
  }

  public int rotationCount = 0;
  public String previousColor = "";
  String[] colorOrder = {"yellow", "blue", "green", "red"};
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    getColor();
  }

  public String getColor() {
    /**
     * Run the color match algorithm on our detected color
     */
    /**
     * The method GetColor() returns a normalized color value from the sensor and can be
     * useful if outputting the color to an RGB LED or similar. To
     * read the raw color, use GetRawColor().
     *
     * The color sensor works best when within a few inches from an object in
     * well lit conditions (the built in LED is a big help here!). The farther
     * an object is the more light from the surroundings will bleed into the
     * measurements and make it difficult to accurately determine its color.
     */
    Color detectedColor = m_colorSensor.getColor();

    String colorString;
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

    if (match.color == kBlueTarget) {
      colorString = "Blue";
    } else if (match.color == kRedTarget) {
      colorString = "Red";
    } else if (match.color == kGreenTarget) {
      colorString = "Green";
    } else if (match.color == kYellowTarget) {
      colorString = "Yellow";
    } else {
      colorString = "Unknown";
    }

    /**
     * Open Smart Dashboard or Shuffleboard to see the color detected by the
     * sensor.
     */
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);


//    SmartDashboard.putNumber("IR", IR);
    /**
     * Attempting to count rotations
     */
    if (previousColor != colorString) {
      if (previousColor == "Blue" && colorString == "Yellow") {
        rotationCount = rotationCount + 1;
        previousColor = colorString;
      }
      if (previousColor == "Red" && colorString == "Green") {
        rotationCount = rotationCount + 1;
        previousColor = colorString;
      }
      if (previousColor == "Green" && colorString == "Blue") {
        rotationCount = rotationCount + 1;
        previousColor = colorString;
      }
      if (previousColor == "Yellow" && colorString == "Red") {
        rotationCount = rotationCount + 1;
        previousColor = colorString;
      }
    }
    SmartDashboard.putNumber("Rotation Count", rotationCount / 8);
    return colorString;
  }
}