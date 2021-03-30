package org.knoldus.database

import org.knoldus.model.{User, UserType}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.funsuite.AsyncFunSuite
import org.scalatest.matchers.should.Matchers

class UserDatabaseTest extends AsyncFunSuite with Matchers with ScalaFutures{

  val userDatabase = new UserDatabase
  val user: User = User(java.util.UUID.randomUUID(), "Yash", UserType.Admin)
  val newUser: User = User(java.util.UUID.randomUUID(), "Rudra", UserType.Admin)

  test("A user should be created"){
    whenReady(userDatabase.createUser(user)) {
      result => result shouldBe true
    }
  }

  test("It should return a list[User]"){
    whenReady(userDatabase.listAllUser()) {
      result => result shouldBe List(user)
    }
  }

  test("It should update a new user with existing user"){
    whenReady(userDatabase.updateUser(user,newUser)) {
      result => result shouldBe true
    }
  }

  test("It should not update the user  beacuse it doesn't exist"){
    whenReady(userDatabase.updateUser(user,newUser)) {
      result => result shouldBe false
    }
  }

  test("it should update the username of existing user"){
    whenReady(userDatabase.updateUserName(newUser,"Honey")) {
      result => result shouldBe true
    }
  }

  test("it should update the category of the existing user"){
    whenReady(userDatabase.updateUserCategory(newUser,UserType.Customer)) {
      result => result shouldBe true
    }
  }

  test("It should get user by its ID"){
    whenReady(userDatabase.getUserById(newUser.id)){
      result => result shouldBe User(newUser.id, "Honey", UserType.Customer)
    }
  }

  test("it should delete the existing user"){
    whenReady(userDatabase.deleteUser(User(newUser.id, "Honey", UserType.Customer))){
      result => result shouldBe true
    }
  }
}
