package models

object WordCount {
  def count(text: String): WordCountReport = {
    val words = text.split(" ")
    val map = words.map(_ -> 1)

    return WordCountReport(words.length, map.toMap)
  }
}

case class WordCountReport(totalNumberOfWords: Int, countsOfEachOccurence: Map[String, Int])