/**
 * 原创声明:csu_xiaotao@163.com
 */
package csu_jwxt;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Class Declaration
 * 
 * @author csu_xiaotao <a href = "https://user.qzone.qq.com/2391527690">月小水长</a>
 *         下午8:32:55 2018年4月22日
 */
public class ScoresUI {
	public ScoresUI(String account,String password) throws Exception {
		NetUtil my = new NetUtil(account, password);
		JFrame couses = new JFrame("成绩展示界面");
		couses.setSize(400, 800);
		couses.setLayout(null);
		couses.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		couses.setVisible(true);
		JTextArea jta = new JTextArea("", 10, 50);
		JScrollPane jsp = new JScrollPane(jta);
		jsp.setBounds(20, 10, 360, 680);
		couses.add(jsp);
		StringBuffer sb = new StringBuffer("您好！" + my.getName() + "\n");
		String[] names = my.StringSpilter(0);
		String[] scores = my.StringSpilter(1);
		for (int i = 0; i < names.length; i++) {
			sb.append(names[i] + ": " + scores[i] + "\n");
		}
		jta.setText(sb.toString());
		jta.setFont(new Font("楷体", Font.BOLD, 14));
		new DataViewer(names, scores).ShowView();
		
		//把当前成绩个数写进文件,方便比对
		FileUtil.writefile(String.valueOf(names.length), Constant.score_size);
	}

}
