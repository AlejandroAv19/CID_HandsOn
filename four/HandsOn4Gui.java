package handson.four;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HandsOn4Gui extends JFrame {

    private HandsOn4 myAgent;

    private JTextField x_field;

    HandsOn4Gui(HandsOn4 a){
        super(a.getLocalName());

        myAgent = a;

        JPanel canvas = new JPanel();
        canvas.setLayout(new GridLayout(2, 2));
        canvas.add(new JLabel("Value of X:"));
        x_field = new JTextField(15);
        canvas.add(x_field);

        getContentPane().add(canvas, BorderLayout.CENTER);

        JButton addButton = new JButton("Add");
        addButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    String _x = x_field.getText().trim();
                    myAgent.update_x(Double.parseDouble(_x), a);
                    x_field.setText("");
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(HandsOn4Gui.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } );
        canvas.add(addButton);
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
