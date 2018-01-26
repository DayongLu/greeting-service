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

    stage('upload-to-artifactory') {
          steps {
            echo 'Upload to Artifactory'
            def server = Artifactory.newServer url: 'http://ec2-18-217-115-41.us-east-2.compute.amazonaws.com:8081/artifactory', username: 'admin', password: '12345678'
            def uploadSpec = """{
              "files": [
                {
                  "pattern": "target/*.jar",
                  "target": "libs-snapshot-local/com/dlu/llc/"
                }
             ]
            }"""

            server.upload(uploadSpec)
          }
    }

    stage('deploy to PCF'){
        steps {
            pushToCloudFoundry cloudSpace: 'development', credentialsId: 'pwsCredential', organization: 'dlu-paradyme', pluginTimeout: 300, selfSigned: true, target: 'api.run.pivotal.io'

        }
    }
  }
}