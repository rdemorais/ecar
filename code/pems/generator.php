<?php
$i=0;
class item{
	public $o='';
	public $f='';
	public $s='';
	public $t='';
};


function Generate($num){

	$tagsMatrix= array();
	array_push($tagsMatrix, 'mulher', 'aids', 'UBS','UPA','ACS','hospital','DENG','complementares','eventos','saude','drogas', 'tecnologia');
	$return = array();
	$return[0] = $tagsMatrix;
	for ($i; $i<$num; $i++){
		$item = new item;
		$item->o = ' '.strval(rand(1,16)).' ';
		$item->f = ' '.strval(rand(1,48)).' ';
		$item->s = rand(0,5);
	
		$t=rand(1,6);
		while($t>0){
			$item->t .= rand(0, count($tagsMatrix)-1).' ';
			--$t;	
		}
		$item->t = ' '.$item->t;
		array_push($return, $item);
		
	}
	return $return;
	
}

echo(json_encode(Generate(1000)));


?>