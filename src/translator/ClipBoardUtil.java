package translator;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class ClipBoardUtil {//ClipBoard:剪贴板
	 public static String ReadOnClipBoard() throws Exception{
	        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();//获取系统剪贴板
	        // 获取剪切板中的内容
	        Transferable clipT = clip.getContents(null);
	        if (clipT != null) {
	        // 检查内容是否是文本类型
	        if (clipT.isDataFlavorSupported(DataFlavor.stringFlavor))
	        return (String)clipT.getTransferData(DataFlavor.stringFlavor); 
	        }
	        return null;
	    }
	 public static boolean WriteToClipBoard(String content) throws Exception{
		 StringSelection stringSelection = new StringSelection( content );
		 // 系统剪贴板
		 Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		 clipboard.setContents(stringSelection, null);
		 if(content.equals(ClipBoardUtil.ReadOnClipBoard()))
			 return true;
		 else return false;
	 }
}
