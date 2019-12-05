/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lightning.LightningRobot;
import frc.robot.subsystems.Core;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hyperion;
import frc.robot.subsystems.Drivetrain.DriveStyle;

public class Robot extends LightningRobot {

    public static Drivetrain drivetrain;
    public static Hyperion hyperion;
    public static Core core;

    public static OI oi;

    public Robot() {
        super();
        drivetrain = new Drivetrain(DriveStyle.TANK_DRIVE);
        // drivetrain = new Drivetrain(DriveStyle.VELOCITY_TANK_DRIVE);
        // drivetrain = new Drivetrain(DriveStyle.TEST_WEIRDNESS);
        hyperion = new Hyperion();
        core = new Core();
        oi = new OI();
    }

    public void robotInit() {
        super.robotInit();
        drivetrain.init();
        hyperion.init();
        core.init();
    }

}


