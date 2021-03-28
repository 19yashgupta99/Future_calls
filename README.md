# OOPs_Assignment_3 & Testing Assignment

In this project I solved the problem which is :

Create a User Management System.

Application
--------------
2 Types of Users:
a) Customer
b) Admins


Acceptance Criteria (Demo) :
1. Create a Main class under `org.knoldus.bootstarp` and perform following operator.

a. Create two User [Customer and Admin]

b. List all user

c. Update UserName of Admin

d. Delete Customer

So, according to above problem I created  User management System which has following structure:-

=>src

	main
	
		scala
			
			org
				
				knoldus
					
					bootstrap
						
						-Main.class
					
					databse
						
						-UserDatabase.scala
					
					model
						
						-User.Scala
						
						-UserType.scala
					
					repository
						
						dao
							
							-Dao.scala
					
					Service
						
						-UserService.scala
	
	test
		
		scala
			
			org
				
				knoldus
					
					database
						
						-UserDatabaseTest.scala
					
					service
					
						-UserServiceUnitTest
						
						-UserServiceIntegrationTest

where every scala class doing its job.

For the Testing coverage I used the scoverage plugin

the result by the scoverage :-

[info] Statement coverage.: 91.23%

[info] Branch coverage....: 85.00%

[info] Coverage reports completed

[info] All done. Coverage was [91.23%]
 





			 
