package csu_jwxt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * 
 * Class Declaration
 * 
 * @author csu_xiaotao <a href = "https://user.qzone.qq.com/2391527690">月小水长</a>
 *         下午11:33:46 2018年4月21日
 */
public class LoginUI implements ActionListener {
	static String filename = Constant.accout_password_filename;
	// 主窗口
	static JFrame jf = new JFrame("登录界面");
	// 列表框子项集合
	static String[] role = new String[] { "学生", "教师" };
	// 列表框
	static JComboBox<String> login_role = new JComboBox<String>(role);
	// 保存列表框中当前被选中的子项
	static String seletcted_role = role[login_role.getSelectedIndex()];
	// 账户输入控件JTF
	static JTextField jtf_account = new JTextField();
	// 登录按钮
	static JButton jb_login = new JButton("登录");
	// 密码输入JPF
	static JPasswordField jpf_password = new JPasswordField();

	// 为什么要写这个构造方法呢，因为在main里不能set(this);
	public LoginUI() throws FileNotFoundException, IOException {
		// jf初始化
		jf.setLayout(null);
		jf.setSize(400, 300);

		// 虽然可能默认是这个选中，形式性地写一下
		login_role.setSelectedIndex(0);
		login_role.setBounds(200, 20, 60, 20);
		login_role.addItemListener(new MyItemListener());
		jf.add(login_role);

		// 列表框选择提示
		JLabel chooser_tip = new JLabel("请选择登录身份");
		chooser_tip.setBounds(100, 20, 100, 20);
		jf.add(chooser_tip);

		// 账户标签
		JLabel jl_account = new JLabel("账户:");
		jl_account.setBounds(100, 50, 30, 20);
		jf.add(jl_account);

		File f = new File(filename);
		if (!f.exists()) {
			f.createNewFile();
		}

		Properties pps = new Properties();
		pps.load(new FileReader(filename));
		String account = pps.getProperty("account");
		String password = pps.getProperty("password");

		// 账户输入JTF
		jtf_account.addKeyListener(new MyTextAccountListener());
		jtf_account.setBounds(140, 50, 120, 20);
		if (account != null)
			jtf_account.setText(account);
		jf.add(jtf_account);

		// 密码标签
		JLabel jl_password = new JLabel("密码");
		jl_password.setBounds(100, 80, 30, 20);
		jf.add(jl_password);

		// 密码输入JPF
		jpf_password.setBounds(140, 80, 120, 20);
		if (password != null)
			jpf_password.setText(password);
		jf.add(jpf_password);

		// 登录按钮
		jb_login.setBounds(165, 120, 60, 20);
		jb_login.addActionListener(this);
		jf.add(jb_login);

		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		new LoginUI();
		System.out.println("@author:csu_xiaotao");
	}

	// 列表监听器类
	static class MyItemListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			String selecting_role = role[login_role.getSelectedIndex()];
			if (seletcted_role.equals(selecting_role))
				;
			else {
				// 好坑！！！,第一个参数不能为this,因为我主类并没有继承JFrame,不能做父窗口
				int n = JOptionPane.showConfirmDialog(jf, "请确定是否改变登录身份为:  " + selecting_role, "提示",
						JOptionPane.YES_NO_OPTION);
				/*
				 * System.out.println("yes"+JOptionPane.YES_OPTION); System.out.println(n);
				 * System.out.println("no"+JOptionPane.NO_OPTION);
				 */
				if (n == JOptionPane.YES_OPTION)
					seletcted_role = selecting_role;
				else if (n == JOptionPane.NO_OPTION) {
					/**
					 * 为什么要点两下“否”才能关掉对话框 在else if里面再次触发了itemStateChanged方法！！！ 这又是为什么 2018/4/17 12:31
					 */
					login_role.setSelectedItem(seletcted_role);
				}
			}
		}

	}

	// itf_account实时监听类
	static class MyTextAccountListener extends KeyAdapter {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
		 */
		@Override
		public void keyTyped(KeyEvent e) {
			// 方法体
			int keyChar = e.getKeyChar();
			if (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) {

			} else {
				// 销毁字符e实例
				e.consume();
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(jb_login)) {
			String account = jtf_account.getText();
			String password = new String(jpf_password.getPassword());
			try {
				FileUtil.writefile("account=" + account + "\r\npassword=" + password,filename);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if (account.length() == 0 || password.length() == 0)
				JOptionPane.showConfirmDialog(jf, "账号或密码输入为空，请重新输入:  ", "警告", JOptionPane.OK_CANCEL_OPTION);
			else {
				int n = JOptionPane.showConfirmDialog(jf, "Keep it in mind!\n账户: " + account, "提示",
						JOptionPane.OK_CANCEL_OPTION);
				if (n == JOptionPane.OK_OPTION) {
					try {
						new ScoresUI(account,password);
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showConfirmDialog(jf, "密码错误  ", "错误", JOptionPane.OK_OPTION);
					}
				}
			}
		}
	}
}
