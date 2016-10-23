package com.sumavision.branch.mycrack;

import java.util.HashMap;

public interface CrackCompleteListener {
	void onCrackComplete(CrackResult result);
	void onCrackFailed(HashMap<String, String> arg0);
	void onJarDownLoading(int process);
}
