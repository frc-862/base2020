/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lightning.util.XBoxController;
import frc.robot.commands.Hyperion.HomeTurret;
import frc.robot.commands.Hyperion.ManualDrive;
import frc.robot.commands.Hyperion.TurretToFieldPosition;
import frc.robot.commands.Hyperion.TurretToRobotSetpoint;
import frc.robot.commands.Quasar.TankDrive;
import frc.robot.commands.Quasar.VelocityTankDrive;

public class OI {
    
    Joystick drive;
    public XBoxController copilot;

    JoystickButton shiftButton;

    public OI() {

        drive = new Joystick(JoystickConstants.DRIVER);
        copilot = new XBoxController(JoystickConstants.COPILOT);

        shiftButton = new JoystickButton(drive, 6);

        initializeButtons();

        initializeDashboardCommands();

    }

    private void initializeButtons() {
        shiftButton.whenPressed(new InstantCommand(Robot.drivetrain, () -> Robot.drivetrain.chngGear()));
    }

    private void initializeDashboardCommands() {

        SmartDashboard.putData("Turret_Manual_Ctrl", new ManualDrive());
        SmartDashboard.putData("Turret_Field_Ctrl", new TurretToFieldPosition());
        SmartDashboard.putData("Turret_Robot_Ctrl", new TurretToRobotSetpoint());
        SmartDashboard.putData("HOME_TURRET", new HomeTurret());

        SmartDashboard.putData("OpenLoop", new TankDrive());
        SmartDashboard.putData("ClosedLoop", new VelocityTankDrive());

    }

    public double getRightThrottleInput() {
        return -drive.getRawAxis(5);
    }

    public double getLeftThrottleInput() {
        return -drive.getRawAxis(1);
    }

    public double getManualTurretPwrInput() {
        return (copilot.getRawAxis(3) - copilot.getRawAxis(2));
    }

    
    public double getThrottle() {
        final double stick = copilot.getLeftStickY();
        return stick * stick * Math.signum(stick);
    }

    public double getTurn() {
        final double stick = copilot.getRightStickX();
        return stick * stick * Math.signum(-stick);
    }

    public boolean getQuickTurn() {
        return copilot.aButton.get();
    }


}
