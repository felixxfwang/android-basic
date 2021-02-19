package org.tiramisu.page.modular.processor

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import org.tiramisu.annotations.ComboInterface
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement

class ComboInterfaceGenerator(env: ProcessingEnvironment) : BaseClassGenerator<ComboInterface>(env) {
    override fun generateKotlinClass(element: TypeElement, annotation: ComboInterface) {
        val interfaces = element.interfaces
        val packageName = if (annotation.packageName.isBlank()) processingEnv.elementUtils.getPackageOf(element).toString() else annotation.packageName
        val builder = TypeSpec.classBuilder(ClassName(packageName, annotation.className))
            .addSuperinterface(element.asClassName())
            .addProperty(
                PropertySpec
                    .builder("list", MUTABLE_LIST.parameterizedBy(element.asClassName()), KModifier.PRIVATE)
                    .initializer("%T<%T>()", ClassName("java.util.concurrent", "CopyOnWriteArrayList"), element.asClassName())
                    .build()
            )
        val addFunction = FunSpec.builder("add${element.simpleName}")
            .addParameter("l", element.asClassName())
            .addStatement("if (!list.contains(l)) list.add(l)")
        val clearFunction = FunSpec.builder("clear")
            .addStatement("list.clear()")

        interfaces.plus(element.asType()).forEachIndexed { index, ele ->
            processingEnv.typeUtils.asElement(ele).enclosedElements
                .filter { it.kind == ElementKind.METHOD }
                .map { it as ExecutableElement }
                .forEach {
                    builder.addFunction(getFunctionImplementation(it, "list"))
                }
        }
        builder.addFunction(addFunction.build())
        builder.addFunction(clearFunction.build())

        FileSpec.builder(packageName, annotation.className)
            .addType(builder.build())
            .build().writeTo(processingEnv.filer)
    }
}