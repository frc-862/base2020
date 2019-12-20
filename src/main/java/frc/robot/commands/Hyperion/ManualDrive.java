/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Hyperion;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hyperion;

public class ManualDrive extends Command {

    public ManualDrive() {
        requires(Robot.hyperion);
    }

    @Override
    public void execute() {
        Robot.hyperion.setPower(Robot.oi.getManualTurretPwrInput());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
