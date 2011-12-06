/*******************************************************************************
 * Copyright (c) 2008 Syntax Consulting, Inc. All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.cishell.testcollector;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.rcpquickstart.bundletestcollector.BundleTestCollector;

public class AllTests extends TestSuite {

	public static Test suite() {
		BundleTestCollector testCollector = new BundleTestCollector();
		
		TestSuite suite = new TestSuite("All Tests");

		/*
		 * assemble as many collections as you like based on bundle, package and
		 * classname filters
		 */
		testCollector.collectTests(suite, "org.cishell.", "org.cishell.",
				"*Test");
		testCollector.collectTests(suite, "edu.iu.nwb.", "edu.iu.nwb.",
				"*Test");
		testCollector.collectTests(suite, "edu.iu.sci2.", "edu.iu.sci2.",
				"*Test");

		return suite;

	}
}
