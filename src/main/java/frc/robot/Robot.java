// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  WPI_TalonFX leftMotor1, leftMotor2, rightMotor1, rightMotor2;
  VictorSPX intakeMouth;
  MotorControllerGroup rightDrive, leftDrive, intake;
  DifferentialDrive drive;
  Joystick driverJoy, mechJoy;
  CANSparkMax intake1, intake2, arm;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    leftMotor1 = new WPI_TalonFX(7);
    leftMotor2 = new WPI_TalonFX(8);
    rightMotor1 = new WPI_TalonFX(9);
    rightMotor2 = new WPI_TalonFX(10);

    intake1 = new CANSparkMax(11, MotorType.kBrushless);
    intake2 = new CANSparkMax(12, MotorType.kBrushless);

    intakeMouth = new VictorSPX(15);

    arm = new CANSparkMax(13, MotorType.kBrushless);
    arm.setInverted(true);

    intake2.setInverted(true);

    intake = new MotorControllerGroup(intake1, intake2);

    

    leftDrive = new MotorControllerGroup(leftMotor1, leftMotor2);
    rightDrive = new MotorControllerGroup(rightMotor1, rightMotor2);
    rightDrive.setInverted(true);
    drive = new DifferentialDrive(leftDrive, rightDrive);

    driverJoy = new Joystick(0);
    mechJoy = new Joystick(1);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    drive.arcadeDrive(driverJoy.getRawAxis(1) * -1 * .7, driverJoy.getRawAxis(4) * -1 * .7);

    // Mechanisms

    if (mechJoy.getRawButton(5)) {
      intake.set(1);
    } else if (mechJoy.getRawButton(6)) {
      intake.set(-1);
    } else {
      intake.set(0);
    }

    if (mechJoy.getPOV() == 0) {
      arm.set(.5);
    } else if (mechJoy.getPOV() == 180) {
      arm.set(-.5);
    } else {
      arm.set(0);
    }

    if (mechJoy.getPOV() == 90) {
      intakeMouth.set(ControlMode.PercentOutput, .3);
    } else if (mechJoy.getPOV() == 270) {
      intakeMouth.set(ControlMode.PercentOutput, -.3);
    } else {
      intakeMouth.set(ControlMode.PercentOutput, 0);
    }
  }

  

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
