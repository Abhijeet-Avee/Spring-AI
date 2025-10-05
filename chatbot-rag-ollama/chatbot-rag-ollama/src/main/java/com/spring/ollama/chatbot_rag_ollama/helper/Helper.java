package com.spring.ollama.chatbot_rag_ollama.helper;

import java.util.List;

public class Helper {

	// Sample data to be saved in vector store
	public static List<String> getData(){
		return List.of(
				"Java is a platform-independent language",
	            "Java follows the principle of write once run anywhere",
	            "JVM executes Java bytecode",
	            "Java supports object-oriented programming",
	            "Encapsulation is one of the core concepts in Java",
	            "Inheritance allows code reusability in Java",
	            "Polymorphism provides flexibility in Java code",
	            "Abstraction hides implementation details in Java",
	            "Java provides automatic garbage collection",
	            "Java supports multithreading for concurrent execution",
	            "Java Collections Framework provides data structures",
	            "Java supports exception handling using try-catch",
	            "Interfaces define contracts in Java",
	            "Abstract classes provide partial abstraction in Java",
	            "Java supports generics for type safety",
	            "Java 8 introduced lambda expressions",
	            "Streams in Java help process collections efficiently",
	            "Java supports JDBC for database connectivity",
	            "Java packages organize classes",
	            "Java is widely used for web, desktop, and enterprise applications",
	            "Python is a high-level programming language.",
	            "It emphasizes code readability and simplicity.",
	            "Python uses indentation to define code blocks.",
	            "It supports multiple programming paradigms.",
	            "Python has a large standard library.",
	            "It is dynamically typed and interpreted.",
	            "Python is great for automation and scripting.",
	            "It is widely used in web development.",
	            "Python powers data science and machine learning.",
	            "It integrates well with C and C++ code.",
	            "The Python Package Index hosts thousands of libraries.",
	            "It supports object-oriented programming.",
	            "Python can be used for backend API development.",
	            "It is known for rapid application development.",
	            "Python is cross-platform and open source.",
	            "It has a vibrant global developer community.",
	            "Python frameworks include Django and Flask.",
	            "NumPy and Pandas make data analysis easy.",
	            "TensorFlow and PyTorch are popular AI libraries.",
	            "Python continues to evolve with every new version."
				);
	}
	
}
