<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $username = $_POST['username'];
    $password = $_POST['password'];

    // connect to the database
    $servername = "localhost";
    $sqlUsername = "CGJ";
    $sqlPassword = "se370";
    $dbname = "GCJHospital";
    $conn = new mysqli($servername, $sqlUsername, $sqlPassword, $dbname);
    if ($conn->connect_error) { die("Connection failed: " . $conn->connect_error); }
    $user_input = mysqli_real_escape_string($conn, $_POST['user_input']);
    
    $sql = "SELECT id FROM Account WHERE email = '$username' AND password = '$password'";
    $result = $conn->query($sql);

    
    if ($result->num_rows > 0) {// check if any rows were returned
        $row = $result->fetch_assoc();
        echo $row['id']; // return id
    } else {
        echo "Failed";
    }
    
    // close the database connection
    $conn->close();
}
?>
