node {
    stage 'checkout'
    checkout scm

    stage 'java settings'
    env.JAVA_HOME="${tool 'JDK1.8u91'}" // JDK1.8u91 est le nom de la task jenkins
    env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"
    env.JAVA_OPTS=""

    stage 'grails settings'
    env.GRAILS_HOME="/appl/usljksu1/grails-3.2.9"
    env.PATH="${env.GRAILS_HOME}/bin:${env.PATH}"
    env.GRAILS_OPTS="-Dhttp.proxyHost=bluelagoon -Dhttp.proxyPort=22222 -Dhttps.proxyHost=bluelagoon -Dhttps.proxyPort=22222 -Dhttp.nonProxyHosts='nexus*|localhost' -Dhttps.nonProxyHosts='nexus*|localhost'"
    sh 'grails -version'

    stage 'build'
    sh 'grails assemble'

}