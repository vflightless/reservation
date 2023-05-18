<?php
// Check if the request was sent via POST method
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // connect to the database
    $servername = "localhost";
    $sqlUsername = "CGJ";
    $sqlPassword = "se370";
    $dbname = "GCJHospital";
    $conn = new mysqli($servername, $sqlUsername, $sqlPassword, $dbname);
    if ($conn->connect_error) { die("Connection failed: " . $conn->connect_error); }

    $sql = "SELECT * FROM Account WHERE type = 'd'";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        $rows = array();// create an array to hold the rows

        while($row = $result->fetch_assoc()) {// output data of each row
            $rows[] = $row['name'];// add the row to the array
        }

        header('Content-Type: application/json');
        echo json_encode($rows);// encode the array as JSON and return it
    } else {
        echo "Failed";
    }

    $conn->close();// close the database connection
}
?>
