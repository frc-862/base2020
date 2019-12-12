/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lightning.logging.DataLogger;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.Hyperion.ManualDrive;
import frc.robot.commands.Hyperion.TurretToFieldPosition;
import frc.robot.commands.Hyperion.TurretToRobotSetpoint;
import frc.robot.misc.REVGains;

public class Hyperion extends Subsystem {

    private final String name = "HYPERION";

    private CANSparkMax driver;

    private CANDigitalInput fwdLimSwitch;

    private CANDigitalInput revLimSwitch;

    double kP = 0.0330; // RIP

    private CANEncoder encoder;

    private PigeonIMU pigeon;

    private CANPIDController PIDFController;

    public Hyperion() {

        setName(name);

        driver = new CANSparkMax(RobotMap.HYPERION_DRIVER_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);

        driver.setIdleMode(IdleMode.kBrake);

        fwdLimSwitch = new CANDigitalInput(driver, CANDigitalInput.LimitSwitch.kForward, CANDigitalInput.LimitSwitchPolarity.kNormallyOpen);
        fwdLimSwitch.enableLimitSwitch(true);

        revLimSwitch = new CANDigitalInput(driver, CANDigitalInput.LimitSwitch.kReverse, CANDigitalInput.LimitSwitchPolarity.kNormallyOpen);
        revLimSwitch.enableLimitSwitch(true);

        encoder = new CANEncoder(driver);

        pigeon = new PigeonIMU(RobotMap.PIGEON_ID);
        pigeon.configFactoryDefault();

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
        
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ManualDrive());
        // setDefaultCommand(new TurretToRobotSetpoint());
        // setDefaultCommand(new TurretToFieldPosition());
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

        SmartDashboard.putBoolean("FwdLimitSwitchOn", fwdLimSwitch.get());
        SmartDashboard.putBoolean("RevLimitSwitchOn", revLimSwitch.get());
        SmartDashboard.putNumber("Turret_Gyro", getTurretHeading());

        double[] ypr = new double[3];
        pigeon.getYawPitchRoll(ypr);
        SmartDashboard.putNumber("YPR", ypr[0]);

        if(fwdLimSwitch.get()) encoder.setPosition(Constants.FWD_LIMIT_TICKS);
        if(revLimSwitch.get()) encoder.setPosition(Constants.REV_LIMIT_TICKS);   

    }

    public void resetEncPos() {
        encoder.setPosition(0.0);
    }

    public void setPower(double pwr) {
        driver.set(pwr * Constants.MAX_TURRET_POWER);
    }

    public void homeTurret() {
        Robot.hyperion.goToPosition(0);
    }

    public void setPosition(double position) {
        PIDFController.setReference(position, ControlType.kPosition);
    }

    public void stop() {
        driver.set(0.0);
    }

    public double getTurretPosition() {
        return encoder.getPosition() * (360 / 76.55);
    }

    public double getTurretHeading() {
        return pigeon.getAbsoluteCompassHeading(); // this functions returns value ranging [0,360)
    }

    public double getTurretVelocity() {
        return encoder.getVelocity();
    }

    public void goToPosition(int targetDegree) {
        double error = targetDegree - getTurretPosition();
        double power = error * kP;
        setPower(power);
    }

    public void goToHeading(double targetHeading) {
        double error = targetHeading - getTurretHeading();
        double power = error * kP;
        setPower(power);
    }

}
