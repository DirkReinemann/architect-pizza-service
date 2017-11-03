package com.blogspot.direinem.application;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

import com.blogspot.direinem.application.admin.OrderProcessControllerTest;
import com.blogspot.direinem.application.user.ContactControllerTest;
import com.blogspot.direinem.application.user.NewsletterControllerTest;
import com.blogspot.direinem.application.user.RegistrationControllerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	OrderProcessControllerTest.class,
	ContactControllerTest.class,
	NewsletterControllerTest.class,
	RegistrationControllerTest.class
})
public class ApplicationTestSuite {

}
