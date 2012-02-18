/**************************************************
 * Board - the board implementation of the game   *
 *                                                *
 * Author: Jacky, Chen                            *
 *                                                *
 * Last Modified: Feb, 18th, 2012                 *
 *                                                *
 * Version: 1.0                                   *
 *                                                *
 **************************************************/


package com.qqending.npuzzle;

import java.util.Random;

public class Board {
	
	//actual board representation
	private int boardConfig[];
	private int dimension;
	private int numTiles;
	private int blankTilePos;
	private int numMoves;
	
	public Board(int dimension) {
		
		this.dimension = dimension;
		this.numTiles = dimension * dimension;
		blankTilePos = numTiles - 1;
		numMoves = 0;
		
		boardConfig = new int[numTiles];
		
		//initialize the array with numbers from 1 to n^2
		
		for(int i = 0; i < numTiles; i++) {
			boardConfig[i] = i+1;
		}
		
		//set the blank tile
		boardConfig[blankTilePos] = 0;
	}
	
	//overloaded constructor
	public Board(int dimension, int[] config, int numMoves) {
		this(dimension);
		this.numMoves = numMoves;
		
		System.arraycopy(config, 0, boardConfig, 0, config.length);
		blankTilePos = findPosition(0);
	}
	
	public int getDimension() {
		return dimension;
	}
	
	//validate the move by checking if the tile the user clicked 
	//Adjacent to the blank one
	public boolean isValidMove(int id) {
		
		int position = findPosition(id);
		
		boolean valid = false;
		
		valid = valid || ((blankTilePos - 1) == position);
		valid = valid || ((blankTilePos + 1) == position);
		valid = valid || ((blankTilePos - dimension) == position);
		valid = valid || ((blankTilePos + dimension) == position);
		
		return valid;
	}
	
	//make a move on the board
	public void makeMove(int id) {
		
		int position = findPosition(id);
		
		swap(position,blankTilePos);
		
		blankTilePos = position;
		numMoves++;
	}
	
	//check if the board configuration is the goal config
	public boolean isWin() {
		
		boolean win = true;
		
		for(int i = 0; i < numTiles - 2; i++) {
			win = win && (boardConfig[i] < boardConfig[i+1]);
		}
		
		win = win && (boardConfig[numTiles - 1] == 0);
		
		return win;
	}
	
	//return the number of moves had been made
	public int getNumMoves() {
		return numMoves;
	}
	
	//shuffle the board with making random moves
	public void shuffle() {
		
		Random random = new Random();
		int position;
		
		for(int i = 0; i < 200 * dimension; i++) {
			position = random.nextInt(numTiles - 1);
			if(isValidMove(position)) {
				makeMove(position);
			}
		}
		
		numMoves = 0;
	}
	
	//return the board congfiguration in an array
	public int[] getBoardConfig(){
		
		int[] config = new int[numTiles];
		
		System.arraycopy(boardConfig, 0, config, 0, config.length);
		
		return config;
	}
	
	//swap the two tiles
	private void swap(int x, int y) {
		int temp = boardConfig[x];
		boardConfig[x] = boardConfig[y];
		boardConfig[y] = temp;
	}
	
	//return the corresponding position of a specific tile
	private int findPosition(int id) {
		
		int i = 0;
		boolean found = false;
		
		while(i < numTiles && !found) {
			if(boardConfig[i] == id) {
				found = true;
			} else {
				i++;
			}
		}
		
		return i;
	}
	
}
