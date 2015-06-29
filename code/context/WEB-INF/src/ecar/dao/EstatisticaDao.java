package ecar.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.SQLQuery;

import comum.database.Dao;
import ecar.pojo.dto.EstatisticaDTO;
import ecar.pojo.dto.SecretariaEstatisticaDTO;

import com.google.gson.*;

public class EstatisticaDao extends Dao{
	
	
	public EstatisticaDao(HttpServletRequest request){
		super();
		this.request = request;
	}
	
	private void queryEstatisticas(
			List<Integer> codigoObjetivoEstrategico, 
			StringBuffer hql, Long exercicio) {
		
		
		hql.append("WITH RECURSIVE iett_recursivo(cod_iett, "+
		"	      cod_are, "+
		"	      cod_sare, "+
		"	      val_previsto_futuro_iett, "+
		"	      ind_bloq_planejamento_iett, "+
		"	      beneficios_iett, "+
		"	      origem_iett, "+
		"	      objetivo_especifico_iett, "+
		"	      objetivo_geral_iett, "+
		"	      ind_monitoramento_iett, "+
		"	      ind_critica_iett, "+
		"	      data_inicio_monitoramento_iett, "+
		"	      data_termino_iett, "+
		"	      data_inicio_iett, "+
		"	      ind_ativo_iett, "+
		"	      data_ult_manutencao_iett, "+
		"	      data_inclusao_iett, "+
		"	      descricao_iett, "+
		"	      sigla_iett, "+
		"	      cod_usu_inc_iett, "+
		"	      cod_usu_ult_manut_iett, "+
		"	      cod_orgao_responsavel1_iett, "+
		"	      cod_orgao_responsavel2_iett, "+
		"	      cod_iett_pai,cod_prcd_iett, "+
		"	      cod_ett,descricao_r5, "+
		"	      descricao_r4, "+
		"	      descricao_r3, "+
		"	      descricao_r2, "+
		"	      descricao_r1, "+
		"	      data_r5, "+
		"	      data_r4, "+
		"	      data_r3, "+
		"	      data_r2, "+
		"	      data_r1, "+
		"	      nome_iett, "+
		"	      cod_uo, "+
		"	      cod_sit, "+
		"	      nivel_iett, "+
		"	      ind_exclusao_pos_historico, "+
		"	      ind_modelo_iett, "+
		"	      atencao_iett)  "+
		" AS( SELECT  cod_iett, "+
		"	      cod_are, "+
		"	      cod_sare, "+
		"	      val_previsto_futuro_iett, "+
		"	      ind_bloq_planejamento_iett, "+
		"	      beneficios_iett, "+
		"	      origem_iett, "+
		"	      objetivo_especifico_iett, "+
		"	      objetivo_geral_iett, "+
		"	      ind_monitoramento_iett, "+
		"	      ind_critica_iett, "+
		"	      data_inicio_monitoramento_iett, "+
		"	      data_termino_iett, "+
		"	      data_inicio_iett, "+
		"	      ind_ativo_iett, "+
		"	      data_ult_manutencao_iett, "+
		"	      data_inclusao_iett, "+
		"	      descricao_iett, "+
		"	      sigla_iett, "+
		"	      cod_usu_inc_iett, "+
		"	      cod_usu_ult_manut_iett, "+
		"	      cod_orgao_responsavel1_iett, "+
		"	      cod_orgao_responsavel2_iett, "+
		"	      cod_iett_pai, "+
		"	      cod_prcd_iett, "+
		"	      cod_ett, "+
		"	      descricao_r5, "+
		"	      descricao_r4, "+
		"	      descricao_r3, "+
		"	      descricao_r2, "+
		"	      descricao_r1, "+
		"	      data_r5, "+
		"	      data_r4, "+
		"	      data_r3, "+
		"	      data_r2, "+
		"	      data_r1, "+
		"	      nome_iett, "+
		"	      cod_uo, "+
		"	      cod_sit, "+
		"	      nivel_iett, "+
		"	      ind_exclusao_pos_historico, "+
		"	      ind_modelo_iett, "+
		"	      atencao_iett "+
		"	  FROM  public.tb_item_estrutura_iett ");
		
		if(codigoObjetivoEstrategico.size() != 0) {
			hql.append(" WHERE cod_iett IN (:codObj) ");	
		}else{
			hql.append(" WHERE cod_iett IN ( ");	
			hql.append("select iettObjs.cod_iett from tb_item_estrutura_iett iettObjs "); 
			hql.append("where iettObjs.nivel_iett = 1 and iettObjs.ind_ativo_iett='S') "); 
		}			
		
		
		hql.append( 
		"	  AND ind_ativo_iett = 'S' "+
		"	  UNION ALL "+
		"	  SELECT  iett.cod_iett, "+
		"	      iett.cod_are, "+
		"	      iett.cod_sare, "+
		"	      iett.val_previsto_futuro_iett, "+
		"	      iett.ind_bloq_planejamento_iett, "+
		"	      iett.beneficios_iett, "+
		"	      iett.origem_iett, "+
		"	      iett.objetivo_especifico_iett, "+
		"	      iett.objetivo_geral_iett, "+
		"	      iett.ind_monitoramento_iett, "+
		"	      iett.ind_critica_iett, "+
		"	      iett.data_inicio_monitoramento_iett, "+
		"	      iett.data_termino_iett, "+
		"	      iett.data_inicio_iett, "+
		"	      iett.ind_ativo_iett, "+
		"	      iett.data_ult_manutencao_iett, "+
		"	      iett.data_inclusao_iett, "+
		"	      iett.descricao_iett, "+
		"	      iett.sigla_iett, "+
		"	      iett.cod_usu_inc_iett, "+
		"	      iett.cod_usu_ult_manut_iett, "+
		"	      iett.cod_orgao_responsavel1_iett, "+
		"	      iett.cod_orgao_responsavel2_iett, "+
		"	      iett.cod_iett_pai, "+
		"	      iett.cod_prcd_iett, "+
		"	      iett.cod_ett, "+
		"	      iett.descricao_r5, "+
		"	      iett.descricao_r4, "+
		"	      iett.descricao_r3, "+
		"	      iett.descricao_r2, "+
		"	      iett.descricao_r1, "+
		"	      iett.data_r5, "+
		"	      iett.data_r4, "+
		"	      iett.data_r3, "+
		"	      iett.data_r2, "+
		"	      iett.data_r1, "+
		"	      iett.nome_iett, "+
		"	      iett.cod_uo, "+
		"	      iett.cod_sit, "+
		"	      iett.nivel_iett, "+
		"	      iett.ind_exclusao_pos_historico, "+
		"	      iett.ind_modelo_iett, "+
		"	      iett.atencao_iett "+
		"	 FROM  public.tb_item_estrutura_iett iett "+
		"	 INNER JOIN iett_recursivo ON iett.cod_iett_pai =  iett_recursivo.cod_iett "+
		"	 WHERE iett.ind_ativo_iett = 'S' "+
		"	 ) "+
		"	 SELECT codigoOrgao, siglaOrgao, "+
		"		SUM(qtdObjetivo) qtdObjetivo, "+
		"		SUM(qtdEstrategia) qtdEstrategia, "+
		"		SUM(qtdResultado) qtdResultado, "+
		"		SUM(qtdProduto) qtdProduto, "+
		"		SUM(qtdAcao) qtdAcao, "+
		"		SUM(qtdAtividade) qtdAtividade, "+
		"		SUM(qtdREM) qtdREM, "+
		"		SUM(qtdIndicador) qtdIndicador, "+
		"		SUM(qtdVerde) qtdVerde, "+
		"		SUM(qtdAmarelo)  qtdAmarelo, "+
		"		SUM(qtdVermelho) qtdVermelho, "+
		"		SUM(qtdAzul) qtdAzul, "+
		"		SUM(qtdCinza) qtdCinza, "+
		"		SUM(qtdBranco) qtdBranco  "+
		"	 FROM( "+
//		"	 --TOTAL OE "+
		"	 SELECT 0 codigoOrgao, 'SEMSECRETARIA' siglaOrgao, count(*) qtdObjetivo, 0 qtdEstrategia, 0 qtdResultado, 0 qtdProduto, 0 qtdAcao, 0 qtdAtividade, 0 qtdREM, 0 qtdIndicador, 0 qtdVerde, 0 qtdAmarelo, 0 qtdVermelho, 0 qtdAzul, 0 qtdCinza, 0 qtdBranco "+
		"	 FROM iett_recursivo WHERE nivel_iett = 1 "+
		"	 UNION ALL "+
//		"	 --TOTAL ESTRATEGIA "+
		"	 SELECT COALESCE(o.cod_org,0) codigoOrgao, COALESCE(o.sigla_org, 'SEMSECRETARIA') siglaOrgao,  0 qtdObjetivo, count(*) qtdEstrategia, 0 qtdResultado, 0 qtdProduto, 0 qtdAcao, 0 qtdAtividade, 0 qtdREM, 0 qtdIndicador, 0 qtdVerde, 0 qtdAmarelo, 0 qtdVermelho, 0 qtdAzul, 0 qtdCinza, 0 qtdBranco "+  
		"	 FROM iett_recursivo iett "+
		"	 LEFT JOIN tb_orgao_org  o ON o.cod_org = iett.cod_orgao_responsavel1_iett "+
		"	 WHERE nivel_iett = 2 "+
		"	 GROUP BY o.cod_org, o.sigla_org "+
		"	 UNION ALL "+
//		"	 --TOTAL RESULTADO "+
		"	 SELECT COALESCE(o.cod_org,0) codigoOrgao, COALESCE(o.sigla_org, 'SEMSECRETARIA') siglaOrgao,  0 qtdObjetivo, 0 qtdEstrategia, count(distinct iett.cod_iett) qtdResultado, 0 qtdProduto, 0 qtdAcao, 0 qtdAtividade, 0 qtdREM, 0 qtdIndicador, 0 qtdVerde, 0 qtdAmarelo, 0 qtdVermelho, 0 qtdAzul, 0 qtdCinza, 0 qtdBranco "+  
		"	 FROM iett_recursivo iett "+
		"	 INNER JOIN tb_acomp_referencia_item_ari ari ON ari.cod_iett = iett.cod_iett "+
		"	 LEFT JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari "+
		"	 LEFT JOIN tb_orgao_org  o ON o.cod_org = iett.cod_orgao_responsavel1_iett "+
		"	 WHERE iett.nivel_iett = 3 "+
		"	 AND (((iett.cod_iett,arel.data_ult_manut_arel) IN (SELECT ari.cod_iett, MAX(arel.data_ult_manut_arel) "+ 
		"								 FROM tb_acomp_referencia_item_ari ari INNER JOIN tb_acomp_relatorio_arel arel ON ari.cod_ari = arel.cod_ari "+
		"								 WHERE (:exercicio=0 OR (arel.data_ult_manut_arel BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio)  "+
		"											        AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) ) ) "+
		"								 GROUP BY 1) "+
		"		AND arel.cod_cor IS NOT NULL) "+
		"		OR "+
		"		(iett.cod_iett, ari.cod_ari, 0) IN( "+ 
		"					SELECT cod_iett, cod_ari, contagem "+ 
		"					FROM(	  "+
		"					WITH todos_ietts_ari as (SELECT DISTINCT ari.cod_iett, ari.cod_ari "+ 
		"								 FROM tb_acomp_referencia_item_ari ari  "+
		"								 INNER JOIN tb_acomp_relatorio_arel arel  "+
		"									ON arel.cod_ari = ari.cod_ari "+
		"								 INNER JOIN tb_acomp_referencia_aref aref "+
		"									ON aref.cod_aref = ari.cod_aref "+
		"								 WHERE  "+
		"								      (:exercicio=0 OR (arel.data_inclusao_arel "+
		"								      BETWEEN (SELECT  data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+ 
		"									       AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) ) ) "+
		"								 AND (TO_DATE(aref.mes_aref||'/'||aref.ano_aref, 'MM/YYYY')) IN  "+
		"										(SELECT MAX(TO_DATE(arefFiltro.mes_aref||'/'||arefFiltro.ano_aref, 'MM/YYYY')) "+
		"										 FROM tb_acomp_referencia_aref arefFiltro  "+
		"										 WHERE (:exercicio=0 OR (TO_DATE(arefFiltro.mes_aref||'/'||arefFiltro.ano_aref, 'MM/YYYY') "+
		"										 BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+
		"										 AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) ) ) "+
		"										 ) 	     "+
		"									      "+
		"								),  "+
		"					     totais_por_iett as (SELECT ari.cod_iett, COUNT(arel.cod_arel) as total "+ 
		"								 FROM tb_acomp_referencia_item_ari ari	  "+
		"								 INNER JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari "+ 
		"								 WHERE ( :exercicio=0 OR ( arel.data_ult_manut_arel "+
		"								      BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+
		"								      AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) ) ) "+
		"								 AND arel.cod_cor IS NOT NULL "+
		"								 AND arel.data_ult_manut_arel IS NOT NULL "+
		"								 GROUP BY 1)  "+
		"					SELECT tia.cod_iett, tia.cod_ari, COALESCE(tpi.total,0) as contagem "+ 	
		"					FROM todos_ietts_ari tia  "+
		"					LEFT JOIN totais_por_iett tpi ON tpi.cod_iett = tia.cod_iett "+ 
		"					) AS NAOMONITORADOS) "+
		"		) "+
		"	 GROUP BY o.cod_org, o.sigla_org "+
		"	 UNION ALL "+
//		"	 --TOTAL PRODUTO "+
		"	 SELECT COALESCE(o.cod_org,0) codigoOrgao, COALESCE(o.sigla_org, 'SEMSECRETARIA') siglaOrgao,  0 qtdObjetivo, 0 qtdEstrategia, 0 qtdResultado, count(distinct iett.cod_iett) qtdProduto, 0 qtdAcao, 0 qtdAtividade, 0 qtdREM, 0 qtdIndicador, 0 qtdVerde, 0 qtdAmarelo, 0 qtdVermelho, 0 qtdAzul, 0 qtdCinza, 0 qtdBranco "+ 
		"	 FROM iett_recursivo iett "+
		"	 INNER JOIN tb_acomp_referencia_item_ari ari ON ari.cod_iett = iett.cod_iett "+
		"	 LEFT JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari "+
		"	 LEFT JOIN tb_orgao_org  o ON o.cod_org = iett.cod_orgao_responsavel1_iett "+
		"	 WHERE iett.nivel_iett = 4 "+
		"	 AND (((iett.cod_iett,arel.data_ult_manut_arel) IN (SELECT ari.cod_iett, MAX(arel.data_ult_manut_arel) "+ 
		"								 FROM tb_acomp_referencia_item_ari ari INNER JOIN tb_acomp_relatorio_arel arel ON ari.cod_ari = arel.cod_ari "+
		"								 WHERE (:exercicio=0 OR ( arel.data_ult_manut_arel BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio)  "+
		"											        AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) ) )"+
		"								 GROUP BY 1) "+
		"		AND arel.cod_cor IS NOT NULL) "+
		"		OR "+
		"		(iett.cod_iett, ari.cod_ari, 0) IN( "+ 
		"					SELECT cod_iett, cod_ari, contagem "+ 
		"					FROM(	  "+
		"					WITH todos_ietts_ari as (SELECT DISTINCT ari.cod_iett, ari.cod_ari "+ 
		"								 FROM tb_acomp_referencia_item_ari ari  "+
		"								 INNER JOIN tb_acomp_relatorio_arel arel  "+
		"									ON arel.cod_ari = ari.cod_ari "+
		"								 INNER JOIN tb_acomp_referencia_aref aref "+
		"									ON aref.cod_aref = ari.cod_aref "+
		"								 WHERE  "+
		"								      (:exercicio=0 OR (arel.data_inclusao_arel "+
		"								      BETWEEN (SELECT  data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+ 
		"									       AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) ) ) "+
		"								 AND (TO_DATE(aref.mes_aref||'/'||aref.ano_aref, 'MM/YYYY')) IN  "+
		"										(SELECT MAX(TO_DATE(arefFiltro.mes_aref||'/'||arefFiltro.ano_aref, 'MM/YYYY')) "+
		"										 FROM tb_acomp_referencia_aref arefFiltro  "+
		"										 WHERE (:exercicio=0 OR (TO_DATE(arefFiltro.mes_aref||'/'||arefFiltro.ano_aref, 'MM/YYYY') "+
		"										 BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+
		"										 AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"										 ) 	     "+
		"									      "+
		"								),  "+
		"					     totais_por_iett as (SELECT ari.cod_iett, COUNT(arel.cod_arel) as total "+ 
		"								 FROM tb_acomp_referencia_item_ari ari	  "+
		"								 INNER JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari "+ 
		"								 WHERE (:exercicio=0 OR (arel.data_ult_manut_arel "+
		"								      BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+
		"								      AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) )) "+
		"								 AND arel.cod_cor IS NOT NULL "+
		"								 AND arel.data_ult_manut_arel IS NOT NULL "+
		"								 GROUP BY 1)  "+
		"					SELECT tia.cod_iett, tia.cod_ari, COALESCE(tpi.total,0) as contagem "+ 	
		"					FROM todos_ietts_ari tia  "+
		"					LEFT JOIN totais_por_iett tpi ON tpi.cod_iett = tia.cod_iett "+ 
		"					) AS NAOMONITORADOS) "+
		"		) "+
		"	 GROUP BY o.cod_org, o.sigla_org	  "+
		"	 UNION ALL "+
//		"	 --TOTAL ACAO "+
		"	 SELECT COALESCE(o.cod_org,0) codigoOrgao, COALESCE(o.sigla_org, 'SEMSECRETARIA') siglaOrgao,  0 qtdObjetivo, 0 qtdEstrategia, 0 qtdResultado, 0 qtdProduto, count(distinct iett.cod_iett) qtdAcao, 0 qtdAtividade, 0 qtdREM, 0 qtdIndicador, 0 qtdVerde, 0 qtdAmarelo, 0 qtdVermelho, 0 qtdAzul, 0 qtdCinza, 0 qtdBranco "+
		"	 FROM iett_recursivo iett "+
		"	 INNER JOIN tb_acomp_referencia_item_ari ari ON ari.cod_iett = iett.cod_iett "+
		"	 LEFT JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari "+
		"	 LEFT JOIN tb_orgao_org  o ON o.cod_org = iett.cod_orgao_responsavel1_iett "+
		"	 WHERE iett.nivel_iett = 5 "+
		"	 AND (((iett.cod_iett,arel.data_ult_manut_arel) IN (SELECT ari.cod_iett, MAX(arel.data_ult_manut_arel) "+ 
		"								 FROM tb_acomp_referencia_item_ari ari INNER JOIN tb_acomp_relatorio_arel arel ON ari.cod_ari = arel.cod_ari "+
		"								 WHERE (:exercicio=0 OR (arel.data_ult_manut_arel BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio)  "+
		"											        AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"								 GROUP BY 1) "+
		"		AND arel.cod_cor IS NOT NULL) "+
		"		OR "+
		"		(iett.cod_iett, ari.cod_ari, 0) IN( "+ 
		"					SELECT cod_iett, cod_ari, contagem "+ 
		"					FROM(	  "+
		"					WITH todos_ietts_ari as (SELECT DISTINCT ari.cod_iett, ari.cod_ari "+ 
		"								 FROM tb_acomp_referencia_item_ari ari  "+
		"								 INNER JOIN tb_acomp_relatorio_arel arel  "+
		"									ON arel.cod_ari = ari.cod_ari "+
		"								 INNER JOIN tb_acomp_referencia_aref aref "+
		"									ON aref.cod_aref = ari.cod_aref "+
		"								 WHERE  "+
		"								      (:exercicio=0 OR (arel.data_inclusao_arel "+
		"								      BETWEEN (SELECT  data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+ 
		"									       AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"								 AND (TO_DATE(aref.mes_aref||'/'||aref.ano_aref, 'MM/YYYY')) IN  "+
		"										(SELECT MAX(TO_DATE(arefFiltro.mes_aref||'/'||arefFiltro.ano_aref, 'MM/YYYY')) "+
		"										 FROM tb_acomp_referencia_aref arefFiltro  "+
		"										 WHERE (:exercicio=0 OR (TO_DATE(arefFiltro.mes_aref||'/'||arefFiltro.ano_aref, 'MM/YYYY') "+
		"										 BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+
		"										 AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"										 ) 	     "+
		"									      "+
		"								),  "+
		"					     totais_por_iett as (SELECT ari.cod_iett, COUNT(arel.cod_arel) as total "+ 
		"								 FROM tb_acomp_referencia_item_ari ari	  "+
		"								 INNER JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari "+ 
		"								 WHERE (:exercicio=0 OR (arel.data_ult_manut_arel "+
		"								      BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+
		"								      AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"								 AND arel.cod_cor IS NOT NULL "+
		"								 AND arel.data_ult_manut_arel IS NOT NULL "+
		"								 GROUP BY 1)  "+
		"					SELECT tia.cod_iett, tia.cod_ari, COALESCE(tpi.total,0) as contagem "+ 	
		"					FROM todos_ietts_ari tia  "+
		"					LEFT JOIN totais_por_iett tpi ON tpi.cod_iett = tia.cod_iett "+ 
		"					) AS NAOMONITORADOS) "+
		"		) "+		
		"	 GROUP BY o.cod_org, o.sigla_org "+
		"	 UNION ALL "+
//		"	 --TOTAL ATIVIDADE "+
		"	 SELECT COALESCE(o.cod_org,0) codigoOrgao, COALESCE(o.sigla_org, 'SEMSECRETARIA') siglaOrgao,  0 qtdObjetivo, 0 qtdEstrategia, 0 qtdResultado, 0 qtdProduto, 0 qtdAcao, count(distinct iett.cod_iett) qtdAtividade, 0 qtdREM, 0 qtdIndicador, 0 qtdVerde, 0 qtdAmarelo, 0 qtdVermelho, 0 qtdAzul, 0 qtdCinza, 0 qtdBranco "+
		"	 FROM iett_recursivo iett "+
		"	 INNER JOIN tb_acomp_referencia_item_ari ari ON ari.cod_iett = iett.cod_iett "+
		"	 LEFT JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari "+
		"	 LEFT JOIN tb_orgao_org  o ON o.cod_org = iett.cod_orgao_responsavel1_iett "+
		"	 WHERE iett.nivel_iett = 6 "+
		"	 AND (((iett.cod_iett,arel.data_ult_manut_arel) IN (SELECT ari.cod_iett, MAX(arel.data_ult_manut_arel) "+ 
		"								 FROM tb_acomp_referencia_item_ari ari INNER JOIN tb_acomp_relatorio_arel arel ON ari.cod_ari = arel.cod_ari "+
		"								 WHERE (:exercicio=0 OR (arel.data_ult_manut_arel BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio)  "+
		"											        AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"								 GROUP BY 1) "+
		"		AND arel.cod_cor IS NOT NULL) "+
		"		OR "+
		"		(iett.cod_iett, ari.cod_ari, 0) IN( "+ 
		"					SELECT cod_iett, cod_ari, contagem "+ 
		"					FROM(	  "+
		"					WITH todos_ietts_ari as (SELECT DISTINCT ari.cod_iett, ari.cod_ari "+ 
		"								 FROM tb_acomp_referencia_item_ari ari  "+
		"								 INNER JOIN tb_acomp_relatorio_arel arel  "+
		"									ON arel.cod_ari = ari.cod_ari "+
		"								 INNER JOIN tb_acomp_referencia_aref aref "+
		"									ON aref.cod_aref = ari.cod_aref "+
		"								 WHERE  "+
		"								      (:exercicio=0 OR (arel.data_inclusao_arel "+
		"								      BETWEEN (SELECT  data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+ 
		"									       AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"								 AND (TO_DATE(aref.mes_aref||'/'||aref.ano_aref, 'MM/YYYY')) IN  "+
		"										(SELECT MAX(TO_DATE(arefFiltro.mes_aref||'/'||arefFiltro.ano_aref, 'MM/YYYY')) "+
		"										 FROM tb_acomp_referencia_aref arefFiltro  "+
		"										 WHERE (:exercicio=0 OR (TO_DATE(arefFiltro.mes_aref||'/'||arefFiltro.ano_aref, 'MM/YYYY') "+
		"										 BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+
		"										 AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"										 ) 	     "+
		"									      "+
		"								),  "+
		"					     totais_por_iett as (SELECT ari.cod_iett, COUNT(arel.cod_arel) as total "+ 
		"								 FROM tb_acomp_referencia_item_ari ari	  "+
		"								 INNER JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari "+ 
		"								 WHERE (:exercicio=0 OR (arel.data_ult_manut_arel "+
		"								      BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+
		"								      AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"								 AND arel.cod_cor IS NOT NULL "+
		"								 AND arel.data_ult_manut_arel IS NOT NULL "+
		"								 GROUP BY 1)  "+
		"					SELECT tia.cod_iett, tia.cod_ari, COALESCE(tpi.total,0) as contagem "+ 	
		"					FROM todos_ietts_ari tia  "+
		"					LEFT JOIN totais_por_iett tpi ON tpi.cod_iett = tia.cod_iett "+ 
		"					) AS NAOMONITORADOS) "+
		"		) "+
		"	 GROUP BY o.cod_org, o.sigla_org			  "+
		"	 UNION ALL "+
//		"	 --TOTAL REM "+
		"	 SELECT COALESCE(o.cod_org,0) codigoOrgao, COALESCE(o.sigla_org, 'SEMSECRETARIA') siglaOrgao,  0 qtdObjetivo, 0 qtdEstrategia, 0 qtdResultado, 0 qtdProduto, 0 qtdAcao, 0 qtdAtividade, count(distinct iett.cod_iett) qtdREM, 0 qtdIndicador, 0 qtdVerde, 0 qtdAmarelo, 0 qtdVermelho, 0 qtdAzul, 0 qtdCinza, 0 qtdBranco "+
		"	 FROM iett_recursivo iett "+
		"	 INNER JOIN TB_ITEM_ESTRUTURA_NIVEL_IETTN prioritario on prioritario.cod_iett = iett.cod_iett "+
		"	 INNER JOIN tb_acomp_referencia_item_ari ari ON ari.cod_iett = iett.cod_iett "+
		"	 LEFT JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari "+
		"	 LEFT JOIN tb_orgao_org  o ON o.cod_org = iett.cod_orgao_responsavel1_iett "+
		"	 WHERE iett.nivel_iett = 3 "+
		"	 AND (((iett.cod_iett,arel.data_ult_manut_arel) IN (SELECT ari.cod_iett, MAX(arel.data_ult_manut_arel) "+ 
		"								 FROM tb_acomp_referencia_item_ari ari INNER JOIN tb_acomp_relatorio_arel arel ON ari.cod_ari = arel.cod_ari "+
		"								 WHERE (:exercicio=0 OR (arel.data_ult_manut_arel BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio)  "+
		"											        AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"								 GROUP BY 1) "+
		"		AND arel.cod_cor IS NOT NULL) "+
		"		OR "+
		"		(iett.cod_iett, ari.cod_ari, 0) IN( "+ 
		"					SELECT cod_iett, cod_ari, contagem "+ 
		"					FROM(	  "+
		"					WITH todos_ietts_ari as (SELECT DISTINCT ari.cod_iett, ari.cod_ari "+ 
		"								 FROM tb_acomp_referencia_item_ari ari  "+
		"								 INNER JOIN tb_acomp_relatorio_arel arel  "+
		"									ON arel.cod_ari = ari.cod_ari "+
		"								 INNER JOIN tb_acomp_referencia_aref aref "+
		"									ON aref.cod_aref = ari.cod_aref "+
		"								 WHERE  "+
		"								      (:exercicio=0 OR (arel.data_inclusao_arel "+
		"								      BETWEEN (SELECT  data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+ 
		"									       AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"								 AND (TO_DATE(aref.mes_aref||'/'||aref.ano_aref, 'MM/YYYY')) IN  "+
		"										(SELECT MAX(TO_DATE(arefFiltro.mes_aref||'/'||arefFiltro.ano_aref, 'MM/YYYY')) "+
		"										 FROM tb_acomp_referencia_aref arefFiltro  "+
		"										 WHERE (:exercicio=0 OR (TO_DATE(arefFiltro.mes_aref||'/'||arefFiltro.ano_aref, 'MM/YYYY') "+
		"										 BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+
		"										 AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"										 ) 	     "+
		"									      "+
		"								),  "+
		"					     totais_por_iett as (SELECT ari.cod_iett, COUNT(arel.cod_arel) as total "+ 
		"								 FROM tb_acomp_referencia_item_ari ari	  "+
		"								 INNER JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari "+ 
		"								 WHERE (:exercicio=0 OR (arel.data_ult_manut_arel "+
		"								      BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+
		"								      AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"								 AND arel.cod_cor IS NOT NULL "+
		"								 AND arel.data_ult_manut_arel IS NOT NULL "+
		"								 GROUP BY 1)  "+
		"					SELECT tia.cod_iett, tia.cod_ari, COALESCE(tpi.total,0) as contagem "+ 	
		"					FROM todos_ietts_ari tia  "+
		"					LEFT JOIN totais_por_iett tpi ON tpi.cod_iett = tia.cod_iett "+ 
		"					) AS NAOMONITORADOS) "+
		"		) "+
		"	 GROUP BY o.cod_org, o.sigla_org "+
		"	 UNION ALL "+
//		"	 --TOTAL INDICADOR "+
		"	 SELECT COALESCE(o.cod_org,0) codigoOrgao, COALESCE(o.sigla_org, 'SEMSECRETARIA') siglaOrgao,  0 qtdObjetivo, 0 qtdEstrategia, 0 qtdResultado, 0 qtdProduto, 0 qtdAcao, 0 qtdAtividade, 0 qtdREM, count(distinct indicador.cod_iettir) qtdIndicador, 0 qtdVerde, 0 qtdAmarelo, 0 qtdVermelho, 0 qtdAzul, 0 qtdCinza, 0 qtdBranco "+
		"	 FROM iett_recursivo iett "+
		"	 INNER JOIN TB_ITEM_ESTRT_IND_RESUL_IETTR indicador on indicador.cod_iett = iett.cod_iett "+
		"	 INNER JOIN tb_acomp_referencia_item_ari ari ON ari.cod_iett = iett.cod_iett "+
		"	 LEFT JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari "+
		"	 LEFT JOIN tb_orgao_org  o ON o.cod_org = iett.cod_orgao_responsavel1_iett "+
		"	 WHERE iett.nivel_iett = 3 "+
		"    AND indicador.ind_ativo_iettr = 'S' "+
		"	 AND (((iett.cod_iett,arel.data_ult_manut_arel) IN (SELECT ari.cod_iett, MAX(arel.data_ult_manut_arel) "+ 
		"								 FROM tb_acomp_referencia_item_ari ari INNER JOIN tb_acomp_relatorio_arel arel ON ari.cod_ari = arel.cod_ari "+
		"								 WHERE (:exercicio=0 OR (arel.data_ult_manut_arel BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio)  "+
		"											        AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"								 GROUP BY 1) "+
		"		AND arel.cod_cor IS NOT NULL) "+
		"		OR "+
		"		(iett.cod_iett, ari.cod_ari, 0) IN( "+ 
		"					SELECT cod_iett, cod_ari, contagem "+ 
		"					FROM(	  "+
		"					WITH todos_ietts_ari as (SELECT DISTINCT ari.cod_iett, ari.cod_ari "+ 
		"								 FROM tb_acomp_referencia_item_ari ari  "+
		"								 INNER JOIN tb_acomp_relatorio_arel arel  "+
		"									ON arel.cod_ari = ari.cod_ari "+
		"								 INNER JOIN tb_acomp_referencia_aref aref "+
		"									ON aref.cod_aref = ari.cod_aref "+
		"								 WHERE  "+
		"								      (:exercicio=0 OR (arel.data_inclusao_arel "+
		"								      BETWEEN (SELECT  data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+ 
		"									       AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"								 AND (TO_DATE(aref.mes_aref||'/'||aref.ano_aref, 'MM/YYYY')) IN  "+
		"										(SELECT MAX(TO_DATE(arefFiltro.mes_aref||'/'||arefFiltro.ano_aref, 'MM/YYYY')) "+
		"										 FROM tb_acomp_referencia_aref arefFiltro  "+
		"										 WHERE (:exercicio=0 OR (TO_DATE(arefFiltro.mes_aref||'/'||arefFiltro.ano_aref, 'MM/YYYY') "+
		"										 BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+
		"										 AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"										 ) 	     "+
		"									      "+
		"								),  "+
		"					     totais_por_iett as (SELECT ari.cod_iett, COUNT(arel.cod_arel) as total "+ 
		"								 FROM tb_acomp_referencia_item_ari ari	  "+
		"								 INNER JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari "+ 
		"								 WHERE (:exercicio=0 OR (arel.data_ult_manut_arel "+
		"								      BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+
		"								      AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"								 AND arel.cod_cor IS NOT NULL "+
		"								 AND arel.data_ult_manut_arel IS NOT NULL "+
		"								 GROUP BY 1)  "+
		"					SELECT tia.cod_iett, tia.cod_ari, COALESCE(tpi.total,0) as contagem "+ 	
		"					FROM todos_ietts_ari tia  "+
		"					LEFT JOIN totais_por_iett tpi ON tpi.cod_iett = tia.cod_iett "+ 
		"					) AS NAOMONITORADOS) "+
		"		) "+
		"	 GROUP BY o.cod_org, o.sigla_org			  "+
		"	 UNION ALL "+
//		"	 --TOTAL VERDE "+
		"	 SELECT COALESCE(o.cod_org,0) codigoOrgao, COALESCE(o.sigla_org, 'SEMSECRETARIA') siglaOrgao,  0 qtdObjetivo, 0 qtdEstrategia, 0 qtdResultado, 0 qtdProduto, 0 qtdAcao, 0 qtdAtividade, 0 qtdREM, 0 qtdIndicador, count(*) qtdVerde, 0 qtdAmarelo, 0 qtdVermelho, 0 qtdAzul, 0 qtdCinza, 0 qtdBranco "+
		"	 FROM iett_recursivo iett  "+
		"	 INNER JOIN tb_acomp_referencia_item_ari ari ON ari.cod_iett = iett.cod_iett "+
		"	 INNER JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari "+
		"	 LEFT JOIN tb_orgao_org  o ON o.cod_org = iett.cod_orgao_responsavel1_iett "+
		"	 WHERE (iett.cod_iett,arel.data_ult_manut_arel) IN (SELECT ari.cod_iett, MAX(arel.data_ult_manut_arel) "+ 
		"					 FROM tb_acomp_referencia_item_ari ari INNER JOIN tb_acomp_relatorio_arel arel ON ari.cod_ari = arel.cod_ari "+
		"					 WHERE (:exercicio=0 OR (arel.data_ult_manut_arel BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+
		"								        AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) ))"+
		"					 GROUP BY 1) "+
		"	 AND arel.cod_cor = 1 "+
		"	 AND iett.nivel_iett = 3 "+
		"	 GROUP BY o.cod_org, o.sigla_org "+
		"	 UNION ALL "+
//		"	 --TOTAL AMARELO "+
		"	 SELECT COALESCE(o.cod_org,0) codigoOrgao, COALESCE(o.sigla_org, 'SEMSECRETARIA') siglaOrgao,  0 qtdObjetivo, 0 qtdEstrategia, 0 qtdResultado, 0 qtdProduto, 0 qtdAcao, 0 qtdAtividade, 0 qtdREM, 0 qtdIndicador, 0 qtdVerde, count(*) qtdAmarelo, 0 qtdVermelho, 0 qtdAzul, 0 qtdCinza, 0 qtdBranco "+
		"	 FROM iett_recursivo iett  "+
		"	 INNER JOIN tb_acomp_referencia_item_ari ari ON ari.cod_iett = iett.cod_iett "+
		"	 INNER JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari "+
		"	 INNER JOIN tb_acomp_referencia_aref aref ON aref.cod_aref = ari.cod_aref "+
		"	 LEFT JOIN tb_orgao_org  o ON o.cod_org = iett.cod_orgao_responsavel1_iett "+
		"	 WHERE (iett.cod_iett,arel.data_ult_manut_arel) IN (SELECT ari.cod_iett, MAX(arel.data_ult_manut_arel) "+ 
		"					 FROM tb_acomp_referencia_item_ari ari INNER JOIN tb_acomp_relatorio_arel arel ON ari.cod_ari = arel.cod_ari "+
		"					 WHERE (:exercicio=0 OR (arel.data_ult_manut_arel BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+
		"								        AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"					 GROUP BY 1) "+
		"	 AND arel.cod_cor = 2 "+
		"	 AND iett.nivel_iett = 3 "+
		"	 GROUP BY o.cod_org, o.sigla_org "+
		"	 UNION ALL "+
//		"	 --TOTAL VERMELHO "+
		"	 SELECT COALESCE(o.cod_org,0) codigoOrgao, COALESCE(o.sigla_org, 'SEMSECRETARIA') siglaOrgao,  0 qtdObjetivo, 0 qtdEstrategia, 0 qtdResultado, 0 qtdProduto, 0 qtdAcao, 0 qtdAtividade, 0 qtdREM, 0 qtdIndicador, 0 qtdVerde, 0 qtdAmarelo, count(*) qtdVermelho, 0 qtdAzul, 0 qtdCinza, 0 qtdBranco "+
		"	 FROM iett_recursivo iett  "+
		"	 INNER JOIN tb_acomp_referencia_item_ari ari ON ari.cod_iett = iett.cod_iett "+
		"	 INNER JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari "+
		"	 INNER JOIN tb_acomp_referencia_aref aref ON aref.cod_aref = ari.cod_aref "+
		"	 LEFT JOIN tb_orgao_org  o ON o.cod_org = iett.cod_orgao_responsavel1_iett "+
		"	 WHERE (iett.cod_iett,arel.data_ult_manut_arel) IN (SELECT ari.cod_iett, MAX(arel.data_ult_manut_arel) "+ 
		"					 FROM tb_acomp_referencia_item_ari ari INNER JOIN tb_acomp_relatorio_arel arel ON ari.cod_ari = arel.cod_ari "+
		"					 WHERE (:exercicio=0 OR (arel.data_ult_manut_arel BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+
		"								        AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"					 GROUP BY 1) "+
		"	 AND arel.cod_cor = 3 "+
		"	 AND iett.nivel_iett = 3 "+
		"	 GROUP BY o.cod_org, o.sigla_org	 "+
		"	 UNION ALL "+
//		"	 --TOTAL AZUL "+
		"	 SELECT COALESCE(o.cod_org,0) codigoOrgao, COALESCE(o.sigla_org, 'SEMSECRETARIA') siglaOrgao,  0 qtdObjetivo, 0 qtdEstrategia, 0 qtdResultado, 0 qtdProduto, 0 qtdAcao, 0 qtdAtividade, 0 qtdREM, 0 qtdIndicador, 0 qtdVerde, 0 qtdAmarelo, 0 qtdVermelho, count(*) qtdAzul, 0 qtdCinza, 0 qtdBranco "+
		"	 FROM iett_recursivo iett  "+
		"	 INNER JOIN tb_acomp_referencia_item_ari ari ON ari.cod_iett = iett.cod_iett "+
		"	 INNER JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari "+
		"	 INNER JOIN tb_acomp_referencia_aref aref ON aref.cod_aref = ari.cod_aref "+
		"	 LEFT JOIN tb_orgao_org  o ON o.cod_org = iett.cod_orgao_responsavel1_iett "+
		"	 WHERE (iett.cod_iett,arel.data_ult_manut_arel) IN (SELECT ari.cod_iett, MAX(arel.data_ult_manut_arel) "+ 
		"					 FROM tb_acomp_referencia_item_ari ari INNER JOIN tb_acomp_relatorio_arel arel ON ari.cod_ari = arel.cod_ari "+
		"					 WHERE (:exercicio=0 OR (arel.data_ult_manut_arel BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+
		"								        AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"					 GROUP BY 1) "+
		"	 AND arel.cod_cor = 10 "+
		"	 AND iett.nivel_iett = 3 "+
		"	 GROUP BY o.cod_org, o.sigla_org			  "+
		"	 UNION ALL "+
//		"	 --TOTAL CINZA "+
		"	 SELECT COALESCE(o.cod_org,0) codigoOrgao, COALESCE(o.sigla_org, 'SEMSECRETARIA') siglaOrgao,  0 qtdObjetivo, 0 qtdEstrategia, 0 qtdResultado, 0 qtdProduto, 0 qtdAcao, 0 qtdAtividade, 0 qtdREM, 0 qtdIndicador, 0 qtdVerde, 0 qtdAmarelo, 0 qtdVermelho, 0 qtdAzul, count(*) qtdCinza, 0 qtdBranco "+
		"	 FROM iett_recursivo iett  "+
		"	 INNER JOIN tb_acomp_referencia_item_ari ari ON ari.cod_iett = iett.cod_iett "+
		"	 INNER JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari "+
		"	 INNER JOIN tb_acomp_referencia_aref aref ON aref.cod_aref = ari.cod_aref "+
		"	 LEFT JOIN tb_orgao_org  o ON o.cod_org = iett.cod_orgao_responsavel1_iett "+
		"	 WHERE (iett.cod_iett,arel.data_ult_manut_arel) IN (SELECT ari.cod_iett, MAX(arel.data_ult_manut_arel) "+ 
		"					 FROM tb_acomp_referencia_item_ari ari INNER JOIN tb_acomp_relatorio_arel arel ON ari.cod_ari = arel.cod_ari "+
		"					 WHERE (:exercicio=0 OR (arel.data_ult_manut_arel BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+
		"								        AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"					 GROUP BY 1) "+
		"	 AND arel.cod_cor = 11 "+
		"	 AND iett.nivel_iett = 3 "+
		"	 GROUP BY o.cod_org, o.sigla_org "+
		"	 UNION ALL "+
//		"	 --TOTAL NAO MONITORADO "+
		"	 SELECT COALESCE(o.cod_org,0) codigoOrgao, COALESCE(o.sigla_org, 'SEMSECRETARIA') siglaOrgao,  0 qtdObjetivo, 0 qtdEstrategia, 0 qtdResultado, 0 qtdProduto, 0 qtdAcao, 0 qtdAtividade, 0 qtdREM, 0 qtdIndicador, 0 qtdVerde, 0 qtdAmarelo, 0 qtdVermelho, 0 qtdAzul, 0 qtdCinza, count(distinct iett.cod_iett) qtdBranco "+
		"	 FROM iett_recursivo iett  "+
		"	 INNER JOIN tb_acomp_referencia_item_ari ari ON ari.cod_iett = iett.cod_iett "+
		"	 INNER JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari "+
		"	 LEFT JOIN tb_orgao_org  o ON o.cod_org = iett.cod_orgao_responsavel1_iett "+
		"	 WHERE 	iett.nivel_iett = 3 AND "+
		"		(iett.cod_iett, ari.cod_ari, 0) IN( "+ 
		"					SELECT cod_iett, cod_ari, contagem "+ 
		"					FROM(	  "+
		"					WITH todos_ietts_ari as (SELECT DISTINCT ari.cod_iett, ari.cod_ari "+ 
		"								 FROM tb_acomp_referencia_item_ari ari  "+
		"								 INNER JOIN tb_acomp_relatorio_arel arel  "+
		"									ON arel.cod_ari = ari.cod_ari "+
		"								 INNER JOIN tb_acomp_referencia_aref aref "+
		"									ON aref.cod_aref = ari.cod_aref "+
		"								 WHERE  "+
		"								      (:exercicio=0 OR (arel.data_inclusao_arel "+
		"								      BETWEEN (SELECT  data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+ 
		"									       AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"								 AND (TO_DATE(aref.mes_aref||'/'||aref.ano_aref, 'MM/YYYY')) IN  "+
		"										(SELECT MAX(TO_DATE(arefFiltro.mes_aref||'/'||arefFiltro.ano_aref, 'MM/YYYY')) "+
		"										 FROM tb_acomp_referencia_aref arefFiltro  "+
		"										 WHERE (:exercicio=0 OR (TO_DATE(arefFiltro.mes_aref||'/'||arefFiltro.ano_aref, 'MM/YYYY') "+
		"										 BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+
		"										 AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"										 ) 	     "+
		"									      "+
		"								),  "+
		"					     totais_por_iett as (SELECT ari.cod_iett, COUNT(arel.cod_arel) as total "+ 
		"								 FROM tb_acomp_referencia_item_ari ari	  "+
		"								 INNER JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari "+ 
		"								 WHERE (:exercicio=0 OR (arel.data_ult_manut_arel "+
		"								      BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+
		"								      AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))) "+
		"								 AND arel.cod_cor IS NOT NULL "+
		"								 AND arel.data_ult_manut_arel IS NOT NULL "+
		"								 GROUP BY 1)  "+
		"					SELECT tia.cod_iett, tia.cod_ari, COALESCE(tpi.total,0) as contagem "+ 	
		"					FROM todos_ietts_ari tia  "+
		"					LEFT JOIN totais_por_iett tpi ON tpi.cod_iett = tia.cod_iett "+ 
		"					) AS NAOMONITORADOS) "+
		"	GROUP BY o.cod_org, o.sigla_org "+	
		"	 ) TOTAIS "+
		"	GROUP BY codigoOrgao, siglaOrgao "+
		"	ORDER BY siglaOrgao ");



	
	}
	
	
	public List<Object[]> listaDadosEstatisticos(
			List<Integer> codigoObjetivoEstrategico, Long exercicio) {
		StringBuffer hql = new StringBuffer();

		queryEstatisticas(codigoObjetivoEstrategico, hql, exercicio);

		
		//System.out.println(hql.toString());
		
		SQLQuery q = this.session.createSQLQuery(hql.toString());

//		if (codigoObjetivoEstrategico.size() != 0) {
//			q.setParameterList("codObj", codigoObjetivoEstrategico);
//		}
//
		if (exercicio != null) {
			q.setParameter("exercicio", exercicio);
		}
		else{
			q.setParameter("exercicio", 0L);
		}
		
		if(codigoObjetivoEstrategico.size() != 0) {
			q.setParameterList("codObj", codigoObjetivoEstrategico);	
		}

		List<Object[]> lista = q.list();
		
		return lista;

	}
	
	public EstatisticaDTO carregaEstatisticaDTO(List<Integer> codigoObjetivoEstrategico, Long exercicio){
		
		
		List<Object[]> lista = new ArrayList<Object[]>();
		
		lista = listaDadosEstatisticos(codigoObjetivoEstrategico, exercicio);
		
		List<SecretariaEstatisticaDTO> secretariaObjetivoEstrategico = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaEstrategia = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaIndicadores = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaREM = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaResultado = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaProduto = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaAcao = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaAtividade = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaVerde = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaAmarelo = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaVermelho = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaAzul = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaCinza = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaBranco = new ArrayList<SecretariaEstatisticaDTO>();		
		
		for(Object[] obj: lista){
			
			if(Long.parseLong(obj[2].toString())!=0L){
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[2].toString()));
				secretariaObjetivoEstrategico.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}
			
			if(Long.parseLong(obj[3].toString())!=0L){
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[3].toString()));
				secretariaEstrategia.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}
			
			if(Long.parseLong(obj[4].toString())!=0L){
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[4].toString()));
				secretariaResultado.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}
			
			if(Long.parseLong(obj[5].toString())!=0L){
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[5].toString()));
				secretariaProduto.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}
			
			if(Long.parseLong(obj[6].toString())!=0L){
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[6].toString()));
				secretariaAcao.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}
			
			if(Long.parseLong(obj[7].toString())!=0L){
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[7].toString()));
				secretariaAtividade.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}
			
			if(Long.parseLong(obj[8].toString())!=0L){
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[8].toString()));
				secretariaREM.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}
			
			if(Long.parseLong(obj[9].toString())!=0L){
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[9].toString()));
				secretariaIndicadores.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}
			
			if(Long.parseLong(obj[10].toString())!=0L){
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[10].toString()));
				secretariaVerde.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}
			
			if(Long.parseLong(obj[11].toString())!=0L){
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[11].toString()));
				secretariaAmarelo.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}
			
			if(Long.parseLong(obj[12].toString())!=0L){
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[12].toString()));
				secretariaVermelho.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}
			
			if(Long.parseLong(obj[13].toString())!=0L){
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[13].toString()));
				secretariaAzul.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}
			
			if(Long.parseLong(obj[14].toString())!=0L){
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[14].toString()));
				secretariaCinza.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}
			
			if(Long.parseLong(obj[15].toString())!=0L){
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[15].toString()));
				secretariaBranco.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}
			

			
		}
		
		EstatisticaDTO estatisticaDTO = new EstatisticaDTO();
		
		estatisticaDTO.setQuantidadeObjetivoEstregico(secretariaObjetivoEstrategico);
		estatisticaDTO.setQuantidadeEstrategia(secretariaEstrategia);
		estatisticaDTO.setQuantidadeResultado(secretariaResultado);
		estatisticaDTO.setQuantidadeProduto(secretariaProduto);
		estatisticaDTO.setQuantidadeAcao(secretariaAcao);
		estatisticaDTO.setQuantidadeAtividade(secretariaAtividade);
		estatisticaDTO.setQuantidadeREM(secretariaREM);
		estatisticaDTO.setQuantidadeIndicadores(secretariaIndicadores);
		estatisticaDTO.setQuantidadeVerde(secretariaVerde);
		estatisticaDTO.setQuantidadeAmarelo(secretariaAmarelo);
		estatisticaDTO.setQuantidadeVermelho(secretariaVermelho);
		estatisticaDTO.setQuantidadeAzul(secretariaAzul);
		estatisticaDTO.setQuantidadeCinza(secretariaCinza);
		estatisticaDTO.setQuantidadeBranco(secretariaBranco);
		
		return estatisticaDTO;
		
	}
	
	
	public String estatisticaJSON(List<Integer> codigoObjetivoEstrategico, Long exercicio){
		EstatisticaDTO dto = new EstatisticaDTO();
		
		dto = carregaEstatisticaDTO(codigoObjetivoEstrategico, exercicio);
		
	    Gson gson = new Gson();
		
		return gson.toJson(dto);
	}

}
