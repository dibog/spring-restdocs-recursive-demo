package io.github.dibog.recursivedemo.controller

import io.github.dibog.recursivedemo.entities.Node
import io.github.dibog.recursivedemo.entities.Node.Leaf
import io.github.dibog.recursivedemo.entities.Node.SubTree
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DemoController {

    @GetMapping("/", produces=[MediaType.APPLICATION_JSON_VALUE])
    fun retrieveTree(): Node {
        return SubTree(
            "$",
            SubTree(
                "l",
                Leaf("l1", "Leaf 1"),
                Leaf("l2", "Leaf 2")
            ),
            Leaf("l3", "Leaf 3")
        )
    }

}