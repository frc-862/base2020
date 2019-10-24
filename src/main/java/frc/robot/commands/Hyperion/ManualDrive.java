/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Hyperion;

import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hyperion;

public class ManualDrive extends SendableCommandBase {

    private Hyperion hyperion;

    public ManualDrive(Hyperion hyperion) {
        addRequirements(hyperion);
        this.hyperion = hyperion;
    }

    @Override
    public void execute() {
        hyperion.setPower(Robot.oi.getTurretPwr());
    }

}
