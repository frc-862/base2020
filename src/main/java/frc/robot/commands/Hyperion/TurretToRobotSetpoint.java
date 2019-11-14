/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Hyperion;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Hyperion;

/**
 * Add your docs here.
 */
public class TurretToRobotSetpoint extends SendableCommandBase {

    private Hyperion hyperion;

    private int degree = 0;

    public TurretToRobotSetpoint(Hyperion hyperion) {
        addRequirements(hyperion);
        this.hyperion = hyperion;
    }

    @Override
    public void execute() {

        if (Robot.oi.copilot.aButton.get()) degree = 180;
        else if (Robot.oi.copilot.bButton.get()) degree = 90;
        else if (Robot.oi.copilot.xButton.get()) degree = 270;
        else degree = 0;

        hyperion.goToPosition(degree);
        
    }

}
