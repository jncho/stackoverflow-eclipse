package pruebaplugin.stackoverflow.model;

import java.util.List;

public class ResultRest<T> {
	private List<T> items;
	private boolean has_more;
	
	public ResultRest() {
		
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public boolean isHas_more() {
		return has_more;
	}

	public void setHas_more(boolean has_more) {
		this.has_more = has_more;
	}
	
	@Override
	public String toString() {
		String cadena = "";
		cadena += "[ITEMS]\n";
		for (T item : this.items) {
			cadena+=item.toString();
			cadena+="\n";
		}
		cadena += "[has_more="+this.has_more+"]\n";
		return cadena;
	}
}
