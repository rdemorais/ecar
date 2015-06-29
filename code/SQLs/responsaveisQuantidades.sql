select usu.nome_usu, org.sigla_org, count(iett.cod_iett) as res from tb_item_estrutura_iett iett
inner join TB_ITEM_EST_USUTPFUAC_IETTUTFA iett_tpfa on iett.cod_iett = iett_tpfa.cod_iett
inner join tb_usuario_usu usu on iett_tpfa.cod_usu = usu.cod_usu
left join tb_orgao_org org on iett.cod_orgao_responsavel1_iett = org.cod_org
where iett_tpfa.cod_tpfa = 3
and iett.nivel_iett = 3
and iett.ind_ativo_iett = 'S'
group by usu.nome_usu, org.sigla_org
order by org.sigla_org, usu.nome_usu, res desc
