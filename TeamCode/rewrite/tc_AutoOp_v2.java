// tc_AutoOp_v2
// kc/lc 24-10-2026
// DO NOT USE!

// package org.firstinspires.ftc.teamcode.rewrite;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "tc_AutoOp_v2")
public class tc_AutoOp_v2 extends OpMode {
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    CRServo intake;
    CRServo intake2;

    void init() {
        // setup method for the bot

        // we don't need to send a lot of telemetry data so 50ms (0.05s) whould work
        telemetry.setMsTransmissionInterval(50);

        // get the hardware mappings

        // wheel motors
        frontLeft = hardwareMap.dcMotor.get("frontLeft");           // <-
        frontRight = hardwareMap.dcMotor.get("frontRight");         // ->
        backLeft = hardwareMap.dcMotor.get("backLeft");             // |^
        backRight = hardwareMap.dcMotor.get("backRight");           // ^|

        // intake motors
        intake = hardwareMap.crservo.get("intake");
        intake2 = hardwareMap.crservo.get("intake2");

        // TODO: verify if the intake directions are correct
        intake.setDirection(CRServo.Direction.FORWARD);
        intake2.setDirection(CRServo.Direction.REVERSE);
        // +1 should be inward, -1 should be outward

        // TODO: verify if the wheel motors directions are correct
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        // this is probably an awful design...... NOT good
        // hey yeah we're using mecanum wheels
    }

    void loop() {

    }
}
