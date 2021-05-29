package org.knoldus.database

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.pattern.{ask, pipe}
import akka.util.Timeout
import org.knoldus.model.{User, UserType}
import org.knoldus.repository.dao.Dao

import java.util.UUID
import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}


case class CreateUser(user:User)
case object ListAllUsers
case class UpdateUser(oldUser : User, newUser : User)
case class UpdateUserName(user: User, newName: String)
case class UpdateUserCategory(user: User, newCategory: UserType.Value)
case class DeleteUser(user: User)
case class GetUserByID(id: UUID)

class ActorModel extends Actor{
  override def receive: Receive = crudOperations(Map(),ListBuffer())

  def crudOperations(userIndex: Map[UUID, User], userDB: ListBuffer[User]) :Receive ={
    case CreateUser(user) =>
      val res = Future{
        if(!userDB.contains(user)){
          Try{
            context.become(crudOperations(userIndex +(user.id -> user),userDB += user))
          } match {
            case Failure(_) => false
            case Success(_) => true
          }
        }
        else false
      }.recover{
        case _:RuntimeException => false
      }
      res.pipeTo(sender())

    case ListAllUsers =>
      val res = Future{
        userDB.toList
      }.recover{
        case _:RuntimeException => List()
      }
      res.pipeTo(sender())

    case UpdateUser(oldUser,newUser) =>
      val res =  Future{
        if(userDB.contains(oldUser)){
          Try{
            context.become(crudOperations((userIndex - oldUser.id)+(newUser.id -> newUser), (userDB -= oldUser)+=newUser))
          } match {
            case Failure(_) => false
            case Success(_) => true
          }
        }
        else false
      }.recover{
        case _:RuntimeException => false
      }
      res.pipeTo(sender())

    case UpdateUserName(user,newName) =>
      val res = Future{
        if(userIndex.contains(user.id)){
          Try{
            val newUserObject = User(userIndex(user.id).id,newName, userIndex(user.id).category)
            context.become(crudOperations((userIndex - user.id)+(newUserObject.id -> newUserObject), (userDB -= user)+=newUserObject))
          } match {
            case Success(_) => true
            case Failure(_) => false
          }
        }
        else {
          false
        }
      }.recover{
        case _:RuntimeException => false
      }
      res.pipeTo(sender())

    case UpdateUserCategory(user, newCategory) =>
      val res = Future{
        if(userIndex.contains(user.id)){
          Try{
            val newUserObject = User(userIndex(user.id).id,userIndex(user.id).name,newCategory)
            context.become(crudOperations((userIndex - user.id)+(newUserObject.id -> newUserObject), (userDB -= user)+=newUserObject))
          } match {
            case Success(_) => true
            case Failure(_) => false
          }
        }
        else false
      }.recover{
        case _:RuntimeException => false
      }
      res.pipeTo(sender())

    case DeleteUser(user) =>
      val res = Future{
        Try{
          context.become(crudOperations(userIndex - user.id, userDB -= user))
        } match {
          case Success(_) => true
          case Failure(_) =>false
        }
      }.recover{
        case _:RuntimeException => false
      }
      res.pipeTo(sender())

    case GetUserByID(id) =>
      val res = Future{
        if(userIndex.contains(id)) userIndex(id)
        else null
      }.recover{
        case _:RuntimeException => null
      }
      res.pipeTo(sender())

  }
}

class UserDatabase extends Dao[User]{

  val system: ActorSystem = ActorSystem("System")
  val actor: ActorRef = system.actorOf(Props[ActorModel])
  implicit val timeout: Timeout = Timeout(5 seconds)

  override def createUser(user: User): Future[Boolean] =
    (actor ? CreateUser(user)).mapTo[Boolean]

  override def listAllUser(): Future[List[User]] =
    (actor ? ListAllUsers).mapTo[List[User]]

  override def updateUser(oldUser : User, newUser : User): Future[Boolean] =
    (actor ? UpdateUser(oldUser,newUser)).mapTo[Boolean]

  override def updateUserName(user: User, newName: String): Future[Boolean] =
    (actor ? UpdateUserName(user,newName)).mapTo[Boolean]

  override def updateUserCategory(user: User, newCategory: UserType.Value): Future[Boolean] =
    (actor ? UpdateUserCategory(user, newCategory)).mapTo[Boolean]

  override def deleteUser(user: User): Future[Boolean] =
    (actor ? DeleteUser(user)).mapTo[Boolean]

  override def getUserById(id: UUID): Future[User] =
    (actor ? GetUserByID(id)).mapTo[User]
}
