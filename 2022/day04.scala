class BaseSolution(var filename: String) {
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

  def part1(): String = { "" }

  def part2(): String = { "" }

  def solve(): Unit = {
    this.importFile()

    println("Part 1: " + this.part1())
    println("Part 2: " + this.part2())
  }
}

import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashSet

class Day04(filename: String) extends BaseSolution(filename: String) {
  class Assignment(var from: Int, var to: Int) {
    def contains(other: Assignment): Boolean = {
      this.from <= other.from && this.to >= other.to
    }

    def anyOverlap(other: Assignment): Boolean = {
      this.to >= other.from && this.from <= other.to
    }
  }

  val assignmentPairs: ArrayBuffer[Array[Assignment]] = new ArrayBuffer
  override def readLine(line: String): Unit = {
    val rawAssignmentPairs: Array[String] = line.split(",")
    val assignmentPair: Array[Assignment] = rawAssignmentPairs.map(rawPair => {
      val indices: Array[Int] = rawPair.split("-").map(_.toInt)

      Assignment(indices(0), indices(1))
    })

    assignmentPairs += assignmentPair
  }

  override def part1() = {
    return assignmentPairs
      .count(assignmentPair => {
        assignmentPair(0).contains((assignmentPair(1))) ||
        assignmentPair(1).contains((assignmentPair(0)))
      })
      .toString()
  }

  override def part2() = {
    return assignmentPairs
      .count(assignmentPair => {
        assignmentPair(0).anyOverlap((assignmentPair(1))) ||
        assignmentPair(1).anyOverlap((assignmentPair(0)))
      })
      .toString()
  }
}

@main
def solve: Unit = {
  val day = "04";
  val generate_solver = filename => new Day04(filename)

  val dirs = Array("2022/examples", "2022/inputs")
  val filenames = dirs.map(dir => f"${dir}/day${day}.txt")
  val solvers = filenames.map(generate_solver)

  solvers.foreach(solver => {
    println(solver.filename)
    solver.solve()
    println("")
  })
}
