class BaseSolution(filename: String) {
  var lines: List[String] = List()

  def importFile(): Unit = {
    import scala.io.Source

    this.lines = Source.fromFile(this.filename).getLines.toList
    println("Lines read: " + this.lines.length)
    for (line: String <- this.lines) {
      this.readLine(line);
    }
  }

  this.importFile()

  def readLine(line: String): Unit = {}

  def part1(): Unit = {}

  def part2(): Unit = {}

}

class Day01(filename: String) extends BaseSolution(filename: String) {
//   var lines: List[String] = List();
  override def readLine(line: String): Unit = {
    println("reading line ...")
  }

  class ApartmentBuilding {
    var floor: Int = 0;

    def up() = { this.floor += 1 }

    def down() = { this.floor -= 1 }

  }

  override def part1() = {
    val building = new ApartmentBuilding();
    for (paren <- this.lines(0)) {
      if (paren == '(') { building.up() }
      if (paren == ')') { building.down() }
    }
    println("Part 1: " + building.floor)
  }

  override def part2() = {
    var building = new ApartmentBuilding
    var position = 0;
    while (building.floor >= 0) {
      val paren: Char = this.lines(0)(position);
      if (paren == '(') { building.up() }
      if (paren == ')') { building.down() }
      position += 1;
    }

    println("Part 2: " + position);
  }
}

@main
def solve = {
    val solution = new Day01("2015/inputs/day01.txt");
    solution.part1();
    solution.part2();
}
