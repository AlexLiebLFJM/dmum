package be.alexandreliebh.picacademy.data.game;

import java.util.ArrayList;
import java.util.List;

import be.alexandreliebh.picacademy.data.PicConstants;
import be.alexandreliebh.picacademy.data.net.PicSocketedUser;
import be.alexandreliebh.picacademy.data.ui.PicDrawingBoard;

public class PicGame {

	private final List<PicSocketedUser> users;
	private byte userCount;

	private PicGameState state;
	private final byte gameID;

	private final PicRound[] rounds;
	private byte roundID;

	private final PicDrawingBoard board;

	private PicGameInfo gameInfo;

	public PicGame(byte id) {
		this.users = new ArrayList<>(PicConstants.MAX_PLAYERS_PER_GAME);

		this.board = new PicDrawingBoard();

		this.rounds = new PicRound[PicConstants.AMOUNT_OF_ROUNDS * PicConstants.MAX_PLAYERS_PER_GAME];
		this.roundID = -1;

		this.gameID = id;

		this.gameInfo = new PicGameInfo(this.gameID);

		this.state = PicGameState.WAITING;
	}

	public PicGame addUser(PicSocketedUser user) {
		this.users.add(user);
		this.userCount++;

		System.out.println("[*] " + getIdentifier() + " += " + user.getIdentifier());

		return this;
	}

	public PicGame removeUser(PicUser user) {
		for (PicSocketedUser picSocketedUser : users) {
			if (picSocketedUser.getID() == user.getID()) {
				this.users.remove(picSocketedUser);
				this.userCount--;
				break;
			}
		}

		System.out.println("[*] " + getIdentifier() + " -= " + user.getIdentifier());

		return this;

	}

	public PicRound nextRound(PicRound round) {
		roundID++;

		round.setRoundId(roundID);
		rounds[roundID] = round;

		return round;
	}

	public PicRound getCurrentRound() {
		try {
			return this.rounds[roundID];
		} catch (Exception e) {
			return null;
		}
	}

	public void setState(PicGameState state) {
		this.state = state;
		System.out.println("Game (Id:" + this.gameID + ") [" + this.userCount + "/" + PicConstants.MAX_PLAYERS_PER_GAME + "] {" + (roundID + 1) + "/" + (getRoundAmount()) + "} is now " + state);
	}

	public void stop() {
		System.out.println("Game (Id:" + this.gameID + ") [" + this.userCount + "/" + PicConstants.MAX_PLAYERS_PER_GAME + "] is closing");
	}

	public boolean hasUser(short userID) {
		for (PicUser picUser : users) {
			if (picUser.getID() == userID) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		return "Game (Id:" + this.gameID + ") [" + this.userCount + "/" + PicConstants.MAX_PLAYERS_PER_GAME + "] {" + this.state + "}";
	}

	public String getIdentifier() {
		return "Game (ID:" + this.gameID + ")";
	}

	public List<PicSocketedUser> getUsers() {
		return users;
	}

	public byte getGameID() {
		return gameID;
	}

	public PicGameState getState() {
		return state;
	}

	public byte getUserCount() {
		return userCount;
	}

	public PicDrawingBoard getBoard() {
		return board;
	}

	public int getRoundAmount() {
		return rounds.length;
	}

	public PicGameInfo getGameInfo() {
		this.gameInfo.setState(state);
		this.gameInfo.setUserCount(userCount);
		return gameInfo;
	}

}
