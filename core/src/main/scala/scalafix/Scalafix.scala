package scalafix

import scala.collection.immutable.Seq
import scala.meta._
import scala.meta.inputs.Input
import scala.meta.parsers.Parsed
import scalafix.rewrite.RewriteCtx
import scalafix.rewrite.ScalafixMirror
import scalafix.util.AssociatedComments
import scalafix.util.Patch
import scalafix.util.TokenList

object Scalafix {
  def fix(code: Input,
          config: ScalafixConfig,
          semanticApi: Option[ScalafixMirror]): Fixed = {
    config.parser.apply(code, config.dialect) match {
      case Parsed.Success(ast) =>
        fix(ast, config, semanticApi)
      case Parsed.Error(pos, msg, e) =>
        Fixed.Failed(Failure.ParseError(pos, msg, e))
    }
  }
  def fix(ast: Tree,
          config: ScalafixConfig,
          semanticApi: Option[ScalafixMirror]): Fixed = {
    val tokens = ast.tokens
    implicit val ctx = RewriteCtx.fromCode(ast, config, semanticApi)
    val patches = config.rewrites.flatMap(_.rewrite(ast, ctx))
    Fixed.Success(Patch.apply(ast, patches))
  }

  def fix(code: Input, config: ScalafixConfig): Fixed = {
    fix(code, config, None)
  }

  def fix(code: String, config: ScalafixConfig = ScalafixConfig()): Fixed = {
    fix(Input.String(code), config, None)
  }
}
