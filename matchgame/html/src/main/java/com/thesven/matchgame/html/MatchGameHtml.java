package com.thesven.matchgame.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import com.thesven.matchgame.core.MatchGame;

public class MatchGameHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform platform = HtmlPlatform.register();
    platform.assets().setPathPrefix("matchgame/");
    PlayN.run(new MatchGame());
  }
}
