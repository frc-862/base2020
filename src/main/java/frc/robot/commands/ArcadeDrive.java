package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends CommandBase {
    private Drivetrain drivetrain;
    double left;
    double right;
    double power;
    double turn;
    double kT=.001;
    double angle;
//    boolean first=true;
    public ArcadeDrive(Drivetrain drivetrain) {

        addRequirements(drivetrain);
        this.drivetrain = drivetrain;
    }

    @Override
    public void execute() {
//        System.out.println(Robot.core.getYaw());

//        if(Math.abs(Robot.oi.getTurn())<.0862){
//            if(first){
//                angle=Robot.core.getYaw();
//                first=false;
//            }
//            power=Robot.oi.getThrottle();
//            turn=(angle-Robot.core.getYaw())*kT;
//            left=power+turn;
//            right=power-turn;
//            drivetrain.setPower(left,right);
//        }else {
//            first=true;
        drivetrain.curvatureDrive(Robot.oi.getThrottle(), Robot.oi.getTurn(), Robot.oi.getQuickTurn());
//        }
    }
}
