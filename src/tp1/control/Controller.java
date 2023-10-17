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

		GamePrinter gamePrinter = new GamePrinter(game);
		System.out.println(gamePrinter);

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
							} else if ("lleft".equals(direction)) {
								move = Move.LLEFT;
							} else if ("right".equals(direction)) {
								move = Move.RIGHT;
							} else if ("rright".equals(direction)) {
								move = Move.RRIGHT;
							}
							game.moveUCMShip(move.getX(), move.getY());
//							game.enableLaser();
							game.moveRegAliens();
						}
						break;
					case "n":
						game.moveRegAliens();
						break;
					// Handle other commands here
					case "e":
						gameRunning = false; // Exit the game
						break;
					case "s":
						game.shootLaser();
						game.enableLaser();
						game.moveRegAliens();
						break;
					case "h":
						game.displayHelp();
						break;
				}
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
