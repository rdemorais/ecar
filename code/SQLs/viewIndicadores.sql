
/*
select cod_iettir, cod_iett, nome_iettir from tb_item_estrt_ind_resul_iettr where ordem <> -1
AND ind_ativo_iettr = 'S'
and cod_iettir not in(
*/
create or replace view indicadores as
select 
distinct ind.cod_iettir as cod_ecar,
cat.nome_categoria as rede,
eti.nome_etiqueta as programa,
org.sigla_org as secretaria,
usu.nome_usu as responsavel,
(per.num_meses_prdc * 30) as periodicidade,
ind.nome_iettir as indicador,
ind.conceituacao_iettir conceituacao,
ind.interpretacao_iettir interpretacao,
ind.metodocalculo_iettir metodo_calculo,
ind.ind_acumulavel_iettr acumulavel
from tb_item_estrt_ind_resul_iettr ind
inner join tb_item_estrutura_iett iett on ind.cod_iett = iett.cod_iett
left join rel_itemestrutiett_etiqueta relEti on relEti.cod_iett = iett.cod_iett
left join tb_etiqueta eti on eti.cod_etiqueta = relEti.cod_etiqueta
left join tb_categoria cat on eti.cod_categoria = cat.cod_categoria
inner join tb_item_estrut_usuario_iettus usuIett on usuIett.cod_iett = iett.cod_iett
left join tb_usuario_usu usu on usu.cod_usu = usuIett.cod_usu
left join tb_orgao_org org on org.cod_org = iett.cod_orgao_responsavel1_iett
left join tb_periodicidade_prdc per on per.cod_prdc = ind.cod_prdc
where
eti.cod_etiqueta in (3,4,11,21,62,92,106,109,114,121,125,128,200,213,424)
AND usuIett.cod_tpfa = 3
AND ind.ind_ativo_iettr = 'S'
AND ordem <> -1
--)
order by ind.cod_iettir
--select nome_iett from tb_item_estrutura_iett where cod_iett = 6212


--select * from tb_etiqueta where nome_etiqueta like '%CRACK%' and ind_ativo = 'S'


create view indicadores_valores as
select 
distinct prev.cod_iettir as cod_ecar, 
mes_iettf as mes, 
ano_iettf as ano, 
qtd_prevista_iettf as qtd_prev, 
qtd_realizada_arf as qtd_rea
from tb_item_estrut_fisico_iettf prev
left join tb_acomp_real_fisico_arf rea on 
	(prev.cod_iettir = rea.cod_iettir and prev.mes_iettf = rea.mes_arf and prev.ano_iettf = rea.ano_arf)
inner join tb_item_estrt_ind_resul_iettr ind on prev.cod_iettir = ind.cod_iettir
where prev.cod_iettir in (

	select distinct ind.cod_iettir
	from tb_item_estrt_ind_resul_iettr ind
	inner join tb_item_estrutura_iett iett on ind.cod_iett = iett.cod_iett
	inner join rel_itemestrutiett_etiqueta relEti on relEti.cod_iett = iett.cod_iett
	inner join tb_etiqueta eti on eti.cod_etiqueta = relEti.cod_etiqueta
	inner join tb_item_estrut_usuario_iettus usuIett on usuIett.cod_iett = iett.cod_iett
	inner join tb_usuario_usu usu on usu.cod_usu = usuIett.cod_usu
	inner join tb_orgao_org org on org.cod_org = iett.cod_orgao_responsavel1_iett
	left join tb_periodicidade_prdc per on per.cod_prdc = ind.cod_prdc
	where
	ordem <> -1
	AND eti.cod_etiqueta in (3,4,11,21,62,92,106,109,114,121,125,128,200,213,424)
	AND usuIett.cod_tpfa = 3
	AND ind.ind_ativo_iettr = 'S'
)

COPY
(select * from indicadores)
TO '/var/lib/pgsql/indicadores.csv' WITH CSV HEADER FORCE QUOTE rede, programa, responsavel, indicador, conceituacao, interpretacao, metodo_calculo;

COPY
(select * from indicadores_valores)
TO '/var/lib/pgsql/indicadores_valores.csv' WITH CSV HEADER;
