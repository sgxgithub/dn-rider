node {
    stage 'checkout'
    checkout scm

stage 'java settings'
    env.JAVA_HOME="${tool 'JDK1.8u91'}" // JDK1.8u91 est le nom de la task jenkins
    env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"
    sh 'java -version'
}