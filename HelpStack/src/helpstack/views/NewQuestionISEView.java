package helpstack.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import helpstack.controllers.NewQuestionISEController;

public class NewQuestionISEView extends ViewPart{
	
	private Button btnPost;
	private Text textTitle;
	private Text textOwner;
	private Text textQuestion;
	private Text textAnswer;

	@Override
	public void createPartControl(Composite parent) {
		
		parent.setLayout(new GridLayout(2, false));
		
		Label lblTitulo = new Label(parent, SWT.NONE);
		lblTitulo.setText("Title* :");
		
		textTitle = new Text(parent, SWT.BORDER);
		textTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblCreador = new Label(parent, SWT.NONE);
		lblCreador.setText("Creator* :");
		
		textOwner = new Text(parent, SWT.BORDER);
		textOwner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		SashForm sashForm = new SashForm(parent, SWT.NONE);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Group grpQuestion = new Group(sashForm, SWT.NONE);
		grpQuestion.setText("Question*");
		grpQuestion.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		textQuestion = new Text(grpQuestion, SWT.MULTI);
		
		Group grpAnswer = new Group(sashForm, SWT.NONE);
		grpAnswer.setText("Answer*");
		grpAnswer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		textAnswer = new Text(grpAnswer, SWT.MULTI);
		sashForm.setWeights(new int[] {1, 1});
		
		btnPost = new Button(parent, SWT.NONE);
		btnPost.setText("Create");
		
		// Controller
		NewQuestionISEController.registerListeners(this);
	}

	@Override
	public void setFocus() {
		
	}

	public Button getBtnPost() {
		return btnPost;
	}

	public Text getTextTitle() {
		return textTitle;
	}

	public void setTextTitle(Text textTitle) {
		this.textTitle = textTitle;
	}

	public Text getTextOwner() {
		return textOwner;
	}

	public void setTextOwner(Text textOwner) {
		this.textOwner = textOwner;
	}

	public Text getTextQuestion() {
		return textQuestion;
	}

	public void setTextQuestion(Text textQuestion) {
		this.textQuestion = textQuestion;
	}

	public Text getTextAnswer() {
		return textAnswer;
	}

	public void setTextAnswer(Text textAnswer) {
		this.textAnswer = textAnswer;
	}

	public void setBtnPost(Button btnPost) {
		this.btnPost = btnPost;
	}

}
