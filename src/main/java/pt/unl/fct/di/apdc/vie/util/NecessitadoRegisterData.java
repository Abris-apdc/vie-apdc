package pt.unl.fct.di.apdc.vie.util;

public class NecessitadoRegisterData {

	private String firstName;
	private String lastName;
	private String nrCartao;
	private String descricao;
	private String local;
	private String password;
	
	public NecessitadoRegisterData() {
	}
	
	public NecessitadoRegisterData(String firstName, String lastName, String nrCartao, String descricao, String local, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.nrCartao = nrCartao;
		this.descricao = descricao;
		this.local = local;
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getNrCartao() {
		return nrCartao;
	}
	public void setNrCartao(String nrCartao) {
		this.nrCartao = nrCartao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
