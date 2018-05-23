package models

object WordCount {
  def count(text: String): WordCountReport = {
    def go(text: Array[String], map: Map[String, Int]): Map[String, Int] = {
      if (text.length > 0) return go(text.tail, addToMap(text.head, map))
      else return map
    }

    val words = text.split("[^A-Za-z0-9_'-]")
    val map = go(words, Map())

    return WordCountReport(words.length, map.toMap)
  }

  def addToMap(word: String, map: Map[String, Int]): Map[String, Int] = {
    val wordCount = map.getOrElse(word, 0) + 1
    return map + (word -> wordCount)
  }
}

case class WordCountReport(totalNumberOfWords: Int, countsOfEachOccurence: Map[String, Int])