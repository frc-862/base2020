/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.experimental.command.SendableSubsystemBase;
import frc.robot.commands.ArcadeDrive;

import java.util.function.Consumer;


public class Drivetrain extends SendableSubsystemBase
{
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    CANSparkMax left1 = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
    CANSparkMax left2 = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
    CANSparkMax left3 = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);

    CANSparkMax right1 = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);
    CANSparkMax right2 = new CANSparkMax(5, CANSparkMaxLowLevel.MotorType.kBrushless);
    CANSparkMax right3 = new CANSparkMax(6, CANSparkMaxLowLevel.MotorType.kBrushless);

    public Drivetrain() {
        left2.setInverted(true);
        right1.setInverted(true);
        right3.setInverted(true);

        withEachMotor((m) -> m.setOpenLoopRampRate(0.5));

        setDefaultCommand(new ArcadeDrive(this));
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
        left *= 0.5;
        right *= 0.5;

        left1.set(left);
        left2.set(left);
//    left3.set(left);

        right1.set(right);
        right2.set(right);
//    right3.set(right);
    }

    public void setVelocity(double left, double right) {
        // TODO implement
    }

    public void resetDistance() {
        // TODO implement
    }

    public double getLeftDistance() {
        // TODO implement
        return 0;
    }

    public double getRightDistance() {
        // TODO implement
        return 0;
    }

    public double getLeftVelocity() {
        // TODO implement
        return 0;
    }

    public double getRightVelocity() {
        // TODO implement
        return 0;
    }
}

