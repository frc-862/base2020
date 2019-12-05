/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Hyperion;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Hyperion;

/**
 * Add your docs here.
 */
public class TurretToFieldPosition extends Command {

    

    private double heading = 0.0d;

    public TurretToFieldPosition() {
        requires(Robot.hyperion);   
    }

    @Override
    public void execute() {

        if (Robot.oi.copilot.aButton.get()) heading = 180.0d;
        else if (Robot.oi.copilot.bButton.get()) heading = 90.0d;
        else if (Robot.oi.copilot.xButton.get()) heading = 270.0d;
        else heading = 0.0d;

        Robot.hyperion.goToHeading(heading);
        
    }
@Override
protected boolean isFinished() {
    return false;
}
}
