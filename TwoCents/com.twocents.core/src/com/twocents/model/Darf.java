package com.twocents.model;

import javax.persistence.Entity;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.twocents.core.util.ToStringStyle;


@Entity
public class Darf extends Persistent {

	private static final long serialVersionUID = 4711441471017914475L;

	//Nome
	private String nome;
	
	//Telefone
	private String telefone;
	
	
	
	//PERÍODO DE APURAÇÃO
	private String periodoDeApuracao;
		
	//03 NÚMERO DO CPF / CNPJ	
	private String cpfOuCpnj;
		
	//04 CÓDIGO DA RECEITA
	private String codigoDaReceita;
	
	//05 NÚMERO DE REFERÊNCIA
	private String numeroDeReferencia;
	
	//06 DATA DE VENCIMENTO
	private String dataDeVencimento;
	
	//07 VALOR DO PRINCIPAL
	private String valorDoPrincipal ;
	
	//08 VALOR DA MULTA
	private String valorDaMulta;
	
	//09 VALOR DOS JUROS E/OU ENCARGOS DL - 1.025/69
	private String juros;
	
	//10 VALOR TOTAL
	private String valorTotal;

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the telefone
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone the telefone to set
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * @return the periodoDeApuracao
	 */
	public String getPeriodoDeApuracao() {
		return periodoDeApuracao;
	}

	/**
	 * @param periodoDeApuracao the periodoDeApuracao to set
	 */
	public void setPeriodoDeApuracao(String periodoDeApuracao) {
		this.periodoDeApuracao = periodoDeApuracao;
	}

	/**
	 * @return the cpfOuCpnj
	 */
	public String getCpfOuCpnj() {
		return cpfOuCpnj;
	}

	/**
	 * @param cpfOuCpnj the cpfOuCpnj to set
	 */
	public void setCpfOuCpnj(String cpfOuCpnj) {
		this.cpfOuCpnj = cpfOuCpnj;
	}

	/**
	 * @return the codigoDaReceita
	 */
	public String getCodigoDaReceita() {
		return codigoDaReceita;
	}

	/**
	 * @param codigoDaReceita the codigoDaReceita to set
	 */
	public void setCodigoDaReceita(String codigoDaReceita) {
		this.codigoDaReceita = codigoDaReceita;
	}

	/**
	 * @return the numeroDeReferencia
	 */
	public String getNumeroDeReferencia() {
		return numeroDeReferencia;
	}

	/**
	 * @param numeroDeReferência the numeroDeReferência to set
	 */
	public void setNumeroDeReferencia(String numeroDeReferencia) {
		this.numeroDeReferencia = numeroDeReferencia;
	}

	/**
	 * @return the dataDeVencimento
	 */
	public String getDataDeVencimento() {
		return dataDeVencimento;
	}

	/**
	 * @param dataDeVencimento the dataDeVencimento to set
	 */
	public void setDataDeVencimento(String dataDeVencimento) {
		this.dataDeVencimento = dataDeVencimento;
	}

	/**
	 * @return the valorDoPrincipal
	 */
	public String getValorDoPrincipal() {
		return valorDoPrincipal;
	}

	/**
	 * @param valorDoPrincipal the valorDoPrincipal to set
	 */
	public void setValorDoPrincipal(String valorDoPrincipal) {
		this.valorDoPrincipal = valorDoPrincipal;
	}

	/**
	 * @return the valorDaMulta
	 */
	public String getValorDaMulta() {
		return valorDaMulta;
	}

	/**
	 * @param valorDaMulta the valorDaMulta to set
	 */
	public void setValorDaMulta(String valorDaMulta) {
		this.valorDaMulta = valorDaMulta;
	}

	/**
	 * @return the juros
	 */
	public String getJuros() {
		return juros;
	}

	/**
	 * @param juros the juros to set
	 */
	public void setJuros(String juros) {
		this.juros = juros;
	}

	/**
	 * @return the valorTotal
	 */
	public String getValorTotal() {
		return valorTotal;
	}

	/**
	 * @param valorTotal the valorTotal to set
	 */
	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.TW_DEFAULT);
	}
	
}
