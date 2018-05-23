package models

import scala.collection.mutable

object WordCount {
  def count(text: String): WordCountReport = {
    val words = text.toLowerCase.split("[^A-Za-z0-9_'-]+")
    val map = mutable.Map[String, Int]()

    words.foreach { word =>
      val wordCount = map.getOrElse(word, 0) + 1
      map += (word -> wordCount)
    }

    return WordCountReport(words.length, map.toMap)
  }
}

case class WordCountReport(totalNumberOfWords: Int, countsOfEachOccurence: Map[String, Int])