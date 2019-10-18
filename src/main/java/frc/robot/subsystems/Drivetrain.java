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
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.experimental.command.SendableSubsystemBase;
import frc.lightning.logging.DataLogger;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.TankDrive;
import frc.robot.commands.VelocityTankDrive;
import frc.robot.misc.Gains;
import frc.robot.misc._Gains;

import javax.xml.crypto.Data;
import java.util.function.Consumer;

public class Drivetrain extends SendableSubsystemBase {

    CANSparkMax left1;
    CANSparkMax left2;
    CANSparkMax left3;
    
    public CANEncoder left1Encoder;

    public CANPIDController leftPIDFController;

    CANSparkMax right1;
    CANSparkMax right2;
    CANSparkMax right3;

    public CANEncoder right1Encoder;

    public CANPIDController rightPIDFController;

    SpeedControllerGroup leftGroup;
    SpeedControllerGroup rightGroup;

    DifferentialDrive drive;

    public Drivetrain(boolean velocity) {

        left1 = new CANSparkMax(RobotMap.LEFT_1_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        left2 = new CANSparkMax(RobotMap.LEFT_2_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        left3 = new CANSparkMax(RobotMap.LEFT_3_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        
        left1Encoder = new CANEncoder(left1);

        leftPIDFController = left1.getPIDController();

        right1 = new CANSparkMax(RobotMap.RIGHT_1_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        right2 = new CANSparkMax(RobotMap.RIGHT_2_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        right3 = new CANSparkMax(RobotMap.RIGHT_3_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);

        right1Encoder = new CANEncoder(right1);

        rightPIDFController = right1.getPIDController();

        left1.setInverted(false);
        left2.setInverted(true);
        left3.setInverted(false);
        right1.setInverted(false);
        right2.setInverted(true);
        right3.setInverted(false);

        withEachMotor((m) -> m.setOpenLoopRampRate(Constants.OPEN_LOOP_RAMP_RATE));

        leftGroup = new SpeedControllerGroup(left1, left2, left3);
        rightGroup = new SpeedControllerGroup(right1, right2, right3);

        drive = new DifferentialDrive(leftGroup, rightGroup);

        leftPIDFController.setP(Constants.leftGains.getkP());
        leftPIDFController.setI(Constants.leftGains.getkI());
        leftPIDFController.setD(Constants.leftGains.getkD());
        leftPIDFController.setFF(Constants.leftGains.getkFF());
        leftPIDFController.setIZone(Constants.leftGains.getkIz());
        leftPIDFController.setOutputRange(Constants.leftGains.getkMinOutput(), Constants.leftGains.getkMaxOutput());

        rightPIDFController.setP(Constants.rightGains.getkP());
        rightPIDFController.setI(Constants.rightGains.getkI());
        rightPIDFController.setD(Constants.rightGains.getkD());
        rightPIDFController.setFF(Constants.rightGains.getkFF());
        rightPIDFController.setIZone(Constants.rightGains.getkIz());
        rightPIDFController.setOutputRange(Constants.rightGains.getkMinOutput(), Constants.leftGains.getkMaxOutput());

        if(velocity) setDefaultCommand(new VelocityTankDrive(this));
        else setDefaultCommand(new TankDrive(this));

        //DataLogger.addDataElement("leftVelocity", () -> left1Encoder.getVelocity());
        //DataLogger.addDataElement("rightVelocity", () -> right1Encoder.getVelocity());
    }

    public void init() {
        this.resetDistance();
    }

    @Override
    public void periodic() {

    }

    private void withEachMotor(Consumer<CANSparkMax> op) {
        op.accept(left1);
        op.accept(left2);
        op.accept(left3);
        op.accept(right1);
        op.accept(right2);
        op.accept(right3);
    }

    public void setPower(double left, double right) {
        drive.tankDrive(left, right, true);
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
        this.rightPIDFController.setReference(left, ControlType.kVelocity);
        this.leftPIDFController.setReference(right, ControlType.kVelocity);
    }

    public void resetDistance() {
        left1Encoder.setPosition(0.0);
        right1Encoder.setPosition(0.0);
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