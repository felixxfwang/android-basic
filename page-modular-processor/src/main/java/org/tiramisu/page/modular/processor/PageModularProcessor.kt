package org.tiramisu.page.modular.processor

import com.google.auto.service.AutoService
import org.tiramisu.page.modular.annotations.ActivityModular
import org.tiramisu.annotations.ComboInterface
import org.tiramisu.page.modular.annotations.FragmentModular
import org.tiramisu.annotations.InterfaceManager
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

/**
 *
 * @author felixxfwang
 * @date   2019-12-10
 */
@AutoService(Processor::class)
class PageModularProcessor: AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(
            FragmentModular::class.java.canonicalName,
            ActivityModular::class.java.canonicalName
        )
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
        if (annotations?.isEmpty() == true) {
            return false
        }
        generateKotlinClass(roundEnv, FragmentModular::class.java, FragmentModularGenerator(processingEnv))
        generateKotlinClass(roundEnv, ActivityModular::class.java, ActivityModularGenerator(processingEnv))
        generateKotlinClass(roundEnv, ComboInterface::class.java, ComboInterfaceGenerator(processingEnv))
        generateKotlinClass(roundEnv, InterfaceManager::class.java, InterfaceManagerGenerator(processingEnv))
        return true
    }

    private fun <T: Annotation> generateKotlinClass(roundEnv: RoundEnvironment, annotationClass: Class<T>, generator: BaseClassGenerator<T>): Boolean {
        roundEnv.getElementsAnnotatedWith(annotationClass)
            .forEach { element ->
                if (element.kind != ElementKind.INTERFACE && element.kind != ElementKind.CLASS) {
                    processingEnv.messager.printMessage(Diagnostic.Kind.ERROR,
                        String.format("Only interface can be annotated with @%s", annotationClass.simpleName),
                        element)
                    return false // 退出处理
                }
                val annotation: T = element.getAnnotation(annotationClass)
                (element as? TypeElement)?.let {
                    generator.generateKotlinClass(element, annotation)
                }
            }
        return true
    }
}
