package tp1.control;

import static tp1.view.Messages.debug;

import java.util.Scanner;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.view.GamePrinter;
import tp1.view.Messages;

/**
 *  Accepts user input and coordinates the game execution logic
 */
public class Controller {

	private Game game;
	private Scanner scanner;
	private GamePrinter printer;

	public Controller(Game game, Scanner scanner) {
		this.game = game;
		this.scanner = scanner;
		printer = new GamePrinter(game);
	}

	/**
	 * Show prompt and request command.
	 *
	 * @return the player command as words
	 */
	private String[] prompt() {
		System.out.print(Messages.PROMPT);
		String line = scanner.nextLine();
		String[] words = line.toLowerCase().trim().split("\\s+");

		System.out.println(debug(line));

		return words;
	}

	/**
	 * Runs the game logic
	 */
	public void run() {
		boolean gameRunning = true;
		boolean laserIsEnabled = false;

		GamePrinter gamePrinter = new GamePrinter(game);

		while (gameRunning) {
			System.out.println(gamePrinter);
			String[] input = prompt();

			if (input.length > 0) {
				switch (input[0]) {
					case "m":
						if (input.length > 1) {
							String direction = input[1].toLowerCase();
							Move move = Move.NONE; // Default to no movement

							if ("left".equals(direction)) {
								move = Move.LEFT;
								game.updateGame();
							} else if ("lleft".equals(direction)) {
								move = Move.LLEFT;
								game.updateGame();
							} else if ("right".equals(direction)) {
								move = Move.RIGHT;
								game.updateGame();
							} else if ("rright".equals(direction)) {
								move = Move.RRIGHT;
								game.updateGame();
							}
							game.moveUCMShip(move.getX(), move.getY());
						}
						break;
					case "":
					case "n":
						game.updateGame();
						break;
					// Handle other commands here
					case "e":
						gameRunning = false; // Exit the game
						break;
					case "w":
						game.shockWave();
						game.updateGame();
						break;
					case "s":
						game.incrementCycles();
						laserIsEnabled = true;
						game.shootLaser();
						game.enableLaser();
						game.moveAliens();
						break;
					case "h":
						game.displayHelp();
						break;
					case "l":
						System.out.println(String.format(Messages.UCM_DESCRIPTION,Messages.UCMSHIP_DESCRIPTION,1,3));
						System.out.println(String.format(Messages.ALIEN_DESCRIPTION,Messages.REGULAR_ALIEN_DESCRIPTION,5,0,2));
						System.out.println(String.format(Messages.ALIEN_DESCRIPTION,Messages.DESTROYER_ALIEN_DESCRIPTION,10,1,1));
						System.out.println(String.format(Messages.ALIEN_DESCRIPTION,Messages.UFO_DESCRIPTION,25,0,1));
						break;
					case "r":
						game.reset();
						break;
					default:
						System.out.println(Messages.UNKNOWN_COMMAND);
				}
			}
			if (game.aliensWin()){
				System.out.println(Messages.ALIENS_WIN);
				System.out.println(gamePrinter);
				gameRunning = false;

			} else if (game.playerWin()){
				System.out.println(Messages.PLAYER_WINS);
				System.out.println(gamePrinter);
				gameRunning = false;

			}
		}

	}

	/**
	 * Draw / paint the game
	 */
	private void printGame() {
		System.out.println(printer);
	}
	
	/**
	 * Prints the final message once the game is finished
	 */
	public void printEndMessage() {
		System.out.println(printer.endMessage());
	}
	
}
