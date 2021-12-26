package com.cs.cs;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CsApplicationTests {

	@ParameterizedTest
	@DisplayName("Extract month Test")
	@CsvSource({ "2" })
	public void extractMonth(Integer quantity) {

		AtomicInteger counter = new AtomicInteger(quantity);
        counter.incrementAndGet();
		System.out.println("count => " + counter);
		Integer size = 2;
		Integer initial = 1;

		List<Integer> j =  IntStream.range(initial, counter.intValue())
					.map(i -> Integer.valueOf(counter.intValue() - i + initial - 1))
					.filter(i-> (counter.intValue() - i + initial - 1)%size == 0)
					.boxed().collect(Collectors.toList());
		System.out.println(j);
		counter.setRelease(quantity);
		if (!j.isEmpty() && j.get(0) > 1 ) {
			System.out.println("pegar a quantidade 1 -> " +quantity);
			System.out.println(j.get(0));
			for( int i = 0 ; i < 3 && counter.decrementAndGet() > 0 ;i++ ){
				
				/**
				 atribuir a quantidade para cara ação.
				 */

				counter.decrementAndGet();

			}


            int countNumOfExec = 0;
            while(20 > countNumOfExec){
				System.out.println("->>>>>> " + countNumOfExec);
				countNumOfExec++;
			}

		}else{
			System.out.println("pegar a quantidade 2 -> " +quantity);
		}

		assertNotNull(quantity);
	}


}
