package com.thesven.matchgame.core.game;

import static playn.core.PlayN.*;

import java.util.Random;

import javax.jws.Oneway;

import com.thesven.matchgame.core.cards.Card;
import com.thesven.matchgame.core.time.SimpleTimer;

import playn.core.GroupLayer;
import playn.core.util.Callback;

public class MatchGameCore {

	public int totalCards;
	public int totalColumns;
	public int cardXSpace;
	public int cardYSpace;
	public int cardXOffset;
	public int cardYOffset;

	private Card cards[];
	private Card selectedCardA;
	private Card selectedCardB;
	private SimpleTimer time;
	private GroupLayer gameLayer;
	private MatchGameCoreCallback mainCallback;

	public MatchGameCore(GroupLayer cardDisplayLayer,final int maxCards,final int maxColumns,final int cardXSpacing,final int CardYSpacing,final int cardXoffset,final int cardYoffset, MatchGameCoreCallback gameCallback){

		totalCards = maxCards;
		totalColumns = maxColumns;
		cardXSpace = cardXSpacing;
		cardYSpace = CardYSpacing;
		cardXOffset = cardXoffset;
		cardYOffset = cardYoffset;
		gameLayer = cardDisplayLayer;
		mainCallback = gameCallback;

	}
	
	public void init(){
		createCards();
	}
	
	public void checkForCardHit(float x, float y) {

		for(int i = 0; i < totalCards; i++){

			Card card = (Card) cards[i];
			if(!card.removed && !card.showingFront){

				if(card.checkCardClick(x, y)){
					if(selectedCardA == null){
						log().debug("Setting Card A "+card.spriteIndex);
						selectedCardA = card;
						return;
					} else if (selectedCardA != null && selectedCardB == null){
						log().debug("Setting Card B "+card.spriteIndex);
						selectedCardB = card;
						break;
					}

				}

			}

		}

		if(selectedCardA != null && selectedCardB != null){

			log().debug("you should check for a match now ... starting the timer");
			
			mainCallback.onStartMatchSearch();
			time = new SimpleTimer(650, new Callback<String>(){
				@Override
				public void onSuccess(String result) {
					checkForMatch(selectedCardA, selectedCardB);
				}
				@Override
				public void onFailure(Throwable cause) {

				}
			});

			time.start();

		}


	}

	public void update(float delta){
		if(time != null){
			time.update(delta);
		}
	}

	private void createCards(){

		cards = new Card[totalCards];
		int cardIndex = 0;
		for(int i = 0; i < totalCards; i++){

			Card newcard = new Card(gameLayer, 0, 0);
			Card newcard2 = new Card(gameLayer, 0, 0);

			cards[i] = newcard;
			i++;
			cards[i] = newcard2;

			newcard.setSpriteIndex(cardIndex);
			newcard2.setSpriteIndex(cardIndex);

			cardIndex++;

		}

		//shuffle the cards
		Random rgen = new Random();
		for(int j = 0; j < totalCards; j++){
			int randomPosition = rgen.nextInt(totalCards);
			Card temp = cards[j];
			cards[j] = cards[randomPosition];
			cards[randomPosition] = temp;
		}

		layoutCards();

	}

	private void layoutCards(){

		for(int i = 0; i < totalCards; i++){

			int row = (int) Math.floor(i / totalColumns);
			int col = i % totalColumns;

			int x = (col * cardXSpace) + cardXOffset;
			int y = (row * cardYSpace) + cardYOffset;

			Card card = (Card) cards[i];
			card.updateCardPosition(x, y);

		}

	}

	private void checkForMatch(Card cardA, Card cardB) {

		log().debug("Checking for a matching pair");

		if(cardA.spriteIndex == cardB.spriteIndex){
			// you have found a match
			cardA.removeCard(gameLayer);
			cardB.removeCard(gameLayer);
			mainCallback.onMatch();
		} else {
			cardA.displayBack();
			cardB.displayBack();
		}

		selectedCardA = null;
		selectedCardB = null;
		time = null;
		mainCallback.onEndMatchSearch();
	}


}
