package com.blogspot.direinem;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.blogspot.direinem.application.ApplicationTestSuite;
import com.blogspot.direinem.domain.DomainTestSuite;
import com.blogspot.direinem.infrastructure.InfrastructureTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ApplicationTestSuite.class,
	DomainTestSuite.class,
	InfrastructureTestSuite.class
})
public class CompleteTestSuite {

}
