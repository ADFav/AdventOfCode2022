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

class Cave {
  val occupiedPositions: HashSet[(Int, Int)] = new HashSet
  val xCoordMap: HashMap[Int, ArrayBuffer[MaterialPile]] = new HashMap

  def get(xCoord: Int, yCoord: Int): Option[MaterialPile] = {
    if (!xCoordMap.contains(xCoord)) {
      xCoordMap(xCoord) = new ArrayBuffer
    }

    return xCoordMap(xCoord)
      .filter(pile => pile.bottom >= yCoord)
      .find(pile => pile.top >= yCoord)
  }

  def maxHeight() = {
    val highestPoint = occupiedPositions.maxBy(_(1))
    highestPoint(1)
  }
}

class MaterialPile(val xCoord: Int, val bottom: Int) {
  var top: Int = bottom;
  if (!Piles.xCoordMap.contains(xCoord)) {
    Piles.xCoordMap(xCoord) = new ArrayBuffer
  }
  Piles.xCoordMap(xCoord) += this
  Piles.xCoordMap(xCoord).sortInPlaceWith(_.bottom < _.bottom)

  Piles.occupiedPositions.add((xCoord, bottom))

  def pileOn(): Unit = {
    top -= 1
    Piles.occupiedPositions.add((xCoord, top))
  }
}

class RockFormation(val inputString: String) {
  val corners = inputString.split(" -> ")
  val cornerCoordinates = corners.map(_.split(",").map(_.toInt))

  def buildPiece(corner1: Array[Int], corner2: Array[Int]): Unit = {
    if (corner1(0) == corner2(0)) {
      val start = Math.min(corner1(1), corner2(1))
      val end = Math.max(corner1(1), corner2(1))

      (start to end).foreach(yCoord => new MaterialPile(corner1(0), yCoord))
    } else if (corner1(1) == corner2(1)) {
      val start = Math.min(corner1(0), corner2(0))
      val end = Math.max(corner1(0), corner2(0))

      (start to end).foreach(xCoord => new MaterialPile(xCoord, corner1(1)))
    } else {
      throw new Exception("What?")
    }
  }

  for (i <- 1 until cornerCoordinates.length) {
    buildPiece(cornerCoordinates(i - 1), cornerCoordinates(i))
  }
}

class CannotDropException extends Exception
class FallsIntoAbyssException extends Exception

object Sand {
  var drops = 0
  val dropJourney: ArrayBuffer[(Int, Int)] = new ArrayBuffer
  val maxHeight = Piles.maxHeight() + 2
  var landsOn: MaterialPile = null

  def dropFrom(xCoord: Int, yCoord: Int): Unit = {
    // println(s"DROP FROM ($xCoord, $yCoord)")
    dropJourney += ((xCoord, yCoord))
    if (!Piles.xCoordMap.contains(xCoord)) {
      val newPile = new MaterialPile(xCoord, maxHeight);
      // throw new FallsIntoAbyssException;
    }
    val nextPile = Piles
      .xCoordMap(xCoord)
      .find(pile => yCoord < pile.top)

    if (nextPile.isEmpty) {
      val newPile = new MaterialPile(xCoord, maxHeight)
      landsOn = newPile;

    } else { landsOn = nextPile.get }
  }

  def drop() = {
    // println("DROP")
    var destination = (landsOn.xCoord, landsOn.top - 1)
    // println(s"Initial Destination: $destination")
    if (destination == dropJourney.last) {
      dropJourney.trimEnd(1)
      if (dropJourney.length == 0) {
        throw new CannotDropException
      }
      val newDropHeight = dropJourney.last
      dropFrom(newDropHeight(0), newDropHeight(1))
      dropJourney.trimEnd(1)

    }
    val DOWN = (0, 1)
    val LEFT = (-1, 1)
    val RIGHT = (1, 1)
    val directionOptions = Array(DOWN, LEFT, RIGHT)
    val newLocation = (dir: (Int, Int)) =>
      (destination(0) + dir(0), destination(1) + dir(1))

    while (
      directionOptions
        .map(newLocation(_))
        .map(!Piles.occupiedPositions.contains(_))
        .reduce(_ || _)
    ) {
      // println("DECIDING")
      val below = newLocation(DOWN)
      val left = newLocation(LEFT)
      val right = newLocation(RIGHT)
      if (!Piles.occupiedPositions.contains(below)) {
        // println("DOWN")
        dropFrom(destination(0), destination(1))
        destination = (landsOn.xCoord, landsOn.top - 1)
      } else if (!Piles.occupiedPositions.contains(left)) {
        // println("LEFT")
        destination = left
      } else if (!Piles.occupiedPositions.contains(right)) {
        // println("RIGHT")
        destination = right
      }
    }
    // println(destination)
    Piles.get(destination(0), destination(1)).get.pileOn()
    drops += 1
  }
}

class Debugger {
  val x_offset = Piles.occupiedPositions.minBy(_(0))
  // val y_offset = Piles.occupiedPositions.minBy(_(1))

  val width = Piles.occupiedPositions.maxBy(_(0))
  val height = Piles.occupiedPositions.maxBy(_(1))

  var symbol = '#'

  println(x_offset)
  // println(y_offset)
  println(width)
  println(height)

  val display: Array[Array[Char]] =
    Array.fill(2 + height(1), 2 + width(0) - x_offset(0))(' ')
  Piles.occupiedPositions.foreach(pos => {
    display(1 + pos(1))(1 + pos(0) - x_offset(0)) = symbol
  })
  display(1)(1 + 500 - x_offset(0)) = '+'

  symbol = 'X'

  def print() = {
    // println("I don't wanna print :(")
    Piles.occupiedPositions.foreach(pos => {
      val existingCell =
        display(1 + pos(1))(1 + pos(0) - x_offset(0))
      if (existingCell == ' ') {
        display(1 + pos(1))(1 + pos(0) - x_offset(0)) = symbol
      }
    })

    println(display.map(_.mkString("")).mkString("\n"))
    println("===========")
  }
}

class Day14(filename: String) extends BaseSolution(filename: String) {
  override def readLine(line: String): Unit = {
    new RockFormation(line)
  }

  override def part1() = {
    // println("what the hell?")
    val numRocks = Piles.occupiedPositions.size
    var canDrop = true
    Sand.dropFrom(500, 0)
    val debugger = new Debugger()
    // debugger.print()
    while (canDrop) {
      try {
        // debugger.print()
        Sand.drop()
        // Thread.sleep(250)

      } catch {
        case e: CannotDropException => {
          canDrop = false;
          // println("CAN'T DROP")
          // Sand.dropJourney.trimEnd(1)
          // val dropCoords = Sand.dropJourney.last
          // Sand.dropFrom(dropCoords(0), dropCoords(1))
        }
        case a: FallsIntoAbyssException => {
          println("ABYSS")
          canDrop = false
        }
      }
    }

    // debugger.print()
    Sand.drops.toString()
  }

  override def part2() = {
    var result = "";

    return result.toString
  }
}

@main
def solve: Unit = {
  val day = "14";
  val generate_solver = filename => new Day14(filename)

  val dirs = Array(
    // "2022/examples"
    "2022/inputs"
  )
  val filenames = dirs.map(dir => f"${dir}/day${day}.txt")
  val solvers = filenames.map(generate_solver)

  solvers.foreach(solver => {
    println(solver.filename)
    solver.solve()
    println("")
  })
}
