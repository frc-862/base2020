/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.misc;

/**
 * Add your docs here.
 */
public class _Gains {
    private double kP, 
        kI, 
        kD, 
        kFF,
        kIz,
        kMaxOutput,
        kMinOutput,
        maxRPM;

    public _Gains() {
        this(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }
    
    public _Gains (double kP, double kI, double kD, double kFF, double kIz, double kMaxOutput, double kMinOutput, double maxRPM) {
        this.setkP(kP);
        this.setkI(kI);
        this.setkD(kD);
        this.setkFF(kFF);
        this.setkIz(kIz);
        this.setkMaxOutput(kMaxOutput);
        this.setkMinOutput(kMinOutput);
        this.setMaxRPM(maxRPM);
    }

    /**
     * @return the maxRPM
     */
    public double getMaxRPM() {
        return maxRPM;
    }

    /**
     * @param maxRPM the maxRPM to set
     */
    public void setMaxRPM(double maxRPM) {
        this.maxRPM = maxRPM;
    }

    /**
     * @return the kMinOutput
     */
    public double getkMinOutput() {
        return kMinOutput;
    }

    /**
     * @param kMinOutput the kMinOutput to set
     */
    public void setkMinOutput(double kMinOutput) {
        this.kMinOutput = kMinOutput;
    }

    /**
     * @return the kMaxOutput
     */
    public double getkMaxOutput() {
        return kMaxOutput;
    }

    /**
     * @param kMaxOutput the kMaxOutput to set
     */
    public void setkMaxOutput(double kMaxOutput) {
        this.kMaxOutput = kMaxOutput;
    }

    /**
     * @return the kIz
     */
    public double getkIz() {
        return kIz;
    }

    /**
     * @param kIz the kIz to set
     */
    public void setkIz(double kIz) {
        this.kIz = kIz;
    }

    /**
     * @return the kFF
     */
    public double getkFF() {
        return kFF;
    }

    /**
     * @param kFF the kFF to set
     */
    public void setkFF(double kFF) {
        this.kFF = kFF;
    }

    /**
     * @return the kD
     */
    public double getkD() {
        return kD;
    }

    /**
     * @param kD the kD to set
     */
    public void setkD(double kD) {
        this.kD = kD;
    }

    /**
     * @return the kI
     */
    public double getkI() {
        return kI;
    }

    /**
     * @param kI the kI to set
     */
    public void setkI(double kI) {
        this.kI = kI;
    }

    /**
     * @return the kP
     */
    public double getkP() {
        return kP;
    }

    /**
     * @param kP the kP to set
     */
    public void setkP(double kP) {
        this.kP = kP;
    }

}
