class BaseSolution(filename: String) {
  var lines: Array[String] = Array()

  def importFile(): Unit = {
    import scala.io.Source

    this.lines = Source.fromFile(this.filename).getLines.toArray
    println("Lines read: " + this.lines.length)
    for (line: String <- this.lines) {
      this.readLine(line);
    }
  }

  def readLine(line: String): Unit = {}

  def part1(): Unit = {}

  def part2(): Unit = {}
}

class RPSChoice(var pointValue: Integer) {
  var beats: RPSChoice = null
  var loses_to: RPSChoice = null

  def will_beat(opponent: RPSChoice): Unit = {
    beats = opponent
    opponent.loses_to = this
  }
}

class RPS(val you_play: RPSChoice, val they_play: RPSChoice) {
  def your_score: Integer = {
    var score: Integer = you_play.pointValue
    if (you_play == they_play) { score += 3 }
    if (you_play.beats == they_play) { score += 6 }

    return score
  }
}

import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

class Day02(filename: String) extends BaseSolution(filename: String) {
  val ROCK = new RPSChoice(1)
  val PAPER = new RPSChoice(2)
  val SCISSORS = new RPSChoice(3)

  PAPER.will_beat(ROCK)
  SCISSORS.will_beat(PAPER)
  ROCK.will_beat(SCISSORS)

  val choiceMap = HashMap(
    "A" -> ROCK,
    "B" -> PAPER,
    "C" -> SCISSORS,
    "X" -> ROCK,
    "Y" -> PAPER,
    "Z" -> SCISSORS
  )
  var allChoices: ArrayBuffer[Array[String]] = new ArrayBuffer
  override def readLine(line: String): Unit = {
    allChoices += line.split(" ")
  }

  this.importFile()
  override def part1() = {
    var running_score: Integer = 0

    allChoices.foreach((choices: Array[String]) => {
      val their_choice = choiceMap(choices(0))
      val my_choice = choiceMap(choices(1))

      val game: RPS = new RPS(my_choice, their_choice)
      running_score += game.your_score
    })

    println("Part 1: " + running_score)
  }

  override def part2() = {
    var running_score: Integer = 0

    val outcomeMap = HashMap[String, RPSChoice => RPSChoice](
      "X" -> ((choice) => choice.beats), // Must lose
      "Y" -> ((choice) => choice), // Must draw
      "Z" -> ((choice) => choice.loses_to) // Must win
    )

    allChoices.foreach((choices: Array[String]) => {
      val their_choice = choiceMap(choices(0))
      val outcome = outcomeMap(choices(1))
      var my_choice: RPSChoice = outcome(their_choice)

      val game: RPS = new RPS(my_choice, their_choice)
      running_score += game.your_score
    })

    println("Part 2: " + running_score);
  }
}

@main
def solve: Unit = {
    println(">>> EXAMPLE")
    val example = new Day02("2022/examples/day02.txt");
    example.part1();
    example.part2();

    println("")
    println(">>> SOLUTION")
    val solution = new Day02("2022/inputs/day02.txt");
    solution.part1();
    solution.part2();
}
