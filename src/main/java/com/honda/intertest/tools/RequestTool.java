package com.honda.intertest.tools;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class RequestTool {
	public static StringEntity setEntity(String reqStr, String caseId) {
		StringEntity se = null;
		try {
			se = new StringEntity(reqStr);
			se.setContentEncoding("UTF-8");
			se.setContentType("application/json");
			return se;
		} catch (UnsupportedEncodingException e1) {
			System.out.println("====>set Entity error: " + caseId);
			e1.printStackTrace();
			return null;
		}
	}
	
	public static String requestServer(String requestMethod, String requestUrl, String caseId, StringEntity se ) {
		CloseableHttpClient chc = HttpClients.createDefault();
		if (requestMethod.equals("0")) {
			HttpPost hp = new HttpPost(requestUrl);
			CloseableHttpResponse chr = null;
			hp.setEntity(se);
			try {
				//excute request here
				chr = chc.execute(hp);
			} catch (Exception e) {
				System.out.println("====>url request error which caseId: " + caseId);
				e.printStackTrace();
				return null;
			}
			HttpEntity he = chr.getEntity();
			if (he != null) {
				String resStr = null;
				try {
					resStr = EntityUtils.toString(he);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			return resStr;
			}
			if (!(null == chr)) {
				try {
					chr.close();
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("====>close response error which caseid: " + caseId);
					return null;
				}
			}
		}
		if (!(null == chc)) {
			try {
				chc.close();
			} catch (IOException e) {
				System.out.println("====>close request error which caseid: " + caseId);
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
}
