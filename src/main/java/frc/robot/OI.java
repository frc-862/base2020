/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.lightning.util.XBoxController;

public class OI {
    
    Joystick drive = new Joystick(0);
    Joystick leftJoy = new Joystick(1/*JoystickConstants.DRIVER_LEFT_JOY*/);
    Joystick rightJoy = new Joystick(JoystickConstants.DRIVER_RIGHT_JOY);
    XBoxController copilot = new XBoxController(JoystickConstants.COPILOT);

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

    public double getRightThrottle() {
        // return -rightJoy.getRawAxis(JoystickConstants.THRUSTMASTER_Y_AXIS);
        return -drive.getRawAxis(5);
    }

    public double getLeftThrottle() {
        // return -leftJoy.getRawAxis(JoystickConstants.THRUSTMASTER_Y_AXIS);
        return -drive.getRawAxis(1);
    }

    public double getTurretPwr() {
        return Math.pow(-copilot.getRightStickX(), 3);
    }

}
