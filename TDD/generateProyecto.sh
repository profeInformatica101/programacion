#!/bin/bash
# Da permisos de ejecución 'chmod+x' primero 
mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.5 -DgroupId=es.iescamas.market -DartifactId=marketapp -Dversion=1.0.0-SNAPSHOT -DjavaCompilerVersion=17  -DjunitVersion=5.11.0  -DinteractiveMode=false

