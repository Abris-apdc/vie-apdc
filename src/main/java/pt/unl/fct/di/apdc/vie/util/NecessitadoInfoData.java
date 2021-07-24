package pt.unl.fct.di.apdc.vie.util;

public class NecessitadoInfoData {

	private String firstName;
	private String lastName;
	private String nrCartao;
	private String descricao;
	private String local;
	private String email;
	private String phone;
	private String state;
	
	public NecessitadoInfoData() {
	}
	
	public NecessitadoInfoData(String firstName, String lastName,
			String nrCartao, String descricao, String local,
			String email, String phone, String state) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.nrCartao = nrCartao;
		this.descricao = descricao;
		this.local = local;
		this.email = email;
		this.phone = phone;
		this.state = state;
	}
	
	public String getState() {
		return state;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
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
}
