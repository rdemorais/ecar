<!--
document.write("<div id='pop'></div>");

POP = document.getElementById("pop");

function pop(msg,e) {

var top = 0, left = 0;
  if (!e) { e = window.event; }
  var myTarget = e.currentTarget;
  if (!myTarget) {
   myTarget = e.srcElement;
  }
  else if (myTarget == "undefined") {
   myTarget = e.srcElement;
  }
  while(myTarget!= document.body) {
     top += myTarget.offsetTop;
     left += myTarget.offsetLeft;
     myTarget = myTarget.offsetParent;
  }

posx = left + 1;
posy = top + 16;

POP.innerHTML = msg;
POP.style.left = posx;
POP.style.top = posy;
window.status = msg;
POP.style.visibility = 'visible';
counter = 0;

timeInterval = setInterval("esconde();",100);
return false ;
}

function popcombo(msg,e,x,y) {

var top = 0, left = 0;
  if (!e) { e = window.event; }
  var myTarget = e.currentTarget;
  if (!myTarget) {
   myTarget = e.srcElement;
  }
  else if (myTarget == "undefined") {
   myTarget = e.srcElement;
  }
  while(myTarget!= document.body) {
     top += myTarget.offsetTop;
     left += myTarget.offsetLeft;
     myTarget = myTarget.offsetParent;
  }

posx = left + x;
posy = top + y;

POP.innerHTML = msg;
POP.style.left = posx;
POP.style.top = posy;
window.status = msg;
POP.style.visibility = 'visible';
counter = 0;

timeInterval = setInterval("esconde();",100);
return false ;
}

function esconde() {
	counter += 1;
	if (counter >= 50) {
	POP.style.visibility = 'hidden';
	window.status = "";
	clearInterval(timeInterval) ;
    return false ;
	}
}
function hidepop() {
	POP.style.visibility = 'hidden';
	window.status = "";
	clearInterval(timeInterval) ;
    return false ;
	}
//-->