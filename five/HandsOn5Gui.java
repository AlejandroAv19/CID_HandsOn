package handson.five;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HandsOn5Gui extends JFrame {

    private HandsOn5 myAgent;

    private JTextField x1_field;
    private JTextField x2_field;

    HandsOn5Gui(HandsOn5 a){
        super(a.getLocalName());

        myAgent = a;

        JPanel canvas = new JPanel();
        canvas.setLayout(new GridLayout(4, 4));
        canvas.add(new JLabel("Value of X1:"));
        x1_field = new JTextField(7);
        canvas.add(new JLabel("Value of X2:"));
        x2_field = new JTextField(7);
        canvas.add(x1_field);
        canvas.add(x2_field);

        getContentPane().add(canvas, BorderLayout.CENTER);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    String _x1 = x1_field.getText().trim();
                    String _x2 = x2_field.getText().trim();
                    myAgent.receiveXValues(Double.parseDouble(_x1),Double.parseDouble(_x2), a);
                    x1_field.setText("");
                    x2_field.setText("");
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(HandsOn5Gui.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } );
        canvas.add(confirmButton);
        getContentPane().add(canvas, BorderLayout.SOUTH);
        addWindowListener(new	WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                myAgent.doDelete();
            }
        } );
        setResizable(false);
    }

    public void showGui() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int)screenSize.getWidth() / 2;
        int centerY = (int)screenSize.getHeight() / 2;
        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
        super.setVisible(true);
    }
}
