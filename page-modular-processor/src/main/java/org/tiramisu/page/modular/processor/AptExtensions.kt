package org.tiramisu.page.modular.processor

import com.squareup.kotlinpoet.*
import javax.lang.model.element.Element
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

/**
 *
 * @author felixxfwang
 * @date   2019-12-13
 */
fun Element.javaToKotlinType(): TypeName =
    asType().asTypeName().javaToKotlinType()

fun TypeName.javaToKotlinType(): TypeName {
    return if (this is ParameterizedTypeName) {
        val className = rawType.javaToKotlinType() as ClassName
        className.parameterizedBy(
            *typeArguments.map { it.javaToKotlinType() }.toTypedArray()
        )
    } else {
        if (this is WildcardTypeName) {
            return this.outTypes.first()
        }
        val className = when (toString()) {
            "java.lang.String" -> "kotlin.String"
            "java.util.List" -> "kotlin.collections.List"
            else -> toString()
        }
        return ClassName.bestGuess(className)
    }
}