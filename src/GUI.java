import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;

public class GUI extends JFrame {


    GameBoard gameScreen;
    //creates new gameScreen
    public GUI() {
        initComponents();
        init();
    }

    //initializes the game
    private void init() {
        gameScreen = new GameBoard(jPanelMain.getWidth(), jPanelMain.getHeight());
        jPanelMain.add(gameScreen);
    }

    private void initComponents() {

        jPanelMain = new javax.swing.JPanel();
        jMenuBar_Main = new javax.swing.JMenuBar();
        jMenu_Game = new javax.swing.JMenu();
        jMenuItem_Restart = new javax.swing.JMenuItem();
        jMenuItem_Undo = new javax.swing.JMenuItem();
        jMenuItem_Close = new javax.swing.JMenuItem();
        jMenu_File = new javax.swing.JMenu();
        jMenuItem_Save = new javax.swing.JMenuItem();
        jMenuItem_Load = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Webale Chess");
        setLocationByPlatform(true);
        jPanelMain.setMaximumSize(new java.awt.Dimension(10000, 10000));
        jPanelMain.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelMain.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanel1_componentResized(evt);

            }
        });

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
                jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanelMainLayout.setVerticalGroup(
                jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 800, Short.MAX_VALUE)
        );

        jMenu_Game.setText("Game");
        jMenuItem_Restart.setText("Restart");
        jMenuItem_Restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem_New2PActionPerformed(evt);
            }
        });
        jMenu_Game.add(jMenuItem_Restart);

        jMenuItem_Undo.setText("Undo");
        jMenuItem_Undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem_UndoActionPerformed(evt);
            }
        });
        jMenu_Game.add(jMenuItem_Undo);

        jMenuItem_Close.setText("Close");
        jMenuItem_Close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem_CloseActionPerformed(evt);
            }
        });
        jMenu_Game.add(jMenuItem_Close);

        jMenuBar_Main.add(jMenu_Game);

        jMenu_File.setText("File");

        jMenuItem_Save.setText("Save");
        jMenuItem_Save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem_SaveActionPerformed(evt);
            }
        });
        jMenu_File.add(jMenuItem_Save);

        jMenuItem_Load.setText("Load");
        jMenuItem_Load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem_LoadActionPerformed(evt);
            }
        });
        jMenu_File.add(jMenuItem_Load);

        jMenuBar_Main.add(jMenu_File);

        setJMenuBar(jMenuBar_Main);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanelMain, GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanelMain, GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }

    private void jMenuItem_CloseActionPerformed(ActionEvent evt) {this.dispose(); }
    private void jMenuItem_New2PActionPerformed(ActionEvent evt) {gameScreen.newGame();}
    private void jMenuItem_LoadActionPerformed(ActionEvent evt) {gameScreen.loadBoard();}
    private void jMenuItem_SaveActionPerformed(ActionEvent evt) {gameScreen.saveBoard();}
    private void jMenuItem_UndoActionPerformed(ActionEvent evt) {gameScreen.undo();}
    private void jPanel1_componentResized(ComponentEvent evt) {

        if (jPanelMain != null && gameScreen != null) {
            int smallerDimension = jPanelMain.getHeight();
            if (jPanelMain.getWidth() < smallerDimension)
                smallerDimension = jPanelMain.getWidth();
            gameScreen.setSize(smallerDimension, smallerDimension);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    private JMenuBar jMenuBar_Main;
    private JMenuItem jMenuItem_Close;
    private JMenuItem jMenuItem_Load;
    private JMenuItem jMenuItem_Restart;
    private JMenuItem jMenuItem_Save;
    private JMenuItem jMenuItem_Undo;
    private JMenu jMenu_File;
    private JMenu jMenu_Game;
    private JPanel jPanelMain;
}