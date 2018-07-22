/**
 * 原创声明:csu_xiaotao@163.com
 */


import java.io.FileReader;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import util.Constant;
import util.FileUtil;
import util.NetUtil;
import view.ScoresUI;

/**
 * Class Declaration
 * 
 * @author csu_xiaotao <a href = "https://user.qzone.qq.com/2391527690">月小水长</a>
 *         下午8:19:56 2018年4月22日
 */

public class QueryRunner {

	/**
	 * 方法定义,<a href = "www.baidu.com">百度一下</a>
	 */
	static Timer timer = new Timer();
	private static int scores_size;

	public static void main(String[] args) {
		// 方法体，在此处补充或重写方法
		try {
			scores_size = Integer.parseInt(FileUtil.readfile(Constant.score_size));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timer.schedule(new MyTask(), 1000, 2000);
	}

static class MyTask extends TimerTask{
		@Override
		public void run() {
			//方法体，在此处补充或重写方法
			Properties pps = new Properties();try
			{
				pps.load(new FileReader(Constant.accout_password_filename));
				String account = pps.getProperty("account");
				String password = pps.getProperty("password");
				if (account == null | password == null) {
					JOptionPane.showConfirmDialog(null, "未初始化,请先运行LoginUI", "警告", JOptionPane.OK_OPTION);
				} else {
					int current_size = new NetUtil(account, password).StringSpilter(0).length;
					if (current_size != scores_size) {
						JOptionPane.showConfirmDialog(null, "您有新成绩，可运行LoginUI查看", "提示", JOptionPane.OK_OPTION);
						new ScoresUI(account, password);
						
						timer.cancel();
						Thread.sleep(20000);
						System.exit(0);
						
					}
				}
			}catch(
			Exception e1)
			{
				System.out.println("Error!");
			}
			/* (non-Javadoc)
			 * @see java.util.TimerTask#run()
			 */
		}
	}
}
