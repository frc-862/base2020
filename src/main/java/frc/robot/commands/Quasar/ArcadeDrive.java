package frc.robot.commands.Quasar;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends Command {
    double left;
    double right;
    double power;
    double turn;
    double kT = .001;
    double angle;
    //boolean first=true;

    public ArcadeDrive() {
        requires(Robot.drivetrain);
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
//            Robot.drivetrain.curvatureDrive(Robot.oi.getThrottle(), Robot.oi.getTurn(), Robot.oi.getQuickTurn());
//        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}