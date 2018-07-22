package util;
/**
 * 原创声明:csu_xiaotao@163.com
 */


/**
 * Class Declaration
 * 
 * @author csu_xiaotao <a href = "https://user.qzone.qq.com/2391527690">月小水长</a>
 *         上午8:56:19 2018年4月22日
 */
public class DataViewerUtil {
	private final String front = "<head>\r\n" + "    <meta charset=\"UTF-8\">\r\n"
			+ "    <meta name=\"viewport\" content=\"width=device-width,height=device-height\">\r\n"
			+ "    <title>成绩折线图</title>\r\n"
			+ "    <style>::-webkit-scrollbar{display:none;}html,body{overflow:hidden;height:100%;}</style>\r\n"
			+ "</head>\r\n" + "<body>\r\n" + "<div id=\"mountNode\"></div>\r\n"
			+ "<script>/*Fixing iframe window.innerHeight 0 issue in Safari*/document.body.clientHeight;</script>\r\n"
			+ "<script src=\"https://gw.alipayobjects.com/os/antv/assets/g2/3.0.5-beta.5/g2.min.js\"></script>\r\n"
			+ "<script src=\"https://gw.alipayobjects.com/os/antv/assets/data-set/0.8.6/data-set.min.js\"></script>\r\n"
			+ "<script>\r\n" + "  const data = [\r\n";
	private final String behind = " ];\r\n" + "  const chart = new G2.Chart({\r\n" + "    container: 'mountNode',\r\n"
			+ "    forceFit: true,\r\n" + "    height: window.innerHeight\r\n" + "  });\r\n"
			+ "  chart.source(data);\r\n" + "  chart.scale('value', {\r\n" + "    min: 0\r\n" + "  });\r\n"
			+ "  chart.scale('year', {\r\n" + "    range: [ 0 , 1 ]\r\n" + "  });\r\n" + "  chart.tooltip({\r\n"
			+ "    crosshairs: {\r\n" + "      type: 'line'\r\n" + "    }\r\n" + "  });\r\n"
			+ "  chart.line().position('year*value');\r\n"
			+ "  chart.point().position('year*value').size(4).shape('circle').style({\r\n" + "    stroke: '#fff',\r\n"
			+ "    lineWidth: 1\r\n" + "  });\r\n" + "  chart.render();\r\n" + "</script>\r\n" + "</body>\r\n"
			+ "</html>\r\n";
	private String content;
	public DataViewerUtil(String [] names,String [] scores) {
	    StringBuffer sb = new StringBuffer();
	    for(int i = 0; i<names.length; i++) {
	    	try {
	    		Integer.parseInt(scores[i]);
	    	}catch(Exception e) {
	    		continue;
	    	}
	    	sb.append("    { year: "+"'"+names[i]+"', value: "+scores[i]+" }");
	    	if(i!=names.length-1)
	    		sb.append(",\r\n");
	    	else
	    		sb.append("\r\n");
	    }
	    content = sb.toString();
	}
	public void ShowView() {
		try {
			FileUtil.writefile(front+content+behind, Constant.line_chart_filename);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
