package org.cishell.testcollector;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.tap4j.model.Plan;
import org.tap4j.model.TestResult;
import org.tap4j.model.TestSet;
import org.tap4j.producer.TapProducer;
import org.tap4j.producer.TapProducerFactory;
import org.tap4j.util.StatusValues;

public class MissingBundleComplainer {
	public static void complain(BundleContext context) {
		Map<Bundle, String> testBundles = findTestBundleErrors(context);
		
		TestSet testOutput = new TestSet();
		testOutput.setPlan(new Plan(testBundles.size()));
		
		int testNumber = 1;
		for (Map.Entry<Bundle, String> entry : testBundles.entrySet()) {
			TestResult result = new TestResult(StatusValues.NOT_OK, testNumber++);
			result.setDescription(entry.getKey().getSymbolicName());
			HashMap<String, Object> bleh = new HashMap<String, Object>();
			bleh.put("description", entry.getValue());
			result.setDiagnostic(bleh);
			
			testOutput.addTestResult(result);
		}
		
		TapProducer tapProducer = TapProducerFactory.makeTap13YamlProducer();
		tapProducer.dump(testOutput, new File("missing-bundles.tap"));
	}

	private static Map<Bundle,String> findTestBundleErrors(BundleContext context) {
		Bundle[] allBundles = context.getBundles();
		Map<Bundle,String> testBundles = new LinkedHashMap<Bundle,String>();
		for (Bundle b : allBundles) {
			if (b.getSymbolicName().endsWith("tests")) {
				if (b.getState() == Bundle.INSTALLED) {
					testBundles.put(b, "State INSTALLED: probably should be RESOLVED or ACTIVE.  You may be unintentionally skipping tests!");
				}
			}
		}
		return testBundles;
	}
}
