package org.cishell.context.test;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.cishell.framework.CIShellContext;
import org.cishell.framework.data.BasicData;
import org.cishell.framework.data.Data;
import org.cishell.service.conversion.DataConversionService;

public class ContextTest extends TestCase {
	private CIShellContext ctx;
	private DataConversionService dcs;
	
	@Override
	protected void setUp() throws Exception {
		ctx = Activator.getCIShellContext();
		if (ctx != null) {
			dcs = (DataConversionService) ctx.getService(DataConversionService.class.getName());
		}
		super.setUp();
	}
	
	public void testContextExists() throws Exception {
		assertNotNull(ctx);
	}

	public void testDataConversionServiceExists() throws Exception {
		assertNotNull(dcs);
	}
	
	public void testDCSCanConvert() throws Exception {
		File inFile = File.createTempFile("cishell-test", ".csv");
		inFile.deleteOnExit();
		Data inData = new BasicData(inFile, "file-ext:csv");
		Data out = dcs.convert(inData, "file:text/csv");
		assertNotNull(out);
		assertEquals(inFile, out.getData());
//		assertEquals("file:text/csv", out.getFormat());
	}
	
}
