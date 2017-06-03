package br.cefet.email;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		Map<Long, Person> people = new HashMap<Long, Person>();
		BufferedReader br = null;
		BufferedWriter bw = null;
		int exit = 0;
		String line, column[];
		
		//reads file and get
		try{
			bw = new BufferedWriter(new FileWriter(new File(args[1])));
			br = new BufferedReader(new InputStreamReader(
				    new FileInputStream(args[0]), "UTF-8"));
			//gets rid of the first line (we don't actually need that)
			br.readLine();
			while((line = br.readLine()) != null){
				column = line.split(",");
				//creates a person removing dots (.) and quotes (") from names
				Person p = new Person(Long.parseLong(column[0]), 
						column[1].replaceAll("\"", "").split(" "),
						column[2].replaceAll("\"", "").split(" "));
				people.put(p.getId(), p);
			}
		} catch(FileNotFoundException e){
			System.out.println("File " + args[0] + " not found");
			exit = 1;
		} catch(IOException e){
			e.printStackTrace();
			exit = 2;
		} finally{
			try{
				if( br != null)
					br.close();
			} catch(IOException e){
				e.printStackTrace();
			}
			if(exit != 0)
				System.exit(exit);
		}
		//printAll(people);
		generateEmails(people, args[2]);
		writeCsv(people, bw);
			try {
				if(bw != null)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	
	public static void generateEmails(Map<Long, Person> people, String domain){
		List<String> emails = new ArrayList<String>();
		String email, surnames[];
		for(Person person : people.values()){
			email = "";
			
			//default email
			for(String name : person.getName()){
				// ignore names like jr., do, da, de, etc.
				if(name.matches("^(D[AOE]?S)|(JR?\\.)$"))
					continue;
				email += Normalizer.normalize(name.toLowerCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
			}
			surnames = person.getSurnames();
			email += "." + Normalizer.normalize(surnames[surnames.length-1].toLowerCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
			//Does anyone else has this e-mail already?
			if(emails.contains(email) == false){
				email += domain;
				//removes dots for names like .jr
				email = email.replaceAll("\\.@", "@");
				person.setEmail(email);
				
			} else{
				/* if the email already exists, try adding an initial letter
				 * of a surname after the dot (".")
				 */
				for(int i = surnames.length-2; i >= 0; i--){
					char initial = surnames[i].toCharArray()[0];
					email = email.substring(0, email.indexOf(".")) + initial +
							email.substring(email.indexOf("."));
					if(emails.contains(email) == false){
						email += domain;
						email = email.replaceAll("\\.@", "@");
						person.setEmail(email);
						break;
					}
					if(i == 0){
						System.out.println("Collision detected stills: " +
								person.toString());
						person.setEmail("NULL");
					}
				}
			}
		} // for each person
		
	}// generateEmails 
	
	//for testing purposes
	public static void printAll(Map<Long, Person> people){
		for(Person p : people.values()){
			String[] names = p.getName(),
					surnames = p.getSurnames();
			System.out.println("     Id: " + p.getId());
			System.out.print("   Name: ");
			for(String name : names){
				System.out.print(name + " ");
			}
			System.out.print("\nSurname: ");
			for(String name : surnames){
				System.out.print(name + " ");
			}
			System.out.println("\n e-mail: " + p.getEmail());
			System.out.println("\n");
			
		}
		
	}// printAll
	
	public static void writeCsv(Map<Long, Person> people, BufferedWriter bw){
		String name, surname;
		try{
			bw.write("id,firstname,lastname\n");
		}catch(IOException e){
			e.printStackTrace();
		}
		for(Person p : people.values()){
			//if has more than one name, surround name with quotes (")
			if(p.getName().length > 1){
				name = "\"";
				for(int i = 0; i < p.getName().length; i++){
					name += p.getName()[i];
					if(i < p.getName().length-1)
						name += " ";
					else
						name += "\"";
				}
			} else{
				name = p.getName()[0];
			}
			//System.out.println(name);
			//same thing for surnames
			if(p.getSurnames().length > 1){
				surname = "\"";
				for(int i = 0; i < p.getSurnames().length; i++){
					surname += p.getSurnames()[i];
					if(i < p.getSurnames().length-1)
						surname += " ";
					else
						surname += "\"";
				}
			} else{
				surname = p.getSurnames()[0];
			}
			//System.out.println(surname);
			try {
				bw.write(p.getId() + "," + name + "," + surname + "," + p.getEmail()+"\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}// writeCsv
	
}
