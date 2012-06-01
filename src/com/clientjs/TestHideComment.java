package com.clientjs;

import com.thoughtworks.selenium.*;
import com.aol.pubt.forc.cfg.PropertyBag;
import com.thoughtworks.selenium.DefaultSelenium;
import org.openqa.selenium.server.SeleniumServer;

public class TestHideComment extends SeleneseTestCase
{
   private static final String BASE_URL = PropertyBag.getProperty("BASE_URL");
   private static final String browser = PropertyBag.getProperty("browser");
    
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

   public void testHideComment()
      throws Exception
   { 	
   	selenium.open(BASE_URL);
  	selenium.click("gcpCommentsOffLink");
	selenium.open(BASE_URL);
	verifyFalse(selenium.isVisible("//*[@id=\"gcpSortMenuTop\"]"));	
	
		}
}
