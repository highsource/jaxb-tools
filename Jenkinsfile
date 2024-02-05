#!groovy

pipeline {
  
    agent any

    tools {
        // ... tell Jenkins what java version, maven version or other tools are required ...
        maven 'maven-3.9'
        jdk 'jdk-11'
    }

    options {
        // Configure an overall timeout for the build of ten hours.
        timeout(time: 8, unit: 'HOURS')
        // When we have test-fails e.g. we don't need to run the remaining steps
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
        disableConcurrentBuilds()
    }

    parameters {
        choice(name: 'Build', choices: ['snapshot', 'release-dry-run', 'release'])
        string(name: 'ReleaseVersion', trim: true, description: 'Override release version number')
        string(name: 'NextSnapshotVersion', trim: true, description: 'Override next SNAPSHOT version number')
        booleanParam(name: 'keepWorkspace', description: 'Keep workspace', defaultValue: false)
        booleanParam(name: 'mavenDebug', description: 'Enable Maven debug mode', defaultValue: false)
    }

    stages {
        stage('Initialization') {
            steps {
                echo 'Building branch ' + env.BRANCH_NAME
                echo 'Using PATH ' + env.PATH
                echo 'Git URL: ' + env.GIT_URL
                echo 'Git branch: ' + env.GIT_BRANCH
                echo 'Git commit: ' + env.GIT_COMMIT
                echo 'Git commiter: ' + env.GIT_COMMITER_NAME
            }
        }

        stage('Cleanup') {
            steps {
                echo 'Cleaning up the workspace'
                deleteDir()
            }
        }

        stage('Checkout') {
            steps {
                script {
                    echo 'Checking out branch ' + env.BRANCH_NAME
                    checkout scm

                    // Check for the temporary merge commit to test PR against target branch
                    //result = sh (script: "git log -1 | grep '^Author: Jenkins'", returnStatus: true)
                    //if (result == 0) {
                        // Check if next real commit was the release plugin
                    //    result = sh (script: "git log -2 | grep '.*\\[maven-release-plugin\\] prepare release.*'", returnStatus: true) 
                    //    if (result == 0) {
                    //        error ("'[maven-release-plugin] prepare release' spotted in git commit. Aborting.")
                    //        currentBuild.result = 'ABORTED'
                    //        error('Build aborted due to matching release commit')
                    //    }
                    //}
                }
            }
        }

        stage('Build') {
            steps {
                echo 'Building'
                sh 'mvn -Pall -U -B -e clean install -DskipTests'
            }
        }

        stage('Tests') {
            steps {
                echo 'Running tests [SKIPPED]'
                sh 'mvn -Pall -B -e -fae test'
            }
            post {
                always {
                    junit(testResults: '**/surefire-reports/*.xml', allowEmptyResults: true)
                    junit(testResults: '**/failsafe-reports/*.xml', allowEmptyResults: true)
                }
            }
        }

        stage('Deploy') {
            when {
                expression {
                    env.BRANCH_NAME ==~ /(master|jaxb-tools-3.x|jaxb-tools-2.x)/
                }
            }
            steps {
                echo 'Deploying [SKIPPED]'
//                sh 'mvn -Pfull,release -B -e deploy -Pdeploy -DskipTests'
            }
        }

        stage("Release Dry Run") {
            when {
                expression { params.Build == 'release-dry-run' }
            }
            steps {
                script {
                    if (!params.ReleaseVersion.isEmpty() && !params.NextSnapshotVersion.isEmpty()) {
                        sh "mvn -Pall,release-dry-run -B -Darguments=\"-DskipTests=true -Dmpir.skip=true\" -DignoreSnapshots=true -DdryRun=true -DskipTests=true -Dmpir.skip=true -DreleaseVersion=${params.ReleaseVersion} -DdevelopmentVersion=${params.NextSnapshotVersion} validate release:prepare release:perform"
                    } else {
                        sh "mvn -Pall,release-dry-run -B -Darguments=\"-DskipTests=true -Dmpir.skip=true\" -DignoreSnapshots=true -DdryRun=true -DskipTests=true -Dmpir.skip=true validate release:prepare release:perform"
                    }
                }
            }
        }

        stage("Release") {
            when {
                expression { params.Build == 'release' }
            }

            steps {
                script {
                    if (params.mavenDebug) {
                        if (!params.ReleaseVersion.isEmpty() && !params.NextSnapshotVersion.isEmpty()) {
                            sh "mvn -Psonatype-oss-release -X -B -Darguments=\"-DskipTests=true -Dmpir.skip=true\" -DignoreSnapshots=true -DskipTests=true -Dmpir.skip=true -DreleaseVersion=${params.ReleaseVersion} -DdevelopmentVersion=${params.NextSnapshotVersion} validate release:clean release:prepare release:perform"
                        } else {
                            sh "mvn -Psonatype-oss-release -X -B -Darguments=\"-DskipTests=true -Dmpir.skip=true\" -DignoreSnapshots=true -DskipTests=true -Dmpir.skip=true validate release:clean release:prepare release:perform"
                        }
                    }  else {
                        if (!params.ReleaseVersion.isEmpty() && !params.NextSnapshotVersion.isEmpty()) {
                            sh "mvn -Psonatype-oss-release -B -Darguments=\"-DskipTests=true -Dmpir.skip=true\" -DignoreSnapshots=true -DskipTests=true -Dmpir.skip=true -DreleaseVersion=${params.ReleaseVersion} -DdevelopmentVersion=${params.NextSnapshotVersion} validate release:clean release:prepare release:perform"
                        } else {
                            sh 'mvn -Psonatype-oss-release -B -Darguments="-DskipTests=true -Dmpir.skip=true" -DignoreSnapshots=true -DskipTests=true -Dmpir.skip=true validate release:clean release:prepare release:perform'
                        }
                    }
                }
            }
        }

        stage("Post-Cleanup") {
            when {
                expression { return !params.keepWorkspace }
            }
            steps {
                deleteDir()
            } 
        }
    }
}
