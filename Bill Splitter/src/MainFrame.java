import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private JTextField guestField;
    private JButton addGuestButton;
    private JTextField nameField;
    private JButton addItemButton;
    private JList friendList;
    private JList foodList;
    private JFormattedTextField costField;
    private JButton nextBtn;
    private JPanel secondPanel;
    private JTable table;
    private JLabel title;
    private JScrollPane f;

    private Main app = new Main();

    public void startup(JPanel frame){
        setContentPane(frame);
        setTitle("Welcome");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public MainFrame(){
        startup(mainPanel);


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
                try{
                    double cost = Double.parseDouble(costField.getText());
                    app.addFood(name, cost);
                    nameField.setText("");
                    costField.setText("");
                    foodList.setListData(app.getFoods());
                } catch(NumberFormatException p){
                    System.out.println("doubles only");
                }
            }
        });
        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startup(secondPanel);

                String[][] data = new String[app.getFriends().length][3];
                String[] header = {"Name", "$$$", "Ate:"};
                int count = 0;
                for(Friend f: app.getFriendObjects()){
                    data[count][0] = f.getName();
                    data[count][1] = Double.toString(f.getDebt());
                    data[count][2] = f.getFoods().toString();
                    count++;
                }
                table = new JTable(data, header);
            }

        });
    }
    public static void main(String[] args){
        MainFrame GUI = new MainFrame();

    }
}
