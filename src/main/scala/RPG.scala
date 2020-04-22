import java.util.Random


object RPG extends App {
  val random = new Random
  val monsterCount = 5
  val hero = new Hero(300, 30)
  var monsters = for (i <- 1 to monsterCount) yield new Monster(random.nextInt(120), random.nextInt(120), false)

  println(
    s"""
       |あなたは冒険中の ${hero} であり、
       |${monsterCount} 匹のモンスターが潜んでいる洞窟を抜けなければならない。
       |【ルール】:
       |1を入力してENTERキーを押すと攻撃、
       |2を入力してENTERキーを押すと防御、
       |それ以外を入力すると逃走となる。
       |攻撃すると攻撃力の分だけモンスターにダメージを与える。
       |防御するとモンスターからうけるダメージを半分にできる。
       |逃走成功率は５０％。逃走に失敗した場合はダメージをうける。
       |一度でもダメージを受けるとモンスターの体力と攻撃力が判明する。
       |またモンスターを倒した場合は武器を奪いその攻撃力を得ることができる。
       |--------------------------------------------------------------
       |未知のモンスターが現れた。
     """.stripMargin
  )

  while (!monsters.isEmpty) {
    val monster = monsters.head
    val input = scala.io.StdIn.readLine("【選択】:攻撃[1] or 防御【２】or 逃走[0] > ")

    if (input == "1") { // 攻撃する
      hero.attack(monster)
      println(s"あなたは${hero.attackDamage}のダメージを与え、${monster.attackDamage}のダメージをうけた。")
    } else if(input == "2"){  // 防御する
      hero.gard(monster)
      println(s"あなたは防御しモンスターから${monster.attackDamage / 2}のダメージを受けた")

    }else { // 逃走する
      if (hero.escape(monster)) {
        println("あなたはモンスターから逃走に成功した。")
      } else {
        println(s"あなたはモンスターからの逃走に失敗し、${monster.attackDamage}のダメージをうけた")
      }
    }
    println(s"【現在の状態】: ${hero}, ${monster}")

    if (!hero.isAlive) { // Hero が死んでいるかどうか
      println(
        """
          |------------------------------------------
          |【ゲームオーバー】：　あなたは無残にも殺されてしまった。
        """.stripMargin
      )
      System.exit(0)
    } else if (!monster.isAlive || monster.isAwayFromHero) { // Monster　いないかどうか
      if (!monster.isAwayFromHero) { // 倒した場合

        println("モンスターは倒れた。そしてあなたはモンスターの武器を奪った")
        if (monster.attackDamage > hero.attackDamage) hero.attackDamage = monster.attackDamage
      }
      monsters = monsters.tail
      println(s"残りのモンスターは${monsters.length}匹となった。")
      if (monsters.length > 0) {
        println(
          s"""
            |--------------------------------------------
            |次のモンスターがあらわれた。
            |【残り】: ${monsters.length} 匹です。
            |【現在の状態】: ${hero}
          """.stripMargin
        )
      }
    }
  }
  println(
    s"""
       |-----------------------------------------------
       |【ゲームクリア】：　あなたは困難を乗り越えた。新たな冒険に祝福を。
       |【結果】： ${hero}
     """.stripMargin
  )
  System.exit(0)
}




abstract class Creature(var hitPoint: Int, var attackDamage: Int){
  def isAlive(): Boolean = this.hitPoint > 0
}

class Hero(_hitPoint: Int, _attackDamage:Int)extends Creature(_hitPoint, _attackDamage){

  def attack(monster: Monster):Unit = {
    monster.hitPoint = monster.hitPoint - this.attackDamage
    this.hitPoint = this.hitPoint - monster.attackDamage
  }

  def gard(monster: Monster):Unit = {
    this.hitPoint = this.hitPoint - (monster.attackDamage / 2)
  }

  def escape(monster: Monster): Boolean = {
    val isEscaped = RPG.random.nextInt(2) == 1
    if(!isEscaped){
      this.hitPoint = this.hitPoint - monster.attackDamage
    } else {
      monster.isAwayFromHero = true
    }
    isEscaped
  }

  override def toString = s"Hero(体力:${hitPoint}, 攻撃力:${attackDamage})"
}

class Monster(_hitPoint:Int, _attackDamage:Int, var isAwayFromHero:Boolean) extends Creature(_hitPoint, _attackDamage){

  override def toString = s"Monster(体力:${hitPoint}, 攻撃力:${attackDamage}, ヒーローから離れている:${isAwayFromHero})"

}