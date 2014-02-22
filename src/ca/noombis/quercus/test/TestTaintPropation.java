/**
 * @author: Xavier N. Noumbissi
 *
 */

package ca.noombis.quercus.test;

import static org.junit.Assert.*;

import static ca.noombis.quercus.test.TestUtil.getLogMsg;
import static ca.noombis.quercus.test.TestUtil.getExpectedResult;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.TextBlock;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

public class TestTaintPropation {

	@Rule public TestName name = new TestName();

	private static final Logger logger = Logger.getLogger(TestTaintPropation.class.getName());

	public static final String pathSeparator = System.getProperty("file.separator");			
	
	private static final int ADDITION 			= 0;
	private static final int SUBSTRACTION 		= 1;
	private static final int DIVIDE 			= 2;
	private static final int MULTIPLICATION 	= 3;
	private static final int POSTINCREMENT 		= 4;
	private static final int PREINCREMENT 		= 5;
	private static final int STRING_TO_FLOAT 	= 6;
	private static final int INT_TO_STRING 		= 7;
	private static final int STRING_CONCAT 		= 8;

	private static final String [][] taintOperations = { 	
		{"ADDITION", 		"addition.php?req1=7", 			"addition.expect"},
		{"SUBSTRACTION", 	"substraction.php?req1=7", 		"substraction.expect"},
		{"DIVIDE", 			"divide.php?req1=7", 			"divide.expect"},
		{"MULTIPLICATION", 	"multiplication.php?req1=7",	"multiplication.expect"},
		{"POSTINCREMENT", 	"postincrement.php?req1=7", 	"postincrement.expect"},
		{"PREINCREMENT", 	"preincrement.php?req1=7", 		"preincrement.expect"},
		{"STRING_TO_FLOAT",	"string-to-float.php?req1=7.1", "string-to-float.expect"},
		{"INT_TO_STRING", 	"int-to-string.php?req1=7", 	"int-to-string.expect"}, 
		{"STRING_CONCAT", 	"string-concat.php?req1=concat", "string-concat.expect"} };

	private static final String testWebUrl = new String("http://localhost:8080/sample/");
	private static final String expectFilesUrl = new String("http://localhost:8080/sample/");

	private WebConversation wc = null;	
	private WebRequest request = null;	
	private WebResponse response = null;
	private String[] outputs = new String[2];
	
	private String getTestMsg(String msg) {
		return getLogMsg(name.getMethodName(), msg);
	}

	/**
	 * Fill the ArrayList outputs with the test output
	 * and the expected output
	 * 
	 * @param reqStr
	 * @throws Exception
	 */
	private void getOutAndExpected(int taintOperation) throws Exception {
		
		String reqStr = testWebUrl + taintOperations[taintOperation][1];
		
		logger.log( Level.FINER, getTestMsg("Sending request: " + reqStr) );
		
		request = new GetMethodWebRequest( reqStr );	    
		response = wc.getResponse( request );

		assertNotNull( getTestMsg( "No response received"), response );

		TextBlock[] textBlocks = response.getTextBlocks();
		assertTrue("Not enough text on the response page as expected: " + textBlocks.length, textBlocks.length > 0);

		String output = textBlocks[0].getText().trim();
		
		logger.log( Level.FINER, getTestMsg("Getting expected result from: " + reqStr) );
		String expected = getExpectedResult(expectFilesUrl, taintOperations[taintOperation][2]).trim();
		
		outputs[0] = output;
		outputs[1] = expected;
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
	
	@Test
	public void testAddition() throws Exception {		

		this.getOutAndExpected(ADDITION);
		
		boolean v = outputs[0].equalsIgnoreCase(outputs[1]);

		if (!v) {			
			System.out.println( getTestMsg("Quercus reponse: \"" + outputs[0] + "\"") );
			System.out.println( getTestMsg("Expected reponse: \"" + outputs[1] + "\"") );
		}

		assertTrue(v);								
	}	

	@Test
	public void testSubstraction() throws Exception {		

		this.getOutAndExpected(SUBSTRACTION);
		
		boolean v = outputs[0].equalsIgnoreCase(outputs[1]);

		if (!v) {			
			System.out.println( getTestMsg("Quercus reponse: \"" + outputs[0] + "\"") );
			System.out.println( getTestMsg("Expected reponse: \"" + outputs[1] + "\"") );
		}

		assertTrue(v);								
	}	

	@Test
	public void testDivide() throws Exception {		

		this.getOutAndExpected(DIVIDE);

		boolean v = outputs[0].equalsIgnoreCase(outputs[1]);

		if (!v) {			
			System.out.println( getTestMsg("Quercus reponse: \"" + outputs[0] + "\"") );
			System.out.println( getTestMsg("Expected reponse: \"" + outputs[1] + "\"") );
		}

		assertTrue(v);								
	}	

	@Test
	public void testMultiplication() throws Exception {		

		this.getOutAndExpected(MULTIPLICATION);

		boolean v = outputs[0].equalsIgnoreCase(outputs[1]);

		if (!v) {			
			System.out.println( getTestMsg("Quercus reponse: \"" + outputs[0] + "\"") );
			System.out.println( getTestMsg("Expected reponse: \"" + outputs[1] + "\"") );
		}

		assertTrue(v);							
	}	

	@Test
	public void testPostincrement() throws Exception {		

		this.getOutAndExpected(POSTINCREMENT);

		boolean v = outputs[0].equalsIgnoreCase(outputs[1]);

		if (!v) {			
			System.out.println( getTestMsg("Quercus reponse: \"" + outputs[0] + "\"") );
			System.out.println( getTestMsg("Expected reponse: \"" + outputs[1] + "\"") );
		}

		assertTrue(v);								
	}	

	@Test
	public void testPreincrement() throws Exception {		

		this.getOutAndExpected(PREINCREMENT);

		boolean v = outputs[0].equalsIgnoreCase(outputs[1]);

		if (!v) {			
			System.out.println( getTestMsg("Quercus reponse: \"" + outputs[0] + "\"") );
			System.out.println( getTestMsg("Expected reponse: \"" + outputs[1] + "\"") );
		}

		assertTrue(v);								
	}	

	@Test
	public void testStringToFloat() throws Exception {		

		this.getOutAndExpected(STRING_TO_FLOAT);

		boolean v = outputs[0].equalsIgnoreCase(outputs[1]);

		if (!v) {			
			System.out.println( getTestMsg("Quercus reponse: \"" + outputs[0] + "\"") );
			System.out.println( getTestMsg("Expected reponse: \"" + outputs[1] + "\"") );
		}

		assertTrue(v);								
	}		
	
	@Test
	public void testIntToString() throws Exception {		

		this.getOutAndExpected(INT_TO_STRING);

		boolean v = outputs[0].equalsIgnoreCase(outputs[1]);

		if (!v) {			
			System.out.println( getTestMsg("Quercus reponse: \"" + outputs[0] + "\"") );
			System.out.println( getTestMsg("Expected reponse: \"" + outputs[1] + "\"") );
		}

		assertTrue(v);								
	}
	
	@Test
	public void testStringConcatenation() throws Exception {		

		this.getOutAndExpected(STRING_CONCAT);

		boolean v = outputs[0].equalsIgnoreCase(outputs[1]);

		if (!v) {			
			System.out.println( getTestMsg("Quercus reponse: \"" + outputs[0] + "\"") );
			System.out.println( getTestMsg("Expected reponse: \"" + outputs[1] + "\"") );
		}

		assertTrue(v);								
	}	

}
