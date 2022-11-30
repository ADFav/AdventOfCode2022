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

class Day01(filename: String) extends BaseSolution(filename: String) {
  import scala.collection.mutable.HashMap
  
  this.importFile()

  override def readLine(line: String): Unit = {
  }
  override def part1() = {

    println("Part 1: " + "")
  }

  override def part2() = {

    println("Part 2: " + "");
  }
}

@main
def solve = {
    println(">>> EXAMPLE")
    val example = new Day01("2022/examples/day01.txt");
    example.part1();
    example.part2();

    println("")
    println(">>> SOLUTION")
    val solution = new Day01("2022/inputs/day01.txt");
    solution.part1();
    solution.part2();
}
