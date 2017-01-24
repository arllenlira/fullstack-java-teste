package br.com.contabilizei.notafiscal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "nota_fiscal")
public class NotaFiscal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String numero;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_emissao")
	private Date dataEmissao;
	
	private String descricao;
	private String valor;
	private String anexo1;
	private String anexo2;
	private String anexo3;
	
	@Transient
	private Long empresaId;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
	
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
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	@Transient
	public Long getEmpresaId() {
		return empresaId;
	}
	@Transient
	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}
	
	
}
