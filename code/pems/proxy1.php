<?php
// create a new cURL resource
$ch = curl_init();

$url = $_GET['url'];
unset($_GET['url']);

if (count($_GET)) {
	$params = array();
	foreach ($_GET as $key => $value)
		$params[] = "{$key}={$value}";

	$params = "?". implode("&", $params);
}
else
	$params = '';

// set URL and other appropriate options
curl_setopt($ch, CURLOPT_URL, "https://aplicacao-homologacao.saude.gov.br/pe-homologa/rest/pems/{$url}{$params}");
curl_setopt($ch, CURLOPT_HEADER, 0);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0);
curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 0);
curl_setopt($ch, CURLOPT_FRESH_CONNECT, 1);

// grab URL and pass it to the browser
$response = curl_exec($ch);
header('Content-Type: application/json; charset=utf-8');
header('Content-Length: '. strlen($response));
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST, GET");
header('Expires: 0');
header('Cache-Control: must-revalidate, post-check=0, pre-check=0');
header('Pragma: no-cache');
print $response;

// close cURL resource, and free up system resources
curl_close($ch);

?>
