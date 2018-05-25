package controllers

import java.nio.file.{Files, Path, Paths}

import javax.inject._
import models.{WordCount, WordCountReport}
import play.api.libs.Files.TemporaryFile
import play.api.libs.json._
import play.api.mvc._

import scala.collection.JavaConverters._

@Singleton
class WordCountController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def wordcount: Action[MultipartFormData[TemporaryFile]] = Action(parse.multipartFormData) { request =>
    request.body.files.headOption.map { file =>
      val filename = Paths.get(file.filename).getFileName
      val path = Files.createTempFile(filename.toString, "text")
      file.ref.moveTo(path, replace = true)

      val text = readFile(path)
      val report = WordCount.count(text)
      val json = reportToJson(report)

      Ok(json)
    }.getOrElse {
      InternalServerError("Could not find file in multipart upload")
    }
  }

  // Scala Source leaves the file open so we'll use Java Files instead
  def readFile(path: Path): String = Files.readAllLines(path).asScala.mkString("\n")

  def reportToJson(report: WordCountReport): JsObject = {
    val counts = report.countsOfEachOccurence.map { case (word, count) =>
      JsObject(Seq("word" -> JsString(word), "count" -> JsNumber(count)))
    }

    JsObject(Seq(
      "totalWordCount" -> JsNumber(report.totalNumberOfWords),
      "counts" -> JsArray(counts.toSeq)
    ))
  }
}
