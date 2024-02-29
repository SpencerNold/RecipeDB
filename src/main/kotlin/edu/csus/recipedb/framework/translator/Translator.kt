package edu.csus.recipedb.framework.translator

fun interface Translator<A, B> {
    fun translate(input: A): B
}