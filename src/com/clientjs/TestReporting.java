package com.clientjs;

import com.thoughtworks.selenium.*;
import com.aol.pubt.forc.cfg.PropertyBag;
import com.thoughtworks.selenium.DefaultSelenium;
import org.openqa.selenium.server.SeleniumServer;

public class TestReporting extends SeleneseTestCase
{
   private static final String BASE_URL = PropertyBag.getProperty("BASE_URL");
   private static final String browser = PropertyBag.getProperty("browser");
   private static final String loginId = PropertyBag.getProperty("loginId");
   private static final String password = PropertyBag.getProperty("password");
   
   
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

   public void testReporting()
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

    selenium.click("//*[@class=\"gcpReportLink\"]");
    
		new Wait(){
        public boolean until() {
        return selenium.isConfirmationPresent();
        }
        }.wait("Report Confirmation was not present");
        
        
        assertTrue(selenium.getConfirmation().matches("^Are you sure you want to report this comment[\\s\\S]$"));
		
		new Wait(){
        public boolean until() {
        return selenium.isAlertPresent();
        }
        }.wait("Report alert was not present");        
        
        assertEquals("Comment has been reported for review. Thank You!", selenium.getAlert());
        

		}
}
