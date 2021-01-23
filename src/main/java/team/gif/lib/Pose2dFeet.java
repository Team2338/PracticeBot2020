/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package team.gif.lib;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import edu.wpi.first.wpilibj.geometry.*;
import edu.wpi.first.wpilibj.util.Units;

import java.io.IOException;
import java.util.Objects;

/**
 * Represents a 2d pose containing translational and rotational elements.
 */

public class Pose2dFeet {

  private Pose2d pos;

  public Pose2dFeet () {
  }

  public Pose2d setWaypoint (double x, double y, double heading) {
    pos = new Pose2d(Units.feetToMeters(x), Units.feetToMeters(y), new Rotation2d(Units.degreesToRadians(heading)));
    return pos;
  }
}
