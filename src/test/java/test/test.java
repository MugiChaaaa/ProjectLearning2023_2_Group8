/**
package test;

import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

public class test extends JFrame implements ActionListener
{
    JPanel p1 = (JPanel) getContentPane();
    JPanel p2 = new JPanel();
    JPanel p31 = new JPanel();
    JPanel p32 = new JPanel();
    JPanel p33 = new JPanel();
    JPanel p34 = new JPanel();
    JPanel p35 = new JPanel();



    JLabel[] lb = new JLabel[5];
    JTextField txt20 = new JTextField(100);
    JButton b20 = new JButton("Search");
    String imageUrlString = "https://pixelartmaker-data-78746291193.nyc3.digitaloceanspaces.com/image/8e901395c4a3dd4.png";
    URL imageUrl;
    JLabel[] imglbl = new JLabel[5];
    static Socket s;
    test(String title) {
        super(title);
        {
            try {
                PrintWriter output = new PrintWriter(s.getOutputStream());
                imageUrl = new URL(imageUrlString);
                for (int i = 0; i < 5; i++)
                    imglbl[i] = new JLabel(new ImageIcon(ImageIO.read(imageUrl.openStream())));
                p1.setLayout(new GridLayout(6, 1));

                p2.setLayout(new BorderLayout());
                p2.add(txt20, BorderLayout.CENTER);
                p2.add(b20, BorderLayout.EAST);
                b20.addActionListener(this);

                for (int i = 0; i < 5; i++)
                    lb[i] = new JLabel(" ");

                p31.setLayout(new FlowLayout());
                p31.add(imglbl[0]);
                p31.add(lb[0]);
                p32.setLayout(new FlowLayout());
                p32.add(imglbl[1]);
                p32.add(lb[1]);
                p33.setLayout(new FlowLayout());
                p33.add(imglbl[2]);
                p33.add(lb[2]);
                p34.setLayout(new FlowLayout());
                p34.add(imglbl[3]);
                p34.add(lb[3]);
                p35.setLayout(new FlowLayout());
                p35.add(imglbl[4]);
                p35.add(lb[4]);

                p1.add(p2);
                p1.add(p31);
                p1.add(p32);
                p1.add(p33);
                p1.add(p34);
                p1.add(p35);
                //System.out.println("test.test");
                output.println(3);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 720);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        //System.out.println("test1");
        try
        {
            Scanner input = new Scanner(s.getInputStream());
            PrintWriter output = new PrintWriter(s.getOutputStream());
            //System.out.println("test2");
            if(e.getSource().equals(b20))
            {
                System.out.println(txt20.getText());
                output.println(txt20.getText());
                output.flush();
                Music musicList = null;
                ObjectInputStream OIS = new ObjectInputStream(s.getInputStream());
                for (int i = 0; i < 5; i++) {
                    musicList = (Music) OIS.readObject();
                    imglbl[i].setIcon(new ImageIcon(ImageIO.read(new URL(musicList.getThumbnailURL()))));
                    lb[i].setText(musicList.getTitle());
                }
                output.println(musicList.getURL());
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

    public static void main(String[] args)
    {
        try {
            s = new Socket("LEEYOUNGMIN.asuscomm.com", 3838);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new test("test");
    }
}
**/