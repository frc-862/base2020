/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Quasar;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;

public class TankDrive extends SendableCommandBase {

  private Drivetrain drivetrain;

  public TankDrive(Drivetrain drivetrain) {
    addRequirements(drivetrain);
    this.drivetrain = drivetrain;
  }

  @Override
  public void execute() {
    drivetrain.tankDrive(Robot.oi.getLeftThrottle(), Robot.oi.getRightThrottle());
  }
  
}
