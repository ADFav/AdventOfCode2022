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

class Day06(filename: String) extends BaseSolution(filename: String) {
  override def readLine(line: String): Unit = {}

  def common_solution(window_width: Integer): Integer = {
    val line = this.lines(0)

    var buffer: ArrayBuffer[Char] =
      line.substring(0, window_width).map(_.toChar).to(ArrayBuffer)
    var index = window_width

    while (buffer.toSet.size != window_width) {
      buffer.remove(0)
      buffer += line(index)
      index += 1
    }

    index
  }
  override def part1() = {
    common_solution(4).toString()
  }

  override def part2() = {
    common_solution(14).toString()
  }
}

@main
def solve: Unit = {
  val day = "06";
  val generate_solver = filename => new Day06(filename)

  val dirs = Array("2022/examples", "2022/inputs")
  val filenames = dirs.map(dir => f"${dir}/day${day}.txt")
  val solvers = filenames.map(generate_solver)

  solvers.foreach(solver => {
    println(solver.filename)
    solver.solve()
    println("")
  })
}
