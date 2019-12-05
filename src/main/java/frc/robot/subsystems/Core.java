/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lightning.logging.DataLogger;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotMap;


public class Core extends Command {

    private final String name = "CORE";
    
    Compressor compressor;

    AHRS navx;

    public Core() {

        setName(name);

        compressor = new Compressor(RobotMap.COMPRESSOR_ID);

        navx = new AHRS(SPI.Port.kMXP);

        if(Constants.CORE_LOGGING_ENABLED){
            DataLogger.addDataElement("Yaw", () -> getYaw());
            DataLogger.addDataElement("Pitch", () -> getPitch());
            DataLogger.addDataElement("Roll", () -> getRoll());
            DataLogger.addDataElement("Angle", () -> getAngle());
        }

        if(Constants.CORE_DASHBOARD_ENABLED){
            SmartDashboard.putNumber("Yaw", getYaw());
            SmartDashboard.putNumber("Roll", getRoll());
            SmartDashboard.putNumber("Pitch", getPitch());
            SmartDashboard.putNumber("Angle", getAngle());
        }
        
    }

    public void init() {
        resetNavx();
    }

    public void periodic() {
        if(Constants.CORE_DASHBOARD_ENABLED){
            SmartDashboard.putNumber("Yaw", getYaw());
            SmartDashboard.putNumber("Roll", getRoll());
            SmartDashboard.putNumber("Pitch", getPitch());
            SmartDashboard.putNumber("Angle", getAngle());
        }
    }

    public void resetNavx() { navx.reset(); }
    public double getYaw() { return (navx.getYaw()); }
    public double getRoll() { return navx.getRoll(); }
    public double getPitch() { return navx.getPitch(); }
    public double getAngle() { return navx.getAngle(); }
    public double getAngleAdj() { return navx.getAngleAdjustment(); }
    //public double getContinuousHeading() { return navx.getAngle(); }

    @Override
    protected boolean isFinished() {
        return false;
    }
}


