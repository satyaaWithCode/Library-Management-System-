package com.springBoot.reactive;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

@Slf4j
@SpringBootTest
class ReactiveBookApiApplicationTests {

	@Test
	void contextLoads() {
	}

//	@Test
//	void workWithMono()
//	{


		// 1-    //mono creation - used to work on Single Element


//		Mono<Object> errorMono = Mono.error(new Exception("error message"));
//		Mono<String> mono = Mono
//				.just("hello sahil");//publisher
//
//              //we can create 2 ways mono which are just and error
//		     // subscriber- on subscribe -> request > publisher - onNext >onComplete
//
////		mono.subscribe(data->{
////			System.out.println("data is "+data);
////		});  or
//		mono.subscribe(System.out::println);


//		2-  withZip() -it will combine current mono with given mono like combine with zip mono - difference used for combined multiple mono based on our requirement we will use

		//zipWith-not static we have to create obj for calling
//
//		Mono<String> nameMono = Mono.just("Satya");
//		Mono<Integer> ageMono = Mono.just(25);
//
//		nameMono
//				.zipWith(ageMono)
//				.map(tuple -> tuple.getT1() + " - " + tuple.getT2())  // Extract name and age
//				.subscribe(result -> System.out.println("Result: " + result));

        // zip()- for merge multiple mono -its static

//		Mono<String> nameMono = Mono.just("Satya");
//		Mono<Integer> ageMono = Mono.just(25);
//		Mono<String> cityMono = Mono.just("BBSR");
//
//		Mono.zip(nameMono, ageMono, cityMono)
//				.map(tuple -> {
//					String name = tuple.getT1();
//					int age = tuple.getT2();
//					String city = tuple.getT3();
//					return name + " | " + age + " | " + city;
//				})
//				.subscribe();


//         3- map, flatMap, flatMapMany , connectWith()
//		map- used for extract or return simple mono value
//		flatMap- used for extract or return nested mono value
//		flatMapMany-used for return  multiple mono value , mono->flux



//		map>ex-1

//		Mono<String> nameMono = Mono.just("satya");
//		Mono<String> upperMono = nameMono.map(name -> name.toUpperCase());

// Result: Mono<"SATYA">

//		flatMap>ex-1

//		Mono<String> userIdMono = Mono.just("123");
//
//		Mono<User> userMono = userIdMono.flatMap(id -> userService.findById(id));

//// Result: Mono<User>



		//flatMapMany>ex-1

//		Mono<String> category = Mono.just("fruits");
//
//		Flux<String> fruitsFlux = category.flatMapMany(cat -> getFruitsByCategory(cat));
//
//// Result: Flux<"Apple", "Banana", "Mango">




//	 System.out::println

//		This is a method reference to println() — it means:

//		element -> System.out.println(element)

//		So when you write:

//		list.forEach(System.out::println);
//		Java understands it like:

//		for (String element : list) {
//			System.out.println(element);
//		}

		//interview question
//		✅ Core Reactive Programming Questions

//		What is Reactive Programming?
//
//		What are the main advantages of Reactive Programming?
//
//		What is the difference between Reactive and Imperative programming?
//
//		What is backpressure? Why is it important?
//
//		Explain Publisher, Subscriber, Subscription, and Processor in Reactive Streams.
//
//		Difference between Mono and Flux?
//
//		What is the role of subscribe() in a reactive pipeline?
//
//		What is the difference between map() and flatMap()?
//
//		What is flatMapMany() used for?
//
//		What are cold and hot publishers?


//💡 Why is Backpressure important?
//		Without it:
//
//		Subscriber can be overloaded
//
//		Memory may overflow
//
//		App may crash or become unresponsive
//
//		With it:
//
//		You keep the system stable
//
//		You control the flow of data


		// Backpressure is handled by the Subscriber, but affects the Publisher.
		//it means backpressure will manage subscriber request
		// which would have going to thousends of request ,it should not overloaded and
		// should not app crash and memory may overflow

//		imp
		// Backpressure is handled by the Subscriber, but affects the Publisher.
		//The subscriber controls how much data it can handle.
		//It tells the publisher:
//👉 “Don’t send too fast, I’ll request items when I’m ready.”
		//The publisher listens to the subscriber and only emits data when requested.
		//If the subscriber says “I’m full,” the publisher slows down or buffers the data.


//	}

}
