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

import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashSet

class Day03(filename: String) extends BaseSolution(filename: String) {
  override def readLine(line: String): Unit = {}

  this.importFile()
  override def part1() = {

    val priorities: Array[Int] = this.lines.map((line) => {
      val line_length = line.length;
      val substr_1 = line.substring(0, line_length / 2).toSet
      var substr_2 = line.substring(line_length / 2).toSet

      val common_letters = substr_1.intersect(substr_2).toArray
      val common_letter = common_letters(0)

      var result: Int = 0
      if (common_letter > 'a') { result = 1 + common_letter - 'a' }
      else if (common_letter > 'A') { result = 27 + common_letter - 'A' }

      result
    })

    println("Part 1: " + priorities.sum)
  }

  override def part2() = {
    var rucksacks = new ArrayBuffer[Set[Char]]()
    var result = 0

    this.lines.foreach(line => {
      rucksacks += (line.toSet)

      if (rucksacks.length == 3) {
        val common_letters =
          rucksacks(0).intersect(rucksacks(1)).intersect(rucksacks(2)).toArray
        val common_letter = common_letters(0)

        if (common_letter > 'a') { result += 1 + common_letter - 'a' }
        else if (common_letter > 'A') { result += 27 + common_letter - 'A' }

        rucksacks = new ArrayBuffer[Set[Char]]()
      }

    })

    println("Part 2: " + result);
  }
}

@main
def solve: Unit = {
    println(">>> EXAMPLE")
    val example = new Day03("2022/examples/day03.txt");
    example.part1();
    example.part2();

    println("")
    println(">>> SOLUTION")
    val solution = new Day03("2022/inputs/day03.txt");
    solution.part1();
    solution.part2();
}
