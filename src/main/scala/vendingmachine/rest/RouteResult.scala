package vendingmachine.rest

import scala.collection.parallel.immutable
import com.sun.deploy.net.HttpResponse

sealed trait RouteResult
object RouteResult {
  final case class Complete(response: HttpResponse) extends RouteResult
//  final case class Rejected(rejections: immutable.Seq[Rejection]) extends RouteResult
}