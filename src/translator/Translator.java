package translator;

import javax.swing.JFrame;


public class Translator {
    public static void main(String[] args) {

        MainFrame mainFrame = new MainFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBounds(300, 200, 400, 300);
        mainFrame.setVisible(true);
        mainFrame.setAlwaysOnTop(true);//设置在最顶层
        mainFrame.setMinWindowLayout();

        Thread t = new Thread(mainFrame);
        t.start();    //开启线程
    }
}