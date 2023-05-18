<?php
// Check if the request was sent via POST method
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Get info from request
    $name = $_POST['fullname'];
    $email = $_POST['email'];
    $password = $_POST['password'];
    $code = $_POST['code'];

    // type handling - default to patient unless regCode match
    $type = 'p';
    if ($code == 'heeheexd') {
        $type = 'd';
    }

    // connect to the database
    $servername = "localhost";
    $sqlUsername = "CGJ";
    $sqlPassword = "se370";
    $dbname = "GCJHospital";
    $conn = new mysqli($servername, $sqlUsername, $sqlPassword, $dbname);
    if ($conn->connect_error) { die("Connection failed: " . $conn->connect_error); }

    $sql = "INSERT INTO Account (name, email, password, type) VALUES ('$name', '$email', '$password', '$type')";
    $result = $conn->query($sql);
    if (!$result) {
        echo "Failed";
    } else {
        echo "Success";
    }

    // close the database connection
    $conn->close();
}
?>