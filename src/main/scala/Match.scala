object Match {

def intToName(num: Int): String = {
  num match {
    case 1 => "one"
    case 2 => "two"
    case 3 | _ => "other"
  }
  }

  def nameToSex(name: String): String = {
    val taro = "Taro"
    name match {
      case "Jiro" | "Taro" => "male"
      case "Eri" => "Female"
    }
  }



}
