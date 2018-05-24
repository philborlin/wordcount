package controllers

import java.nio.file.Files

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.Files.{SingletonTemporaryFileCreator, TemporaryFile}
import play.api.mvc.{ControllerComponents, MultipartFormData}
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Injecting}

class WordCountControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {
  "WordCountController POST" should {
    implicit lazy val materializer = app.materializer
    implicit lazy val components = inject[ControllerComponents]

    "parse a file and return some statistics" in {
      val tmpFile = java.io.File.createTempFile("prefix", "txt")
      tmpFile.deleteOnExit()
      val contents = "foo bar"
      val path = Files.write(tmpFile.toPath, contents.getBytes())

      val controller = new WordCountController(components)

      val temporaryFile = SingletonTemporaryFileCreator.create(path)
      val file = MultipartFormData.FilePart("file", tmpFile.getAbsolutePath, Some("text/plain"), temporaryFile)
      val data = MultipartFormData[TemporaryFile](dataParts = Map.empty, files = Seq(file), badParts = Seq.empty)
      val request = FakeRequest().withBody(data)

      val result = controller.wordcount(request)

      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
      val resultJson = contentAsJson(result)
      resultJson.toString() mustBe """{"totalWordCount":2,"counts":[{"word":"foo","count":1},{"word":"bar","count":1}]}"""
    }
  }
}
