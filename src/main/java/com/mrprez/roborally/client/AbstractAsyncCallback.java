package com.mrprez.roborally.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;

public abstract class AbstractAsyncCallback<T> implements AsyncCallback<T> {

	@Override
	public void onFailure(Throwable caught) {
		DialogBox dialogBox = new DialogBox(false, true);
		dialogBox.setTitle(caught.getMessage());
		dialogBox.setText(caught.getMessage());
		for(StackTraceElement stackEl : caught.getStackTrace()){
			String stackTraceElText = "at "+stackEl.getClassName()+"."+stackEl.getMethodName()
					+"("+stackEl.getFileName()+":"+stackEl.getLineNumber()+")";
			dialogBox.add(new Label(stackTraceElText));
		}
		
		dialogBox.center();
		
	}
}
