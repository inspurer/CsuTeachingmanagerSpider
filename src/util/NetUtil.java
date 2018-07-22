package util;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
/**
 * 
 * Class Declaration
 * @author csu_xiaotao
 *<a href = "https://user.qzone.qq.com/2391527690">月小水长</a>
 * 下午11:33:30
 * 2018年4月21日
 */
public class NetUtil {
	public final String content;

	public NetUtil(String username, String password) throws Exception {
		URL url = new URL("http://csujwc.its.csu.edu.cn/jsxsd/xk/LoginToXk");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setInstanceFollowRedirects(false);
		/**
		 * 设置为true，则系统自动处理跳转，但是对于有多次跳转的情况，就只能处理第一次。
		 * 如果设置为false，则是你自己手动处理跳转，此时可以拿到一些比较有用的数据，比如cookie，Location之类的
		 */
		connection.setDoOutput(true);
		/**
		 * httpUrlConnection.setDoOutput(false); 以后就可以使用conn.getOutputStream().write()
		 * httpUrlConnection.setDoInput(true); 以后就可以使用conn.getInputStream().read();
		 */
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		/*
		 * 这两天在做http服务端请求操作，客户端post数据到服务端后， 服务端通过request.getParameter()进行请求，无法读取到数据，
		 * 搜索了一下发现是因为设置为text/plain模式才导致读取不到数据
		 * urlConn.setRequestProperty("Content-Type","text/plain; charset=utf-8");
		 * 若设置为以下方式，则通过request.getParameter()可以读取到数据
		 * urlConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded"
		 * );
		 */
		String encoded = Base64.getEncoder().encodeToString(username.getBytes("utf-8")) + "%%%"
				+ Base64.getEncoder().encodeToString(password.getBytes("utf-8"));
		/*
		 * System.out.println(encoded);
		 */ connection.getOutputStream().write(("encoded=" + URLEncoder.encode(encoded, "utf-8")).getBytes("utf-8"));
		/*
		 * System.out.println(("encoded="+ URLEncoder.encode(encoded,"utf-8")));
		 */ if (connection.getResponseCode() != 302) {
			throw new AccountException();
		}
		List<String> cookies = connection.getHeaderFields().get("Set-Cookie");
		/* System.out.println(cookies); */
		// get page
		url = new URL("http://csujwc.its.csu.edu.cn/jsxsd/kscj/yscjcx_list");
		connection = (HttpURLConnection) url.openConnection();
		for (String cookie : cookies) {
			/*
			 * System.out.println(cookie);
			 */ connection.addRequestProperty("Cookie", cookie.substring(0, cookie.indexOf(';')));
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
		StringBuilder body = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			body.append(line);
			body.append('\n');
		}

		// exit
		url = new URL("http://csujwc.its.csu.edu.cn/jsxsd/xk/LoginToXk?method=exit");
		connection = (HttpURLConnection) url.openConnection();
		connection.setInstanceFollowRedirects(false);
		for (String cookie : cookies) {
			connection.addRequestProperty("Cookie", cookie.substring(0, cookie.indexOf(';')));
		}
		connection.getResponseCode();

		content = body.toString();
		/*
		 * FileOutputStream fos = new FileOutputStream("test.txt"); OutputStreamWriter
		 * osw = new OutputStreamWriter(fos, "UTF-8"); BufferedWriter bw = new
		 * BufferedWriter(osw); bw.write(content); bw.close(); osw.close(); fos.close();
		 */
		/* System.out.println(content); */
	}

	public String getName() {
		String pt = "&nbsp;&nbsp;(.*)\\(";
		Pattern pattern = Pattern.compile(pt);
		Matcher m = pattern.matcher(content);
		m.find();
		/*
		 * System.out.println(m.group());
		 */ return m.group(1);
	}

	public String getContent() {
		return content;
	}

	public String[] StringSpilter(int index) {
		Document document = Jsoup.parse(getContent());
		// #id: 通过ID查找元素，比如：#logo
		Elements names = document.select("[align=left]");
		Elements scores = document.select("[href~=javascript:JsMod]");
		/*System.out.println(scores.get(0).text());
		System.out.println(names.get(0).text());*/
		String[] x = new String[names.size()];
		if (index == 0) {
			for(int i = 0; i<names.size(); i++) {
				x [i] = names.get(i).text();
				x [i] = x[i].substring(x[i].indexOf(']')+1);
			}
		}
		else if (index == 1) {
			for(int i = 0; i<scores.size(); i++)
				x [i] = scores.get(i).text();
		}
		else
			System.out.println("输入参数非法");
		return x;
	}
}

@SuppressWarnings("serial")
class AccountException extends Exception {

	AccountException() {
		super("用户名或密码错误");
	}
}