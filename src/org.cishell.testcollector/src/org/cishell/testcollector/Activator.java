package org.cishell.testcollector;

import junit.framework.Test;

import org.junit.runner.JUnitCore;
import org.junit.runner.notification.RunListener;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.tap4j.ext.junit.JUnitTestTapReporter;

public class Activator implements BundleActivator {

	public void start(BundleContext context) throws Exception {
		System.out.println("Started something.");
		
		Test suite = AllTests.suite();
		JUnitCore core = new JUnitCore();
		RunListener listener = new JUnitTestTapReporter();
		core.addListener(listener);
		core.run(suite);
		
		MissingBundleComplainer.complain(context);
		
		
		if (System.getProperty(this.getClass().getPackage().getName() + ".exit", "false").equals("true")) {
			System.exit(0);
		}
	}

	public void stop(BundleContext arg0) throws Exception {
	}


}
