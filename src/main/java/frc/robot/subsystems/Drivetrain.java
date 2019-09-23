/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.experimental.command.SendableSubsystemBase;
import frc.lightning.logging.DataLogger;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.TankDrive;
import frc.robot.commands.VelocityTankDrive;
import frc.robot.misc.Gains;
import frc.robot.misc._Gains;

import javax.xml.crypto.Data;
import java.util.function.Consumer;


public class Drivetrain extends SendableSubsystemBase {

    CANSparkMax left1;
    public CANEncoder left1Encoder;
    CANSparkMax left2;
    CANSparkMax left3;
    public CANPIDController leftPIDFController;

    CANSparkMax right1;
    public CANEncoder right1Encoder;
    CANSparkMax right2;
    CANSparkMax right3;
    public CANPIDController rightPIDFController;

    SpeedControllerGroup leftGroup;
    SpeedControllerGroup rightGroup;

    DifferentialDrive drive;

    public _Gains leftGains;
    public _Gains rightGains;

    public Drivetrain() {
        left1 = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
        left1Encoder = new CANEncoder(left1);
        left2 = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
        // left3 = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftPIDFController = left1.getPIDController();

        right1 = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);
        right1Encoder = new CANEncoder(right1);
        right2 = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);
        //right3 = new CANSparkMax(6, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightPIDFController = right1.getPIDController();

        //left3.setInverted(false);
        left2.setInverted(true);
        left1.setInverted(false);
        right1.setInverted(false);
        right2.setInverted(true);
        //right3.setInverted(false);

        withEachMotor((m) -> m.setOpenLoopRampRate(0.5));

        leftGroup = new SpeedControllerGroup(left1, left2/*, left3*/);
        rightGroup = new SpeedControllerGroup(right1, right2/*, right3*/);
        drive = new DifferentialDrive(leftGroup, rightGroup);

        /*                      P    I    D   FF   Iz   MaxOutput   MinOutput  MaxRPM */
        leftGains = new _Gains(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

        /*                       P    I    D   FF   Iz   MaxOutput   MinOutput  MaxRPM */
        rightGains = new _Gains(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

        leftPIDFController.setP(leftGains.getkP());
        leftPIDFController.setI(leftGains.getkI());
        leftPIDFController.setD(leftGains.getkD());
        leftPIDFController.setFF(leftGains.getkFF());
        leftPIDFController.setIZone(leftGains.getkIz());
        leftPIDFController.setOutputRange(leftGains.getkMinOutput(), leftGains.getkMaxOutput());

        rightPIDFController.setP(rightGains.getkP());
        rightPIDFController.setI(rightGains.getkI());
        rightPIDFController.setD(rightGains.getkD());
        rightPIDFController.setFF(rightGains.getkFF());
        rightPIDFController.setIZone(rightGains.getkIz());
        rightPIDFController.setOutputRange(rightGains.getkMinOutput(), leftGains.getkMaxOutput());

        setDefaultCommand(new VelocityTankDrive(this));

        DataLogger.addDataElement("leftVelocity", () -> left1Encoder.getVelocity());
        DataLogger.addDataElement("rightVelocity", () -> right1Encoder.getVelocity());
    }

    @Override
    public void periodic() {

    }

    private void withEachMotor(Consumer<CANSparkMax> op) {
        op.accept(left1);
        op.accept(left2);
        //op.accept(left3);
        op.accept(right1);
        op.accept(right2);
        //op.accept(right3);
    }

    public void setPower(double left, double right) {
        left *= 0.5;
        right *= 0.5;
        drive.tankDrive(left, right, false);
    }

    public void arcadeDrive(double speed, double rotation) {
        drive.arcadeDrive(speed, rotation, false);
    }

    public void curvatureDrive(double speed, double rotation) {
        drive.curvatureDrive(speed, rotation, false);
    }

    public void curvatureDrive(double speed, double rotation, boolean quickTurn) {
        drive.curvatureDrive(speed*.5, -rotation, quickTurn);
    }

    public void tankDrive(double left, double right){
        drive.tankDrive(left, right, true);
    }

    public void setVelocity(double left, double right) {
        // TODO implement
    }

    public void resetDistance() {
        left1Encoder.setPosition(0);
        right1Encoder.setPosition(0);
    }

    public double getLeftDistance() {
        return left1Encoder.getPosition();
    }

    public double getRightDistance() {
        return right1Encoder.getPosition();
    }

    public double getLeftVelocity() {
        return left1Encoder.getVelocity();
    }

    public double getRightVelocity() {
        return right1Encoder.getVelocity();
    }
}

