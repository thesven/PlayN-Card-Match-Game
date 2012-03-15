package com.thesven.matchgame.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import com.thesven.matchgame.core.MatchGame;

public class MatchGameJava {

  public static void main(String[] args) {
    JavaPlatform platform = JavaPlatform.register();
    platform.assets().setPathPrefix("com/thesven/matchgame/resources");
    PlayN.run(new MatchGame());
  }
}
