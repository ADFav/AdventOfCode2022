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

class Day05(filename: String) extends BaseSolution(filename: String) {
  var stacks: ArrayBuffer[ArrayBuffer[String]] = new ArrayBuffer()
  var instructions: ArrayBuffer[Array[Int]] = new ArrayBuffer()
  override def readLine(line: String): Unit = {
    if (line.trim().length == 0) return;
    if (line.trim()(0) == '[') {
      val WIDTH = 4;
      var offset = 1;

      println(stacks.length + ", " + line.length.toString)
      while (stacks.length <= (line.length - offset) / WIDTH) {
        stacks += new ArrayBuffer[String]()
      }

      var index = 0;
      while (index < stacks.length) {
        val c: Char = line(offset + index * WIDTH)
        if (c != ' ') stacks(index) += c.toString

        index += 1
      }
    }
    if (line.startsWith("move")) {
      instructions += line
        .replaceAllLiterally("move ", "")
        .replaceAllLiterally("from ", "")
        .replaceAllLiterally("to ", "")
        .split(" ")
        .map(_.toInt)
    }
  }

  override def part1() = {
    // instructions.foreach(instruction => {
    //   val quantity = instruction(0)
    //   val from = instruction(1) - 1
    //   val to = instruction(2) - 1

    //   for (i <- 1 to quantity) {
    //     val crate = stacks(from).remove(0)
    //     stacks(to).insert(0, crate)
    //   }
    // })

    // stacks.map(stack => stack(0)).reduce((a, b) => a + b)
    // return result.toString

    ""
  }

  override def part2() = {
    instructions.foreach(instruction => {
      val quantity = instruction(0)
      val from = instruction(1) - 1
      val to = instruction(2) - 1

      val buffer = new ArrayBuffer[String]()

      for (i <- 1 to quantity) {
        val crate = stacks(from).remove(0)
        buffer += crate;
      }
      buffer.reverse
        .foreach(crate => {
          stacks(to).insert(0, crate)
        })
    })

    stacks.map(stack => stack(0)).reduce((a, b) => a + b)
    // return result.toString
  }
}

@main
def solve: Unit = {
  val day = "05";
  val generate_solver = filename => new Day05(filename)

  val dirs = Array("2022/examples", "2022/inputs")
  val filenames = dirs.map(dir => f"${dir}/day${day}.txt")
  val solvers = filenames.map(generate_solver)

  solvers.foreach(solver => {
    println(solver.filename)
    solver.solve()
    println("")
  })
}
