# FRC2020
 FRC robot code for 2338 Gear it Forward's 2020 robot, "Prowler".
 
 ## Drivetrain
 Arcade Drive.
 Uses 4 TalonSRX motor controllers with 4 CIM motors.
 
 ## Hanger
 W.I.P. Motion Magic
 
 ## Indexer
 Capable of using TalonSRX OR VictorSPX.
 If available space is detected by sensors, power cells are moved through the system.
 Can reverse last power cells in last 3 positions if jammed.
 Can be toggled on/off if jammed or if sensor is accidentally triggered.
 
 ## Intake
 Vectored wheels positioned to draw power cells towards the center, where they are fed into the indexer.
 Holds the 5th power cell.
 
 ## Shooter
 REV SparkMax motor controller partnered with NEO brushless motor.
 Uses Feed-Forward and P loop to reach target RPM.
