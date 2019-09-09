package helpstack.views;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;

import org.eclipse.core.runtime.Platform;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.osgi.framework.Bundle;

public class ViewTools {

	public static String prettify(String content) {
		// Add prettify to code and pre tags
		Document doc = Jsoup.parse(content);
		Iterator<Element> iterator = doc.select("code, pre").iterator();
		
		while(iterator.hasNext()) {
			iterator.next().addClass("prettyprint");
		}
		
		return doc.html();
	}
	
	public static String wrapHtml(String content) throws URISyntaxException, IOException {
		Bundle bundle = Platform.getBundle("helpstack");
		URL fileURL = bundle.getEntry("template/template_result.html");
	    Document document = Jsoup.parse(fileURL.openStream(), "UTF-8", fileURL.toURI().toString());
	    
	    document.select("body").html(content);
	    
	    return document.html();
	}

}
