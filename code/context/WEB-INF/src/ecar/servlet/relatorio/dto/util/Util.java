package ecar.servlet.relatorio.dto.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import comum.util.Ordena;

import ecar.api.facade.EcarData;
import ecar.api.facade.Exercicio;
import ecar.api.facade.IndicadorResultado;
import ecar.api.facade.Realizado;
import ecar.dao.AcompReferenciaDao;
import ecar.dao.ExercicioDao;
import ecar.exception.ECARException;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstUsutpfuacIettutfa;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.UsuarioUsu;
import ecar.servlet.relatorio.dto.marcas.RedeCicloIndicadorDTO;
import ecar.servlet.relatorio.dto.marcas.RedePlanoAcaoDTO;

/**
 * 
 * @author Rafael Freitas de Morais
 * 11/04/2012
 */
public class Util {
	
	public static void metaRealIndicador(ItemEstrtIndResulIettr ind, 
			RedeCicloIndicadorDTO dto, 
			List<String> listCiclos,
			AcompReferenciaDao acompReferenciaDao,
			ExercicioDao exercicioDao) throws NumberFormatException, ECARException {
		
		IndicadorResultado wrapperIndicador = new IndicadorResultado(ind);
		EcarData ecarData;
		ExercicioExe ex = exercicioDao.getExercicio("06", "2012");
		Exercicio exe = new Exercicio(ex);

		if(wrapperIndicador.getPrevistoNoExercicio(exe) != 0) {
			dto.setMeta(comum.util.Util.formataDecimalPT_BR(wrapperIndicador.getPrevistoNoExercicio(exe)));
		}
		
		List<Object[]> mesAno_ = new ArrayList<Object[]>();
		mesAno_.add(new Object[]{04,2012});
		mesAno_.add(new Object[]{05,2012});
		mesAno_.add(new Object[]{06,2012});
		
		for (int i = 0; i < mesAno_.size(); i++) {
			
			Object[] mesAno = mesAno_.get(i);
			ecarData = new EcarData(Integer.parseInt(mesAno[0] + ""), Integer.parseInt(mesAno[1] + ""));
			Realizado real = wrapperIndicador.getRealizadoMensal(ecarData);
			
			//esta parte está ridícula!
			if(i==0) {
				if(real != null) {
					dto.setInd_real_1(real.getRealizadoFormatado());					
				}
			}else if(i==1) {
				if(real != null) {
					dto.setInd_real_2(real.getRealizadoFormatado());					
				}
			}else if(i==2) {
				if(real != null) {
					dto.setInd_real_3(real.getRealizadoFormatado());					
				}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static UsuarioUsu responsavel(ItemEstruturaIett iett) {
		Iterator it = iett.getItemEstUsutpfuacIettutfas().iterator();
		while(it.hasNext()) {
			return ((ItemEstUsutpfuacIettutfa) iett.getItemEstUsutpfuacIettutfas().iterator().next()).getUsuarioUsu();
		}
		return new UsuarioUsu();
	}
	
	public static void formataGantt(RedePlanoAcaoDTO dto, ItemEstruturaIett iett) {
		Calendar dtInicio = Calendar.getInstance();
		Calendar dtFinal = Calendar.getInstance();
		
		dtInicio.setTime(iett.getDataInicioIett());
		dtFinal.setTime(iett.getDataTerminoIett());
		
		List<Integer> anos = itensDoPeriodo(dtInicio, dtFinal, Calendar.YEAR);
		
		for (int i = 0; i < anos.size(); i++) {
			if(anos.size() >= 2 && (i < (anos.size() -1))) {
				dtFinal.set(anos.get(i), Calendar.DECEMBER, 31);
			}
			if(i > 0) {
				dtInicio.set(anos.get(i), Calendar.JANUARY, 1);
			}
			if(i == (anos.size() -1)) {
				dtFinal.setTime(iett.getDataTerminoIett());
			}
			List<Integer> meses = itensDoPeriodo(dtInicio, dtFinal, Calendar.MONTH);
			for (Integer mes : meses) {
				int anoIndice = anos.get(i) - 2011;
				dto.addMes(mes + (12*anoIndice));
			}
		}
	}
	
	/**
	 *
	 * @param ini
	 * @param fim
	 * @param tipo
	 * @return os dias, meses, ou anos
	 */
	private static List<Integer> itensDoPeriodo(Calendar ini, Calendar fim, int tipo) {
		List<Integer> itens = new ArrayList<Integer>();
		int iniNumero = ini.get(tipo);
		int fimNumero = fim.get(tipo);
		for(;iniNumero < fimNumero; iniNumero++) {
			itens.add(iniNumero);
		}
		itens.add(fimNumero);
		return itens;
	}
	
	public static void ordenarPlanoAcao(List<RedePlanoAcaoDTO> planos) {
		Ordena<RedePlanoAcaoDTO> ordenacao = new Ordena<RedePlanoAcaoDTO>();
		try {
			ordenacao.ordenarLista(planos, "getAtividade");
			ordenacao.ordenarLista(planos, "getAcao");
			ordenacao.ordenarLista(planos, "getEixo");
			ordenacao.ordenarLista(planos, "getRede");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
