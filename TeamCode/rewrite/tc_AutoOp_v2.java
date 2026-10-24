// tc_AutoOp_v2
// kc/lc 24-10-2026
// DO NOT USE!

// package org.firstinspires.ftc.teamcode.rewrite;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;

@Autonomous(name = "tc_AutoOp_v2")
public class tc_AutoOp_v2 extends OpMode {
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    CRServo intake;
    CRServo intake2;

    void init() {
        telemetry.setMsTransmissionInterval(50);

        // get the hardware mappings

        // movement motors
        frontLeft = hardwareMap.dcMotor.get("frontLeft");           // <-
        frontRight = hardwareMap.dcMotor.get("frontRight");         // ->
        backLeft = hardwareMap.dcMotor.get("backLeft");             // |^
        backRight = hardwareMap.dcMotor.get("backRight");           // ^|

        // intake motors
        intake = hardwareMap.crservo.get("intake");
        intake2 = hardwareMap.crservo.get("intake2");
    }
}
