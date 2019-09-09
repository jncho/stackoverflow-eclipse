package helpstack.views;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import helpstack.controllers.ResultSearchISEController;
import helpstack.intrase.model.QuestionIntraSE;

public class ResultSearchISEView extends ViewPart{

	// Views
	private Composite parent;
	private StackLayout stackLayout;
	private Composite viewQuestion;
	private Composite editQuestion;
	
	// UI view question
	private Browser browserQuestion;
	private Browser browserAnswer;
	private Button btnEdit;
	
	// UI edit question
	private Text textTitle;
	private Text textOwner;
	private Text textQuestion;
	private Text textAnswer;
	
	private Button btnApply;
	private Button btnCancel;
	private Button btnDelete;
	
	// Question reference
	private QuestionIntraSE question;
	

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		
		this.stackLayout = new StackLayout();
		parent.setLayout(stackLayout);
		
		this.viewQuestion = createView(parent);
		this.editQuestion = createEditView(parent);
		
		stackLayout.topControl = this.viewQuestion;
		
		// Controller
		ResultSearchISEController.registerListeners(this);
		
		System.out.println("[LOG-HS] ResultSearchISEView created.");
		
	}
	
	private Composite createView(Composite root) {
		// Composite view
		Composite viewQuestion = new Composite(root, SWT.NONE);
		viewQuestion.setLayout(new GridLayout(1, false));

		SashForm sashForm = new SashForm(viewQuestion, SWT.NONE);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Group grpQuestion = new Group(sashForm, SWT.NONE);
		grpQuestion.setText("Question");
		grpQuestion.setLayout(new FillLayout(SWT.HORIZONTAL));

		browserQuestion = new Browser(grpQuestion, SWT.NONE);

		Group grpAnswer = new Group(sashForm, SWT.NONE);
		grpAnswer.setText("Answer");
		grpAnswer.setLayout(new FillLayout(SWT.HORIZONTAL));

		browserAnswer = new Browser(grpAnswer, SWT.NONE);
		sashForm.setWeights(new int[] { 1, 1 });

		this.btnEdit = new Button(viewQuestion, SWT.NONE);
		this.btnEdit.setText("Edit");
		
		return viewQuestion;
	}
	
	private Composite createEditView(Composite root) {
		// Composite edit
		Composite editQuestion = new Composite(root, SWT.NONE);
		editQuestion.setLayout(new GridLayout(2, false));

		Label lblTitulo = new Label(editQuestion, SWT.NONE);
		lblTitulo.setText("Title* :");

		this.textTitle = new Text(editQuestion, SWT.BORDER);
		textTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblCreador = new Label(editQuestion, SWT.NONE);
		lblCreador.setText("Creator* :");

		this.textOwner = new Text(editQuestion, SWT.BORDER);
		textOwner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		SashForm sashForm1 = new SashForm(editQuestion, SWT.NONE);
		sashForm1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		Group grpQuestion1 = new Group(sashForm1, SWT.NONE);
		grpQuestion1.setText("Question*");
		grpQuestion1.setLayout(new FillLayout(SWT.HORIZONTAL));

		this.textQuestion = new Text(grpQuestion1, SWT.MULTI);

		Group grpAnswer1 = new Group(sashForm1, SWT.NONE);
		grpAnswer1.setText("Answer*");
		grpAnswer1.setLayout(new FillLayout(SWT.HORIZONTAL));

		this.textAnswer = new Text(grpAnswer1, SWT.MULTI);
		sashForm1.setWeights(new int[] { 1, 1 });

		
		// Botonera
		Composite botonera = new Composite(editQuestion, SWT.NONE);
		botonera.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,false,2,1));
		botonera.setLayout(new RowLayout());
		
		this.btnApply = new Button(botonera, SWT.NONE);
		this.btnApply.setText("Apply");
		
		this.btnCancel = new Button(botonera, SWT.NONE);
		this.btnCancel.setText("Cancel");
		
		this.btnDelete = new Button(botonera, SWT.NONE);
		this.btnDelete.setText("Delete");
		
		return editQuestion;
	}
	
	public void updateView() {
		
		String body = ViewTools.prettify(question.getBody());
		String answer = ViewTools.prettify(question.getAnswer());
		
		try {
			browserQuestion.setText(ViewTools.wrapHtml(body));
			browserAnswer.setText(ViewTools.wrapHtml(answer));
		}catch (Exception e) {
			System.err.println(e);
			MessageDialog.openError(getSite().getShell(), "Error getting template", e.getMessage());
		}
		
	}
	
	public void updateView(QuestionIntraSE question) {
		this.question = question;
		updateView();
	}
	
	public void updateEditView() {
		textTitle.setText(question.getTitle());
		textOwner.setText(question.getOwner());
		textQuestion.setText(question.getBody());
		textAnswer.setText(question.getAnswer());
	}

	@Override
	public void setFocus() {
		
		
	}

	public Composite getViewQuestion() {
		return viewQuestion;
	}

	public void setViewQuestion(Composite viewQuestion) {
		this.viewQuestion = viewQuestion;
	}

	public Composite getEditQuestion() {
		return editQuestion;
	}

	public void setEditQuestion(Composite editQuestion) {
		this.editQuestion = editQuestion;
	}

	public Browser getBrowserQuestion() {
		return browserQuestion;
	}

	public void setBrowserQuestion(Browser browserQuestion) {
		this.browserQuestion = browserQuestion;
	}

	public Browser getBrowserAnswer() {
		return browserAnswer;
	}

	public void setBrowserAnswer(Browser browserAnswer) {
		this.browserAnswer = browserAnswer;
	}

	public Button getBtnEdit() {
		return btnEdit;
	}

	public void setBtnEdit(Button btnEdit) {
		this.btnEdit = btnEdit;
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

	public Button getBtnApply() {
		return btnApply;
	}

	public void setBtnApply(Button btnApply) {
		this.btnApply = btnApply;
	}

	public Button getBtnCancel() {
		return btnCancel;
	}

	public void setBtnCancel(Button btnCancel) {
		this.btnCancel = btnCancel;
	}

	public Button getBtnDelete() {
		return btnDelete;
	}

	public void setBtnDelete(Button btnDelete) {
		this.btnDelete = btnDelete;
	}

	public StackLayout getStackLayout() {
		return stackLayout;
	}

	public void setStackLayout(StackLayout stackLayout) {
		this.stackLayout = stackLayout;
	}

	public Composite getParent() {
		return parent;
	}

	public void setParent(Composite parent) {
		this.parent = parent;
	}

	public QuestionIntraSE getQuestion() {
		return question;
	}

	
	
}
