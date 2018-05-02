package translator;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import translator.ClipBoardUtil;
import translator.GetHtmlUtil;

public class MainFrame extends JFrame implements Runnable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField srcContentTextField; // 记录剪切板的内容
    private JTextArea resContentTextField; // 记录翻译的内容
    private JCheckBox translateFlag;       //标记单词的获取来源
                                           //选中：手动输入    未选中：剪切板获取
    private Container topContainer;

    public MainFrame() {//初始化控件
        srcContentTextField = new JTextField(10);
        resContentTextField = new JTextArea();
        translateFlag = new JCheckBox();
        topContainer = new Container();
    }

    public void setMinWindowLayout() {
        // TODO Auto-generated method stub
        //布局设置
        resContentTextField.setBorder(new LineBorder(new java.awt.Color(127, 157, 185), 1, false));
        this.setLayout(new BorderLayout());
        this.add(this.resContentTextField);
        translateFlag.setToolTipText("手动输入取词");
        topContainer.setLayout(new BorderLayout());
        topContainer.add(srcContentTextField, BorderLayout.CENTER);
        topContainer.add(translateFlag, BorderLayout.EAST);
        this.add(this.topContainer, BorderLayout.NORTH);
        this.setResizable(false);

        translateFlag.addActionListener(new ActionListener() {//设置JCheckBox的监听

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (translateFlag.isSelected()) {
                    translateFlag.setToolTipText("自动复制取词");    //设置提示
                } else {
                    translateFlag.setToolTipText("手动输入取词");
                }
            }
        });
        //监听JTextField里面内容改变的事件
        srcContentTextField.getDocument().addDocumentListener(new DocumentListener() {    

            @Override
            public void changedUpdate(DocumentEvent arg0) {

            }

            @Override
            public void insertUpdate(DocumentEvent arg0) {    //内容改变

                try {
                    //调用接口获取翻译结果
                    String result = GetHtmlUtil.getTranslateResult(srcContentTextField.getText());
                    if (result == "")
                        result = "!Sorry,未找到该词!";
                    resContentTextField.setText(result);//显示翻译结果
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    resContentTextField.setText("!Sorry,未找到该词!");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {

            }

        });

        this.validate();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            if (!translateFlag.isSelected()) {  //如果JCheckBox没有被选中，则从剪切板获取单词
                try {
                    String content = ClipBoardUtil.ReadOnClipBoard();
                    srcContentTextField.setText(getSimpleWord(content));
                } catch (Exception e) { 
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static String getSimpleWord(String content) {//去掉切板里面的一些特殊字符
        return content.replace(".", "").replace(",", "")
                .replace("'", "").replace(":", "")
                .replace(";", "").trim();
    }
}