/**
 * @author: Xavier N. Noumbissi
 *
 */

package ca.noombis.quercus.test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

public class TestUtil {
	
	protected static final String getLogMsg(String testName, String msg) {
		StringBuilder logMsg = new StringBuilder("[");
		logMsg.append(testName)
		.append("] ")
		.append(msg);
		return logMsg.toString();
	}

	protected static final String getExpectedResult(String expectFilesUrl, 
												  String urlEnd) throws Exception {
		String reqStr = expectFilesUrl + urlEnd;
		WebConversation webConv = new WebConversation();
		WebRequest request = new GetMethodWebRequest( reqStr );
		WebResponse response = webConv.getResponse( request );
		String res = response.getText();

		return res;
	}	
	
}
