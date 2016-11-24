package synapticloop.newznab.api;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

/*
 * Copyright (c) 2016 Synapticloop.
 * 
 * All rights reserved.
 * 
 * This code may contain contributions from other parties which, where 
 * applicable, will be listed in the default build file for the project 
 * ~and/or~ in a file named CONTRIBUTORS.txt in the root of the project.
 * 
 * This source code and any derived binaries are covered by the terms and 
 * conditions of the Licence agreement ("the Licence").  You may not use this 
 * source code or any derived binaries except in compliance with the Licence.  
 * A copy of the Licence is available in the file named LICENSE.txt shipped with 
 * this source code or binaries.
 */

public class HttpUtils {
	public static CloseableHttpClient getDefaultClient() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		return(HttpClients.custom()
				.setSSLHostnameVerifier(
						new NoopHostnameVerifier())
				.setSSLContext(new SSLContextBuilder()
						.loadTrustMaterial(null, 
								(x509Certificates, s) -> true).build()).build());
	}
}
