class User(private val name: String, private val age:Int) {

  override def toString = s"User($name, $age)"

}

object User{
  def apply(name:String, age: Int) = new User(name, age)

  def printAge(user: User) = println(user.age)
}