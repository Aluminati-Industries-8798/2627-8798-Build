package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "tc_AutoOp")
public class tc_AutoOp extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor launcher;

    // the following are continuous motors and so we have to find a way to properly align these at the start... figure it out soon
    private CRServo intake;
    private CRServo intake2;

    @Override                               // don't touch this
    public void runOpMode() {               // or this...
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        launcher = hardwareMap.get(DcMotor.class, "launcher");

        intake = hardwareMap.get(CRServo.class, "intake");
        intake2 = hardwareMap.get(CRServo.class, "intake2");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intake.setDirection(CRServo.Direction.FORWARD);

        waitForStart();

        if (opModeIsActive()) {
            telemetry.setMsTransmissionInterval(5);

            telemetry.addData("Autonomous Mode", "Running");

            // essentially, we don't have time nor the parts for a full auto mode, so the goal is to just move into the
            // parking spot and just sit there - we can get some points for that...
            frontLeft.setPower(1);
            backRight.setPower(1);
            // TODO: verify if this is right (URGENT)
            // what?

            sleep(200);

            frontLeft.setPower(0);
            backRight.setPower(0);

            telemetry.addData("Autonomous Mode", "Done");
        }
    }
}
