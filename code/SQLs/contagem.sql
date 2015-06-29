select
count(iett.cod_iett),
iettOrg.sigla_org
from tb_item_estrutura_iett iett
left join tb_orgao_org iettOrg on iett.COD_ORGAO_RESPONSAVEL1_IETT = iettOrg.cod_org
where 
iett.ind_ativo_iett = 'S'  
and iett.nivel_iett = 3
and iett.data_inclusao_iett < '2013-01-31'
group by iettOrg.sigla_org

--Por secretaria
--Prioritário
select count (cor.cod_cor) as contar,
cor.significado_cor as nome,
iettOrg.sigla_org
from tb_acomp_referencia_item_ari ari  
inner join tb_item_estrutura_iett iett on iett.cod_iett = ari.cod_iett  
inner join tb_acomp_relatorio_arel arel on ari.cod_ari = arel.cod_ari  
inner join tb_cor cor on arel.cod_cor = cor.cod_cor 
inner join TB_ITEM_ESTRUTURA_NIVEL_IETTN prioritario on prioritario.cod_iett = iett.cod_iett
left join tb_orgao_org iettOrg on iett.COD_ORGAO_RESPONSAVEL1_IETT = iettOrg.cod_org 
where iett.nivel_iett = 3 
--and sigla_org = 'SE' 
and iett.ind_ativo_iett='S'
and data_inclusao_iett < '2013-01-31'
and arel.data_ult_manut_arel = (select max(arel.data_ult_manut_arel) from tb_acomp_relatorio_arel arel  
	inner join tb_acomp_referencia_item_ari ari on ari.cod_ari = arel.cod_ari  
	where ari.cod_iett = iett.cod_iett  
	and arel.cod_cor is not null)  
group by cor.significado_cor, iettOrg.sigla_org 
order by iettOrg.sigla_org, cor.significado_cor