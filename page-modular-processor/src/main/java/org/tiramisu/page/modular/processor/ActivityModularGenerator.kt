package org.tiramisu.page.modular.processor

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import org.tiramisu.page.modular.annotations.ActivityModular
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement

class ActivityModularGenerator(env: ProcessingEnvironment) : BaseClassGenerator<ActivityModular>(env) {

    companion object {
        private const val BASE_PACKAGE_NAME = "org.tiramisu.page.modular"
        private const val ACTIVITY_PACKAGE_NAME = "org.tiramisu.page.modular.activity"
    }

    override fun generateKotlinClass(element: TypeElement, annotation: ActivityModular) {
        val interfaces = element.interfaces
        val packageName = if (annotation.packageName.isBlank()) processingEnv.elementUtils.getPackageOf(element).toString() else annotation.packageName
        val builder = TypeSpec.classBuilder(ClassName(packageName, annotation.className))
            .superclass(ClassName(ACTIVITY_PACKAGE_NAME, "ActivityModuleManager"))
            .addSuperinterface(element.asClassName())
        val addFunction = FunSpec.builder("addModule")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("module", ClassName(BASE_PACKAGE_NAME, "IPageModule"))
            .addStatement("super.addModule(module)")
        val clearFunction = FunSpec.builder("clear")
            .addModifiers(KModifier.OVERRIDE)
            .addStatement("super.clear()")
        interfaces.forEachIndexed { index, ele ->
            val interfaceName = ele.asTypeName()
            builder.addProperty(
                PropertySpec
                    .builder("modules$index", MUTABLE_LIST.parameterizedBy(interfaceName), KModifier.PRIVATE)
                    .initializer("ArrayList<%T>()", interfaceName)
                    .build()
            )
            addFunction.addStatement("if (module is %T) modules$index.add(module)", interfaceName)
            clearFunction.addStatement("modules$index.clear()")
            processingEnv.typeUtils.asElement(ele).enclosedElements
                .filter { it.kind == ElementKind.METHOD }
                .map { it as ExecutableElement }
                .forEach {
                    builder.addFunction(getFunctionImplementation(it, "modules$index"))
                }
        }
        builder.addFunction(addFunction.build())
        builder.addFunction(clearFunction.build())

        FileSpec.builder(packageName, annotation.className)
            .addType(builder.build())
            .build().writeTo(processingEnv.filer)
    }

}