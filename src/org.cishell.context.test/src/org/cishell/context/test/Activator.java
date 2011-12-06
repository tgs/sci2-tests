package org.cishell.context.test;

import org.cishell.framework.CIShellContext;
import org.cishell.framework.LocalCIShellContext;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private static CIShellContext ciShellContext;

	static BundleContext getContext() {
		return context;
	}
	
	static CIShellContext getCIShellContext() {
		return ciShellContext;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		Activator.ciShellContext = new LocalCIShellContext(bundleContext);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
