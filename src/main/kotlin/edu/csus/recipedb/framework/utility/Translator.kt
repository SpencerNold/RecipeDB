package edu.csus.recipedb.framework.utility

fun interface Translator<A, B> {
    fun translate(input: A): B
}