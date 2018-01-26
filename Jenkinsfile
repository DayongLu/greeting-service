node('master') {
    def server
    def uploadSpec
    def buildInfo
    def rtMaven

    stage('Artifactory configuration') {

        server = Artifactory.newServer url: 'http://ec2-18-217-115-41.us-east-2.compute.amazonaws.com:8081/artifactory', username: 'admin', password: '1234qwer'

        rtMaven = Artifactory.newMavenBuild()
        rtMaven.tool = 'maven352'
        rtMaven.deployer releaseRepo: 'libs-release-local', snapshotRepo: 'libs-snapshot-local', server: server
        rtMaven.resolver releaseRepo: 'libs-release', snapshotRepo: 'libs-snapshot', server: server
        rtMaven.deployer.deployArtifacts = false
        buildInfo = Artifactory.newBuildInfo()

    }

    stage('Test') {
        rtMaven.run pom: 'pom.xml', goals: 'clean test'
    }

    stage('Install') {
        rtMaven.run pom: 'pom.xml', goals: 'install', buildInfo: buildInfo
    }

    stage('Deploy') {
        rtMaven.deployer.deployArtifacts buildInfo
    }

    stage('Publish build info') {
        server.publishBuildInfo buildInfo
    }

    stage('deploy to PCF') {
        steps {
            pushToCloudFoundry cloudSpace: 'development', credentialsId: 'pwsCredential', organization: 'dlu-paradyme', pluginTimeout: 300, selfSigned: true, target: 'api.run.pivotal.io'

        }
    }

}