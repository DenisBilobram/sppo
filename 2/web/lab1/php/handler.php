<?php 

$xcoord = $_POST["xcoord"];
$ycoord = $_POST["ycoord"];
$rval = $_POST["rval"];
date_default_timezone_set('Europe/Moscow');
$currentDateTime = date("Y-m-d H:i:s");

$isin = "false";
if ((($xcoord <= 0 && $ycoord <= 0) && ($xcoord^2 + $ycoord^2 <= $rval^2)) || (($xcoord > 0 && $ycoord < 0) && ($xcoord <= $rval && $ycoord <= ($rval / 2))) || (($xcoord >= 0 && $ycoord >= 0) && ($ycoord <= ((-1/2)*$xcoord + (1/2)*$rval)))) {
    $isin = "true";
}

session_start();
if (!isset($_SESSION['data_records'])) {
    $_SESSION['data_records'] = array();
}
$record = array($xcoord, $ycoord, $rval, $isin, $currentDateTime);
array_push($_SESSION['data_records'], $record);

$url = "../index.php?xcoord=" . $xcoord . "&?ycoord=" . $ycoord . "&?rval=" . $rval;

header("Location: " . $url);

?>