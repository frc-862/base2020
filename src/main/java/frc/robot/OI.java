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

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    XboxController driver = new XboxController(2);

    Joystick rightJoy = new Joystick(1);
    Joystick leftJoy = new Joystick(0);

    public double getThrottle() {
        final double stick = driver.getRawAxis(1);
        return stick * stick * Math.signum(-stick);
    }

    public double getTurn() {
        final double stick = driver.getRawAxis(4);
        return stick * stick * Math.signum(-stick);
    }

    public boolean getQuickTurn() {
        return driver.getRawButton(1);
    }

    public double getRightThrottle() {
        return -rightJoy.getRawAxis(1);
    }

    public double getLeftThrottle() {
        return -leftJoy.getRawAxis(1);
    }

}
