package com.onedsol.calculator.impl

import scala.util.parsing.combinator.JavaTokenParsers

object Calculator extends JavaTokenParsers {

  def expr: Parser[Double] = term ~ rep(("+" | "-") ~ term) ^^ eval

  def term: Parser[Double] = factor ~ rep(("*" | ":") ~ term) ^^ eval

  def factor: Parser[Double] = (floatingPointNumber ^^ (_.toDouble)
    | "(" ~> expr <~ ")"
    )

  def eval: Double ~ List[String ~ Double] => Double = {
    case v ~ list => (v /: list) {
      case (op1, "+" ~ op2) => op1 + op2
      case (op1, "-" ~ op2) => op1 - op2
      case (op1, "*" ~ op2) => op1 * op2
      case (op1, ":" ~ op2) => op1 / op2
    }
  }

  def compute(args: String) = {
    parseAll(expr, args).get
  }

}
