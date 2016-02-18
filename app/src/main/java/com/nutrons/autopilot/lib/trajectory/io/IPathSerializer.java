package com.nutrons.autopilot.lib.trajectory.io;

import com.nutrons.autopilot.lib.trajectory.Path;

/**
 * Interface for methods that serialize a Path or Trajectory.
 *
 * @author Jared341
 */
public interface IPathSerializer {

  public String serialize(Path path);
}
