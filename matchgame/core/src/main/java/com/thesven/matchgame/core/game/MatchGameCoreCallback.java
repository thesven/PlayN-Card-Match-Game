package com.thesven.matchgame.core.game;

import playn.core.util.Callback;

public interface MatchGameCoreCallback extends Callback<String> {
	void onStartMatchSearch();
	void onEndMatchSearch();
	boolean onMatch();
}
