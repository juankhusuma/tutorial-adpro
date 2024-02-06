# Advanced Programming Tutorial
## Juan Dharmananda Khusuma - 2206081521

### Reflection 1
> You already implemented two new features using Spring Boot. Check again your source code and evaluate the coding standards that you have learned in this module. Write clean code principles and secure coding practices that have been applied to your code.  If you find any mistake in your source code, please explain how to improve your code. Please write your reflection inside the repository's README.md file.

In my source code, I've implemented several clean code principles and secure coding practices that I've learned in this module. Some of the clean code principles and secure coding practices that I've applied to my code are:
- using meaningful variable names and method names
- using proper indentation and formatting
- adhering to the DRY (Don't Repeat Yourself) principle by reusing the code and creating helper methods such as in the `findProductById` method that is used in `editProduct` and `deleteProduct`
- using proper exception handling and error messages to prevent the site from crashing and to provide a better user experience
- using proper access modifiers for the methods and variables
- processing the user input on the server side to prevent XSS attacks


### Reflection 2
> After writing the unit test, how do you feel? How many unit tests should be made in a class? How to make sure that our unit tests are enough to verify our program? It would be good if you learned about code coverage. Code coverage is a metric that can help you understand how much of your source is tested. If you have 100% code coverage, does that mean your code has no bugs or errors?

After writing the unit test for my code, I'm starting to understand the importance of unit testing. It helps to ensure that the codes that I've written are working as expected and could handle several edge cases that could help prevent and reduce any potential for bugs and errors to appear. In terms of ensuring that our unit test are sufficient, I believe that there should be at least one unit test for each method in a class. However, it would be better if there are more than one unit test for each method, to ensure that the method is working as expected under several scenarios and edge cases. To make sure that our unit tests are enough to verify our program, we can use code coverage. Code coverage is a measurement of how much code are tested by our test suite by checking how many functions and lines of code were ran during the testing process. However, having 100% code coverage doesn't mean that our code has no bugs or errors. It only means that all of our code is tested and executed by the test suite, but it doesn't guarantee that our code is either bug-free or error-free.

> Suppose that after writing the CreateProductFunctionalTest.java along with the corresponding test case, you were asked to create another functional test suite that verifies the number of items in the product list. You decided to create a new Java class similar to the prior functional test suites with the same setup procedures and instance variables. 
What do you think about the cleanliness of the code of the new functional test suite? Will the new code reduce the code quality? Identify the potential clean code issues, explain the reasons, and suggest possible improvements to make the code cleaner! Please write your reflection inside the repository's README.md file.

I believe that the code quality on this new functional test suite would surely decline, since we're breaking the DRY (Don't Repeat Yourself) principle. Any changes or fixes within one of the reused methods would require us to change all of the other methods that are using the same method. This would cause more and more maintanability issues as our codebase grows. To improve the cleanliness of the code, we could use some helper function like the `setUp` with a `@BeforeEach` method to reduce the redundancy of and boilerplate whenever starting a new test. Other possible improvements if the test classes are similiar enough is to use inheritance, where we could create a parent class that contains the `setUp` method and other common methods, and then extend the parent class to the new test classes. This way, we could reduce the redundancy and boilerplate of the code, and also improve the maintainability of the codebase.