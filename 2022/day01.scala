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
  private var currentCalories: Integer = 0
  
  import scala.collection.mutable.ArrayBuffer
  private var totalCalories: ArrayBuffer[Integer] = new ArrayBuffer();

  this.importFile()

  override def readLine(line: String): Unit = {
    if (line.trim == "") {
      this.totalCalories += (this.currentCalories)
      this.currentCalories = 0
    } else {
      this.currentCalories += line.toInt
    }
  }

  override def part1() = {
  val sortedCalories = scala.util.Sorting.stableSort(this.totalCalories).reverse
    println("Part 1: " + sortedCalories(0))
  }

  override def part2() = {
    val sortedCalories = scala.util.Sorting.stableSort(this.totalCalories).reverse
    val result = sortedCalories(0) + sortedCalories(1) + sortedCalories(2)

    println("Part 2: " + result);
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
