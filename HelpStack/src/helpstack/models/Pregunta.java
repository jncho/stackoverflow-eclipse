package helpstack.models;

public class Pregunta {
	
	private String titulo;
	private boolean check;
	private int votes;
	
	public Pregunta(String titulo,boolean check,int votes) {
		this.titulo = titulo;
		this.check = check;
		this.votes = votes;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}
	
	
}
