package models

import org.scalatest.WordSpec

class WordCountTest extends WordSpec {
  "Wordcount" when {
    "given a letter" should {
      "should report 1 word and 1 occurrence of that word" in {
        val word = "a"
        val report = WordCountReport(1, Map(word -> 1))
        assert(WordCount.count(word) === report)
      }
    }
  }
}