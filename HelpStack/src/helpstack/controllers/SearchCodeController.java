package helpstack.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import helpstack.search.SearchEngine;
import helpstack.stackoverflow.exceptions.ConnectionSOException;
import helpstack.views.SearchCodeDialog;
import helpstack.views.ViewTools;

public class SearchCodeController {
	
	public static void registerListeners(SearchCodeDialog view) {
		
		view.getBtnSearch().addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				TableViewer table = view.getTableViewer();
				
				String query = view.getText().getText();
				if (query.isEmpty()) {
					table.setInput(null);
					table.refresh();
					return;
				}
				
				try {
					ArrayList<String> codes = SearchEngine.searchQuestionCode(query);
	
					// Update ui
					table.setInput(codes);
					table.refresh();
				
				}catch (ConnectionSOException exception) {
					System.err.println(exception);
					
				}
				
			}
			
		});
		
		view.getTableViewer().addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent e) {
				IStructuredSelection selection = (IStructuredSelection) e.getSelection();
				String code = (String) selection.getFirstElement();

				if (code != null) {
					try {
						view.getBrowser().setText(ViewTools.wrapHtml("<pre class='prettyprint'><code>"+code+"</code></pre>"));
					} catch (Exception exception) {
						System.err.println(e);
						view.getBrowser().setText("INTERNAL ERROR");
					}
				} else {
					view.getBrowser().setText("");
				}
			}
		});
		
	}

}
