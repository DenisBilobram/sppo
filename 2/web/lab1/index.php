<?php
session_start();
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lab1</title>
    <style>
        body {
            font-family: sans-serif;
            font-size: 16px;
            color: black;
        }
        .main {
            margin: 0 auto;
            width: 70%;
        }
        .header {
            margin: 0 auto;
            text-align: center; 
        }

        .coordinates-container {
            text-align: center;
            display: block;
            margin-top: 40px;
        }

        #coordinates {
            margin: 0 auto;
        }

        .form-container {
            text-align: center;
            display: block;
            margin: 40px 0 auto auto;
        }

        form #yInput, form #rInput, #submitBut {
            width: 95%;
            padding: 5px 2px;
            border-radius: 3px;
        }

        #submitBut {
            background-color: rgb(255, 255, 255);
            transition: 0.1s;
        }
        #submitBut:hover {
            background-color: rgb(219, 219, 219);
        }
        #submitBut:active {
            background-color: rgb(100, 102, 107);
        }

        .dataform {
            margin: 0 auto;
        }

        .results-table {
            border-collapse: collapse;
            margin: 0 auto;
        }
        tr > td {
            border: 1px solid white;
            border-radius: 3px;
            padding: 10px; 
            text-align: center;
            background-color: rgba(226, 226, 226, 0.381);
            background-clip: padding-box;
            box-shadow: 0px 0px 6px rgba(0, 0, 0, 0.35);

        }   
        .succes {
            background-color: rgba(40, 255, 7, 0.788);
        }
        .fail {
            background-color: rgba(255, 7, 7, 0.822)
        }
        .clear-button {
            margin-top: 15px;
            padding: 10px;
            width: 30%;
        }

    </style>
</head>
<body>
    <table class="main">
        <tr>
            <td>
                <table class="header">
                    <tr>
                        <td><span>P3219 Билобрам Денис Андреевич</span></td>
                    </tr>
                    <tr>
                        <td><span>Вариант №2993</span></td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td class="coordinates-container">
                <canvas id="coordinates" width="300" height="300"></canvas>
                <script src="js/coordinates.js"></script>
            </td>
        </tr>
        <tr>
            <td class="form-container">
                <form action="php/handler.php" method="post">
                    <table class="dataform">
                        <tr>
                            <td>
                                <span>X: </span>
                                <label>
                                    <input type="radio" name="xcoord" value="-4" required="true" <?php if (!empty($_SESSION['data_records'])) {if ($_SESSION['data_records'][count($_SESSION['data_records'])-1][0] === "-4") {echo("checked");};} ?>> -4
                                </label>
                                <label>
                                    <input type="radio" name="xcoord" value="-3" required="true" <?php if (!empty($_SESSION['data_records'])) {if ($_SESSION['data_records'][count($_SESSION['data_records'])-1][0] === "-3") {echo("checked");};} ?>> -3
                                </label>
                                <label>
                                    <input type="radio" name="xcoord" value="-2" required="true" <?php if (!empty($_SESSION['data_records'])) {if ($_SESSION['data_records'][count($_SESSION['data_records'])-1][0] === "-2") {echo("checked");};} ?>> -2
                                </label>
                                <label>
                                    <input type="radio" name="xcoord" value="-1" required="true" <?php if (!empty($_SESSION['data_records'])) {if ($_SESSION['data_records'][count($_SESSION['data_records'])-1][0] === "-1") {echo("checked");};} ?>> -1
                                </label>
                                <label>
                                    <input type="radio" name="xcoord" value="0" required="true" <?php if (!empty($_SESSION['data_records'])) {if ($_SESSION['data_records'][count($_SESSION['data_records'])-1][0] === "0") {echo("checked");};} ?>> 0
                                </label>
                                <label>
                                    <input type="radio" name="xcoord" value="1" required="true" <?php if (!empty($_SESSION['data_records'])) {if ($_SESSION['data_records'][count($_SESSION['data_records'])-1][0] === "1") {echo("checked");};} ?>> 1
                                </label>
                                <label>
                                    <input type="radio" name="xcoord" value="2" required="true" <?php if (!empty($_SESSION['data_records'])) {if ($_SESSION['data_records'][count($_SESSION['data_records'])-1][0] === "2") {echo("checked");};} ?>> 2
                                </label>
                                <label>
                                    <input type="radio" name="xcoord" value="3" required="true" <?php if (!empty($_SESSION['data_records'])) {if ($_SESSION['data_records'][count($_SESSION['data_records'])-1][0] === "3") {echo("checked");};} ?>> 3
                                </label>
                                <label>
                                    <input type="radio" name="xcoord" value="4" required="true" <?php if (!empty($_SESSION['data_records'])) {if ($_SESSION['data_records'][count($_SESSION['data_records'])-1][0] === "4") {echo("checked");};} ?>> 4
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td><input type="text" name="ycoord" placeholder="Y {-3...5}" required="true" maxlength="17" id="yInput" value="<?php if (!empty($_SESSION['data_records'])) {echo($_SESSION['data_records'][count($_SESSION['data_records'])-1][1]);} ?>" /></td>
                        </tr>
                        <tr>
                            <td><input type="text" name="rval" placeholder="R {1...4}" required="true" maxlength="17" id="rInput" value="<?php if (!empty($_SESSION['data_records'])) {echo($_SESSION['data_records'][count($_SESSION['data_records'])-1][2]);} ?>"/></td>
                        </tr>
                        <tr>
                            <td><input type="submit" id="submitBut"/></td>
                        </tr>
                    </table>
                </form>
                <div id="form-errors">
                    <div id="yErrors"></div>
                    <div id="rErrors"></div>
                </div>
                <script src="js/valid.js"></script>
            </td>
        </tr>
        <tr>
            <td>
                <table class="results-table">
                    <tr>
                        <td>X</td>
                        <td>Y</td>
                        <td>R</td>
                        <td>RESULT</td>
                        <td>TIME</td>
                    </tr>
                    <?php

                    if (!empty($_SESSION['data_records'])) {
                        
                        $reversedArray = array_reverse($_SESSION['data_records']);

                        foreach ($reversedArray as $record) {
                            if ($record[3] === "true") {
                                $class = "succes";
                            } else {
                                $class = "fail";
                            }
                            echo("<tr class='$class'>");
                            echo("<td>" . $record[0] . "</td>");
                            echo("<td>" . $record[1] . "</td>");
                            echo("<td>" . $record[2] . "</td>");
                            echo("<td>" . $record[3] . "</td>");
                            echo("<td>" . $record[4] . "</td>");
                            echo("</tr>");
                        }
                    }

                    ?>

                    
                </table>
                <form method="DELETE" action="php/cleartable.php">
                    <input type="submit" value="Clear Table" class="clear-button"/>
                </form>
            </td>
        </tr>
    </table>
</body>
</html>