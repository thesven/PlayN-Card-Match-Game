package com.thesven.matchgame.core.cards;

import static playn.core.PlayN.log;

import playn.core.GroupLayer;
import playn.core.ResourceCallback;
import com.thesven.matchgame.core.sprites.Sprite;
import com.thesven.matchgame.core.sprites.SpriteLoader;

public class Card {
  public static String IMAGE = "images/CardDeck.png";
  public static String JSON = "sprite-data/card.json";
  private Sprite sprite;
  private int spriteIndex = 52;
  private float angle;
  private boolean hasLoaded = false; // set to true when resources have loaded and we can update

  public Card(final GroupLayer peaLayer, final float x, final float y) {
    // Sprite method #1: use a sprite image and json data describing the sprites
    sprite = SpriteLoader.getSprite(IMAGE, JSON);
    
    sprite.addCallback(new ResourceCallback<Sprite>() {
      @Override
      public void done(Sprite sprite) {
        sprite.setSprite(spriteIndex);
        sprite.layer().setOrigin(sprite.width() / 2f, sprite.height() / 2f);
        sprite.layer().setTranslation(x, y);
        peaLayer.add(sprite.layer());
        hasLoaded = true;
      }

      @Override
      public void error(Throwable err) {
        log().error("Error loading image!", err);
      }
    });
  }

  public void update(float delta) {
    if (hasLoaded) {
      if (Math.random() > 0.95) {
        spriteIndex = (spriteIndex + 1) % sprite.numSprites();
        sprite.setSprite(spriteIndex);
      }
      angle += delta;
      sprite.layer().setRotation(angle);
    }
  }
}
