package hci.viewer;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import hci.util.Point;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class LabelBox extends JTextField implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dimension size;
	
	public LabelBox(JPanel parent){
		super(20);
		parent.setLayout(null);
		addActionListener(this);
		setEditable(true);
		setVisible(false);
		parent.add(this);
		size = getPreferredSize();
		System.out.println("Added the TXT field to the panel");
		repaint();
		
	}
	
	
	public void setPosition(Point P) {
		
		setBounds(P.getX(), P.getY(), size.width, size.height);
		
		
	}
	
	public void show(Point P){
		
		setPosition(P);
		setVisible(true);
		
	}
	
	public void hide(){
		
		setVisible(false);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
