/** Funcao para colocar o foco no primeiro campo nao hidden do form
 *  @param objForm - o form
 */
function focoInicial(objForm)
{
   var nItensForm = objForm.length;
   var objCampo;
   for (var j = 0;j < nItensForm; j++)
   {
      objCampo=objForm.elements[j];
      if (objCampo.type!="hidden" && objCampo.disabled == false && 
      (objCampo.type=="text" || objCampo.type=="textarea" || objCampo.type=="select-one" || objCampo.type=="radio"))
		break;
   }
   objCampo.focus();
}     
