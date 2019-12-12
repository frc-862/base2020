/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Hyperion;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.subsystems.Hyperion;

/**
 * Add your docs here.
 */
public class TurretToRobotSetpoint extends Command {

    private int degree = 0;

    public TurretToRobotSetpoint() {
        requires(Robot.hyperion);
    }

    @Override
    public void execute() {

        if (Robot.oi.copilot.bButton.get())
            degree = 90;
        else if (Robot.oi.copilot.xButton.get())
            degree = -90;
        else
            degree = 0;

        Robot.hyperion.goToPosition(degree);

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
