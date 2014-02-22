/**
 * @author: Xavier N. Noumbissi
 *
 */
package ca.noombis.quercus.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.logging.Level;
import java.util.logging.Logger;

import static ca.noombis.quercus.test.TestUtil.getLogMsg;
import static ca.noombis.quercus.test.TestUtil.getExpectedResult;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.TextBlock;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

public class TestDataSanitization {

	@Rule public TestName name = new TestName();
	
	private static final Logger logger = Logger.getLogger(TestDataSanitization.class.getName());
	
	private static final String [][] tests = { 	
		{"SANITIZATION", 		"sanitization.php?req1=hello", 			"sanitization.expect"} };	
	
	private static final String testWebUrl = new String("http://localhost:8080/sample/");
	private static final String expectFilesUrl = new String("http://localhost:8080/sample/");	
	
	private WebConversation wc = null;	
	private WebRequest request = null;	
	private WebResponse response = null;
	private String[] outputs = new String[2];
	
	private String getTestMsg(String msg) {
		return getLogMsg(name.getMethodName(), msg);
	}	
	
	@Before
	public void setUpBeforeTest() throws Exception {
		wc = new WebConversation();	
	}	

	@After
	public void tearDown() throws Exception {
		wc = null;
		request = null;
		response = null;
		outputs[0] = "OUTPUT";
		outputs[1] = "EXPECTED";
	}
	
	/**
	 * Fill the ArrayList outputs with the test output
	 * and the expected output
	 * 
	 * @param reqStr
	 * @throws Exception
	 */
	private void getOutAndExpected(int testNr) throws Exception {
		
		String reqStr = testWebUrl + tests[testNr][1];
		
		logger.log( Level.FINER, getTestMsg("Sending request: " + reqStr) );
		
		request = new GetMethodWebRequest( reqStr );	    
		response = wc.getResponse( request );

		assertNotNull( getTestMsg("No response received"), response );

		TextBlock[] textBlocks = response.getTextBlocks();
		assertTrue("Not enough text on the response page as expected: " + textBlocks.length, textBlocks.length > 0);

		String output = textBlocks[0].getText().trim();
		
		logger.log( Level.FINER, getTestMsg("Getting expected result from: " + reqStr) );
		String expected = getExpectedResult(expectFilesUrl, tests[testNr][2]).trim();
		
		outputs[0] = output;
		outputs[1] = expected;
	}	
	
	@Test
	public void testSanitization() throws Exception {		

		this.getOutAndExpected(0);
		
		boolean v = outputs[0].equalsIgnoreCase(outputs[1]);

		if (!v) {			
			System.out.println( getTestMsg("Quercus reponse: \"" + outputs[0] + "\"") );
			System.out.println( getTestMsg("Expected reponse: \"" + outputs[1] + "\"") );
		}

		assertTrue(v);								
	}
	
}
