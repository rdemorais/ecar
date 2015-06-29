package ecar.webservices.exc;

import org.hibernate.hql.ast.QuerySyntaxException;


public class EcarWSExceptionHandler {
	
	public void fireException(Exception causa) throws EcarWSException {
		if(causa instanceof QuerySyntaxException) {
			throw new EcarWSException(ExceptionGlossario.ERRO_CONSTRUCAO_CONSULTA);
		}else if(causa instanceof EcarWSException) {
			throw new EcarWSException(causa.getMessage());
		}
	}
}
