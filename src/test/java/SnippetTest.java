import com.debacharya.nsgaii.Reporter;
import com.debacharya.nsgaii.Service;

import java.util.List;

public class SnippetTest {

	public static void main(String[] args) {

		List<Integer> numbers = Service.generateUniqueRandomNumbers(2, 10);

		numbers.forEach(System.out::println);
		Reporter.p("\n\n");

		for(int i = 0; i < (numbers.size() / 2); i++) {
			int j = i + (numbers.size() / 2);
			System.out.println("Swap: " + numbers.get(i) + " --> " + numbers.get(j));
		}
	}
}
