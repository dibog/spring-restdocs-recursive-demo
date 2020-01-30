package io.github.dibog.recursivedemo.entities

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME

@JsonTypeInfo(use=NAME, include=PROPERTY, property="type")
@JsonSubTypes(
    Type(value= Node.SubTree::class, name="subtree"),
    Type(value= Node.Leaf::class, name="leaf")
)
sealed class Node {
    abstract val id: String

    data class SubTree(override val id: String, val left: Node, val right: Node): Node()
    data class Leaf(override val id: String, val value: String): Node()
}
