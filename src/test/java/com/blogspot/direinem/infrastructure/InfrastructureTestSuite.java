package com.blogspot.direinem.infrastructure;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.blogspot.direinem.infrastructure.encryption.ShaEcnryptorTest;
import com.blogspot.direinem.infrastructure.repository.PizzaRepositoryTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ShaEcnryptorTest.class,
	PizzaRepositoryTest.class
})
public class InfrastructureTestSuite {

}
