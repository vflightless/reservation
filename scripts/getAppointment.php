<?php
// Check if the request was sent via POST method
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // connect to the database
    $servername = "localhost";
    $sqlUsername = "CGJ";
    $sqlPassword = "se370";
    $dbname = "GCJHospital";
    $conn = new mysqli($servername, $sqlUsername, $sqlPassword, $dbname);

    $id = $_POST['userID'];

    // check connection
    if ($conn->connect_error) {
        die("Failed");
    }

    // check for user type
    $sql = "SELECT * FROM Account WHERE id = '$id';";
    $result = $conn->query($sql);
    $row = $result->fetch_assoc();

    if($row['type'] == 'd') {// doctor so fetch all appointments where doctor id matches
        $sql = "SELECT * FROM Appointments WHERE doctorID = '$id' AND date >= CURDATE() ORDER BY date ASC, time ASC";
        $result = $conn->query($sql);
        echo mysqli_error($conn);

        if ($result->num_rows > 0) {
            $rows = array();
            while ($row = $result->fetch_assoc()) {
                $rows[] = $row;
            }
            echo json_encode($rows);
        } else {
            echo "Failed";
        }
    } else {// select appointments based on user id
        $sql = "SELECT * FROM Appointments WHERE userID = '$id' AND date >= CURDATE() ORDER BY date ASC, time ASC";
        $result = $conn->query($sql);
        echo mysqli_error($conn);

        if ($result->num_rows > 0) {
            $rows = array();
            while ($row = $result->fetch_assoc()) {
                $rows[] = $row;
            }
            echo json_encode($rows);
        } else {
            echo "Failed";
        }
    }

    // close the database connection
    $conn->close();
}
?>