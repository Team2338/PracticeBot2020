# FRC2020
 2020 Robot code for FRC team 2338 Gear it Forward.
 
 ## Drivetrain
 Arcade Drive.
 Uses 4 TalonSRX motor controllers
 
 ## Hanger
 W.I.P. Motion Magic
 
 ## Indexer
 Capable of using TalonSRX OR VictorSPX.
 If available space is detected by sensors, power cells are moved through the system.
 Can reverse last power cells in last 3 positions if jammed.
 Can be toggled on/off if jammed or if sensor is accidentally triggered.
 
 ## Intake
 Mecanum wheels positioned to draw power cells towards the center, where they are fed into the indexer.
 Holds the 5th power cell.
 
 ## Shooter
 REV SparkMax motor controller partnered with NEO brushless motor.
 Uses Feed-Forward and P loop to reach target RPM.