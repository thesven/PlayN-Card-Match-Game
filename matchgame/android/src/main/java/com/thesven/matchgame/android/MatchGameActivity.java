package com.thesven.matchgame.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import com.thesven.matchgame.core.MatchGame;

public class MatchGameActivity extends GameActivity {

  @Override
  public void main(){
    platform().assets().setPathPrefix("com/thesven/matchgame/resources");
    PlayN.run(new MatchGame());
  }
}
