delete from tb_item_estrut_upload_iettup;
delete from tb_acomp_relatorio_arel;

delete from tb_historico_iettuph;
delete from tb_item_estr_upl_categ_iettuc;
delete from tb_acomp_ref_item_limites_arli;
delete from tb_acomp_referencia_item_ari;

delete from tb_acomp_ref_limites_arl;
delete from tb_acomp_referencia_aref;

delete from tb_item_estrut_usuario_iettus;
delete from tb_item_estrut_marcador_iettm;
delete from tb_itemestrutura_sisatributo_iettsatb;
delete from rel_itemestrutiett_etiqueta;
delete from tb_item_est_usutpfuac_iettutfa;
delete from tb_historico_iettush;
delete from tb_historico_ietth;
delete from tb_item_estrut_entidade_iette;
delete from tb_item_estrut_acao_ietta;
delete from tb_historico_iettfh;
delete from tb_item_estrut_fisico_iettf;
delete from tb_acomp_real_fisico_arf;
delete from tb_item_estrt_ind_resul_iettr;
delete from tb_item_estrutura_nivel_iettn;
delete from tb_historico_iettrh;
delete from tb_ef_item_est_previsao_efiep;
delete from tb_ef_iett_fonte_tot_efieft;
delete from tb_ef_item_est_conta_efiec;
delete from tb_item_estrutura_iett;


delete from tb_usuario_atributo_usua where cod_usu <> 1;
delete from tb_usuario_orgao_usuorg where cod_usu <> 1;
delete from tb_historico_master_mah;
delete from tb_usuario_usu where cod_usu <> 1;
delete from tb_orgao_org where cod_org <> 16;