package helpstack.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PlatformUI;

import helpstack.views.SearchCodeDialog;

public class ShowSearchCodeHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		SearchCodeDialog diag = new SearchCodeDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		diag.open();
		return null;
	}
}
