package models

import org.scalatest.WordSpec

class WordCountTest extends WordSpec {
  "Wordcount" when {
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
    }
  }
}