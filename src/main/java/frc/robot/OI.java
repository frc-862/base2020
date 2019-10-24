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
    
    Joystick leftJoy = new Joystick(JoystickConstants.DRIVER_LEFT_JOY);
    Joystick rightJoy = new Joystick(JoystickConstants.DRIVER_RIGHT_JOY);
    XBoxController driver = new XBoxController(JoystickConstants.COPILOT);

    public double getThrottle() {
        final double stick = driver.getLeftStickY();
        return stick * stick * Math.signum(stick);
    }

    public double getTurn() {
        final double stick = driver.getRightStickX();
        return stick * stick * Math.signum(-stick);
    }

    public boolean getQuickTurn() {
        return driver.aButton.get();
    }

    public double getRightThrottle() {
        return -rightJoy.getRawAxis(JoystickConstants.THRUSTMASTER_Y_AXIS);
    }

    public double getLeftThrottle() {
        return -leftJoy.getRawAxis(JoystickConstants.THRUSTMASTER_Y_AXIS);
    }

    public double getTurretPwr() {
        return Math.pow(-driver.getRightStickX(), 3);
    }

}
