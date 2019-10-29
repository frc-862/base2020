/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Quasar;

import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
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
        // double leftSetPoint = Robot.oi.getLeftThrottle() * Constants.leftGains.getMaxRPM();
        // double rightSetPoint = Robot.oi.getRightThrottle() * Constants.rightGains.getMaxRPM();

        double targetLeft = (Math.abs(Robot.oi.getLeftThrottle()) < 0.1) ? 0.0 : Robot.oi.getLeftThrottle() * Constants.leftGains.getMaxRPM();
        double targetRight = (Math.abs(Robot.oi.getRightThrottle()) < 0.1) ? 0.0 : Robot.oi.getRightThrottle() * Constants.rightGains.getMaxRPM();
        
        SmartDashboard.putNumber("Left Target", targetLeft);
        SmartDashboard.putNumber("Right Target", targetRight);

        drivetrain.setVelocity(targetLeft, targetRight);
    }

}