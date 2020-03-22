#!/usr/bin/env bash

POM_FILE=$(ls *.pom)

echo "Using POM_FILE: ${POM_FILE}"

## extract values required
VERSION=$(mvn -f ${POM_FILE} help:evaluate -Dexpression=project.version -q -DforceStdout)
GROUP_ID=$(mvn -f ${POM_FILE} help:evaluate -Dexpression=project.groupId -q -DforceStdout)
ARTIFACT_ID=$(mvn -f ${POM_FILE} help:evaluate -Dexpression=project.artifactId -q -DforceStdout)

ARTIFACT_ID_WITH_VERSION="${ARTIFACT_ID}-${VERSION}"

echo "MVN: ${GROUP_ID}:${ARTIFACT_ID}:${VERSION}"

## Upload files to nexus
mvn deploy:deploy-file \
    -DgroupId=${GROUP_ID} \
    -DartifactId=${ARTIFACT_ID} \
    -Dversion=${VERSION} \
    -Dpackaging=jar \
    -DpomFile=${ARTIFACT_ID_WITH_VERSION}.pom \
    -Dfile=${ARTIFACT_ID_WITH_VERSION}.jar \
    -Dfiles="${ARTIFACT_ID_WITH_VERSION}-javadoc.jar.asc,${ARTIFACT_ID_WITH_VERSION}-sources.jar.asc,${ARTIFACT_ID_WITH_VERSION}.jar.asc,${ARTIFACT_ID_WITH_VERSION}.pom.asc" \
    -Dclassifiers="javadoc,sources,," \
    -Dtypes="jar.asc,jar.asc,jar.asc,pom.asc" \
    -Dsources=${ARTIFACT_ID_WITH_VERSION}-sources.jar \
    -Djavadoc=${ARTIFACT_ID_WITH_VERSION}-javadoc.jar \
    -DrepositoryId=sonatype-nexus-staging-bluurr \
    -Durl=${URL}