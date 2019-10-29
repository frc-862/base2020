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

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.experimental.command.SendableSubsystemBase;
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

public class Drivetrain extends SendableSubsystemBase {

    private final String name = "DRIVETRAIN";
    
    public static enum DriveStyle {

        ARCADE_DRIVE("Arcade Drive"),
        TANK_DRIVE("Open Loop - Tank Drive"),
        VELOCITY_TANK_DRIVE("Closed Loop - Tank Drive");

        private String displayId = "";

        DriveStyle(String displayId) {
            this.displayId = displayId;
        }

        public String getDisplayId() {
            return this.displayId;
        }

    }

    private CANSparkMax left1;
    private CANSparkMax left2;
    private CANSparkMax left3;
    
    private CANEncoder leftEncoder;

    private CANPIDController leftPIDFController;

    private CANSparkMax right1;
    private CANSparkMax right2;
    private CANSparkMax right3;

    private CANEncoder rightEncoder;

    private CANPIDController rightPIDFController;

    private SpeedControllerGroup leftGroup;
    private SpeedControllerGroup rightGroup;

    private DifferentialDrive drive;

    DriveStyle driveStyle;

    public Drivetrain(Drivetrain.DriveStyle driveStyle) {

        this.driveStyle = driveStyle;

        setName(name);

        left1 = new CANSparkMax(RobotMap.LEFT_1_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        left2 = new CANSparkMax(RobotMap.LEFT_2_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        left3 = new CANSparkMax(RobotMap.LEFT_3_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        
        leftEncoder = new CANEncoder(left1);

        leftPIDFController = left1.getPIDController();

        right1 = new CANSparkMax(RobotMap.RIGHT_1_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        right2 = new CANSparkMax(RobotMap.RIGHT_2_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        right3 = new CANSparkMax(RobotMap.RIGHT_3_CAN_ID, CANSparkMaxLowLevel.MotorType.kBrushless);

        LiveWindow.add(this);

        rightEncoder = new CANEncoder(right1);

        rightPIDFController = right1.getPIDController();

        initMotorDirections();

        withEachMotor((m) -> m.setOpenLoopRampRate(Constants.OPEN_LOOP_RAMP_RATE));
        withEachMotor((m) -> m.setClosedLoopRampRate(Constants.CLOSE_LOOP_RAMP_RATE));
        //withEachMotor((m) -> m.setIdleMode(IdleMode.kBrake));
        withEachMotor((m) -> m.setIdleMode(IdleMode.kCoast));

        leftGroup = new SpeedControllerGroup(left1, left2, left3);
        rightGroup = new SpeedControllerGroup(right1, right2, right3);

        drive = new DifferentialDrive(leftGroup, rightGroup);

        drive.setSafetyEnabled(false);

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

        switch(driveStyle) {
            case ARCADE_DRIVE:
                setDefaultCommand(new ArcadeDrive(this));
                break;
            case VELOCITY_TANK_DRIVE:
                setDefaultCommand(new VelocityTankDrive(this));
                break;
            default:
                setDefaultCommand(new TankDrive(this));
                break;
        }
  
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

    public void init() {
        this.resetDistance();
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
        op.accept(left1);
        op.accept(left2);
        op.accept(left3);
        op.accept(right1);
        op.accept(right2);
        op.accept(right3);
    }

    private void initMotorDirections() {
        left1.setInverted(false);
        left2.setInverted(true);
        left3.setInverted(false);
        right1.setInverted(false);
        right2.setInverted(true);
        right3.setInverted(false);
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

}