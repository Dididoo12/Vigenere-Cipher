/*

Author: Edward Tang
Date: October 20, 2018
Purpose: This program is designed to use the Vigenere cipher to encrypt and decrypt text.

*/

// The "Crypto" class.
import java.awt.*;
import hsa.*;

public class Crypto
{
    static Console c;           // The output console

    public static void main (String[] args)
    {
	c = new Console (21, 110, "Cryptography");

	String restart;
	do
	{
	    c.print("Enter the encryption key: ");
	    String key = c.readLine();
	    Vigenere cipher = new Vigenere (key);

	    c.print ("Enter the text file name for input: ");
	    String fileName = c.readLine ();
	    c.println ("Encrypted text file address: " + cipher.encrypt (fileName));

	    c.println ("Decrypted text file address: " + cipher.decrypt (fileName));

	    c.println ();
	    c.print ("Enter anything to perform another cipher, or enter 'stop' to stop the program: ");
	    restart = c.readString ();
	    c.println ();
	}
	while (!restart.equals ("stop"));

	// Place your program here.  'c' is the output console
    } // main method
} // Crypto class

/*

Author: Edward Tang
Date: October 20, 2018
Purpose: This class is designed to receive and output text in a newspaper format, including with files.
Fields:
    key            The encryption key
Methods:
    constructor
    encrypt        Reads a file from a given file name (expected to be a text file), encrypts it and returns the file name of the encrypted .cyp file
    encryptLine    Returns given text in encrypted form
    decrypt        Reads a file from a given file name (expected to be a .cyp file), decrypts it and returns the file name of the decrypted .pln file
    decryptLine    Returns given encrypted text in its decrypted (original) form
*/
class Vigenere
{
    String key;

    /*

    Author: Edward Tang
    Date: October 20, 2018
    Purpose: This is the constructor for the Vigenere class, with a String parameter for the encryption key.
    Input: [String] The encryption key
    Output: N/A

    */

    public Vigenere (String key)
    {
	this.key = key;
    }


    /*

    Author: Edward Tang
    Date: October 20, 2018
    Purpose: This is the constructor for the Vigenere class, with no parameters (default no encryption key).
    Input: N/A
    Output: N/A

    */

    public Vigenere ()
    {
	this ("");
    }


    /*

    Author: Edward Tang
    Date: October 20, 2018
    Purpose: This is the clone constructor for the Vigenere class, with a parameter for a Vigenere object.
    Input: [Vigenere] The object to clone
    Output: N/A

    */

    public Vigenere (Vigenere oldObject)
    {
	this (oldObject.key);
    }


    /*

    Author: Edward Tang
    Date: October 20, 2018
    Purpose: This method is designed to read a file from a given file name (expected to be a text file), encrypt it and return the file name of the encrypted .cyp file.
    Input: [String] The file name of the file to encrypt
    Output: [String] The file name of the encrypted file

    */

    public String encrypt (String fileName)
    {
	TextInputFile input = new TextInputFile (fileName + ".txt");

	String cipherName = fileName + ".cyp";
	TextOutputFile output = new TextOutputFile (cipherName);

	while (!input.eof ())
	    output.println (encryptLine (input.readLine ()));
	input.close ();
	output.close ();

	return cipherName;
    }


    /*

    Author: Edward Tang
    Date: October 20, 2018
    Purpose: This method is designed to return given text in encrypted form (the text is encrypted twice).
    Input: [String] The text to be encrypted
    Output: [String] The encrypted text

    */

    public String encryptLine (String line)
    {
	StringBuffer buffLine = new StringBuffer (line);

	for (int x = 1 ; x <= 2 ; x++)
	{
	    int keyIndex = 0;
	    for (int i = 0 ; key.length () > 0 && i < buffLine.length () ; i++)
	    {
		int newChar = buffLine.charAt (i) + key.charAt (keyIndex) - 32;
		if (newChar > 126)
		    newChar -= 95;
		buffLine.setCharAt (i, (char) (newChar));

		keyIndex++;
		if (keyIndex > key.length () - 1)
		    keyIndex = 0;
	    }
	}
	return buffLine.toString ();
    }


    /*

    Author: Edward Tang
    Date: October 20, 2018
    Purpose: This method is designed to read a file from a given file name (expected to be a .cyp file), decrypt it and return the file name of the decrypted .pln file.
    Input: [String] The file name of the file to decrypt
    Output: [String] The file name of the decrypted file

    */

    public String decrypt (String fileName)
    {
	TextInputFile input = new TextInputFile (fileName + ".cyp");

	String outputName = fileName + ".pln";
	TextOutputFile output = new TextOutputFile (outputName);

	while (!input.eof ())
	    output.println (decryptLine (input.readLine ()));
	input.close ();
	output.close ();

	return outputName;
    }


    /*

    Author: Edward Tang
    Date: October 20, 2018
    Purpose: This method is designed to return given encrypted text in its decrypted (original) form
    Input: [String] The text to be decrypted
    Output: [String] The decrypted (original) text

    */

    public String decryptLine (String line)
    {
	StringBuffer buffLine = new StringBuffer (line);

	for (int x = 1 ; x <= 2 ; x++)
	{
	    int keyIndex = 0;
	    for (int i = 0 ; key.length () > 0 && i < buffLine.length () ; i++)
	    {
		int newChar = buffLine.charAt (i) - key.charAt (keyIndex) + 32;
		if (newChar < 32)
		    newChar += 95;
		buffLine.setCharAt (i, (char) (newChar));

		keyIndex++;
		if (keyIndex > key.length () - 1)
		    keyIndex = 0;
	    }
	}
	return buffLine.toString ();
    }
}
