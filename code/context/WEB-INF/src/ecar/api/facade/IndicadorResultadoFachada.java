package ecar.api.facade;

import ecar.dao.CorDao;
import ecar.exception.ECARException;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.Cor;

public class IndicadorResultadoFachada {

	private Cor corPeriodoCorrente;
	private Cor corLinhaDeBase;
	private CorDao corDao;
	
	public IndicadorResultadoFachada() {
		corDao = new CorDao(null);
	}
	
	public void calculaSinalizacao(AcompReferenciaItemAri ari, AcompRealFisicoArf ind) {
		//CalculoSinalizacao calculoSinalizacao = new CalculoSinalizacao(ari, ind);
		
		try {
			//calculoSinalizacao.execute();
			
			//corPeriodoCorrente = calculoSinalizacao.getFaixa().getCor();
			//corLinhaDeBase = calculoSinalizacao.getFaixaLinhaDeBase().getCor();
			corPeriodoCorrente = null;
			corLinhaDeBase = null;
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} 
		/*catch (ECARException e) {
			e.printStackTrace();
		}*/
	}

	public String getCorPeriodoCorrente() {
		try {
			return imagePath(corPeriodoCorrente);
		} catch (ECARException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getCorLinhaDeBase() {
		try {
			return imagePath(corLinhaDeBase);
		} catch (ECARException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String imagePath(Cor cor) throws ECARException {
		return corDao.getImagemIndResul(cor);
	}
}