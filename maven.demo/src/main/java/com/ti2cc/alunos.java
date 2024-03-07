package com.ti2cc;


public class alunos {
	private int matricula;
	private int nota;
	private String materiafav;
	private char sexo;
	
	public alunos() {
		this.matricula = -1;
		this.nota = 0;
		this.materiafav = "";
		this.sexo = '*';
	}
	
	public alunos(int matricula, int nota, String materiafav, char sexo) {
		this.matricula = matricula;
		this.nota = nota;
		this.materiafav = materiafav;
		this.sexo = sexo;
	}

	public int getMatricula() {
		return matricula;
	}

	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	public String getMateriafav() {
		return materiafav;
	}

	public void setMateriafav(String materiafav) {
		this.materiafav = materiafav;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	@Override
	public String toString() {
		return "Alunos [matricula=" + matricula + ", nota=" + nota + ", materiafav=" + materiafav + ", sexo=" + sexo + "]";
	}	
}

