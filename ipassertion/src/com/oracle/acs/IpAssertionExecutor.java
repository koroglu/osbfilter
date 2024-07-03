package com.oracle.acs;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import oracle.wsm.common.sdk.IContext;
import oracle.wsm.common.sdk.IMessageContext;
import oracle.wsm.common.sdk.IResult;
import oracle.wsm.common.sdk.Result;
import oracle.wsm.common.sdk.WSMException;
import oracle.wsm.policy.model.IAssertion;
import oracle.wsm.policy.model.IAssertionBindings;
import oracle.wsm.policy.model.IConfig;
import oracle.wsm.policy.model.IPropertySet;
import oracle.wsm.policy.model.ISimpleOracleAssertion;
import oracle.wsm.policy.model.impl.SimpleAssertion;
import oracle.wsm.policyengine.IExecutionContext;
import oracle.wsm.policyengine.impl.AssertionExecutor;

public class IpAssertionExecutor extends AssertionExecutor {

	private boolean transperentMode = false;
	private String url;

	@Override
	public void destroy() {
		this.assertion = assertion;
		this.econtext = econtext;

	}

	public static String getHTML(String urlToRead) throws Exception {
		StringBuilder result = new StringBuilder();
		URL url = new URL(urlToRead);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
			for (String line; (line = reader.readLine()) != null;) {
				result.append(line);
			}
		}
		return result.toString();
	}

	@Override
	public IResult execute(IContext context) throws WSMException {

		try {
			IAssertionBindings bindings = ((SimpleAssertion) (this.assertion)).getBindings();
			IConfig config = bindings.getConfigs().get(0);
			IPropertySet propertyset = config.getPropertySets().get(0);
			String valid_ips = propertyset.getPropertyByName("valid_ips").getValue();

			String strURL = propertyset.getPropertyByName("url").getValue();
			String serviceURL = ((IMessageContext) context).getServiceURL();
			System.out.println("###### ACS Logging :Service URL = " + ((IMessageContext) context).getServiceURL());

			Map<String, String> allHeaders = ((IMessageContext) context).getTransportContext().getAllHeaders();
			for (Map.Entry<String, String> entry : allHeaders.entrySet()) {
				System.out.println("######## ACS Logging : " + entry.getKey() + ": " + entry.getValue());
			}

			// TODO: Eğer servis 3 sn den uzun cevap veremez ise izin verme modunda çalış ve
			// alarm üret.!!!!mail at!!!!

			String ipAddr = ((IMessageContext) context).getRemoteAddr();
			strURL+="?ipaddress="+ipAddr+"&context="+serviceURL;
			IResult result = new Result();
		
			if(getHTML(strURL).contains("success")) {
				result.setStatus(IResult.SUCCEEDED);
				return result;
			}else {
				result.setStatus(IResult.FAILED);
				return result;
			}
			
		/*	
			if (valid_ips != null && valid_ips.trim().length() > 0) {
				String[] valid_ips_array = valid_ips.split(",");
				boolean isPresent = false;
				for (String valid_ip : valid_ips_array) {
					if (ipAddr.equals(valid_ip.trim())) {
						isPresent = true;
					}
				}
				if (isPresent) {
					result.setStatus(IResult.SUCCEEDED);
				} else {
					result.setStatus(IResult.FAILED);
					result.setFault(new WSMException(WSMException.FAULT_FAILED_CHECK));
				}
			} else {
				result.setStatus(IResult.SUCCEEDED);
			}
			return result;
		 */
		} catch (Exception e) {
			throw new WSMException(WSMException.FAULT_FAILED_CHECK, e);
		}
	
	}

	@Override
	public void init(IAssertion assertion, IExecutionContext executionContext, IContext context) throws WSMException {
		System.out.println("###### ACS Logging : INIT IpAsserter.....");
		this.assertion = assertion;
		this.econtext = econtext;
	}

	@Override
	public IResult postExecute(IContext messageContext) throws WSMException {
		IResult result = new Result();
		result.setStatus(IResult.SUCCEEDED);
		return result;

	}

	public boolean isAssertionEnabled() {
		return ((ISimpleOracleAssertion) this.assertion).isEnforced();
	}

	public String getAssertionName() {
		return this.assertion.getQName().toString();
	}

}
