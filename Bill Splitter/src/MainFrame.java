import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private JLabel Title;
    private JTextField guestField;
    private JButton addGuestButton;
    private JTextField nameField;
    private JButton addItemButton;
    private JList list1;
    private JList list2;
    private JTextField costField;

    private Main app = new Main();

    public MainFrame(){
        setContentPane(mainPanel);
        setTitle("Welcome");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


        addGuestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = guestField.getText();
                app.addFriend(name);
            }
        });
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                double cost = Double.parseDouble(costField.getText());
                app.addFood(name, cost);
            }
        });
    }
    public static void main(String[] args){
        MainFrame GUI = new MainFrame();
    }
}
