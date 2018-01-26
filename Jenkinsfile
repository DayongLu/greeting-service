pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        echo 'Starting build and test via Maven'
        withMaven(jdk: 'JDK8', maven: 'maven352', options: [pipelineGraphPublisher()]) {
            sh 'mvn clean package'
        }

      }
    }

    stage('deploy to PCF'){
        steps {
            pushToCloudFoundry cloudSpace: 'development', credentialsId: 'pwsCredential', organization: 'dlu-paradyme', selfSigned: true, target: 'api.run.pivotal.io'
        }
    }
  }
}