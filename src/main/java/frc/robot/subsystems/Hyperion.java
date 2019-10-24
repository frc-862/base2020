/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.experimental.command.SendableSubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lightning.logging.DataLogger;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.commands.Hyperion.ManualDrive;
import frc.robot.misc.REVGains;

public class Hyperion extends SendableSubsystemBase {

    private final String name = "HYPERION";

    private CANSparkMax driver;

    private CANEncoder encoder;

    private CANPIDController PIDFController;

    public Hyperion() {

        setName(name);

        driver = new CANSparkMax(RobotMap.HYPERION_DRIVER_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);

        encoder = new CANEncoder(driver);

        PIDFController = driver.getPIDController();

        PIDFController.setI(Constants.hyperionGains.getkI());
        PIDFController.setD(Constants.hyperionGains.getkD());
        PIDFController.setP(Constants.hyperionGains.getkP());
        PIDFController.setFF(Constants.hyperionGains.getkFF());
        PIDFController.setIZone(Constants.hyperionGains.getkIz());
        PIDFController.setOutputRange(Constants.hyperionGains.getkMinOutput(), Constants.hyperionGains.getkMaxOutput());

        REVGains.putGainsToBeTunedOnDash(name, Constants.hyperionGains);

        if(Constants.HYPERION_LOGGING_ENABLED) {
            DataLogger.addDataElement("turretVelocity", () -> getTurretVelocity());
            DataLogger.addDataElement("turretPosition", () -> getTurretPosition());
        }

        if(Constants.HYPERION_DASHBOARD_ENABLED) {
            SmartDashboard.putNumber("Turret Velocity", getTurretVelocity());
            SmartDashboard.putNumber("Turret Position", getTurretPosition());
        }

        setDefaultCommand(new ManualDrive(this));
        
    }

    public void init() {
        this.resetEncPos();
    }

    @Override
    public void periodic() {
        REVGains.updateGainsFromDash(name, Constants.hyperionGains, PIDFController);
        if(Constants.HYPERION_DASHBOARD_ENABLED) {
            SmartDashboard.putNumber("Turret Velocity", getTurretVelocity());
            SmartDashboard.putNumber("Turret Position", getTurretPosition());
        }
    }

    public void resetEncPos() {
        encoder.setPosition(0.0);
    }

    public void setPower(double pwr) {
        driver.set(pwr);
    }

    public void homeTurret() {
        PIDFController.setReference(0.0, ControlType.kPosition);
    }

    public void setPosition(double position) {
        PIDFController.setReference(position, ControlType.kPosition);
    }

    public double getTurretPosition() {
        return encoder.getPosition();
    }

    public double getTurretVelocity() {
        return encoder.getVelocity();
    }

}