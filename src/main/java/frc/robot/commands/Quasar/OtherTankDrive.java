/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Quasar;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;

public class OtherTankDrive extends Command {

  public OtherTankDrive() {
    requires(Robot.drivetrain);
  }

  @Override
  public void execute() {
    Robot.drivetrain.otherTankDrive(Robot.oi.getLeftThrottle(), Robot.oi.getRightThrottle());
  }

    @Override
    protected boolean isFinished() {
        return false;
    }
  
}
