/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;

public class VelocityTankDrive extends SendableCommandBase {

  private Drivetrain drivetrain;

  public VelocityTankDrive(Drivetrain drivetrain) {
    addRequirements(drivetrain);
    this.drivetrain = drivetrain;
  }

  @Override
  public void execute() {

    double leftSetPoint = Robot.oi.getLeftThrottle() * this.drivetrain.leftGains.getMaxRPM();
    double rightSetPoint = Robot.oi.getRightThrottle() * this.drivetrain.rightGains.getMaxRPM();

    drivetrain.rightPIDFController.setReference(leftSetPoint, ControlType.kVelocity);
    drivetrain.leftPIDFController.setReference(rightSetPoint, ControlType.kVelocity);

    SmartDashboard.putNumber("leftSetPoint", leftSetPoint);
    SmartDashboard.putNumber("leftEncoderVelocity", drivetrain.left1Encoder.getVelocity());
    
    SmartDashboard.putNumber("rightSetPoint", rightSetPoint);
    SmartDashboard.putNumber("rightEncoderVelocity", drivetrain.right1Encoder.getVelocity());
    
  }
}
