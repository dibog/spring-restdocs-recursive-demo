package io.github.dibog.recursivedemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RecursiveDemoApplication

fun main(args: Array<String>) {
	runApplication<RecursiveDemoApplication>(*args)
}
