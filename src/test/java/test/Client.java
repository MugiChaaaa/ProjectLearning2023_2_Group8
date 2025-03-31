package test;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.*;
import java.util.List;

public class Client extends JFrame implements ActionListener {

    // Music related
    private String ID = "";
    private String[] genres = { "J-Pop", "J-Rock", "アニメ", "ポップス", "ロック", "クラシック", "ジャズ", "K-Pop", "演歌・歌謡曲" };
    public Music[] music = new Music[10000];
    private Music selectedMusic = null;
    private Music[] musicArray;

    // Server related
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectInputStream ois;

    // Panel used in the frame
    private JPanel loginpanel;
    private JPanel accountpanel;
    private JPanel homepanel;
    private JPanel suggestpanel;

    // Screen transition uses
    private CardLayout layout;
    private JPanel panel;

    // Login panel component
    private JLabel loginlabel;
    private JLabel idlabel;
    private JLabel pwlabel;
    private JTextField idInput;
    private JPasswordField pwInput;
    private JButton loginbutton;
    private JButton registerbutton;

    // Create account component
    private JLabel accountlabel;
    private JLabel idAccount;
    private JLabel pwAccount;
    private JLabel pwAccount2;
    private JTextField idNewinput;
    private JPasswordField pwNewinput;
    private JPasswordField pwNewinput2;
    private JButton createbutton;
    private JButton backbutton;
    // private JTextField Q_sec_input;

    // Main lobby component
    // private JPanel musicPanel;
    private JLabel welcomelabel;
    private JButton rcmButton;
    private JLabel rcmLabel;
    private JButton filterButton;
    private JPanel musicPanel;
    private int selectedGenre = -1;
    private Music[] filteredMusic;
    private JScrollPane scrollPane;

    // おススメする曲の登録画面の要素
    private JTextField searchTextField;
    private JButton backButton;
    private JButton searchButton;
    private JButton[] musicButton;
    private JLabel[] thumbnailLabel;
    private boolean popup = false;
    // private boolean clicked = false;
    private static final int MAX_RESULTS = 5;
    private JPanel thumbnailPanel;

    public Client() {

        try {
            socket = new Socket("LEEYOUNGMIN.asuscomm.com", 3838);
            System.out.println("Connected to server");
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ois = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Information exchange error: " +
                    e.getMessage());
        }

        // フレームの設定
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 720);
        setTitle("おすすめ曲の登録");

        /*-----------------------------------------------------------------------------------------------------------------------------------------*/

        // ログイン
        // Font font13 = new Font("Serif", Font.PLAIN, 13);
        Font font20 = new Font("Serif", Font.PLAIN, 20);
        // Font font30 = new Font("Serif", Font.PLAIN, 30);

        loginpanel = new JPanel();
        loginpanel.setLayout(null);
        loginpanel.setBackground(Color.WHITE);

        loginlabel = new JLabel("ログイン");
        loginlabel.setFont(new Font("Serif", Font.PLAIN, 50));
        loginlabel.setBounds(420, 90, 400, 60);
        loginpanel.add(loginlabel);

        idlabel = new JLabel("USER ID");
        idlabel.setFont(font20);
        idlabel.setBounds(350, 220, 150, 40);
        // idlabel.setHorizontalAlignment(SwingConstants.LEFT);
        loginpanel.add(idlabel);

        idInput = new JTextField(16);
        idInput.setBounds(520, 220, 220, 40);
        //idInput.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        loginpanel.add(idInput);

        pwlabel = new JLabel("PASSWORD");
        pwlabel.setFont(font20);
        pwlabel.setBounds(350, 280, 150, 40);
        loginpanel.add(pwlabel);

        pwInput = new JPasswordField(16);
        pwInput.setBounds(520, 280, 220, 40);
        //pwInput.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        loginpanel.add(pwInput);

        loginbutton = new JButton("ログイン");
        loginbutton.setFont(new Font("Serif", Font.BOLD, 20));
        loginbutton.setBounds(280, 420, 500, 60);
        loginbutton.setBackground(Color.RED);
        loginbutton.setForeground(Color.WHITE);
        loginbutton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
        loginbutton.setActionCommand("Login");
        loginbutton.addActionListener(this);
        loginpanel.add(loginbutton);

        registerbutton = new JButton("新規アカウント作成");
        registerbutton.setFont(new Font("Serif", Font.BOLD, 20));
        registerbutton.setBounds(280, 490, 500, 60);
        registerbutton.setBackground(Color.GRAY);
        registerbutton.setForeground(Color.WHITE);
        registerbutton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
        registerbutton.setActionCommand("NewAccountPage");
        registerbutton.addActionListener(this);
        loginpanel.add(registerbutton);

        /*-----------------------------------------------------------------------------------------------------------------------------------------*/

        // 新規アカウント作成
        accountpanel = new JPanel();
        accountpanel.setLayout(null);
        accountpanel.setBackground(Color.WHITE);

        accountlabel = new JLabel("新規登録");
        accountlabel.setFont(new Font("Serif", Font.PLAIN, 50));
        accountlabel.setBounds(420, 90, 400, 60);
        accountpanel.add(accountlabel);

        idAccount = new JLabel("USER ID");
        idAccount.setFont(font20);
        idAccount.setBounds(350, 220, 150, 40);
        accountpanel.add(idAccount);

        idNewinput = new JTextField(16);
        idNewinput.setBounds(520, 220, 220, 40);
        accountpanel.add(idNewinput);

        pwAccount = new JLabel("PASSWORD");
        pwAccount.setFont(font20);
        pwAccount.setBounds(350, 280, 150, 40);
        accountpanel.add(pwAccount);

        pwNewinput = new JPasswordField(16);
        pwNewinput.setBounds(520, 280, 220, 40);
        accountpanel.add(pwNewinput);

        pwAccount2 = new JLabel("PASSWORD2");
        pwAccount2.setFont(font20);
        pwAccount2.setBounds(350, 340, 150, 40);
        accountpanel.add(pwAccount2);

        pwNewinput2 = new JPasswordField(16);
        pwNewinput2.setBounds(520, 340, 220, 40);
        accountpanel.add(pwNewinput2);

        createbutton = new JButton("確定");
        createbutton.setFont(new Font("Serif", Font.BOLD, 20));
        createbutton.setBounds(280, 420, 500, 60);
        createbutton.setBackground(Color.RED);
        createbutton.setForeground(Color.WHITE);
        createbutton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
        createbutton.setActionCommand("CreateAccount");
        createbutton.addActionListener(this);
        accountpanel.add(createbutton);

        backbutton = new JButton("戻る");
        backbutton.setFont(new Font("Serif", Font.BOLD, 20));
        backbutton.setBounds(280, 490, 500, 60);
        backbutton.setBackground(Color.GRAY);
        backbutton.setForeground(Color.WHITE);
        backbutton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
        backbutton.setActionCommand("BacktoLogin");
        backbutton.addActionListener(this);
        accountpanel.add(backbutton);

        /*-----------------------------------------------------------------------------------------------------------------------------------------*/

        // Main Lobby settings

        homepanel = new JPanel();
        homepanel.setLayout(null);
        homepanel.setBackground(Color.WHITE);

        welcomelabel = new JLabel("ようこそ " + ID + " さん!");
        welcomelabel.setFont(new Font("Serif", Font.BOLD, 30));
        welcomelabel.setBounds(100, 50, 400, 40);
        homepanel.add(welcomelabel);

        rcmButton = new JButton("\u271A");
        rcmButton.setBounds(100, 150, 50, 50);
        rcmButton.setFont(new Font("Serif", Font.PLAIN, 15));
        rcmButton.setBackground(Color.RED);
        rcmButton.setForeground(Color.WHITE);
        rcmButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
        rcmButton.setActionCommand("Recommend");
        rcmButton.addActionListener(this);
        homepanel.add(rcmButton);

        rcmLabel = new JLabel("曲をおすすめする");
        rcmLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        rcmLabel.setBounds(160, 150, 200, 50);
        homepanel.add(rcmLabel);

        filterButton = new JButton("ジャンル選択");
        filterButton.setBounds(850, 150, 150, 50);
        filterButton.setFont(new Font("Serif", Font.BOLD, 13));
        filterButton.setBackground(Color.RED);
        filterButton.setForeground(Color.WHITE);
        filterButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
        filterButton.setActionCommand("Filter");
        filterButton.addActionListener(this);
        homepanel.add(filterButton);

        /*-----------------------------------------------------------------------------------------------------------------------------------------*/

        // 登録画面の設定

        suggestpanel = new JPanel();
        suggestpanel.setLayout(null);
        suggestpanel.setBackground(Color.WHITE);
        suggestpanel.setVisible(true);

        JLabel label = new JLabel("おすすめ曲の登録");
        label.setBounds(100, 50, 400, 40);
        Font labelFont = new Font(Font.SANS_SERIF, Font.BOLD, 30);
        label.setFont(labelFont);
        suggestpanel.add(label);

        backButton = new JButton("戻る");
        backButton.setBounds(880, 50, 100, 40);
        backButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
        backButton.setActionCommand("BackMainLobby");
        backButton.addActionListener(this);
        suggestpanel.add(backButton);

        searchTextField = new JTextField("Search here...");
        searchTextField.setBounds(100, 150, 720, 40);
        suggestpanel.add(searchTextField);

        // Textfield をクリアする
        searchTextField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                searchTextField.setText("");
            }
        });

        searchTextField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // searchMusicFromServer(ID);
                }
            }
        });

        searchButton = new JButton("\u2192");
        searchButton.setBounds(880, 150, 100, 40);
        searchButton.setBackground(Color.RED);
        searchButton.setForeground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
        searchButton.setActionCommand("Search");
        searchButton.addActionListener(this);
        suggestpanel.add(searchButton);

        musicButton = new JButton[MAX_RESULTS];
        thumbnailLabel = new JLabel[MAX_RESULTS];
        for (int i = 0; i < MAX_RESULTS; i++) {
            musicButton[i] = new JButton();
            musicButton[i].setBounds(100, 240 + i * 80, 880, 60);
            musicButton[i].setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));
            musicButton[i].setBackground(Color.WHITE);
            musicButton[i].setLayout(new BorderLayout());

            thumbnailPanel = new JPanel();
            thumbnailPanel.setPreferredSize(new Dimension(50, 50));
            thumbnailLabel[i] = new JLabel();
            thumbnailLabel[i].setPreferredSize(new Dimension(50, 50));
            thumbnailPanel.add(thumbnailLabel[i]);
            musicButton[i].add(thumbnailPanel, BorderLayout.WEST);
            musicButton[i].setActionCommand("Suggest");
            musicButton[i].addActionListener(this);

            // Add mouse listener to the music button
            if (!popup) {
                musicButton[i].addMouseListener(new MouseAdapter() {

                    private int originalWidth;
                    private int originalHeight;
                    private int originalX;
                    private int originalY;

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        // マウス入ってからサイズチェンジ
                        JButton button = (JButton) e.getSource();

                        // if (!popup && clicked) {
                        originalWidth = button.getWidth();
                        originalHeight = button.getHeight();
                        originalX = button.getX();
                        originalY = button.getY();

                        button.setBounds(originalX - 5, originalY - 5, originalWidth + 10, originalHeight + 10);
                        // }

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        // マウス出たらサイズチェンジ

                        JButton button = (JButton) e.getSource();
                        // if (!popup && clicked) {
                        button.setBounds(originalX, originalY, originalWidth, originalHeight);
                        // }
                    }

                });
            }

            suggestpanel.add(musicButton[i]);

        }

        // 画面遷移
        panel = new JPanel();
        layout = new CardLayout();
        panel.setLayout(layout);
        panel.add(loginpanel, "Login");
        panel.add(accountpanel, "Account");
        panel.add(homepanel, "Home");
        panel.add(suggestpanel, "Suggest");
        layout.show(panel, "Login");

        add(panel);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();
        int musicNum = 0;
        switch (cmd) {
            case "Login":
                boolean logincheck = authentication(ID = idInput.getText(), String.valueOf(pwInput.getPassword()));
                //ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                if (logincheck) {
                    try {
                        Scanner input = new Scanner(socket.getInputStream());
                        welcomelabel.setText("ようこそ " + ID + " さん");
                        for(int i=0;i<music.length;i++){
                            music[i] = new Music();
                        }
                        musicNum = Integer.parseInt(input.nextLine());
                        System.out.println("musicNum: " + musicNum);
                        //System.out.println("print music");
                        for (int i = musicNum-1; i >= 0; i--) {
                            music[i] = (Music) ois.readObject();
                            //System.out.println("music: \n" + ow.writeValueAsString(music[i]));
                        }

                        //ois.readObject();

                        System.out.println("received music");

                        createMusicPanel();
                        layout.show(panel, "Home");

                    } catch (Exception ex) {
                        System.out.println("Error getting music object :" + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "IDまたはパスワードが間違っています。", "エラー", JOptionPane.ERROR_MESSAGE);
                    pwInput.setText("");
                    idInput.setText("");
                }

                break;
            case "NewAccountPage":

                layout.show(panel, "Account");
                break;
            case "CreateAccount":

                if (idNewinput.getText().equals("") || String.valueOf(pwNewinput.getPassword()).equals("") || String.valueOf(pwNewinput2.getPassword()).equals("")) {

                    JOptionPane.showMessageDialog(panel, "IDまたはパスワードが入力されていません。", "エラー", JOptionPane.ERROR_MESSAGE);

                } else if (!String.valueOf(pwNewinput.getPassword()).equals(String.valueOf(pwNewinput2.getPassword()))) {

                    JOptionPane.showMessageDialog(panel, "パスワードが一致しません。", "エラー", JOptionPane.ERROR_MESSAGE);

                } else {
                    if (setNewAccount(idNewinput.getText(), String.valueOf(pwNewinput.getPassword()))) {

                        JOptionPane.showMessageDialog(panel, "アカウントが作成されました。", "成功", JOptionPane.INFORMATION_MESSAGE);

                        layout.show(panel, "Login");
                    } else {
                        JOptionPane.showMessageDialog(panel, "アカウントの作成に失敗しました。", "エラー", JOptionPane.ERROR_MESSAGE);
                    }
                }
                idNewinput.setText("");
                pwNewinput.setText("");
                pwNewinput2.setText("");
                break;
            case "BacktoLogin":
                layout.show(panel, "Login");
                break;
            case "Filter":
                showFilterDialog();
                break;
            case "Recommend":
                layout.show(panel, "Suggest");
                break;
            case "BackMainLobby":
                layout.show(panel, "Home");
                for (int i = 0; i < 5; i++) {
                    musicButton[i].setText("");
                    musicButton[i].setIcon(null);
                    thumbnailLabel[i].setIcon(null);
                }

                break;
            case "Search":
                searchMusic(ID);
                break;
            case "Suggest":

                JButton mbutton = (JButton) e.getSource();

                int originalWidth = mbutton.getWidth();
                int originalHeight = mbutton.getHeight();
                int originalX = mbutton.getX();
                int originalY = mbutton.getY();
                mbutton.setBounds(originalX + 5, originalY + 5, originalWidth - 10, originalHeight - 10);

                int index = Arrays.asList(musicButton).indexOf(mbutton);
                selectedMusic = null;
                if (musicArray != null) {
                    selectedMusic = musicArray[index];
                }

                showDialogPanel(selectedMusic, index);

                break;
            default:
                break;

        }

    }

    private void createMusicPanel() {

        if (musicPanel != null) {

            homepanel.remove(scrollPane);
        }

        musicPanel = new JPanel();
        musicPanel.setLayout(new BoxLayout(musicPanel, BoxLayout.Y_AXIS));
        musicPanel.setBackground(Color.WHITE);
        // musicPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0,
        // Color.WHITE));
        // musicPanel.setBounds(100, 200, 900, 360);

        // musicPanel.removeAll();
        Music[] musicToShow;

        if (filteredMusic != null && filteredMusic.length > 0) {
            musicToShow = filteredMusic; // Use the filtered music
            System.out.println("filteredMusic ");
        } else {
            musicToShow = music;
            System.out.println("music ");// Use the original unfiltered music
        }

        if (musicToShow != null && musicToShow.length > 0) {
            for (Music song : musicToShow) {

                if (song == null) {
                    continue;
                }

                JButton songButton = new JButton();
                songButton.setLayout(new BorderLayout());
                songButton.setPreferredSize(new Dimension(250, 100));
                songButton.setBackground(Color.WHITE);

                JLabel songthumbnail = new JLabel();
                JLabel songlabel = new JLabel();
                songlabel.setSize(900, 100);
                JLabel songname = new JLabel();

                String songt = (song != null) ? song.getTitle() : "";
                String songth = (song != null) ? song.getThumbnailURL() : "";
                int g = (song != null) ? song.getGenre() : -1;

                ImageIcon songicon = createImageIcon(songth);
                songthumbnail.setIcon(songicon);
                songButton.add(songthumbnail, BorderLayout.WEST);

                songlabel.setLayout(new GridLayout(1, 4));
                songlabel.setBackground(Color.WHITE);
                songlabel.add(new JLabel("  "));

                songname.setText("   " + songt);
                songname.setHorizontalAlignment(SwingConstants.CENTER);
                songname.setFont(new Font("Serif", Font.BOLD, 20));
                songlabel.add(songname);

                String songg = "";
                if (song != null && g >= 0 && g < genres.length) {
                    songg = genres[g];
                }
                JLabel songgenre = new JLabel(songg);
                songgenre.setFont(new Font("Serif", Font.PLAIN, 15));
                songlabel.add(new JLabel("  "));
                songgenre.setHorizontalAlignment(SwingConstants.CENTER);
                songlabel.add(songgenre);
                songButton.add(songlabel, BorderLayout.CENTER);
                songButton.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));

                musicPanel.add(songButton);

            }

        } else {
            JLabel noMusicLabel = new JLabel("No music available.");
            noMusicLabel.setFont(new Font("Serif", Font.PLAIN, 16));
            noMusicLabel.setHorizontalAlignment(SwingConstants.CENTER);
            musicPanel.add(noMusicLabel);
        }

        scrollPane = new JScrollPane(musicPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        scrollBar.setUnitIncrement(20); // Set the scroll unit increment (small scroll amount)
        scrollBar.setBlockIncrement(20);
        scrollBar.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(100, musicPanel.getWidth()));
        scrollPane.setBounds(100, 240, 900, 360);
        // scrollPane.setBorder(BorderFactory.createEmptyBorder());
        // homepanel.add(musicPanel);
        homepanel.add(scrollPane);

        scrollPane.revalidate(); // Revalidate the home panel to update its layout
        scrollPane.repaint();

    }

    private void applyGenreFilter(int genre) {
        List<Music> filteredList = new ArrayList<>();

        if (music != null) {
            for (Music song : music) {
                if (song != null && song.getGenre() == genre) {
                    filteredList.add(song);
                }
            }
        }

        filteredMusic = filteredList.toArray(new Music[0]);
        System.out.println("Filtered list: " + filteredList);
        System.out.println("Filtered music array: " + Arrays.toString(filteredMusic));

        // homepanel.remove(musicPanel);
        createMusicPanel();
    }

    private void showFilterDialog() {

        for (Component component : homepanel.getComponents()) {
            component.setEnabled(false);
            //scrollPane.setEnabled(false);
        }

        JDialog dialog = new JDialog();
        dialog.setUndecorated(true);
        dialog.setLayout(null);
        Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
        dialog.getRootPane().setBorder(border);
        
        dialog.setSize(300, 200);

        Window window = SwingUtilities.getWindowAncestor(homepanel);
        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                dialog.setLocationRelativeTo(homepanel);
            }
        });

        JLabel messageLabel = new JLabel("聞きたいジャンルは？");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        messageLabel.setBounds(0, 10, 300, 45);
        dialog.add(messageLabel);

        Box genreBox = Box.createVerticalBox();
        genreBox.setBounds(0, 50, 300, 110);

        // Add genre checkboxes
        int rows = 3;
        int columns = 3;
        JRadioButton[] radioButton = new JRadioButton[9];
        for (int i = 0; i < rows; i++) {
            Box rowBox = Box.createHorizontalBox();

            for (int j = 0; j < columns; j++) {
                int index = i * columns + j;
                if (index < genres.length) {
                    radioButton[index] = new JRadioButton(genres[index]);
                    Font genreFont = new Font(Font.SANS_SERIF, 10, 15);
                    radioButton[index].setFont(genreFont);
                    rowBox.add(radioButton[index]);
                }
            }

            genreBox.add(rowBox);
        }

        dialog.add(genreBox);

        JButton okButton = new JButton("確定");
        okButton.setBounds(60, 150, 90, 30);
        okButton.setBackground(Color.RED);
        okButton.setForeground(Color.WHITE);
        okButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedGenre = -1;
                for (int i = 0; i < genres.length; i++) {
                    if (radioButton[i].isSelected()) {
                        System.out.println("Genre chosen: " + genres[i]);
                        selectedGenre = i;
                        break;
                    }
                }
                System.out.println("Selected genre index: " + selectedGenre);

                if (selectedGenre == -1) {
                    filteredMusic = null; // Reset the filtered music
                } else {
                    applyGenreFilter(selectedGenre);
                }
                dialog.dispose();
                for (Component component : homepanel.getComponents()) {
                    component.setEnabled(true);
                    //scrollPane.setEnabled(true);
                }
                createMusicPanel();

            }
        });
        dialog.add(okButton);

        JButton cancelButton = new JButton("取り消し");
        cancelButton.setBounds(160, 150, 90, 30);
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                for (Component component : homepanel.getComponents()) {
                    component.setEnabled(true);
                    //scrollPane.setEnabled(true);
                }
            }
        });
        dialog.add(cancelButton);

        dialog.setAlwaysOnTop(true);
        dialog.setLocationRelativeTo(homepanel);
        dialog.setVisible(true);

    }

    private void showDialogPanel(Music music, int num) {

        popup = true;

        String[] options = { "確定", "取り消し" };
        for (Component component : suggestpanel.getComponents()) {
            component.setEnabled(false);
        }

        JDialog dialog = new JDialog();
        dialog.setUndecorated(true);
        dialog.setLayout(null);
        dialog.setSize(350, 400);
        Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
        dialog.getRootPane().setBorder(border);

        Window window = SwingUtilities.getWindowAncestor(suggestpanel);
        window.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentMoved(ComponentEvent e) {
                dialog.setLocationRelativeTo(suggestpanel);
            }
        });

        JLabel messageLabel = new JLabel("この曲をおすすめしますか？");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        messageLabel.setBounds(20, 10, 300, 45);
        dialog.add(messageLabel);

        // Create an array to hold the genre checkboxes

        Box genreBox = Box.createVerticalBox();
        genreBox.setBounds(20, 70, 310, 110);
        Border genreBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
        genreBox.setBorder(
                BorderFactory.createTitledBorder(genreBorder, "ジャンルを選択してください", TitledBorder.CENTER, TitledBorder.TOP));

        // Add genre checkboxes
        int rows = 3;
        int columns = 3;

        for (int i = 0; i < rows; i++) {
            Box rowBox = Box.createHorizontalBox();

            for (int j = 0; j < columns; j++) {
                int index = i * columns + j;
                if (index < genres.length) {
                    JRadioButton radioButton = new JRadioButton(genres[index]);
                    Font genreFont = new Font(Font.SANS_SERIF, 10, 15);
                    radioButton.setFont(genreFont);
                    rowBox.add(radioButton);
                }
            }

            genreBox.add(rowBox);
        }

        dialog.add(genreBox);

        JTextArea explanation = new JTextArea("紹介文を書いてください...");
        explanation.setForeground(Color.GRAY);
        explanation.setBounds(20, 200, 310, 120);
        explanation.setLineWrap(true);
        explanation.setWrapStyleWord(true);
        explanation.setAlignmentY(JTextArea.TOP_ALIGNMENT);
        explanation.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

                explanation.setText("");
                explanation.setForeground(Color.BLACK);

            }

            @Override
            public void focusLost(FocusEvent e) {
                if (explanation.getText().isEmpty()) {
                    explanation.setForeground(Color.LIGHT_GRAY);
                    explanation.setText("紹介文を書いてください");
                }
            }
        });
        dialog.add(explanation);

        JPanel buttonPanel = new JPanel(null);
        buttonPanel.setBounds(70, 340, 210, 30);

        int buttonWidth = 90;
        int buttonHeight = 30;
        int buttonSpacing = 10;
        int x = 10;
        int y = (buttonPanel.getHeight() - buttonHeight) / 2;

        for (int i = 0; i < options.length; i++) {
            JButton button = new JButton(options[i]);
            button.setBounds(x + (buttonWidth + buttonSpacing) * i, y, buttonWidth, buttonHeight);
            if (options[i].equals("確定")) {
                button.setBackground(Color.RED);
                button.setForeground(Color.WHITE);
                button.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
            } else {
                button.setBackground(Color.WHITE);
                button.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
            }
            buttonPanel.add(button);

            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    String selectedGenre = "";
                    int genrenum = 0;
                    // Handle button click
                    if (button.getText().equals("確定")) {
                        // ’確定’の場合

                        // Get the selected checkbox string

                        Component[] components = genreBox.getComponents();
                        for (Component component : components) {
                            if (component instanceof Box) {
                                Box rowBox = (Box) component;
                                Component[] checkBoxes = rowBox.getComponents();
                                for (Component checkBox : checkBoxes) {
                                    if (checkBox instanceof JCheckBox) {
                                        JCheckBox genreCheckBox = (JCheckBox) checkBox;
                                        if (genreCheckBox.isSelected()) {
                                            selectedGenre = genreCheckBox.getText();
                                            switch (selectedGenre) {
                                                case "J-Pop":
                                                    genrenum = 1;
                                                    break;
                                                case "J-Rock":
                                                    genrenum = 2;
                                                    break;
                                                case "アニメ":
                                                    genrenum = 3;
                                                    break;
                                                case "ポップス":
                                                    genrenum = 4;
                                                    break;
                                                case "ロック":
                                                    genrenum = 5;
                                                    break;
                                                case "クラシック":
                                                    genrenum = 6;
                                                    break;
                                                case "ジャズ":
                                                    genrenum = 7;
                                                    break;
                                                case "K-Pop":
                                                    genrenum = 8;
                                                    break;
                                                case "演歌・歌謡曲":
                                                    genrenum = 9;
                                                    break;
                                                default:

                                                    break;

                                            }
                                        }
                                    }
                                }
                                if (!selectedGenre.isEmpty()) {
                                    break;
                                }
                            }
                        }
                        String note = explanation.getText();
                        if (note.equals("紹介文を書いてください..."))
                        {
                            note = "";
                        }
                        out.println("---c-o-n-f-i-r-m---");
                        registerMusic(music, ID, num, genrenum, note);
                    } else {
                        // "取り消し" の場合
                        out.println("false");
                    }
                    popup = false;
                    for (Component component : suggestpanel.getComponents()) {
                        component.setEnabled(true);
                    }
                    dialog.dispose();
                }

            });
        }

        dialog.add(buttonPanel);
        //dialog.setAlwaysOnTop(true);
        dialog.setLocationRelativeTo(suggestpanel);
        dialog.setVisible(true);
    }

    private boolean authentication(String id, String pw) {

        String response = "";

        try {
            out.println("1");
            out.println(id);
            System.out.println("Send id login: " + id);

            out.println(pw);
            System.out.println("Send pw login: " + pw);

            response = in.readLine();
        } catch (IOException e) {
            System.out.println("Login info exchange error: " + e.getMessage());
        }

        return response.equals("Success");

    }

    private boolean setNewAccount(String id, String pw) {

        String response = "";

        try {
            out.println("2");
            out.println(id);
            System.out.println("Send id register: " + id);
            out.println(pw);
            System.out.println("Send pw register: " + pw);
            response = in.readLine();
            System.out.println("Response from server: " + response);
        } catch (IOException e) {
            System.out.println("Register account info exchange error: " + e.getMessage());
        }

        return response.equals("Success");

    }

    private void registerMusic(Music music, String name, int num, int genre, String notes) {
        // Send music object, genre and notes to server
        //out.println("Register");
        out.println(num);
        System.out.println("Send music title to server" + num);
        out.println(name);
        out.println(genre);
        out.println(notes);
        //System.out.println("Send music genre " + genre + " and notes " + notes + " to server);

        try {
            String ok = in.readLine();
            System.out.print(ok);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Send 'like' to server
        out.println("4");
        out.println(music.getTitle());

    }

    private ImageIcon createImageIcon(String thumbnailURL) {
        if (thumbnailURL != null && !thumbnailURL.isEmpty()) {
            try {
                URL url = new URL(thumbnailURL);
                Image image = ImageIO.read(url);
                return new ImageIcon(image.getScaledInstance(90, 90, Image.SCALE_SMOOTH));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("URLからのサムの読み込みエラー: " + e.getMessage());
            }
        }
        return null;

    }

    private void searchMusic(String name) {
        try {

            // clicked = true;

            String searchTerm = searchTextField.getText();
            musicArray = new Music[5];
            out.println("3");
            out.println(searchTerm);
            System.out.println("Send search request");
            //out.println(name);
            //System.out.println("Send user id");

            // String response = in.readLine();
            // System.out.println("サーバーからの反応: " + response);
            // Clear previous search results
            for (int i = 0; i < 5; i++) {
                musicButton[i].setText("");
                musicButton[i].setIcon(null);
            }
            ois = new ObjectInputStream(socket.getInputStream());
            for (int i = 0; i < 5; i++) {

                Object obj = ois.readObject();
                System.out.println("Received object");
                if (obj instanceof Music) {
                    musicArray[i] = (Music) obj; // Assign the received Music object to the array
                } else {
                    System.out.println("Received object is not of type Music");
                }
            }

            int i =0;
            for (Music music : musicArray) {
                String musicInfo = music.getTitle();
                String thumbnailurl = music.getThumbnailURL();

                if (musicInfo != null && thumbnailurl != null) {
                    musicButton[i].setText(musicInfo);
                    ImageIcon imageIcon = createImageIcon(thumbnailurl);
                    thumbnailLabel[i].setIcon(imageIcon);
                    i++;
                } else {
                    break; // Break loop if no more search results
                }

            }

        } catch (Exception e) {
            System.out.println("Search error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            /*
             * try {
             * UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
             * } catch (Exception e) {
             * System.out.println(e.getMessage());
             * }
             */

            Client client = new Client();
            client.setVisible(true);

        });
    }

}
