package org.tiramisu.page.modular.processor

import com.squareup.kotlinpoet.*
import com.sun.tools.javac.code.Symbol
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror

abstract class BaseClassGenerator<T: Annotation>(protected val processingEnv: ProcessingEnvironment) {

    abstract fun generateKotlinClass(element: TypeElement, annotation: T)

    protected fun ensureKotlinType(type: TypeMirror): TypeName {
        return type.asTypeName().javaToKotlinType()
    }

    protected fun getElementType(element: Element): TypeName {
        val nullable = (element as? Symbol.VarSymbol)?.metadata?.declarationAttributes?.any { it.type.toString() == "org.jetbrains.annotations.Nullable" } ?: false
        return ensureKotlinType(element.asType()).copy(nullable)
    }

    protected fun getFunctionImplementation(it: ExecutableElement, vararg listNames: String): FunSpec {
        val parameters = it.parameters.map{ param ->
            ParameterSpec.builder(param.simpleName.toString(), getElementType(param)).build()
        }
        val paramString = parameters.joinToString(", ") { param -> param.name }
        val function = FunSpec.builder(it.simpleName.toString())
            .addModifiers(KModifier.OVERRIDE)
            .addParameters(parameters)
        if (it.returnType.kind == TypeKind.VOID) {
            listNames.forEach { listName ->
                function.addStatement("$listName.forEach { it.${it.simpleName}($paramString) }")
            }
        } else {
            val (type, defaultValue) = ensureKotlinReturnType(it.returnType)
            val defaultValueString = defaultValue?.let { " ?: $defaultValue" } ?: ""
            val listName = listNames.firstOrNull() ?: ""
            function.returns(type)
                .addStatement("return $listName.firstOrNull()?.${it.simpleName}($paramString)$defaultValueString")
        }
        return function.build()
    }

    protected fun ensureKotlinReturnType(type: TypeMirror): Pair<TypeName, Any?> {
        return ensureKotlinType(type).let { name ->
            when (name) {
                is ParameterizedTypeName -> {
                    when (name.rawType.toString()) {
                        "kotlin.collections.List" -> name to "emptyList()"
                        else -> name.copy(true) to null
                    }
                }
                else -> {
                    when (name.toString()) {
                        "kotlin.String" -> name to "\"\""
                        "kotlin.Boolean" -> name to "false"
                        "kotlin.Int" -> name to 0
                        "kotlin.Float" -> name to 0
                        "kotlin.Double" -> name to 0
                        else -> name.copy(true) to null
                    }
                }
            }
        }
    }
}