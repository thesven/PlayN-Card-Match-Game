package com.thesven.matchgame.core.cards;

import static playn.core.PlayN.*;

import playn.core.GroupLayer;
import playn.core.ResourceCallback;
import com.thesven.matchgame.core.sprites.Sprite;
import com.thesven.matchgame.core.sprites.SpriteLoader;

public class Card {
  public static String IMAGE = "images/CardDeck.png";
  public static String JSON = "sprite-data/card.json";
  
  private static int SPRITE_BACK = 52;
  
  private int spriteIndex;
  private Sprite sprite;
  private float angle;
  private boolean hasLoaded = false; // set to true when resources have loaded and we can update

  public Card(final GroupLayer displayLayer, final float x, final float y) {
	  
	spriteIndex = SPRITE_BACK;
	  
    // Sprite method #1: use a sprite image and json data describing the sprites
    sprite = SpriteLoader.getSprite(IMAGE, JSON);
    
    sprite.addCallback(new ResourceCallback<Sprite>() {
      @Override
      public void done(Sprite sprite) {
        sprite.setSprite(spriteIndex);
        displayLayer.add(sprite.layer());
        hasLoaded = true;
      }

      @Override
      public void error(Throwable err) {
        log().error("Error loading image!", err);
      }
    });
  }
  
  public void setSpriteIndex(int index){
	  spriteIndex = index;
	  sprite.setSprite(index);
  }
  
  public void displayBack(){
	  spriteIndex = SPRITE_BACK;
	  sprite.setSprite(SPRITE_BACK);
  }
  
  public void updateCardPosition(int x, int y){
	  sprite.layer().setTranslation(x, y);
  }

  public void update(float delta) {
    
  }
  
}
