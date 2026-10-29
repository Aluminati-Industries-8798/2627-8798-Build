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

    // Continuous rotation servos for intake
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

        // Motor directions
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        // Run modes
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

                // Front wheels: side-to-side, moving in the same direction with strafe
                // This is really bad: god help me
                double frontLeftPower  = strafe + rotate;
                double frontRightPower = strafe - rotate;

                // Back wheels: parallel with frame (drive + rotate),
                // with half-power opposite movement for strafing
                double backLeftPower   = drive - (0.5 * strafe) + rotate;
                double backRightPower  = drive + (0.5 * strafe) - rotate;
                // ---------------------------------------------

                // Prevent motor power clipping above 1.0
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

                // Intake controls (Gamepad 1 Dpad)
                if (gamepad1.dpad_down) {
                    int_tog_type = "in";
                }
                if (gamepad1.dpad_up) {
                    int_tog_type = "out";
                }
                if (gamepad1.dpad_left) {
                    int_tog_type = "off";
                }

                // Launcher controls (Gamepad 2)
                if (gamepad2.a) {
                    launcher_is_toggled = true;
                } else if (gamepad2.b) {
                    launcher_is_toggled = false;
                    launcher.setPower(-1);
                }

                // Apply intake states
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

                // Apply launcher state
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