import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class test extends JFrame implements ActionListener
{
    JPanel p1 = (JPanel) getContentPane();
    JPanel p20 = new JPanel();
    JPanel p21 = new JPanel();
    JPanel p22 = new JPanel();
    JPanel p23 = new JPanel();
    JPanel p24 = new JPanel();
    JPanel p25 = new JPanel();
    JLabel lb21 = new JLabel("None");
    JLabel lb22 = new JLabel("None");
    JLabel lb23 = new JLabel("None");
    JLabel lb24 = new JLabel("None");
    JLabel lb25 = new JLabel("None");
    JTextField txt20 = new JTextField(100);
    JButton b20 = new JButton("Search");
    test(String title) {
        super(title);

        String imageUrlString = "https://i.ytimg.com/vi/oZ2hR-Jjsnk/default.jpg";
        URL imageUrl;

        {
            try {
                imageUrl = new URL(imageUrlString);
                InputStream inputStream = imageUrl.openStream();
                ImageIcon imageIcon = new ImageIcon(ImageIO.read(inputStream));
                JLabel imglbl = new JLabel(imageIcon);



                p1.setLayout(new GridLayout(6, 1));

                p20.setLayout(new BorderLayout());
                p20.add(txt20, BorderLayout.CENTER);
                p20.add(b20, BorderLayout.EAST);
                b20.addActionListener(this);

                imageUrlString = "https://i.ytimg.com/vi/oZ2hR-Jjsnk/default.jpg";
                p21.setLayout(new FlowLayout());
                p21.add(imglbl);
                p21.add(lb21);

                imageUrlString = "https://i.ytimg.com/vi/oZ2hR-Jjsnk/default.jpg";
                p22.setLayout(new FlowLayout());
                p22.add(imglbl);
                p22.add(lb22);

                p23.setLayout(new FlowLayout());
                p23.add(imglbl);
                p23.add(lb23);

                p24.setLayout(new FlowLayout());
                p24.add(imglbl);
                p24.add(lb24);

                p25.setLayout(new FlowLayout());
                p25.add(imglbl);
                p25.add(lb25);

                p1.add(p20);
                p1.add(p21);
                p1.add(p22);
                p1.add(p23);
                p1.add(p24);
                p1.add(p25);

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setVisible(true);
    }

    public void printVideoLists()
    {
        Search search = new Search();
        lbl21.setText();
    }
    public void ActionPerformed(ActionEvent e)
    {
        try
        {
            if(e.getSource() == b20)
            {
                printVideoLists();
            }
            else
            {
                System.out.println("Error");
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
    }

    public static void main(String args[])
    {
        new test("test");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
