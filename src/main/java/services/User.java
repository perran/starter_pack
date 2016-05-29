package services;

public class User {

	private String name;
	private String town;
	private int age;

	public User(){
		
	}
	
	public User(String name, int age, String town){
		this.name = name;
		this.age = age;
		this.town = town;	
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
