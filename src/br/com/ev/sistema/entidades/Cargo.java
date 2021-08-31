package br.com.ev.sistema.entidades;

public class Cargo {
	
	private String nome;
	private int id;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String toString(){
		
		return nome ;
		
	}
}
