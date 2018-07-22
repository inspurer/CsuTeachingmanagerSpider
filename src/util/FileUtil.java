package util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 
 * Class Declaration
 * 
 * @author csu_xiaotao <a href = "https://user.qzone.qq.com/2391527690">月小水长</a>
 *         上午9:14:56 2018年4月22日
 */
public class FileUtil {
	public static void writefile(String content, String filename) throws Exception {
		FileOutputStream fos = new FileOutputStream(filename);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
		BufferedWriter bw = new BufferedWriter(osw);
		bw.write(content);
		bw.close();
		osw.close();
		fos.close();
	}

	public static String readfile(String filename) throws Exception {// 原样读取
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
		StringBuffer lines = new StringBuffer();
		
		int count = 0;
		while ((count = br.read()) != -1) {
			// read方法返回一个整数，用(char)转为字符
			lines.append((char) count);
		}
		br.close();
		return lines.toString();
	}


}
