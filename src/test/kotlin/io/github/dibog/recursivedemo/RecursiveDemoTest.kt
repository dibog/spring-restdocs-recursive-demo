package io.github.dibog.recursivedemo

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.payload.PayloadDocumentation.beneathPath
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
@TestInstance(PER_CLASS)
class RecursiveDemoTest {
    private lateinit var mockMvc: MockMvc

    @Autowired
    private val context: WebApplicationContext? = null

    @BeforeEach
    fun setUp(
        webApplicationContext: WebApplicationContext,
        restDocumentation: RestDocumentationContextProvider
    )
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply<DefaultMockMvcBuilder>(documentationConfiguration(restDocumentation))
            .build()
    }

    @Test
    fun exampleTest() {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk)
            .andDo(document("example-test",
                responseFields(
                    fieldWithPath("id").description("The id of the root node"),
                    fieldWithPath("type").description("The type of the root node")),
                responseFields(beneathPath("left.left").withSubsectionId("leaf"),
                    fieldWithPath("id").description("ID of the node"),
                    fieldWithPath("type").description("Type of the node. Always 'leaf' for leaf nodes"),
                    fieldWithPath("value").description("Value of the node")),
                responseFields(beneathPath("left").withSubsectionId("subtree"),
                    fieldWithPath("id").description("ID of the node"),
                    fieldWithPath("type").description("Type of the node. Always 'subtree' for nodes with children"),
                    subsectionWithPath("left").description("Left-hand child node. Either a subtree or a leaf"),
                    subsectionWithPath("right").description("Right-hand child node. Either a subtree or a leaf"))
            ))

        // Test fails due to not documented properties. How can the recursive schema be documented?
    }
}