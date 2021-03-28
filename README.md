# Future Calls and its Testing

In this project I make User Management System in which I implement the CRUD 

Types of Users:
a) Customer
b) Admins


So, this User management System has following structure:-

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

*All the method of UserDatabase class implemented using Future to run them concurrent.

*To test these Future calls I used the For expression.

 





			 
