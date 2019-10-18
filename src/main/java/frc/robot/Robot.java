/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lightning.LightningRobot;
import frc.robot.subsystems.Core;
import frc.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.experimental.command.Command;
import edu.wpi.first.wpilibj.experimental.command.CommandScheduler;

public class Robot extends LightningRobot {

    public static Drivetrain drivetrain;
    public static Core core;
    public static OI oi;

    public Robot() {
        super();
        drivetrain = new Drivetrain(false);
        core = new Core();
        oi = new OI();
    }

    public void robotInit() {
        super.robotInit();
        core.init();
        drivetrain.init();
    }
}


