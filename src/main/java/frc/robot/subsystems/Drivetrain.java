/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.experimental.command.SendableSubsystemBase;
import frc.lightning.logging.DataLogger;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.TankDrive;
import frc.robot.commands.VelocityTankDrive;

import javax.xml.crypto.Data;
import java.util.function.Consumer;


public class Drivetrain extends SendableSubsystemBase
{
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    CANSparkMax left1;
    CANEncoder left1Encoder;
    CANSparkMax left2;
    CANSparkMax left3;

    CANSparkMax right1;
    CANEncoder right1Encoder;
    CANSparkMax right2;
    CANSparkMax right3;

    SpeedControllerGroup leftGroup;
    SpeedControllerGroup rightGroup;

    DifferentialDrive drive;

    public Drivetrain() {
        left1 = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
        left1Encoder = new CANEncoder(left1);
        left2 = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
        // left3 = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);

        right1 = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);
        right1Encoder = new CANEncoder(right1);
        right2 = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);
        //right3 = new CANSparkMax(6, CANSparkMaxLowLevel.MotorType.kBrushless);

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

