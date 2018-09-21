package com.wkrzywiec.spring.libraryrest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
			BookControllerTest.class,
			UserControllerTest.class})
public class AllTests {

}
