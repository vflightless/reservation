<?php
// Check if the request was sent via POST method
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Get info from request
    $id = $_POST['userID'];
    $user = $_POST['name'];
    $doctor = $_POST['doctor'];
    $date = $_POST['date'];
    $time = $_POST['time'];
    $reason = $_POST['reason'];

    // connect to the database
    $servername = "localhost";
    $sqlUsername = "CGJ";
    $sqlPassword = "se370";
    $dbname = "GCJHospital";
    $conn = new mysqli($servername, $sqlUsername, $sqlPassword, $dbname);
    if ($conn->connect_error) { die("Connection failed: " . $conn->connect_error); }
    $user_input = mysqli_real_escape_string($conn, $_POST['user_input']);

    // add selected dr based on type and name
    $sql = "SELECT id FROM Account WHERE type = 'd' AND name = '$doctor';";
    $result = $conn->query($sql);
    $row = $result->fetch_assoc();
    $drID = $row['id'];
    
    // check for existing appointment
    $sql = "SELECT * FROM Appointments WHERE doctor = '$doctor' AND date = '$date' AND time = STR_TO_DATE('$time', '%h:%i %p')";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {//if there are matches
        echo "Failed";
    } else {//no matches - create the appointment
        $sql = "INSERT INTO Appointments (userID, doctorID, name, doctor, date, time, reason) VALUES ('$id', '$drID', '$user', '$doctor', '$date', STR_TO_DATE('$time', '%h:%i %p'), '$reason');";
        $result = $conn->query($sql);
        $err = mysqli_error($conn);
        echo $err;
        echo $result;
    }

    // close the database connection
    $conn->close();
}
?>
