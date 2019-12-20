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
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lightning.logging.DataLogger;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.commands.Quasar.ArcadeDrive;
import frc.robot.commands.Quasar.TankDrive;
import frc.robot.commands.Quasar.VelocityTankDrive;
import frc.robot.misc.Gains;
import frc.robot.misc.REVGains;

import javax.xml.crypto.Data;
import java.util.function.Consumer;

public class Drivetrain extends Subsystem {

    private final String name = "DRIVETRAIN";
    
    public static final DoubleSolenoid.Value kHighGear = DoubleSolenoid.Value.kForward;
    public static final DoubleSolenoid.Value kLowGear = DoubleSolenoid.Value.kReverse;

    private CANSparkMax leftMaster;
    private CANSparkMax leftSlave1;
    private CANSparkMax leftSlave2;
    
    private CANEncoder leftEncoder;

    private CANPIDController leftPIDFController;

    private CANSparkMax rightMaster;
    private CANSparkMax rightSlave1;
    private CANSparkMax rightSlave2;

    private CANEncoder rightEncoder;

    private CANPIDController rightPIDFController;

    private DoubleSolenoid shifter;

    // private SpeedControllerGroup leftGroup;
    // private SpeedControllerGroup rightGroup;

    // private DifferentialDrive drive;

    public Drivetrain() {

        setName(name);

        leftMaster = new CANSparkMax(RobotMap.LEFT_1_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftSlave1 = new CANSparkMax(RobotMap.LEFT_2_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftSlave2 = new CANSparkMax(RobotMap.LEFT_3_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        
        leftEncoder = new CANEncoder(leftMaster);

        leftPIDFController = leftMaster.getPIDController();

        rightMaster = new CANSparkMax(RobotMap.RIGHT_1_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSlave1 = new CANSparkMax(RobotMap.RIGHT_2_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSlave2 = new CANSparkMax(RobotMap.RIGHT_3_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);

        LiveWindow.add(this);

        rightEncoder = new CANEncoder(rightMaster);

        rightPIDFController = rightMaster.getPIDController();

        shifter = new DoubleSolenoid(RobotMap.SHIFTER_MODULE_NUM, RobotMap.SHIFTER_FWD_CHANNEL, RobotMap.SHIFTER_REVERSE_CHANNEL);

        initMotorDirections();

        withEachMotor((m) -> m.setOpenLoopRampRate(Constants.OPEN_LOOP_RAMP_RATE));
        withEachMotor((m) -> m.setClosedLoopRampRate(Constants.CLOSE_LOOP_RAMP_RATE));
        withEachMotor((m) -> m.setIdleMode(IdleMode.kBrake));

        // leftGroup = new SpeedControllerGroup(left1, left2, left3);
        // rightGroup = new SpeedControllerGroup(right1, right2, right3);

        // drive = new DifferentialDrive(leftGroup, rightGroup);

        // drive.setSafetyEnabled(false);

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
  
        REVGains.putGainsToBeTunedOnDash((name + "_RIGHT"), Constants.rightGains);
        REVGains.putGainsToBeTunedOnDash((name + "_LEFT"), Constants.leftGains);
        
        if(Constants.DRIVETRAIN_DASHBOARD_ENABLED) {
            SmartDashboard.putNumber("Left Velocity", getLeftVelocity());
            SmartDashboard.putNumber("Left Distance", getLeftDistance());
            SmartDashboard.putNumber("Right Velocity", getRightVelocity());            
            SmartDashboard.putNumber("Right Distance", getRightDistance());
        }

        if(Constants.DRIVETRAIN_LOGGING_ENABLED){
            DataLogger.addDataElement("leftVelocity", () -> leftEncoder.getVelocity());
            DataLogger.addDataElement("rightVelocity", () -> rightEncoder.getVelocity());
        }

    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new TankDrive());
    }

    public void init() {
        this.resetDistance();
        this.lowGear();
    }

    @Override
    public void periodic() {
        REVGains.updateGainsFromDash((name + "_RIGHT"), Constants.rightGains, rightPIDFController);
        REVGains.updateGainsFromDash((name + "_LEFT"), Constants.leftGains, leftPIDFController);
        if(Constants.DRIVETRAIN_DASHBOARD_ENABLED) {
            SmartDashboard.putNumber("Left Velocity", getLeftVelocity());
            SmartDashboard.putNumber("Left Distance", getLeftDistance());
            SmartDashboard.putNumber("Right Velocity", getRightVelocity());            
            SmartDashboard.putNumber("Right Distance", getRightDistance());
        }
    }

    private void withEachMotor(Consumer<CANSparkMax> op) {
        op.accept(leftMaster);
        op.accept(leftSlave1);
        op.accept(leftSlave2);
        op.accept(rightMaster);
        op.accept(rightSlave1);
        op.accept(rightSlave2);
    }

    private void initMotorDirections() {
        rightMaster.setInverted(true);
        rightSlave1.follow(rightMaster, true);
        rightSlave2.follow(rightMaster, false); 
        leftMaster.setInverted(false);
        leftSlave1.follow(leftMaster, true);
        leftSlave2.follow(leftMaster, false);
    }

    public void highGear() {
        shifter.set(kHighGear);
    }

    public void lowGear() {
        shifter.set(kLowGear);
    }

    public boolean isHighGear() {
        if(shifter.get().equals(kHighGear)) return true;
        return false;
    }

    public void chngGear() {
        if(isHighGear()) lowGear();
        else highGear();
    }

    public void setPower(double left, double right) {
        rightMaster.set(right);
        leftMaster.set(left);
    }

    public void setVelocity(double left, double right) {   
        this.rightPIDFController.setReference(right, ControlType.kVelocity);
        this.leftPIDFController.setReference(left, ControlType.kVelocity);
    }

    public void resetDistance() {
        leftEncoder.setPosition(0.0);
        rightEncoder.setPosition(0.0);
    }

    public double getLeftDistance() {
        return leftEncoder.getPosition();
    }

    public double getRightDistance() {
        return rightEncoder.getPosition();
    }

    public double getLeftVelocity() {
        return leftEncoder.getVelocity();
    }

    public double getRightVelocity() {
        return rightEncoder.getVelocity();
    }

    public CANSparkMax getRightMaster() {
        return rightMaster;
    }

    public CANSparkMax getLeftMaster() {
        return leftMaster;
    }

}