package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "tc_TeleOp")
public class tc_TeleOp extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor launcher;

    // the following are continuous motors and so we have to find a way to properly align these at the start... figure it out soon
    private CRServo intake;
    private CRServo intake2;

    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        launcher = hardwareMap.get(DcMotor.class, "launcher");

        intake = hardwareMap.get(CRServo.class, "intake");
        intake2 = hardwareMap.get(CRServo.class, "intake2");

        // i had to reverse these, if they ever do sm weird replace REVERSE with FORWARD and vice versa
        // important: left side is REVERSED! right side is forward and MUST NOT BE TOUCHED!!!
        // important: front wheels are side-to-side, back wheels are parallel with the main frame
        // note: check to see if changing the front wheels messes anything up - the back motors may remain the same (update: yes they are reversed, this makes sense and is more pleasing
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        // this was in the original code and idk what these do exactly but i'm not touching them out of an abundance of caution
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intake.setDirection(CRServo.Direction.FORWARD);

        waitForStart();

        if (opModeIsActive()) {
            telemetry.setMsTransmissionInterval(5);
            String int_tog_type = "off";
            boolean launcher_is_toggled = false;

            while (opModeIsActive()) {
                double drive  = -gamepad1.left_stick_y;
                double strafe =  gamepad1.left_stick_x;
                double rotate =  gamepad1.right_stick_x;

                double frontLeftPower  = drive + strafe + rotate;
                double backLeftPower   = drive - strafe + rotate;
                double frontRightPower = drive - strafe - rotate;
                double backRightPower  = drive + strafe - rotate;

                double max = Math.max(Math.abs(frontLeftPower), Math.abs(backLeftPower));
                max = Math.max(max, Math.abs(frontRightPower));
                max = Math.max(max, Math.abs(backRightPower));

                if (max > 1.0) {
                    frontLeftPower  /= max;
                    backLeftPower   /= max;
                    frontRightPower /= max;
                    backRightPower  /= max;
                }

                frontLeft.setPower(frontLeftPower);
                backLeft.setPower(backLeftPower);
                frontRight.setPower(frontRightPower);
                backRight.setPower(backRightPower);

                if (gamepad1.dpad_down) {
                    int_tog_type = "in";
                }
                if (gamepad1.dpad_up) {
                    int_tog_type = "out";
                }
                if (gamepad1.dpad_left) {
                    int_tog_type = "off";
                }

                if (gamepad2.a) {
                    launcher_is_toggled = true;
                } else if (gamepad2.b) {
                    launcher_is_toggled = false;
                    launcher.setPower(-1);
                }

                if (int_tog_type.equals("in")) {
                    intake.setPower(-1);
                    intake2.setPower(1);
                } else if (int_tog_type.equals("off")) {
                    intake.setPower(0);
                    intake2.setPower(0);
                } else if (int_tog_type.equals("out")) {
                    intake.setPower(1);
                    intake2.setPower(-1);
                }

                if (launcher_is_toggled) {
                    launcher.setPower(1);
                } else {
                    launcher.setPower(0);
                }

                // Telemetry Update
                telemetry.addData("Launcher Status", launcher_is_toggled);
                telemetry.update();
            }
        }
    }
}
