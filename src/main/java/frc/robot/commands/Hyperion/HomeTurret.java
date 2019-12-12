/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Hyperion;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class HomeTurret extends Command {

    public HomeTurret() {
        requires(Robot.hyperion);
    }

    @Override
    protected void initialize() {

    }

    @Override
    public void execute() {
        Robot.hyperion.homeTurret();
    }

    @Override
    protected boolean isFinished() {
        return (Math.abs(Robot.hyperion.getTurretPosition()) < 1.5d) ? true : false;
    }

}
