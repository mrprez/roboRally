package com.mrprez.roborally.client.animation;

public interface UnitAnimation {

	void update(double progress);

	void onStart();

	void onComplete();

}
