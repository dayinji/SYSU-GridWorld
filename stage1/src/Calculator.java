import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
public class Calculator {
	public static void main(String[] args) {
		Calcu ca = new Calcu();
		ca.init();
	}
}
class Calcu {
	private java.util.List<JButton> btList = new ArrayList<JButton>();
	private java.util.List<JTextField> tfList = new ArrayList<JTextField>();
	private String[] bt = {"", "", "",  "=", "", "-", "+", "*", "/", "OK"};
	private JFrame f;
	private MyListener myLinstener = new MyListener();
	private Font font = new Font("隶书",Font.BOLD,15);
              private int frameWidth = 600,  frameHeight = 300;


	public void init() {
		f = new JFrame("13331365-Calculator!!");
       	f.setLayout(new GridLayout(2,5,1,1));
       	for (int i = 0 ; i < 10 ; i++) {
       		if (i == 0 || i == 2) {
       			JTextField myTF = new JTextField();
       			myTF.setFont(font);
       			myTF.addKeyListener(new KeyAdapter(){  
            		public void keyTyped(KeyEvent e) {  
                		int keyChar = e.getKeyChar();                 
                		if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){  
                      
                		}else{  
                			// if input != [0~9], delete it!
                    		e.consume();
                		}  
            		}  
        		});  
        		tfList.add(myTF);
       			f.add(myTF);
       			continue;
       		}
       		JButton myBt = new JButton(bt[i]);
       		myBt.setFont(font);
       		btList.add(myBt);
       		if (i == 1 || i == 3 || i == 4)
       			myBt.setEnabled(false);
       		myBt.addActionListener(myLinstener);
       		f.add(myBt);
       	}
       	f.setSize(frameWidth,frameHeight);
       	f.setVisible(true);
	}

	private class MyListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
                                          // 取得事件源
			JButton btn=(JButton) e.getSource();  
			switch(btn.getText()) {
				case "*":
				case "-":
				case "/":
				case "+":
					btList.get(0).setText(btn.getText());
					break;
				case "OK":
					String strA = tfList.get(0).getText();
					String strB = tfList.get(1).getText();
					if (strA.equals("") || strB.equals(""))  {
					   break;
                                                                      }
					int a = Integer.parseInt(strA);
					int b = Integer.parseInt(strB);
					handleOk(a, b);
					break;
			}

		}
  	};
              private void handleOk(int a, int b) {
                  if (btList.get(0).getText().equals("")) {
                    return;
                  }
                  if (btList.get(0).getText().equals("*")) {
                    btList.get(2).setText(Integer.toString(a*b));
                  }
                  else if (btList.get(0).getText().equals("/")) {
                     double c = a;
                     btList.get(2).setText(Double.toString(c/b));
                    }
                   else if (btList.get(0).getText().equals("+")) {
                      btList.get(2).setText(Integer.toString(a+b));
                   }
                   else if (btList.get(0).getText().equals("-")) {
                      btList.get(2).setText(Integer.toString(a-b));
                    }
              }
}