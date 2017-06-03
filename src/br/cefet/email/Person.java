package br.cefet.email;

public class Person {
	private Long id;
	private String[] name;
	private String[] surnames;
	private String email;
	
	//constructors	
	public Person(Long id, String[] name, String[] surnames) {
		super();
		this.id = id;
		this.name = name;
		this.surnames = surnames;
	}
	
	//getters and setters
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String[] getName() {
		return name;
	}
	
	public void setName(String[] name) {
		this.name = name;
	}
	
	public String[] getSurnames() {
		return surnames;
	}
	
	public void setSurnames(String[] surnames) {
		this.surnames = surnames;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		String str = "";
		for(int i = 0; i < name.length; i++){
			str += name[i] + " ";
		}
		for(int i = 0; i < surnames.length; i++){
			str += surnames[i] + " ";
		}
		str += "email: ";
		if(email != null){
			str += email;
		} else{
			str += "null";
		}
		
		return str;
	}

}
