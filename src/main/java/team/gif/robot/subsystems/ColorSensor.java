/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package team.gif.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import team.gif.robot.RobotMap;

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
    System.out.println("Color Senor Initiated");
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

//  }
//
//  public void getColor() {
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
    System.out.println("In robot periodic");
    Color detectedColor = m_colorSensor.getColor();

    /**
     * The sensor returns a raw IR value of the infrared light detected.
     */
    double IR = m_colorSensor.getIR();

    /**
     * Open Smart Dashboard or Shuffleboard to see the color detected by the
     * sensor.
     */
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("IR", IR);

    /**
     * In addition to RGB IR values, the color sensor can also return an
     * infrared proximity value. The chip contains an IR led which will emit
     * IR pulses and measure the intensity of the return. When an object is
     * close the value of the proximity will be large (max 2047 with default
     * settings) and will approach zero when the object is far away.
     *
     * Proximity can be used to roughly approximate the distance of an object
     * or provide a threshold for when an object is close enough to provide
     * accurate color values.
     */
    int proximity = m_colorSensor.getProximity();

    SmartDashboard.putNumber("Proximity", proximity);

    /**
     * Dispays our guess of the color.
     * Order is confidence level.
     * All are with sensor light on.
     * 1: Red - Highest value = Red
     * 2: Yellow - Highest value = Green, R > B
     * 3: Green - Highest value = Green, B > R, Blue and Red within 10
     * 4: Blue - Green & Blue > Red, G & B are within 5, Blue and Red greater than 10
     */
    String colorGuess = "";
    double redBlueDifference = Math.abs(detectedColor.blue - detectedColor.red);
    if(detectedColor.red > detectedColor.blue && detectedColor.red > detectedColor.green) {
      colorGuess = "Red";

    } else if(detectedColor.green > detectedColor.red && detectedColor.green > detectedColor.blue && detectedColor.red > detectedColor.blue) {
      colorGuess = "Yellow";
    } else if(detectedColor.green > detectedColor.blue && detectedColor.green > detectedColor.red && redBlueDifference < 0.15) {
      colorGuess = "Green";
    } else {
      colorGuess = "Blue";
    }

    SmartDashboard.putString("Color", colorGuess);
  }
}