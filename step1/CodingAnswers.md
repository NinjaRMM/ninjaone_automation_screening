# General Questions

1. Name some tools and/or techniques that you personally find to be the most helpful surrounding development.
+ Tools:
IDE (if offers links to definitions, error highlighting, etc.),
	debugger,
	git: versioning and change control,
	file compare tool.
+ Techniques:
		Drawing a block diagram of the solution.
		And writing clean code: It involves several aspects but the starting point is choosing good names. A good name is readable and meaningful.

2. Name some tools and/or techniques that you personally find to be the most helpful surrounding code maintenance.
+ Tools: git, debugger.
+ Techniques:
		Writing clean code, it means code which is easy to understand and maintain: it avoids big code blocks, it uses slim, concise functions or methods, and it uses cohesive classes.

3. Explain your familiarity with automation infrastructures. The questions below are to help give a guideline, please feel free to be as detailed as you please.
+ a. How did you deploy and execute your test suites?
	+ It depends on the application under test. But in general we use:
		+  A test framework which allow us to act on the application under test.
		+  A set of test scripts, each of them implements the following:
			- arrange: allow us to inject input data to the test framework
			- act: execute such input data on the application under test
			- assert: compare against an expected output
		+ We can use a CD/CI tool to execute our set of test scripts to test the changes the development team made to the application.

+ b. How did you target different devices?
	+ At the high level we can execute the same test on different devices by having a translation low level layer for each different device.

+ c. How did you handle scalability?
	+ Scalability tests are part of the performance tests. They help us to evaluate the limits of our application as we increase the load. In my experience, the type of load has different meanings depending on the platform where the software is running. There were cases where the software is running on a microcontroller in a vehicle. And there were cases where the software is running on a server. But the principle is the same: we need to answer how much can we increase the load? One possibility is to use multithreading in our test tool to simulate a high clients load.

+ d. How did you report test results?
	+ Usually it is a service provided by the test framework. If it is not the case, we can add this functionality by using some open source framework available, like Cucumber Reports service.

4. What testing frameworks are you familiar with for system level/desktop applications?
	+ I haven't tested desktop applications. But if I have to test them, I would use a test framework like pytest over the exposed application's API (Shared Library).

5. What testing frameworks are you familiar with for browser applications?
	+ I have used Selenium.
And I have learned java Script and Cypress.

6. What tools are you familiar with for Performance, Load and Stress testing?
	+ I am not familiar with any commercial tool. In the projects where I had participated, we created our own tools.

7. Tell us about a project your worked on that you found to be interesting or unusual.
	+ In 2010 I worked in a project which was quite unusual at that time. It was an IOT solution for fleet management. The test were made at several levels. At the web level we need to validate the published information. At the device level we needed to test every module of the device: GPS, modem, OBDII.
There was a time when our device application was crashing on certain types of trucks. I was in charge of analyze the failure logs and reproduce those field conditions in a test bench.

# Technical Questions

1. When would you use multithreading vs multiprocessing?
	+ In Unix systems, when we have multiple processes running the same code, each proces have their own resources: cpu time, memory space. It means they have its own variable's copies. If any of these processes becomes blocked, the others continue running.
	+ On other hand, in the case of multithreading, each single process can have several threads, all of them sharing this process resources: cpu time, memory space. It means a process' variable is shared among all the threads. In addition, if the process becomes blocked it affects all the threads.

2. Describe the differences between Unit Tests, Functional Tests, and Integration Tests?
	+ Unit Tests: These are the test made at the component/module level. Often it is done by the author of the module in isolation from the rest of the system.
	+ Integration Tests: This type of testing focuses in the communication and interaction between the modules of the system.
	+ Functional Tests: Functional tests check the system at all test levels to ensure that all scenarios are covered. These tests evaluate if the system does what it’s supposed to do.

	i. Do you have a preference and why?
	+ I prefer functional testing specially if we don't have access to the code, because it considers the behavior of the system. We compare what is the expected output given a particular input, so we use black box test techniques. And it can uncover failures which were not found during unit test or integration test.

3. What are the some of the pros and cons of object-oriented programming vs functional programming?
 + In functional programming data can not be stored in objects. It uses immutable data to tell the program exactly what to do. Data manipulation can be achieved by creating functions. It has the disadvantage that when the code gets longer or more complex we need to change existing functions or create new ones.
 + In OO programming, data is stored in objects. OOP give us the advantage when the code gets longer or more complex rather than changing a method we can extend the existing functionality by inheritance.

4. What security concerns have you come across in the past and how have you addressed them?
+ No I haven't. But security is one area which I'd like to explore.

# Small Programming Challenges

1. Using a known programming language write a small program to:
a. Query the OS for the OS Patches that are currently installed on the system.
	i. For example, on windows: Windows Update Settings -> View Update History
	iii. [Optional] Add a function to report if Automatic Updates are enabled or disabled for the device.
b. How would you consider validating the above program returns all installed patches on the system from an automation perspective?
	i. What automation framework(s) you would consider utiltizing?
c. Let's say your program was written to be cross platform, how would you design an infrastructure for deploying your program and executing the test case(s) across multiple Windows, Linux and Mac devices?
	i. After a reboot, a system may show different patches as installed, would this cause complications with your validation? If so, what alternatives do you see available?
