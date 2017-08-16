node {
    stage 'checkout'
    checkout scm

     stage 'versioning'
     sh 'echo ${BUILD_NUMBER}'
     sh 'sed -i -e "s/version \\"1.0*\\"/version \\"1.0-${BUILD_NUMBER}\\"/g" ./build.gradle'
     sh 'cat ./build.gradle'

    stage 'build'
    env.JAVA_HOME="${tool 'JDK1.8u91'}" // JDK1.8u91 est le nom de la task jenkins
    env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"
    env.JAVA_OPTS=""
    sh 'java -version'
    env.GRAILS_HOME="/appl/usljksu1/grails-3.2.9"
    env.PATH="${env.GRAILS_HOME}/bin:${env.PATH}"
    env.GRAILS_OPTS="-Dhttp.proxyHost=bluelagoon -Dhttp.proxyPort=22222 -Dhttps.proxyHost=bluelagoon -Dhttps.proxyPort=22222 -Dhttp.nonProxyHosts='nexus*|localhost' -Dhttps.nonProxyHosts='nexus*|localhost'"
    sh 'grails -version'
    sh 'grails clean'
    sh 'grails package'

    stage 'deploy'
    sh 'scp build/libs/dn-rider-build-1.0-${BUILD_NUMBER}.war wasktcu1@crisdorgasmes-bck:dn-rider/dn-rider-0.1.war'
    sh 'scp launch.sh wasktcu1@crisdorgasmes-bck:dn-rider/launch.sh'
    sh 'ssh wasktcu1@crisdorgasmes-bck chmod +x dn-rider/launch.sh'

    stage 'run'
    sh 'ssh wasktcu1@crisdorgasmes-bck dn-rider/launch.sh'
}