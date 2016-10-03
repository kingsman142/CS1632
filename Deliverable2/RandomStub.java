import java.util.Random;

public class RandomStub extends Random{
	private int[] numbers;
	private int rngPointer = 0;

	public RandomStub(int[] randomNumbers){
		numbers = randomNumbers;
	}

	@Override
	public int nextInt(){
		if(rngPointer > numbers.length-1) rngPointer = 0;
		return numbers[rngPointer++];
	}
}
