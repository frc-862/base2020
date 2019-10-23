/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {
    
    Joystick leftJoy = new Joystick(JoystickConstants.DRIVER_LEFT_JOY);
    Joystick rightJoy = new Joystick(JoystickConstants.DRIVER_RIGHT_JOY);
    XboxController driver = new XboxController(JoystickConstants.COPILOT);

    public double getThrottle() {
        final double stick = driver.getRawAxis(JoystickConstants.THRUSTMASTER_Y_AXIS);
        return stick * stick * Math.signum(-stick);
    }

    public double getTurn() {
        final double stick = driver.getRawAxis(4); // TODO - constant
        return stick * stick * Math.signum(-stick);
    }

    public boolean getQuickTurn() {
        return driver.getRawButton(1); // TODO - constant
    }

    public double getRightThrottle() {
        return -rightJoy.getRawAxis(JoystickConstants.THRUSTMASTER_Y_AXIS);
    }

    public double getLeftThrottle() {
        return -leftJoy.getRawAxis(JoystickConstants.THRUSTMASTER_Y_AXIS);
    }

}
