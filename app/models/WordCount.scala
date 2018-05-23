package models

object WordCount {
  def count(text: String): WordCountReport = {
    ???
  }
}

case class WordCountReport(totalNumberOfWords: Int, countsOfEachOccurence: Map[String, Int])