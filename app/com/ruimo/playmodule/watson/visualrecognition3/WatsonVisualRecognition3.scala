package com.ruimo.playmodule.watson.visualrecognition3

import java.nio.file.Path
import javax.inject.Inject

import akka.stream.scaladsl.{FileIO, Source}
import play.api.libs.ws._
import play.api.{Configuration, Logger}
import play.api.libs.json.{JsPath, Json, Reads}
import play.api.mvc.MultipartFormData.FilePart

import scala.annotation.tailrec
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}
import play.api.libs.functional.syntax._

class WatsonVisualRecognition3 @Inject() (ws: WSClient, conf: Configuration) {
  val Url: String = stripTrailingSlash(
    conf.getString("watson.visualRecognition.url").getOrElse(
      throw new Error("Cannot find 'watson.visualRecognition.url' in application.conf")
    )
  )
  val ApiKey = conf.getString("watson.visualRecognition.apiKey").getOrElse(
    throw new Error("Cannot find 'watson.visualRecognition.url' in application.conf")
  )
  val Ver = "v3"

  @tailrec private def stripTrailingSlash(path: String): String =
    if (path.isEmpty) path
    else if (path.charAt(path.length - 1) == '/')
      stripTrailingSlash(path.substring(0, path.length - 1))
    else
      path

  def postClassifyImage(
    apiKey: String,
    apiVersion: String,
    imageFile: Path,
    additionalJsonParm: Option[Path],
    acceptLang: Option[AcceptLanguage]
  ): Future[Try[ClassifyResponse]] = {
    acceptLang.foldLeft(
      ws.url(Url + "/v3/classify?api_key=" + apiKey + "&version=" + apiVersion)
    ) { (req, lang) => req.withHeaders("Accept-Language" -> lang.code) }.post(
      Source(
        List(
          FilePart(
            "image_file",
            imageFile.getFileName().toString,
            None,
            FileIO.fromFile(imageFile.toFile)
          )
        ) ++ additionalJsonParm.map { jsonf =>
          List(
            FilePart(
              "parameters",
              jsonf.getFileName().toString,
              Some("application/json"),
              FileIO.fromFile(jsonf.toFile)
            )
          )
        }.getOrElse(Nil)
      )
    ).map(ClassifyResponse.fromResponse)
  }
}

object ClassifyResponse {
  implicit val classifiedClassReads: Reads[ClassifiedClass] = (
    (JsPath \ "class").read[String] and
      (JsPath \ "score").read[Double]
    )(ClassifiedClass)

  implicit val classifierReads: Reads[Classifier] = (
    (JsPath \ "classes").read[Seq[ClassifiedClass]] and
      (JsPath \ "classifier_id").read[String] and
      (JsPath \ "name").read[String]
    )(Classifier)

  implicit val classifierResponseImageReads: Reads[ClassifyResponseImage] = (
    (JsPath \ "classifiers").read[Seq[Classifier]] and
      (JsPath \ "image").readNullable[String] and
      (JsPath \ "resolved_url").readNullable[String] and
      (JsPath \ "source_url").readNullable[String]
    )(ClassifyResponseImage)

  implicit val classifierResponseReads: Reads[ClassifyResponse] = (
    (JsPath \ "images").read[Seq[ClassifyResponseImage]] and
      (JsPath \ "images_processed").read[Int]
    )(ClassifyResponse.apply _)

  def fromResponse(resp: WSResponse): Try[ClassifyResponse] = {
    Logger.info("Watson visual recognition status = " + resp.status + ", statusText = '" + resp.statusText + "'")
    if (resp.status != 200) Failure(new WatsonException("Watson Visual Recognition", resp.status, resp.statusText))
    else Success(
      ClassifyResponse.fromString(resp.body)
    )
  }

  def fromString(body: String): ClassifyResponse = Json.parse(body).as[ClassifyResponse]
}

case class ClassifyResponse(
  images: Seq[ClassifyResponseImage],
  processedImageCount: Int
)

case class ClassifyResponseImage(
  classifiers: Seq[Classifier],
  fileName: Option[String],
  resolvedUrl: Option[String],
  sourceUrl: Option[String]
)

case class Classifier(
  classes: Seq[ClassifiedClass],
  classifierId: String,
  name: String
)

case class ClassifiedClass(
  className: String,
  score: Double
)