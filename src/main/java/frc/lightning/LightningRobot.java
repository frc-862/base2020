package frc.lightning;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.experimental.command.Command;
import edu.wpi.first.wpilibj.experimental.command.CommandScheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lightning.logging.DataLogger;
import frc.lightning.util.FaultCode;
import frc.lightning.util.FaultMonitor;
import frc.lightning.util.TimedFaultMonitor;
import frc.lightning.util.FaultCode.Codes;
import frc.robot.Constants;
import java.util.Map;

/**
 * Base robot class, provides {@link frc.lightning.ConstantBase constants},
 * {@link frc.lightning.logging.DataLogger logging},
 * {@link FaultMonitor fault monitoring}, and loops with varying
 * periods {@link LightningRobot#robotBackgroundPeriodic() background},
 * {@link LightningRobot#robotLowPriorityPeriodic() low}, and
 * {@link LightningRobot#robotMediumPriorityPeriodic() medium} priority
 * loops.
 *
 * Expands on template code to provide {@link LightningRobot#registerAutonomousCommmand(String, Command)
 * a method to register auton commands}. And integrated self test support (still in progress)
 */
public class LightningRobot extends TimedRobot {
    public ConstantBase constants;
    public DataLogger dataLogger;

    private int counter = 0;
    private int medPriorityFreq = (int) Math.round(0.1 / getPeriod());
    private double loopTime;
    private int lowPriorityFreq = (int) Math.round(1 / getPeriod());
    private int backgroundPriorityFreq = (int) Math.round(10 / getPeriod());

    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     *
     * If you override it, be sure to call super.robotInit
     */
    @Override
    public void robotInit() {
        System.out.println("LightningRobot.robotInit");
        System.out.println("Starting time:" + Timer.getFPGATimestamp());
        constants = new Constants();
        constants.readFromFile();

        SmartDashboard.putData("Auto Mode", chooser);

        // By this point all datalog fields should be registered
        DataLogger.preventNewDataElements();

        FaultMonitor.register(new TimedFaultMonitor(Codes.SLOW_LOOPER, () -> getLoopTime() > getPeriod(),
                              0.08, "Loop is running slow: " + getLoopTime()));

        FaultCode.eachCode((code, state) -> {
            var nte = Shuffleboard.getTab("Fault Codes")
            .add("FAULT_" + code.toString(), state)
            .withWidget("Boolean Box")
            .withProperties(Map.of("colorWhenTrue", "green", "colorWhenFalse", "maroon"))
            .getEntry();
            FaultCode.setNetworkTableEntry(code, nte);
        });
    }

    double getLoopTime() {
        return loopTime;
    }

    /**
     * The first command registered will be the default command
     *
     * @param name This is the name that appears on shuffleboard
     * @param command This is the command that will be started during autonomous,
     *                use an actual instance new MyCommand()
     *
     */
    int autoCommandCount = 0;
    public void registerAutonomousCommmand(String name, Command command) {
        if (autoCommandCount == 0) {
            chooser.setDefaultOption(name, command);
        } else {
            chooser.addOption(name, command);
        }
        autoCommandCount += 1;
    }

    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want ran during disabled,
     * autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before
     * LiveWindow and SmartDashboard integrated updating.
     *
     * If you override this method, be sure to call super.robotPeriod() as
     * it drives our lower priority loops, datalogging, fault monitoring,
     * etc.
     */
    @Override
    public void robotPeriodic() {
        double time = Timer.getFPGATimestamp();
        if (time > Constants.SETTLE_TIME) {
            counter += 1;
            if (counter % medPriorityFreq == 0) {
                robotMediumPriorityPeriodic();
            }
            if (counter % lowPriorityFreq == 0) {
                robotLowPriorityPeriodic();
            }
            if (counter % backgroundPriorityFreq == 0) {
                robotBackgroundPeriodic();
            }

            FaultMonitor.checkMonitors();
            DataLogger.logData();
            loopTime = Timer.getFPGATimestamp() - time;
        }

        CommandScheduler.getInstance().run();
    }

    /**
     * A slower loop, running once every 10 seconds
     *
     * Note as currently implemented it still needs to
     * complete in our loop time or it delay higher
     * priority opterations. If you have a low priority,
     * long running operation, consider creating a background
     * thread.
     */
    protected void robotBackgroundPeriodic() {
        DataLogger.flush();
        DataLogger.checkBaseFileName();
    }

    /**
     *  A slow loop, running once a second
     *
     * Note as currently implemented it still needs to
     * complete in our loop time or it delay higher
     * priority opterations. If you have a low priority,
     * long running operation, consider creating a background
     * thread.
     */
    protected void robotLowPriorityPeriodic() {
        DataLogger.getLogger().getLogWriter().drain();
    }

    /**
     *  A loop, running 10 times a second
     *
     * Note as currently implemented it still needs to
     * complete in our loop time or it delay higher
     * priority opterations. If you have a low priority,
     * long running operation, consider creating a background
     * thread.
     */
    protected void robotMediumPriorityPeriodic() {
        FaultCode.update();
    }

    @Override
    public void disabledInit() {
        System.out.println("disabledInit");
    }

    /**
     * The default implementation handles getting the selected command
     * from Shuffleboard.
     *
     * TODO consider adding check for failure to communicate with Shuffleboard
     * and using the default command.
     *
     * If you override this method, be sure to call super.autonomousInit() or
     * the selected registered command will not be executed.
     */
    @Override
    public void autonomousInit() {
        // LightningServer.stop_server();
        autonomousCommand = chooser.getSelected();//changed

        // schedule the autonomous command (example)
        if (autonomousCommand != null) {
            autonomousCommand.schedule();
        }
    }

    @Override
    public void teleopInit() {
        // LightningServer.stop_server();
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }
}
