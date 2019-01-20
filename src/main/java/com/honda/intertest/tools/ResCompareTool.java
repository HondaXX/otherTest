package com.honda.intertest.tools;

import java.util.Map;

import com.google.gson.internal.LinkedTreeMap;

public class ResCompareTool {
	@SuppressWarnings("unchecked")
	public static boolean compareRes(Map<String, Object> trueResMap, Map<String, Object> trueRxpectMap, String caseId) {
		for(String trueKey : trueResMap.keySet()) {
			if (trueRxpectMap.containsKey(trueKey)) {
				Object trueValue = trueResMap.get(trueKey);
				Object expectValue = trueRxpectMap.get(trueKey);
//				System.out.println(trueValue.getClass() + "====" + expectValue.getClass());
				if (!(trueValue instanceof LinkedTreeMap && expectValue instanceof LinkedTreeMap)) {
					if (!trueValue.equals(expectValue)) {
						System.out.println("====>caseid: " + caseId +  ", error with unequal value: (" + trueKey + ") retrunvalue: " + trueValue.toString());
						return false;
					}
				}else{
					Map<String, Object> trueValueMap = (Map<String, Object>) trueValue;
					Map<String, Object> expectValueMap = (Map<String, Object>) expectValue;
					for(String childTrueKey : trueValueMap.keySet()) {
						if (expectValueMap.containsKey(childTrueKey)) {
							Object childTrueValue = trueValueMap.get(childTrueKey);
							Object childExpectValue = expectValueMap.get(childTrueKey);
							if (!childTrueValue.equals(childExpectValue)) {
								System.out.println("====>caseid: " + caseId +  ", error with unequal value: (" + childTrueKey + ") returnvalue: " + childTrueValue.toString());
								return false;
							}
						}else {
							System.out.println("====>caseid: " + caseId + ", not exist key: " + childTrueKey);
							return false;
						}
					}
				}
			}else {
				System.out.println("====>caseid: " + caseId + ", not exist key: " + trueKey);
				return false;
			}
		}
		System.out.println("====>caseid: " + caseId + ", test pass!");
		return true;
	}
}
