package be.alexandreliebh.picacademy.server;

import java.util.List;

import be.alexandreliebh.picacademy.data.PicConstants;
import be.alexandreliebh.picacademy.data.util.LoadingUtil;
import be.alexandreliebh.picacademy.server.game.PicGameManager;
import be.alexandreliebh.picacademy.server.net.PicNetServer;

/**
 * Point d'entrée du programme du Serveur Met en place le Socket pour recevoir
 * les requetes des clients Crée les parties pour acceuillir les joueurs Charge
 * les mots d'une liste hébergée sur le serveur
 * 
 * @author Alexandre Liebhaberg
 * 
 */
public class PicAcademyServer {

	private PicNetServer server;
	private PicGameManager gameManager;

	private static PicAcademyServer INSTANCE;

	private List<String> words;

//	private boolean running;

	private PicAcademyServer(String[] args) {
		INSTANCE = this;
//		this.running = true;

		int port = Integer.parseInt(args[0]);

		System.out.println(PicConstants.SERVER_CONSOLE_ART + "Server started on port " + port);

		try {
			// Lance la gérance du networking et ouvre un socket sur le port
			this.server = new PicNetServer(port);
			this.server.start();

			// Lance la gérance des parties et des joueurs
			this.gameManager = new PicGameManager();
			this.server.setManager(this.gameManager);

			// Charge les mots à partir du fichier words.csv
			if (!LoadingUtil.loadWords("words", words)) {
				System.err.println("ERROR WHILE LOADING THE WORDS");
			}

			// Affiche si le mode de débug est activé
			if (PicConstants.debugMode) {
				System.out.println("Debug mode : ON");
			} else {
				System.out.println("Debug mode : OFF");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new PicAcademyServer(args);
	}

	public PicNetServer getNetServer() {
		return server;
	}

	public PicGameManager getGameManager() {
		return gameManager;
	}

	public static PicAcademyServer getInstance() {
		return INSTANCE;
	}

	public List<String> getWords() {
		return words;
	}

}
