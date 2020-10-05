import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class NumGuessHW 
{
	private int level = 1;
	private int strikes = 0;
	private int maxStrikes = 10;
	private int number = 0;
	private boolean isRunning = false;
	final String saveFile = "numberGuesserSave.txt";

	/***
	 * Gets a random number between 1 and level.
	 * 
	 * @param level (level to use as upper bounds)
	 * @return number between bounds
	 */
	public static int getNumber(int level) {
		int range = 9 + ((level - 1) * 5);
		System.out.println("I picked a random number between 1-" + (range + 1) + ", let's see if you can guess.");
		return new Random().nextInt(range) + 1;
	}

	private void win() {
		System.out.println("That's right!");
		level++;// level up!
		saveLevel();
		strikes = 0;
		System.out.println("Welcome to level " + level);
		number = getNumber(level);
	}

	private void lose() {
		System.out.println("Uh oh, looks like you need to get some more practice.");
		System.out.println("The correct number was " + number);
		strikes = 0;
		level--;
		if (level < 1) {
			level = 1;
		}
		saveLevel();
		number = getNumber(level);
	}

	private void processCommands(String message) {
		if (message.equalsIgnoreCase("quit")) {
			System.out.println("Tired of playing? No problem, see you next time.");
			isRunning = false;
		}
	}

	private void processGuess(int guess) {
		saveNum();
		if (guess < 0) {
			return;
		}
		System.out.println("You guessed " + guess);
		if (guess == number) {
			win();
		} else {
			System.out.println("That's wrong");
			strikes++;
			saveStrikes();
			if (strikes >= maxStrikes) {
				lose();
			} else {
				int remainder = maxStrikes - strikes;
				System.out.println("You have " + remainder + "/" + maxStrikes + " attempts remaining");
				if (guess > number) {
					System.out.println("Lower");
				} else if (guess < number) {
					System.out.println("Higher");
				}
			}
		}
	}

	private int getGuess(String message) {
		int guess = -1;
		try {
			guess = Integer.parseInt(message);
		} catch (NumberFormatException e) {
			System.out.println("You didn't enter a number, please try again");

		}
		return guess;
	}
	
	private void saveNum() {
		try (FileWriter f = new FileWriter(saveFile)) {
			f.write("" + number);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private boolean loadNum() {
		File file = new File(saveFile);
		if (!file.exists()) {
			return false;
		}
		try (Scanner reader = new Scanner(file)) {
			while (reader.hasNextLine()) {
				int N = reader.nextInt();
				if (N >= 1) {
					number = N;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e2) {
			e2.printStackTrace();
			return false;
		}
		return number >= 1;
	}

	private void saveLevel() {
		try (FileWriter fw = new FileWriter(saveFile)) {
			fw.write("" + level);// here we need to convert it to a String to record correctly
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean loadLevel() {
		File file = new File(saveFile);
		if (!file.exists()) {
			return false;
		}
		try (Scanner reader = new Scanner(file)) {
			while (reader.hasNextLine()) {
				int _level = reader.nextInt();
				if (_level > 1) {
					level = _level;
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e2) {
			e2.printStackTrace();
			return false;
		}
		return level > 1;
	}
	
	private void saveStrikes()
	{
		try (FileWriter F = new FileWriter(saveFile)) {
			if(strikes < maxStrikes)
			{
				F.write("" + strikes);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean loadStrikes()
	{
		File file = new File(saveFile);
		if (!file.exists()) {
			return false;
		}
		try (Scanner reader = new Scanner(file)) {
			while (reader.hasNextLine()) {
				int S = reader.nextInt();
				if(S > 0 && S < maxStrikes)
				{
					strikes = S;
				}
			}
			
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e2) {
			e2.printStackTrace();
			return false;
		}
		return strikes > 0;
	}
	

	void run() {
		try (Scanner input = new Scanner(System.in);) {
			System.out.println("Welcome to Number Guesser");
			System.out.println("I'll ask you to guess a number between a range, and you'll have " + maxStrikes
					+ " attempts to guess.");
			if (loadLevel()) {
				System.out.println("Successfully loaded level " + level + " let's continue then");
			}
			if (loadStrikes())
			{
				System.out.println("You have " + strikes + " stikes");
			}
			number = getNumber(level);
			isRunning = true;
			while (input.hasNext()) {
				String message = input.nextLine();
				processCommands(message);
				if (!isRunning) {
					break;
				}
				int guess = getGuess(message);
				processGuess(guess);
			}
					
			loadNum();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		NumGuessHW guesser = new NumGuessHW();
		guesser.run();
	}
}
