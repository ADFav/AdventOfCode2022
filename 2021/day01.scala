class BaseSolution {
  private var lines: List[String];

  def importFile(filename: String): Unit = {
    import scala.io.Source

    this.lines = Source.fromFile(filename).getLines().toList()
    for (line: String <- this.lines) {
      this.readLine(line);
    }
  }

  def readLine(line: String): Unit = {
    return;
  }
}

class Day01 extends BaseSolution {}
