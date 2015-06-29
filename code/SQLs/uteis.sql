-- Atualizar o cadastro com as situações do monitoramento. Deve ser executada quando se tem apenas um ciclo
update tb_item_estrutura_iett as iett set cod_sit = sits.cod_sit
FROM (
select cod_iett, arel.cod_sit
from tb_acomp_referencia_item_ari ari
inner join tb_acomp_relatorio_arel arel on ari.cod_ari = arel.cod_ari
where arel.cod_sit is not null) as sits where iett.cod_iett = sits.cod_iett