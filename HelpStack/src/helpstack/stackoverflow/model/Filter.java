package helpstack.stackoverflow.model;

import java.util.List;

public class Filter {
	private List<String> included_fields;
	private String filter_type;
	private String filter;
	
	public Filter() {
		
	}

	public List<String> getInclude_fields() {
		return included_fields;
	}

	public void setInclude_fields(List<String> include_fields) {
		this.included_fields = include_fields;
	}

	public String getFilter_type() {
		return filter_type;
	}

	public void setFilter_type(String filter_type) {
		this.filter_type = filter_type;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}
	
	@Override
	public String toString() {
		
		return "[FILTRO] (filter="+this.filter+")";
	}
}
