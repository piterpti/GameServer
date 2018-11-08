package elukasik.pl.view.component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class ClickableField extends JLabel {

	private static final long serialVersionUID = 1L;

	
	public ClickableField(int x, int y, String txt) {
		super(txt);
		setBorder(BorderFactory.createBevelBorder(1));
	}

}
