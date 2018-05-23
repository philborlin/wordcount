package models

object WordCount {
  def count(text: String): WordCountReport = {
    return WordCountReport(1, Map(text -> 1))
  }
}

case class WordCountReport(totalNumberOfWords: Int, countsOfEachOccurence: Map[String, Int])