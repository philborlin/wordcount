package controllers

import java.nio.file.{Files, Path, Paths}

import javax.inject._
import play.api.mvc._

import scala.collection.JavaConverters._
import models.{WordCount, WordCountReport}
import play.api.http.HttpEntity
import play.api.libs.Files.TemporaryFile
import play.api.libs.json._

@Singleton
class WordCountController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def wordcount: Action[MultipartFormData[TemporaryFile]] = Action(parse.multipartFormData) { request =>
    request.body.files.headOption.map { file =>
      val path = Paths.get(file.filename)
      val text = readFile(path)
      val report = WordCount.count(text)
      val json = reportToJson(report)

      Ok(json)
    }.getOrElse {
      val headers = Map("error" -> request.body.files.mkString("\n"))
      new Result(header = ResponseHeader(500, headers), body = HttpEntity.NoEntity)

//      InternalServerError("Could not find file in multipart upload")
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
