package controllers

import javax.inject._

import play.api.mvc.{Controller, Action}

import scala.concurrent.Future

class ApplicationController @Inject() () extends Controller {
  def index = Action.async { implicit request =>
    Future.successful(Ok(views.html.index()))
  }
}
