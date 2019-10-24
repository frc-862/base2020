package frc.robot;

import com.revrobotics.CANPIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lightning.ConstantBase;
import frc.robot.misc.REVGains;

public class Constants extends ConstantBase {

    // DEVELOPMENT
    public static final boolean TUNING_ENABLED = true;
    public static final boolean DRIVETRAIN_LOGGING_ENABLED = true;
    public static final boolean DRIVETRAIN_DASHBOARD_ENABLED = true;
    public static final boolean CORE_LOGGING_ENABLED = true;
    public static final boolean CORE_DASHBOARD_ENABLED = true;
    public static final boolean HYPERION_LOGGING_ENABLED = true;
    public static final boolean HYPERION_DASHBOARD_ENABLED = true;

    // HARDWARE
    public static final double TICS_PER_ROTATION = 4 * 360;
    public static final int NEO_MAX_RPM = 5676;

    // DRIVETRAIN
    public static final double OPEN_LOOP_RAMP_RATE = 0.5;
    public static final double SETTLE_TIME = 5.0;
    public static final double MOVING_CURRENT = 40;
    public static final double MOVING_VELOCITY = 40;
    public static REVGains leftGains = new REVGains(2.0, 0.0,  0.14, 0.99, 500.0, 1.0, -1.0, NEO_MAX_RPM); // P, I, D, FF, Iz, MaxOutput, MinOutput, MaxRPM
    public static REVGains rightGains = new REVGains(2.0, 0.0,  0.14, 0.99, 500.0, 1.0, -1.0, NEO_MAX_RPM); // P, I, D, FF, Iz, MaxOutput, MinOutput, MaxRPM

    // HYPERION
    public static REVGains hyperionGains = new REVGains(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, NEO_MAX_RPM); // P, I, D, FF, Iz, MaxOutput, MinOutput, MaxRPM

}
