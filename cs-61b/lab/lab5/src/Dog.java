public class Dog extends Animal{
	
	public Dog() {
		System.out.println("Dog");
	}
	
	public void Bark() {
		System.out.println("Bark");
	}
	
	
	public void Eat() {
		System.out.println("Eat");
	}
	
	
	public static void main(String[] args) {
		
		Dog d = new Dog();
		
		d.Walk();
		
		d.Eat();
		

		
	}
}