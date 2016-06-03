package com.mrprez.roborally.client;

public interface MoveAnimation {

	void update(double progress);

	void onStart();

	void onComplete();

}
