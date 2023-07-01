import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private JLabel Title;
    private JTextField guestField;
    private JButton addGuestButton;
    private JTextField nameField;
    private JButton addItemButton;
    private JList friendList;
    private JList foodList;
    private JTextField costField;

    private Main app = new Main();

    public MainFrame(){
        setContentPane(mainPanel);
        setTitle("Welcome");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


        // ADD GUEST BUTTON
        addGuestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = guestField.getText();
                app.addFriend(name);
                guestField.setText("");
                friendList.setListData(app.getFriends());
            }
        });
        // ADD FOOD ITEM BUTTON
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                double cost = Double.parseDouble(costField.getText());
                app.addFood(name, cost);
                nameField.setText("");
                costField.setText("");
                foodList.setListData(app.getFoods());
            }
        });
    }
    public static void main(String[] args){
        MainFrame GUI = new MainFrame();

    }
}
