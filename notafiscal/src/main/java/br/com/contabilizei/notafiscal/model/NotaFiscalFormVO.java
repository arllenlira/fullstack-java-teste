package br.com.contabilizei.notafiscal.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class NotaFiscalFormVO {

	private Long id;
	
	private String numero;

	private Date dataEmissao;
	
	private String descricao;
	private String valor;
	private String anexo1;
	private String anexo2;
	private String anexo3;
	
    private Long empresaID;
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Date getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getAnexo1() {
		return anexo1;
	}
	public void setAnexo1(String anexo1) {
		this.anexo1 = anexo1;
	}
	public String getAnexo2() {
		return anexo2;
	}
	public void setAnexo2(String anexo2) {
		this.anexo2 = anexo2;
	}
	public String getAnexo3() {
		return anexo3;
	}
	public void setAnexo3(String anexo3) {
		this.anexo3 = anexo3;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEmpresaID() {
		return empresaID;
	}
	public void setEmpresaID(Long empresaID) {
		this.empresaID = empresaID;
	}

}
