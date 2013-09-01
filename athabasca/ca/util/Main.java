package athabasca.ca.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import athabasca.ca.model.User;
import com.project.rest.user.V2_user;

public class Main
{
	private static int MAX_PHONES = 10;
	private static int MAX_EMAILS = 10;
	
	private enum Birthday
	{ 
		DAY( 0 ), MONTH( 1 ), YEAR( 2 );
		private int value;
		
		private Birthday( int value )
		{
			this.value = value;
		}
		
		public int Value( )
		{
			return value;
		}
	};
	
	public static void main( String[] args ) throws Exception
	{
		Scanner scanner = new Scanner( System.in ) ;
		String input = "";
		do
		{
			//clearConsole( );
			System.out.println( "Select your option:\n" + 
					"[C]reate - for User insertion\n" +
					"[R]etrieve - for User selection\n" +
					"[U]pdate - for User information saving\n" +
					"[D]elete - for User information removal\n" +
					"[Q]uit - for exiting the application\n");
			
			input = scanner.next( );
			System.out.println( "------------------------------------------- \n\n" );
			
			handleInput( input, scanner );
		}while( input.compareTo( "Q" ) != 0 && input.compareTo( "q" ) != 0 );
		
		scanner.close( );
		
		System.exit( 0 );

	}
	
	private static void handleInput( String input, Scanner scanner ) throws Exception
	{
		switch( input )
		{
			case "C":
			case "c":
			{
				userCreation( scanner );
			}
			break;
			
			case "R":
			case "r":
			{
				userRetrieval( scanner );
;			}
			break;
			
			case "U":
			case "u":
			{
				
			}
			break;
			
			case "D":
			case "d":
			{
				userDelete( scanner );
			}
			break;
			
			case "Q":
			case "q":
			{
				System.out.println( "Application exit..." );
			}
			break;
			
			default:
			{
				System.err.println( "WARNING: Invalid command!" );
			}
			break;
		}
	}

	private static void userCreation( Scanner scanner ) throws Exception
	{
		User user = new User();
		
		System.out.println( "C: User insertion\n" );
		System.out.println( "Insert user information... " );
		
		System.out.println( "ID: " );
		if( scanner.hasNextBigInteger( ) )
		{
			user.setUserId( scanner.nextLong( ) );
		}
		else
		{
			System.err.println( "ERROR: Invalid field!" );
			System.exit( 0 );
		}
		
		System.out.println( "Password: " );
		if( scanner.hasNext( ) )
		{
			user.setPassword( scanner.next( ) );
		}
		else
		{
			System.err.println( "ERROR: Invalid field!" );
			System.exit( 0 );
		}
		
		System.out.println( "First Name: " );
		if( scanner.hasNext( ) )
		{
			user.setFirstName( scanner.next( ) );
		}
		else
		{
			System.err.println( "ERROR: Invalid field!" );
			System.exit( 0 );
		}
		
		System.out.println( "Last Name: " );
		if( scanner.hasNext( ) )
		{
			user.setLastName( scanner.next( ) );
		}
		else
		{
			System.err.println( "ERROR: Invalid field!" );
			System.exit( 0 );
		}
		
		System.out.println( "Birthday: " );
		if( scanner.hasNext( ) )
		{
			String[] dateFields = scanner.next( ).split( "/" );
			Calendar birthday = Calendar.getInstance( );
			birthday.set( 	Integer.parseInt( dateFields[ Birthday.DAY.Value( ) ] ),
							Integer.parseInt( dateFields[ Birthday.MONTH.Value( ) ] ),
							Integer.parseInt( dateFields[ Birthday.YEAR.Value( )  ] ) );
			user.setBirthday( birthday.toString() );
		}
		else
		{
			System.err.println( "ERROR: Invalid field!" );
			System.exit( 0 );
		}
		
		int i = 0;
		int numberOfPhones = 0;
		String[] phones = new String[ MAX_PHONES ]; 
		System.out.println( "Number of phones: " );
		if( scanner.hasNextBigInteger( ) )
		{
			numberOfPhones = scanner.nextInt( );
			for(; i < numberOfPhones; ++i )
			{
				System.out.println( "Phone " + ( i + 1 ) + ": " );
				if( scanner.hasNext( ) )
				{
					phones[ i ] = ( scanner.next( ) );
				}
				else
				{
					System.err.println( "ERROR: Invalid field!" );
					System.exit( 0 );
				}
			}
			
			if( numberOfPhones > 0 && i > 0 )
			{
				user.setTelephones( phones );
			}
		}
		else
		{
			System.err.println( "ERROR: Invalid field!" );
			System.exit( 0 );
		}
		
		i = 0;
		int numberOfEmails = 0;
		String[] emails = new String[ MAX_EMAILS ]; 
		System.out.println( "Number of emails: " );
		if( scanner.hasNextBigInteger( ) )
		{
			numberOfEmails = scanner.nextInt( );
			for(; i < numberOfEmails; ++i )
			{
				System.out.println( "Email " + ( i + 1 ) + ": " );
				if( scanner.hasNext( ) )
				{
					emails[ i ] = ( scanner.next( ) );
				}
				else
				{
					System.err.println( "ERROR: Invalid field!" );
					System.exit( 0 );
				}
			}
			
			if( numberOfEmails > 0 && i > 0 )
			{
				user.setEmails( emails );
			}
		}
		else
		{
			System.err.println( "ERROR: Invalid field!" );
			System.exit( 0 );
		}
		
		System.out.println( "Other preferences (type everything and press enter): " );
		if( scanner.hasNextLine( ) )
		{
			user.setOtherPreferences( scanner.nextLine( ) );
		}
		else
		{
			System.err.println( "ERROR: Invalid field!" );
			System.exit( 0 );
		}
		
		System.out.println( "\n\n" + user.toString( ) );
		V2_user insertUser = new V2_user();
		boolean isTrue = insertUser.create(user);
		
		if (isTrue == true) {
			System.out.println("Creation was a success!");
		} else {
			System.out.println("User creation failed!");
		}
		
		scanner.reset( );
	}
	
	private static void userRetrieval( Scanner scanner ) throws Exception
	{
		V2_user selectUser = new V2_user();
		long selection = 0;
		
		System.out.println( "R: User selection\n" );
		System.out.println( "Insert user id... " );
		
		if( scanner.hasNextBigInteger( ) )
		{
			selection = scanner.nextLong( );
		}
		else
		{
			System.err.println( "ERROR: Invalid field!" );
			System.exit( 0 );
		}
		
		User specificUser = selectUser.select(selection);
		
		if (specificUser != null) {
			System.out.println("Id: " + specificUser.getUserId());
			System.out.println("First Name: " + specificUser.getFirstName());
			System.out.println("Last Name: " + specificUser.getLastName());
			System.out.println("Birthday: " + specificUser.getBirthday());
			System.out.println("Other Preferences: " + specificUser.getOtherPreferences());
			
			for (int i = 0; i < specificUser.getTelephones().length; i++) {
				System.out.println("Telephone: " + specificUser.getTelephones()[i]);
			}
			
			for (int i2 = 0; i2 < specificUser.getEmails().length; i2++) {
				System.out.println("Email: " + specificUser.getEmails()[i2]);
			}
			
		} else {
			System.err.println("Could not find user!");
		}
		
		scanner.reset();
	}
	
	private static void userDelete( Scanner scanner ) throws Exception
	{
		V2_user deleteUser = new V2_user();
		long selection = 0;
		
		System.out.println( "D: User deletion\n" );
		System.out.println( "Insert user id... " );
		
		if( scanner.hasNextBigInteger( ) )
		{
			selection = scanner.nextLong( );
		}
		else
		{
			System.err.println( "ERROR: Invalid field!" );
			System.exit( 0 );
		}
		
		boolean isTrue = deleteUser.delete(selection);
		
		if (isTrue == true) {
			System.out.println("User has been deleted!");
		} else {
			System.out.println("User was not found!");
		}
		
		scanner.reset();
	}
	
	
	
	/* this method does not work
	private static void clearConsole( )
	{
	    try
	    {
	        String os = System.getProperty( "os.name" );

	        if ( os.contains( "Windows" ) )
	        {
	            Runtime.getRuntime( ).exec( "cls" );
	        }
	        else
	        {
	            Runtime.getRuntime( ).exec( "clear" );
	        }
	    }
	    catch ( Exception exception )
	    {
	        System.out.println( exception.getMessage( ) );
	    }
	}
	*/

}




