package frc.robot;

import frc.lightning.ConstantBase;
import frc.robot.misc._Gains;

public class Constants extends ConstantBase {

    // HARDWARE
    public static final double TICS_PER_ROTATION = 4 * 360;

    // DRIVETRAIN
    public static final double OPEN_LOOP_RAMP_RATE = 0.5;
    public static final double SETTLE_TIME = 5.0;
    public static final double MOVING_CURRENT = 40;
    public static final double MOVING_VELOCITY = 40;
    public static _Gains leftGains = new _Gains(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0); // P, I, D, FF, Iz, MaxOutput, MinOutput, MaxRPM
    public static _Gains rightGains = new _Gains(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0); // P, I, D, FF, Iz, MaxOutput, MinOutput, MaxRPM

}
