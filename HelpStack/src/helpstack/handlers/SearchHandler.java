package helpstack.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.IConsoleView;

import helpstack.controllers.SearchController;
import helpstack.views.ResultSearchISEView;
import helpstack.views.SearchView;


public class SearchHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		try {               
		    //IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		    IWorkbenchPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
		    if ( part instanceof IConsoleView) {
		        final IConsoleView editor = (IConsoleView)part;
		        ISelection sel = editor.getSite().getSelectionProvider().getSelection();
		        if ( sel instanceof TextSelection ) {
		            final TextSelection textSel = (TextSelection)sel;
		            System.out.println(textSel.getText());
		            
		            SearchView searchView = (SearchView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("helpstack");
				    searchView.getTextSearch().setText(textSel.getText());
				    SearchController.search(searchView);
				    searchView.getTextSearch().setFocus();
				    
		        }
		    }else {
		    	SearchView searchView = (SearchView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("helpstack");
		    	searchView.getTextSearch().setFocus();
		    }
		    
		    
		    
		    
		} catch ( Exception ex ) {
		    ex.printStackTrace();
		}    
		
		return null;
	}

}
