/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Quasar;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import frc.robot.subsystems.Drivetrain;

/**
 * Add your docs here.
 */
public class ToggleShifter extends SendableCommandBase{

    private Drivetrain drivetrain;

    public ToggleShifter(Drivetrain drivetrain) {
        addRequirements(drivetrain);
        this.drivetrain = drivetrain;
    }

    int count = 0;

    @Override
    public void execute() {

        if(count == 0) {

            DoubleSolenoid.Value currentGear = drivetrain.getGear();

            if(currentGear.equals(Drivetrain.kHighGear)) drivetrain.lowGear();
            else if (currentGear.equals(Drivetrain.kLowGear)) drivetrain.highGear();
            else drivetrain.lowGear();

            System.out.println("Changed.");

        }

        count++;

        end(false);
        
    }

}
