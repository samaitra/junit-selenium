package com.clientjs;

import com.thoughtworks.selenium.*;

import java.net.URLDecoder;
import java.util.UUID;
import com.aol.pubt.forc.cfg.PropertyBag;
import com.thoughtworks.selenium.DefaultSelenium;
import org.openqa.selenium.server.SeleniumServer;

public class TestPagination extends SeleneseTestCase
{
   private static final String BASE_URL = PropertyBag.getProperty("BASE_URL");
   private static final String browser = PropertyBag.getProperty("browser");
   private static final String loginId = PropertyBag.getProperty("loginId");
   private static final String password = PropertyBag.getProperty("password");
   private static final String commentsPerPage = PropertyBag.getProperty("commentsPerPage");

   private Selenium selenium = new DefaultSelenium( "localhost",
                                                    4444,
                                                    browser,
                                                    BASE_URL);
   private SeleniumServer seleniumServer;
   
   public void setUp()
      throws Exception
   {
      seleniumServer = new SeleniumServer();
      seleniumServer.start();
      selenium.start();
   }

   public void tearDown()
      throws Exception
   {
      selenium.stop();
      seleniumServer.stop();
   }

   public void testPagination()
      throws Exception
   {	  	
   	selenium.open(BASE_URL);
   	selenium.click("authLink");
	selenium.waitForPopUp("jQuery_popUp", "30000");
	selenium.selectWindow("name=jQuery_popUp");
	selenium.type("lgnId", loginId);
	selenium.type("pwdId", password);
	selenium.click("aolsubmit");
	selenium.selectWindow(null);
	
	
	selenium.windowMaximize();
	selenium.windowFocus();
	
	new Wait(){
        public boolean until() {
        return selenium.isTextPresent("Welcome TravelTestsQa! Sign out.") ;
        }
        }.wait("User not signed in");

	int numComments = Integer.parseInt(commentsPerPage);
	
	for (int i=0;i<=numComments;i++){
	
		final UUID commentId = UUID.randomUUID();
    	selenium.type("gcpCommentBody", commentId+" comment");
    	selenium.click("gcpAddCommentButton");  		

	new Wait(){
        public boolean until() {
        return selenium.isAlertPresent(); 
        }
        }.wait("Comment Post Alert was not present");

	assertEquals("Comment added successfully", selenium.getAlert());
	verifyTrue(selenium.isTextPresent(commentId+" comment"));	
	
		}
	
	selenium.click("//div[@id='gcpCommentsTopNav']/div/div[2]/a/span");	
	String loc = selenium.getLocation();
	URLDecoder.decode(loc);
	String finalurl = BASE_URL +"#gcp_comments%5Bp%5D=2&gcp_comments%5Bs%5D=new";
	verifyEquals(loc, finalurl);
	
	}
}
