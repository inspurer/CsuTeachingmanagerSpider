package translator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetHtmlUtil {
	private final static String PreUrl="http://www.baidu.com/s?wd=";                        //百度搜索URL
    private final static String TransResultStartFlag="<span class=\"op_dict_text2\">";      //翻译开始标签
    private final static String TransResultEndFlag="</span>";                               //翻译结束标签

    public static String getTranslateResult(String urlString) throws Exception {    //传入要搜索的单词
        URL url = new URL(PreUrl+urlString);            //生成完整的URL
        // 打开URL
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        // 得到输入流，即获得了网页的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String preLine="";
        String line;
        int flag=1;
        //File.savetofile(reader);
        // 读取输入流的数据，并显示
        String content="";          //翻译结果
        while ((line = reader.readLine()) != null) {            //获取翻译结果的算法
        	//System.out.println(line);
      
            if(preLine.indexOf(TransResultStartFlag)!=-1&&line.indexOf(TransResultEndFlag)==-1){
            	//System.out.println(line);
                content+=line.replaceAll(" ", "")+"\n";   //去掉源代码上面的半角以及全角字符
                flag=0;
                //System.out.println(content);
                //Thread.sleep(1000000);
            }
            
            if(line.indexOf(TransResultEndFlag)!=-1){
                flag=1;
            }
            if(flag==1){
                preLine=line;
            }
        }
        return content;//返回翻译结果
    }

}
