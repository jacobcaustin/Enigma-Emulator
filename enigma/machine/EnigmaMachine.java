package enigma.machine;

/**
 * Simulates an Enigma machine with the following structure:
 * || reflector | rotor1 | rotor2 | rotor3 ||
 * As with a standard Enigma machine, input goes through:
 * input -> plugboard -> rotor[2] (RightToLeft) -> rotor[1] (RightToLeft) -> rotor[0] (RightToLeft) -> reflector 
 * -> rotor[0] (LeftToRight) -> rotor[1] (LeftToRight) -> rotor[2] (LeftToRight) -> plugboard -> output
 * Rotor3 turns before a char is encrypted
 * @author johnrizkalla
 *
 */
public class EnigmaMachine {
	private Rotor[] rotor; // 3 rotors
	private Reflector reflector;
	private Plugboard plugboard;
	
	private void connectRotors(){
		if (rotor[0] != null && rotor[1] != null && rotor[2] != null){
			rotor[2].setConnection(rotor[1]);
			rotor[1].setConnection(rotor[0]);
		}
	}
	
	/**
	 * Creates a new enigma machine with the parts passed in
	 * @param rotors rotor[i] has 3 rotors. rotor[0] is the one at the left and rotor[2] is the one at the right. They don't have to be connected
	 * @param reflector the reflector
	 * @param plugboard the plugboard
	 */
	public EnigmaMachine(Rotor[] rotors, Reflector reflector, Plugboard plugboard){
		if (rotors != null){
			if (rotors.length != 3)
				throw new IllegalArgumentException();
			rotor = rotors;
		}
		
		this.reflector = reflector;
		this.plugboard = plugboard;
	}
	
	/**
	 * Creates an empty EnigmaMachine
	 */
	public EnigmaMachine(){
		this(null, null, null);
	}
	
	/**
	 * @return the rotors
	 */
	public Rotor getRotor(int rotorNum) {
		return rotor[rotorNum];
	}

	/**
	 * @param rotor the rotor to set
	 */
	public void switchRotor(int rotorNum, Rotor rotor) {
		if (this.rotor == null)
			this.rotor = new Rotor[3];
		this.rotor[rotorNum] = rotor;
		this.connectRotors();
	}

	/**
	 * @return the reflector
	 */
	public Reflector getReflector() {
		return reflector;
	}

	/**
	 * @param reflector the reflector to set
	 */
	public void switchReflector(Reflector reflector) {
		this.reflector = reflector;
	}

	/**
	 * @return the plugboard
	 */
	public Plugboard getPlugboard() {
		return plugboard;
	}
	
	/**
	 * @param plugboard the plugboard to set
	 */
	public void switchPlugboard(Plugboard plugboard){
		this.plugboard = plugboard;
	}

	/**
	 * Encryptes the input. For this to worlk, all parts must. Throws an exception if any of the parts are null
	 * @param input the input to be encrypted
	 * @return the encrypted char
	 */
	public char encrypt(char input){
		if (rotor[0] == null || rotor[1] == null || rotor[2] == null)
			throw new IllegalStateException();
		if (plugboard == null || reflector == null)
			throw new IllegalStateException();
		
		// make sure that input is a letter
		if ((input >= 'A' && input <= 'Z') || (input >= 'a' && input <= 'z')){
		
		rotor[2].rotate();
//		System.err.print("input: " + input);
		input = plugboard.map(input);
//		System.err.print(" -> " + input);
		input = rotor[0].translateRightToLeft(rotor[1].translateRightToLeft(rotor[2].translateRightToLeft(input)));
//		System.err.print(" -> " + input);
		input = reflector.map(input);
//		System.err.print(" -> " + input);
		input = rotor[2].translateLeftToRight(rotor[1].translateLeftToRight(rotor[0].translateLeftToRight(input)));
//		System.err.print(" -> " + input);
		input = plugboard.map(input);
//		System.err.println (" -> " + input);
		
		return input;
		}

		return input;
	}
	
	public String encrypt(String input){
		char [] result = new char[input.length()];
		char [] inputArr = input.toCharArray();
		
		for (int i = 0; i < inputArr.length; i++){
			result[i] = encrypt(inputArr[i]);
		}
		
		return new String(result);
	}
}
