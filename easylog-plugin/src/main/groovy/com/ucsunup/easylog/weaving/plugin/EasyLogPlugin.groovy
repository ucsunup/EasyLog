package com.ucsunup.easylog.weaving.plugin

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile

class EasyLogPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        def hasApp = project.plugins.withType(AppPlugin)
        def hasLib = project.plugins.withType(LibraryPlugin)

        if (!hasApp && !hasLib) {
            throw IllegalStateException("'android' or 'android-library' plugin required.")
        }

        project.dependencies {
            implementation 'org.aspectj:aspectjrt:1.8.13'
            implementation 'com.ucsunup.easylog:easylog-runtime:1.0.0'
            implementation 'com.ucsunup.easylog:easylog-annotations:1.0.0'
        }

        final def log = project.logger
        final def variants
        if (hasApp) {
            variants = project.android.applicationVariants
        } else {
            variants = project.android.libraryVariants
        }

        project.extensions.create("easylog", EasyLogExtension)

        variants.all { varient ->
            if (!varient.buildType.isDebuggable()) {
                log.debug("Skip not debuggable buildType: '{$varient.buildType.name}'")
                return
            } else if (!project.easylog.enabled) {
                log.debug("EasyLog is close.")
                return
            }

            JavaCompile javaCompile = varient.javaCompile
            javaCompile.doLast {
                String[] args = [
                        "-showWeaveInfo",
                        "-1.5",
                        "-inpath", javaCompile.destinationDir.toString(),
                        "-aspectpath", javaCompile.classpath.asPath,
                        "-d", javaCompile.destinationDir.toString(),
                        "-classpath", javaCompile.classpath.asPath,
                        "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)
                ]
                log.debug "ajc args: " + Arrays.toString(args)

                MessageHandler messageHandler = new MessageHandler(true)
                new Main().run(args, messageHandler)
                for (IMessage message : messageHandler.getMessages(null, true)) {
                    switch (message.getKind()) {
                        case IMessage.ABORT:
                        case IMessage.ERROR:
                        case IMessage.FAIL:
                            log.error message.message, message.thrown
                            break;
                        case IMessage.WARNING:
                            log.warn message.message, message.thrown
                            break;
                        case IMessage.INFO:
                            log.info message.message, message.thrown
                            break;
                        case IMessage.DEBUG:
                            log.debug message.message, message.thrown
                            break;
                    }
                }
            }
        }
    }
}