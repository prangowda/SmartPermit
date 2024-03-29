<?php

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;

require_once __DIR__ . '/vendor/phpmailer/src/Exception.php';
require_once __DIR__ . '/vendor/phpmailer/src/PHPMailer.php';
require_once __DIR__ . '/vendor/phpmailer/src/SMTP.php';

// passing true in constructor enables exceptions in PHPMailer
$mail = new PHPMailer(true);

include 'connection.php';

// $email = 'manojkpajeer127@gmail.com';

$email = $_POST['email'];

$res = mysqli_query($conn, "select name, password, email from user where email = '$email' and status = true ");
if(mysqli_num_rows($res)>0)
{
    $rows = mysqli_fetch_assoc($res);
    $name1 = $rows['name'];
    $useremail1 = $rows['email'];
    $password1 = $rows['password'];
    $appname = "OCR For Nexus";

    try {

        // Server settings
        // $mail->SMTPDebug = SMTP::DEBUG_SERVER; // for detailed debug output
        $mail->isSMTP();
        $mail->Host = 'smtp.gmail.com';
        $mail->SMTPAuth = true;
        $mail->SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS;
        $mail->Port = 587;

        $mail->Username = 'project.head1994@gmail.com'; // YOUR gmail email
        $mail->Password = 'MAnoj143'; // YOUR gmail password

        // Sender and recipient settings
        $mail->setFrom('project.head1994@gmail.com', $appname);
        $mail->addAddress($useremail1, $name1);
        $mail->addReplyTo('project.head1994@gmail.com', $appname); // to set the reply to

        // Setting the email content
        $mail->IsHTML(true);
        $mail->Subject = "Forgot Password : ".$appname;
        $mail->Body = 'Dear '.$name1.'<br> You recently requested reset your password<br> Password : '.$password1.'<br><br> Thank you<br>Team '.$appname;
        $mail->AltBody = 'Forgot password reset email';

        $mail->send();
        // echo "Email message sent.";

        $response['success'] = true;
        $response['message'] = "Password has been sent to your registered email..!";
    } 
    catch (Exception $e) 
    {
        // echo "Error in sending email. Mailer Error: {$mail->ErrorInfo}";
        $response['success'] = false;
        $response['message'] = "Unable to send an email..!"; 
    }

}
else
{
    $response['success'] = false;
    $response['message'] = "Invalid data you provided..!";
}

    echo json_encode($response);
?>