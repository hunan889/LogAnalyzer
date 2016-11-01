//package com.duowan.loganalyzer.ui;
//
//import com.duowan.loganalyzer.rule.Result;
//import com.duowan.loganalyzer.rule.Rule;
//import com.duowan.loganalyzer.rule.RuleGroup;
//
//import java.awt.BorderLayout;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.Font;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
//import javax.swing.JButton;
//import javax.swing.JFileChooser;
//import javax.swing.JFrame;
//import javax.swing.JMenu;
//import javax.swing.JMenuBar;
//import javax.swing.JMenuItem;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.JTextArea;
//import javax.swing.WindowConstants;
//
///**
// * @author carlosliu on 2016/10/30.
// */
//public class MainFrame extends JFrame {
//
//    private JTable mLogTable;
//    private JTextArea mLogTextArea;
//    private JTextArea mResultArea;
//
//    private void initUI() {
//        initMenu();
//        initMainFrame();
//        initFeatureArea();
//        initLogArea();
//        invalidate();
//    }
//
//    private static final Font GLOBAL_FONT_CONTENT = new Font("微软雅黑", Font.PLAIN, 16);
//    private static final Font GLOBAL_FONT_MENU = new Font("宋体", Font.PLAIN, 16);
//
//    private void initLogArea() {
//        mLogTable = new JTable();
//        mLogTable.setFont(GLOBAL_FONT_CONTENT);
//        mLogTable.setRowHeight(20);
//        mLogTable.setShowGrid(false);
//        mLogTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
//        JScrollPane pane = new JScrollPane(mLogTable);
//        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        pane.setSize(getSize());
//        pane.setPreferredSize(getSize());
//        getContentPane().add(pane, BorderLayout.CENTER);
//    }
//
//
////    private void initLogArea() {
////        mLogTextArea = new JTextArea();
////        mLogTextArea.setFont(GLOBAL_FONT_CONTENT);
////        JScrollPane pane = new JScrollPane(mLogTextArea);
////        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
////        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
////        pane.setSize(getSize());
////        pane.setPreferredSize(getSize());
////        getContentPane().add(pane, BorderLayout.CENTER);
////    }
//
//    private void initFeatureArea() {
//        mResultArea = new JTextArea();
//        JButton filterButton = new JButton("filter");
//        JTextArea keyInput = new JTextArea();
//        keyInput.setSize(100, 100);
//        keyInput.setPreferredSize(new Dimension(100, 100));
//
//        JPanel featurePanel = new JPanel();
//        featurePanel.setLayout(new FlowLayout());
//        featurePanel.add(filterButton);
//        featurePanel.add(keyInput);
//        featurePanel.add(mResultArea);
//        filterButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String key = keyInput.getText();
//
//                Rule rule = new Rule(key);
//                RuleGroup ruleGroup = new RuleGroup(0, rule);
//                ruleGroup.execute(new Result(mLogTextArea.getText()));
//                Map<String, List<Result>> results = ruleGroup.getResults();
//                String s = "";
//                for (List<Result> resultList : results.values()) {
//                    for (Result result : resultList) {
//                        if (result.mIsMatched) {
//                            s += result.mFilterResult + "\n";
//                        }
//                    }
//                }
//                System.out.println(s);
//                mResultArea.setText(s);
//            }
//        });
//        getContentPane().add(featurePanel, BorderLayout.SOUTH);
//    }
//
//    private void initMainFrame() {
//        setTitle("LogAnalyzer");
//        setLayout(new BorderLayout());
//        setPreferredSize(new Dimension(800, 600));
//        setSize(getPreferredSize());
//        setVisible(true);
////        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//    }
//
//    private void initMenu() {
//        JMenuBar menuBar = new JMenuBar();
//        menuBar.setFont(GLOBAL_FONT_MENU);
//        JMenu menuFile = new JMenu("文件");
//        menuFile.setFont(GLOBAL_FONT_MENU);
//        JMenuItem menuItem = new JMenuItem("打开...");
//        menuItem.setFont(GLOBAL_FONT_MENU);
//        JFileChooser fileChooser = new JFileChooser();
//
//        menuItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int result = fileChooser.showOpenDialog(null);
//                if (result == JFileChooser.APPROVE_OPTION) {
//                    File file = fileChooser.getSelectedFile();
//
//                    try {
//                        mLogTable.setModel(new LogViewTableModel(file.getPath()));
//                        mLogTable.getColumnModel().getColumn(0).setPreferredWidth(50);
//                        mLogTable.getColumnModel().setColumnMargin(10);
//                        mLogTable.getColumnModel().getColumn(0).setMaxWidth(50);
////                        mLogTable.getColumnModel().getColumn(0).set
//                    } catch (IOException e1) {
//                        e1.printStackTrace();
//                    }
////                    new FileReaderWorker(file, mLogTextArea).execute();
////                    try {
////                        FileGetter.loadFile(mLogTextArea, file);
////                    } catch (IOException e1) {
////                        e1.printStackTrace();
////                    }
//
//                }
//            }
//        });
//        addKeyListener(new KeyListener() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//
//            }
//
//            @Override
//            public void keyPressed(KeyEvent e) {
//
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) {
//
//            }
//        });
//
//        menuFile.add(menuItem);
//        menuBar.add(menuFile);
//        setJMenuBar(menuBar);
//    }
//
//    public MainFrame() {
//        initUI();
//    }
//
//    public static void main(String args[]) {
//
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//
//                new MainFrame();
//            }
//        });
//    }
//}
