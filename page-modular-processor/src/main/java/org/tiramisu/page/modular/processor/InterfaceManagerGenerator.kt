package org.tiramisu.page.modular.processor

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import org.tiramisu.annotations.InterfaceManager
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement

class InterfaceManagerGenerator(env: ProcessingEnvironment) : BaseClassGenerator<InterfaceManager>(env) {

    override fun generateKotlinClass(element: TypeElement, annotation: InterfaceManager) {
        val interfaces = element.interfaces
        val packageName = if (annotation.packageName.isBlank()) processingEnv.elementUtils.getPackageOf(element).toString() else annotation.packageName
        val builder = TypeSpec.classBuilder(ClassName(packageName, annotation.className))
            .addModifiers(KModifier.OPEN)
        if (element.kind == ElementKind.CLASS) {
            builder.superclass(element.asClassName())
        } else if (element.kind == ElementKind.INTERFACE) {
            builder.addSuperinterface(element.asClassName())
        }

        val clearFunction = FunSpec.builder("clear")
        val recycleFunction = FunSpec.builder("recycle")
        if (element.kind == ElementKind.CLASS) {
            clearFunction.addModifiers(KModifier.OVERRIDE).addStatement("super.clear()")
            recycleFunction.addModifiers(KModifier.OVERRIDE).addStatement("super.clear()")
        } else {
            clearFunction.addModifiers(KModifier.OPEN)
            recycleFunction.addModifiers(KModifier.OPEN)
        }
        interfaces.forEachIndexed { index, ele ->
            val interfaceName = ele.asTypeName()
            val interName = (interfaceName as ClassName).simpleName
            builder.addProperty(getProperty("m$interName", interfaceName))
            if (annotation.recyclable) {
                builder.addProperty(getProperty("mUnrecyled$interName", interfaceName))
            }
            val addFunction = FunSpec.builder("add$interName")
                .addParameter("l", interfaceName)
            if (annotation.recyclable) {
                addFunction.addParameter("canRecycle", BOOLEAN)
                    .addCode("""
                        if (canRecycle) {
                            if (!m$interName.contains(l)) m$interName.add(l)
                        } else {
                            if (!mUnrecyled$interName.contains(l)) mUnrecyled$interName.add(l)
                        }
                    """.trimIndent(), COPY_ON_WRITE_LIST, interfaceName)
            } else {
                addFunction.addStatement("if (!m$interName.contains(l)) m$interName.add(l)")
            }
            builder.addFunction(addFunction.build())
            clearFunction.addStatement("m$interName.clear()")
            if (annotation.recyclable) {
                clearFunction.addStatement("mUnrecyled$interName.clear()")
                recycleFunction.addStatement("m$interName.clear()")
            }

            processingEnv.typeUtils.asElement(ele).enclosedElements
                .filter { it.kind == ElementKind.METHOD }
                .map { it as ExecutableElement }
                .forEach {
                    if (annotation.recyclable) {
                        builder.addFunction(getFunctionImplementation(it, "m$interName", "mUnrecyled$interName"))
                    } else {
                        builder.addFunction(getFunctionImplementation(it, "m$interName"))
                    }
                }
        }
        builder.addFunction(clearFunction.build())
        if (annotation.recyclable) {
            builder.addFunction(recycleFunction.build())
        }

        FileSpec.builder(packageName, annotation.className)
            .addType(builder.build())
            .build().writeTo(processingEnv.filer)
    }

    private fun getProperty(name: String, interfaceName: TypeName): PropertySpec {
        return PropertySpec
            .builder(name, MUTABLE_LIST.parameterizedBy(interfaceName), KModifier.PRIVATE)
            .delegate("lazy { %T<%T>() }", COPY_ON_WRITE_LIST, interfaceName)
            .build()
    }

    private val COPY_ON_WRITE_LIST = ClassName("java.util.concurrent", "CopyOnWriteArrayList")

}