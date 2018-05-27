package models

import org.scalatest.WordSpec

import scala.io.Source

class WordCountSpec extends WordSpec {
  "WordCount" when {
    "given a letter" should {
      "report 1 word and 1 occurrence of that word" in {
        val word = "a"
        val report = WordCountReport(1, Map(word -> 1))
        assert(WordCount.count(word) === report)
      }
    }

    "given two different words" should {
      "report 2 words and 1 occurrence of each word" in {
        val text = "foo bar"
        val report = WordCountReport(2, Map("foo" -> 1, "bar" -> 1))
        assert(WordCount.count(text) === report)
      }
    }

    "given two of the same word" should {
      "report 2 words and 2 occurrence of the word" in {
        val text = "foo foo"
        val report = WordCountReport(2, Map("foo" -> 2))
        assert(WordCount.count(text) === report)
      }
    }

    "given a simple line break" should {
      "split the word" in {
        val text = "foo\nbar"
        val report = WordCountReport(2, Map("foo" -> 1, "bar" -> 1))
        assert(WordCount.count(text) === report)
      }
    }

    "given special characters" should {
      "split the word" in {
        val text = "foo!foo\"foo#foo$foo%foo&foo(foo)foo*foo+foo,foo.foo/foo:foo;foo<foo=foo>foo?foo@foo[foo\\foo]foo^foo`foo{foo|foo}foo~foo"
        val report = WordCountReport(30, Map("foo" -> 30))
        assert(WordCount.count(text) === report)
      }

      "except when there is a hyphenated word" in {
        val text = "dog-friendly"
        val report = WordCountReport(1, Map(text -> 1))
        assert(WordCount.count(text) === report)
      }

      "or a contraction" in {
        val text = "don't"
        val report = WordCountReport(1, Map(text -> 1))
        assert(WordCount.count(text) === report)
      }
    }

    "given multiple whitespace characters in a row" should {
      "NOT create additional words" in {
        val text = "foo  bar"
        val report = WordCountReport(2, Map("foo" -> 1, "bar" -> 1))
        assert(WordCount.count(text) === report)
      }
    }

    "given two of the same word with different capitalization" should {
      "count as one word with two occurrences" in {
        val text = "Foo foo"
        val report = WordCountReport(2, Map("foo" -> 2))
        assert(WordCount.count(text) === report)
      }
    }

    "given a large block of text (10MB)" should {
      val stream = getClass.getClassLoader.getResourceAsStream("ipsum.txt")
      val text = Source.fromInputStream(stream).mkString

      "perform within a couple seconds" in {
        val t0 = System.nanoTime()
        WordCount.count(text)
        val t1 = System.nanoTime()
        val elapsed = t1 - t0
        assert(elapsed < 2000000000)
      }

      "parse correctly" in {
        val report = WordCountReport(1656000, ipsumMap)
        assert(WordCount.count(text) === report)
      }
    }
  }

  val ipsumMap: Map[String, Int] = {
    Map(
      "lorem" -> 24000,
      "ipsum" -> 24000,
      "dolor" -> 24000,
      "sit" -> 24000,
      "amet" -> 24000,
      "consectetur" -> 24000,
      "adipiscing" -> 24000,
      "elit" -> 24000,
      "sed" -> 24000,
      "do" -> 24000,
      "eiusmod" -> 24000,
      "tempor" -> 24000,
      "incididunt" -> 24000,
      "ut" -> 72000,
      "labore" -> 24000,
      "et" -> 24000,
      "dolore" -> 48000,
      "magna" -> 24000,
      "aliqua" -> 24000,
      "enim" -> 24000,
      "ad" -> 24000,
      "minim" -> 24000,
      "veniam" -> 24000,
      "quis" -> 24000,
      "nostrud" -> 24000,
      "exercitation" -> 24000,
      "nisi" -> 24000,
      "aliquip" -> 24000,
      "ex" -> 24000,
      "ea" -> 24000,
      "commodo" -> 24000,
      "consequat" -> 24000,
      "duis" -> 24000,
      "aute" -> 24000,
      "irure" -> 24000,
      "dolor" -> 48000,
      "in" -> 72000,
      "reprehenderit" -> 24000,
      "voluptate" -> 24000,
      "velit" -> 24000,
      "esse" -> 24000,
      "cillum" -> 24000,
      "eu" -> 24000,
      "fugiat" -> 24000,
      "nulla" -> 24000,
      "pariatur" -> 24000,
      "excepteur" -> 24000,
      "sint" -> 24000,
      "occaecat" -> 24000,
      "cupidatat" -> 24000,
      "non" -> 24000,
      "proident" -> 24000,
      "sunt" -> 24000,
      "culpa" -> 24000,
      "qui" -> 24000,
      "officia" -> 24000,
      "deserunt" -> 24000,
      "mollit" -> 24000,
      "anim" -> 24000,
      "id" -> 24000,
      "est" -> 24000,
      "laborum" -> 24000,
      "laboris" -> 24000,
      "ullamco" -> 24000
    )
  }
}