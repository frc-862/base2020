package frc.robot.commands;

import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends SendableCommandBase {
    private Drivetrain drivetrain;

    public ArcadeDrive(Drivetrain drivetrain) {
        addRequirements(drivetrain);
        this.drivetrain = drivetrain;
    }

    @Override
    public void execute() {
        drivetrain.curvatureDrive(Robot.oi.getThrottle(), Robot.oi.getTurn(), Robot.oi.getQuickTurn());
    }
}
